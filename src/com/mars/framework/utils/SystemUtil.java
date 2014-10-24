package com.mars.framework.utils;


/**
 * 
 * @项目名称：SohuClient    
 * @类名称：SystemUtil    
 * @类描述：获得系统信息    
 * @创建人：pengfeizheng    
 * @创建时间：2014-5-14 下午6:09:31    
 * @修改人：pengfeizheng    
 * @修改时间：2014-5-14 下午6:09:31    
 * @修改备注：    
 * @version
 */
public class SystemUtil {
    
    /**
     * 获得系统版本
     * @return
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.CODENAME;
    }

    /**
     * 获得系统
     * @return
     */
    public static int getVersionCode() {
        return android.os.Build.VERSION.SDK_INT;
    }
    
}
