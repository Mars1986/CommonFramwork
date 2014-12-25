package com.mars.framework.sp;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SPManager 管理app中的SharePrefence   
 */
public class SPManager {
    
	private static SPManager instance;
	
    private Context mContext;
    
	public static SPManager getInstance(Context context){
		if (instance == null) {
			synchronized (SPManager.class) {
				if (instance == null) {
					 instance = new SPManager(context);
				}
			}
		}
		
		return instance;
	}
    
	public SPManager(Context context){
        mContext = context;
    }
    
	/**
	 * 保存String到指定spName的key下
	 * @param spName
	 * @param key
	 * @param value
	 */
    public void setData(String spName,String key,String value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putString(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存int到指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public void setData(String spName,String key,int value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putInt(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存float指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public void setData(String spName,String key,float value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putFloat(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存boolean指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public void setData(String spName,String key,boolean value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putBoolean(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存long指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public void setData(String spName,String key,long value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putLong(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存value指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public void setData(String spName,String key,Set<String> value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putStringSet(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    /**
     * 保存key指定spName的key下
     * @param spName
     * @param key
     * @param value
     */
    public int getIntData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(key, Context.MODE_PRIVATE);
        return share.getInt(key, 0);
    }
    
    /**
     * 获得spName所存得key对应的布尔值，默认为false;
     * @param spName sp的名称
     * @param key  sp对应的字段key
     * @return
     */
    public boolean getBooleanData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getBoolean(key, false);
    }
    
    /**
     * 获得spName所存得key对应float值，默认为0.0f;
     * @param spName sp的名称
     * @param key  sp对应的字段key
     * @return
     */
    public float getFloatData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getFloat(key, 0.0f);
    }
    
    /**
     * 获得spName所存得key对应String值，默认为"";
     * @param spName sp的名称
     * @param key  sp对应的字段key
     * @return
     */
    public String getStringData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getString(key, "");
    }
    
    /**
     * 获得spName所存得key对应Long值，默认为0;
     * @param spName sp的名称
     * @param key  sp对应的字段key
     * @return
     */
    public long getLongData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getLong(key, 0);
    }
    
    /**
     * 获得spName所存得key对应Set<String>值，默认为null;
     * @param spName sp的名称
     * @param key  sp对应的字段key
     * @return
     */
    public Set<String> getStringSetData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getStringSet(key, null);
    }
    
    /**
     * 移除spName下指定的key值
     * @param spName
     * @param key
     */
    public void removeKey(String spName,String key){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.remove(key);
    }
    
    /**
     * 移除指定的spName
     * @param spName
     */
    public void removeAll(String spName){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.clear();
        edit.commit();  //保存数据信息 
   }
    
}
