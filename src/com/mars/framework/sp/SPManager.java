package com.mars.framework.sp;

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SPManager 管理app中的SharePrefence   
 */
@SuppressLint("NewApi")
public class SPManager {
    
    private Context mContext;
    
    public SPManager(Context context){
        mContext = context;
    }
    
    public void setData(String spName,String key,String value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putString(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    public void setData(String spName,String key,int value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putInt(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    public void setData(String spName,String key,float value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putFloat(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    public void setData(String spName,String key,boolean value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putBoolean(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    public void setData(String spName,String key,long value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putLong(key, value);  
        edit.commit();  //保存数据信息 
    }
    
    public void setData(String spName,String key,Set<String> value){
        SharedPreferences share = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);   
        SharedPreferences.Editor edit = share.edit(); //编辑文件  
        edit.putStringSet(key, value);  
        edit.commit();  //保存数据信息 
    }
    
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
    
    public float getFloatData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getFloat(key, 0.0f);
    }
    
    public String vStringData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getString(key, "");
    }
    
    public long getLongData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getLong(key, 0);
    }
    
    public Set<String> getStringSetData(String spName,String key){
        SharedPreferences share =  mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return share.getStringSet(key, null);
    }
    
}
