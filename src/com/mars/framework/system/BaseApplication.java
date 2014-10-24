package com.mars.framework.system;

import android.app.Application;
import android.content.BroadcastReceiver;

/**
 * 应用入口类
 * 
 * @author Mars
 *
 */
public class BaseApplication extends Application
{
    private static BaseApplication instance = null;
    
    public static BaseApplication getInstance()
    {
        return instance;
    }
    
    public BaseApplication()
    {
        super();
        instance = this;
        
    }

	@Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        
    }

}
