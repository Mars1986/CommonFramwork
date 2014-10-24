package com.mars.framework.utils;

import com.mars.framework.system.BaseApplication;

public class DeviceUtil {

    /**
     * 转换dp到像素
     * @param dp
     * @return
     */
    public static int dpToPx(int dp){
//    	final float densityDpi = LauncherApplication.getInstance()
//				.getResources().getDisplayMetrics().densityDpi;
//		final float scaledDensity = LauncherApplication.getInstance()
//				.getResources().getDisplayMetrics().scaledDensity;
	
		// SohuLog.d(TAG, "scale : " + scale);
		// SohuLog.d(TAG, "densityDpi : " + densityDpi);
		// SohuLog.d(TAG, "scaledDensity : " + scaledDensity);
    	final float scale = BaseApplication.getInstance().getResources()
				.getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
    }

}
