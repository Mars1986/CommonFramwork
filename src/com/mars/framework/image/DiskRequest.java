package com.mars.framework.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mars.framework.log.LogManager;

/**
 * 
 * @author Mars
 *
 */
public class DiskRequest {
	
	private final static String TAG = "DiskRequest";

	private String mFileSuffix;

	private File mRootDirectory;

	private static final String PATH_MID = "/";

	/**
	 * 
	 * @param diskPath
	 * @param fileSuffix: this parameter can be null
	 */
	protected DiskRequest(File rootDirectory, String fileSuffix) {
		this.mRootDirectory = rootDirectory;
		this.mFileSuffix = fileSuffix == null ? "" : fileSuffix;
	}

	/**
	 * 读取客户端本地图片生成byte数组
	 * 
	 * @param imageUrl
	 * @return
	 */
	protected byte[] query(String imageUrl) {
		if (imageUrl == null) {
			return null;
		}
		File file = new File(getImageAbsolutePath(imageUrl));
		if (!file.exists()) {
			return null;
		}
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(file);
			data = new byte[(int) file.length()];
			inputStream.read(data);
		} catch (IOException e) {
			LogManager.e(TAG, "Query image from disk IO error!");
		} catch (RuntimeException e) {
			LogManager.e(TAG, "Query image from disk fail!", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return data;
	}

	/**
	 * 读取客户端本地图片生成Bitmap
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @return
	 */
	protected Bitmap query(String imageUrl, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		if (imageUrl == null) {
			return null;
		}
		String imagePath = getImageAbsolutePath(imageUrl);
		File file = new File(imagePath);
		if (!file.exists()) {
			return null;
		}
		if (width <= 0 || height <= 0) {
			try {
				this.setOptions(options);
				return BitmapFactory.decodeFile(imagePath, options);
			} catch (OutOfMemoryError e) {
				LogManager.e(TAG, "DecodeFile error! OutOfMemoryError.");
				return null;
			}
		}
		options.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeFile(imagePath, options);
		} catch (OutOfMemoryError e) {
			LogManager.e(TAG, "DecodeFile error! OutOfMemoryError.");
			return null;
		}
		options.inSampleSize = ImageUtil.calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		this.setOptions(options);
		return BitmapFactory.decodeFile(imagePath, options);
	}
	
	private void setOptions(BitmapFactory.Options options) {
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
	}

	/**
	 * 在客户端本地磁盘存储图片
	 * 
	 * @param name
	 * @param image
	 * @return
	 */
	protected synchronized boolean save(String imageUrl, byte[] image) {
		if (imageUrl == null || image == null) {
			return false;
		}
		if (!mRootDirectory.exists()) {
			mRootDirectory.mkdirs();
		}
		String name = this.ToBKDRHash(imageUrl) + "";
		File file = new File(mRootDirectory, name);
		File fileWithSuff = new File(mRootDirectory, name + mFileSuffix);
		if (fileWithSuff.exists()) {
			return true;
		} else if (file.exists()) {
			if (!file.delete()) {
				return false;
			}
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(image);
		} catch (IOException e) {
			LogManager.e(TAG, "Save image to disk IO error!");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return file.renameTo(fileWithSuff);
	}

	/**
	 * 从客户端本地磁盘删除图片
	 * 
	 * @param name
	 * @return
	 */
	protected boolean delete(String name) {
		File file = new File(mRootDirectory, name);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * 图片URL转换成数字串的Hash算法
	 * 
	 * @param imageUrl
	 * @return
	 */
	private int ToBKDRHash(String imageUrl) {
		int seed = 131;
		int hash = 0;
		int urlSize = imageUrl.length();
		for (int i = 0; i < urlSize; i++) {
			hash = (hash * seed) + imageUrl.charAt(i);
		}
		return (hash & 0x7FFFFFFF);
	}

	/**
	 * 根据图片URl生成本地保存路径
	 * 
	 * @param imageUrl
	 * @return
	 */
	private String getImageAbsolutePath(String imageUrl) {
		StringBuilder path = new StringBuilder();
		path.append(mRootDirectory.getAbsolutePath()).append(PATH_MID)
				.append(this.ToBKDRHash(imageUrl)).append(mFileSuffix);
		return path.toString();
	}

}
