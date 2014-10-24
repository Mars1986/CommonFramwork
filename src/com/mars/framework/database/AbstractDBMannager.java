package com.mars.framework.database;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * 数据库管理抽象类
 * 
 * @author Mars
 *
 */
public abstract class AbstractDBMannager extends OrmLiteSqliteOpenHelper {

	/**
	 * @param context
	 *            Associated content from the application. This is needed to locate the database.
	 * @param databaseName
	 *            Name of the database we are opening.
	 * @param factory
	 *            Cursor factory or null if none.
	 * @param databaseVersion
	 *            Version of the database we are opening. This causes {@link #onUpgrade(SQLiteDatabase, int, int)} to be
	 *            called if the stored database is a different version.
	 */
	public AbstractDBMannager(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		
		
	}
	
	
	/**
	 * Same as the other constructor with the addition of a file-id of the table config-file. See
	 * {@link OrmLiteConfigUtil} for details.
	 * 
	 * @param context
	 *            Associated content from the application. This is needed to locate the database.
	 * @param databaseName
	 *            Name of the database we are opening.
	 * @param factory
	 *            Cursor factory or null if none.
	 * @param databaseVersion
	 *            Version of the database we are opening. This causes {@link #onUpgrade(SQLiteDatabase, int, int)} to be
	 *            called if the stored database is a different version.
	 * @param configFileId
	 *            file-id which probably should be a R.raw.ormlite_config.txt or some static value.
	 */
	public AbstractDBMannager(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, int configFileId) {
		super(context, databaseName, factory, databaseVersion, configFileId);
		
	}
	
	
	
	/**
	 * Same as the other constructor with the addition of a config-file. See {@link OrmLiteConfigUtil} for details.
	 * 
	 * @param context
	 *            Associated content from the application. This is needed to locate the database.
	 * @param databaseName
	 *            Name of the database we are opening.
	 * @param factory
	 *            Cursor factory or null if none.
	 * @param databaseVersion
	 *            Version of the database we are opening. This causes {@link #onUpgrade(SQLiteDatabase, int, int)} to be
	 *            called if the stored database is a different version.
	 * @param configFile
	 *            Configuration file to be loaded.
	 */
	public AbstractDBMannager(Context context, String databaseName,
			CursorFactory factory, int databaseVersion, File configFile) {
		super(context, databaseName, factory, databaseVersion, configFile);
	}



	/**
	 * Same as the other constructor with the addition of a input stream to the table config-file. See
	 * {@link OrmLiteConfigUtil} for details.
	 * 
	 * @param context
	 *            Associated content from the application. This is needed to locate the database.
	 * @param databaseName
	 *            Name of the database we are opening.
	 * @param factory
	 *            Cursor factory or null if none.
	 * @param databaseVersion
	 *            Version of the database we are opening. This causes {@link #onUpgrade(SQLiteDatabase, int, int)} to be
	 *            called if the stored database is a different version.
	 * @param stream
	 *            Stream opened to the configuration file to be loaded. It will be closed when this method returns.
	 */
	public AbstractDBMannager(Context context, String databaseName, CursorFactory factory, int databaseVersion,
			InputStream stream) {
		super(context, databaseName, factory, databaseVersion, stream);
	}
}
