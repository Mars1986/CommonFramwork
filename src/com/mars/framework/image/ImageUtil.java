package com.mars.framework.image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageUtil {
	
	private static final String TAG = "ImageUtil";

	/**
	 * 根据原始图片的Options(尺寸数据)，计算图片的压缩比
	 * 
	 * @param optionsSource
	 * @param width
	 * @param height
	 * @return
	 */
	public static int calculateInSampleSize(Options optionsSource, int width, int height) {
		int heightSource = optionsSource.outHeight;
		int widthSource = optionsSource.outWidth;
		float inSampleSize = 1;
		if (width <= 0 || height <= 0) {
			return 1;
		} else if (heightSource > height || widthSource > width) {
			float heightRatio = (float) heightSource / (float) height;
			float widthRatio = (float) widthSource / (float) width;
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return getCompressValue(inSampleSize);
	}

	/**
	 * 从byte数组转换成适当大小的bitmap
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap decodeBitmapFromByte(byte[] image, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(image, 0, image.length, options);
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap iamge = null;
		try {
			iamge = BitmapFactory.decodeByteArray(image, 0, image.length, options);
		} catch (OutOfMemoryError e) {
			Logger.e(TAG, "DecodeByteArray error! OutOfMemoryError.");
			return null;
		}
		return iamge;
	}
	
	/**
	 * Bitmap图片资源释放
	 * 
	 * @param image
	 */
	public static void imageRecycle(Bitmap image) {
		try {
			if (image != null && !image.isRecycled()) {
				Logger.w(TAG, "---Image recycle!---");
				image.recycle();
				image = null;
			}
		} catch (RuntimeException e) {
			Logger.e(TAG, "Recycle image fail!");
		}
	}
	
	/**
	 * 获取最佳的压缩倍数
	 * 
	 * @param realValule
	 * @return
	 */
	private static int getCompressValue(float realValule) {
		// Max is Math.pow(2, 4);
		int max = 4;
		if (realValule <= 1) {
			return 1;
		}
		int result = (int) Math.pow(2, 4);
		for (int i = 1; i < max; i++) {
			float multi = (float) Math.pow(2, i);
			if (realValule <= multi) {
				result = (int) multi;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 网络下载图片
	 * 
	 * @param urlStr
	 * @return
	 * @throws IOException
	 */
	public static byte[] download(String urlStr){
		if (urlStr == null) {
			return null;
		}
		byte[] date = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.connect();
			inputStream = conn.getInputStream();
			date = readStream(inputStream);
		} catch (OutOfMemoryError e) {
			Logger.e(TAG, "Download image OOM!");
		} catch (IOException e) {
			Logger.e(TAG, "Download image IO error!", e);
		} catch (RuntimeException e) {
			Logger.e(TAG, "Download image fail!", e);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return date;
	}

	/**
	 * 从IO读取图片数据保存byte数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static byte[] readStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		byte[] data = null;
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			data = outSteam.toByteArray();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outSteam != null) {
				outSteam.close();
			}
		}
		return data;
	}

}
