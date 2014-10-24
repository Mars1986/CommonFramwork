package com.mars.framework.image;

/**
 * LOG生成器
 * 
 * @author Jason
 */
public class Logger {

	private static final String FUNCTION = "SOHU_BOX_CACHE";

	private static final String LOG_DELIMITER = "|";

	private static int LOG_LEVEL = 5;

	public static void setLogLevel(int level) {
		LOG_LEVEL = level;
	}

	public static void v(String location, String msg) {
		if (LOG_LEVEL >= 5) {
			android.util.Log.v(FUNCTION, buildLog(location, msg));
		}
	}

	public static void d(String location, String msg) {
		if (LOG_LEVEL >= 4) {
			android.util.Log.d(FUNCTION, buildLog(location, msg));
		}
	}

	public static void i(String location, String msg) {
		if (LOG_LEVEL >= 3) {
			android.util.Log.i(FUNCTION, buildLog(location, msg));
		}
	}

	public static void w(String location, String msg) {
		if (LOG_LEVEL >= 2) {
			android.util.Log.w(FUNCTION, buildLog(location, msg));
		}
	}

	public static void w(String location, String msg, Throwable tr) {
		if (LOG_LEVEL >= 2) {
			android.util.Log.w(FUNCTION, buildLog(location, msg), tr);
		}
	}

	public static void e(String location, String msg) {
		if (LOG_LEVEL >= 1) {
			android.util.Log.e(FUNCTION, buildLog(location, msg));
		}
	}

	public static void e(String location, String msg, Throwable tr) {
		if (LOG_LEVEL >= 1) {
			android.util.Log.e(FUNCTION, buildLog(location, msg), tr);
		}
	}

	private static String buildLog(String location, String msg) {
		StringBuilder logStr = new StringBuilder();
		if (location != null) {
			logStr.append(LOG_DELIMITER).append(location);
		} else {
			logStr.append(LOG_DELIMITER).append(FUNCTION);
		}
		logStr.append(LOG_DELIMITER).append(msg);
		return logStr.toString();
	}

}
