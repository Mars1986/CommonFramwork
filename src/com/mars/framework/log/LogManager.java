package com.mars.framework.log;

import android.util.Log;

/**
 * log管理类
 * 
 * @author Mars
 *
 */
public class LogManager
{
    private static String TAG = "SohuLogManager";
    
    private static boolean isShowLog = true; // 默认打开
    
    public static boolean canShow()
    {
        return isShowLog;
    }
    
    public static void printStackTrace(Throwable e)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (e != null)
                {
                    e.printStackTrace();
                }
            }
            catch (IllegalArgumentException ex)
            {
                // do nothing
            }
            catch (Exception ex)
            {
                // do nothing
            }
            catch (Error ex)
            {
                // do nothing
            }
        }
    }
    
    public static void v(String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                Log.v(TAG, msg);
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void i(String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                Log.i(TAG, msg);
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void d(String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                Log.d(TAG, msg);
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void w(String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                Log.w(TAG, msg);
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void e(String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                Log.e(TAG, msg);
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void v(String tag, String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.v(tag, msg);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void i(String tag, String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.i(tag, msg);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void i(String tag, Object msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.i(tag, msg + "");
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void d(String tag, String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.d(tag, msg);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void w(String tag, String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.w(tag, msg);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void e(String tag, String msg)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.e(tag, msg);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static void e(String tag, String msg, Throwable th)
    {
        isShowLog = canShow();
        if (isShowLog)
        {
            try
            {
                if (msg != null)
                {
                    Log.e(tag, msg, th);
                }
            }
            catch (Exception e)
            {
                LogManager.printStackTrace(e);
            }
            catch (Error e)
            {
                LogManager.printStackTrace(e);
            }
        }
    }
    
    public static String getTAG()
    {
        return TAG;
    }
    
    public static void setTAG(String tAG)
    {
        TAG = tAG;
    }
    
    /**
     * 日志开关
     * @param isShowLog  true是开启，false是关闭
     */
    public static void switchLog(boolean isShowLog)
    {
        LogManager.isShowLog = isShowLog;
    }
    
}
