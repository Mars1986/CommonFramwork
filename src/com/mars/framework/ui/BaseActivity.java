package com.mars.framework.ui;

import android.app.Activity;
import android.os.Bundle;

/**
 * activity基类
 * 
 * @author Mars
 *
 */
public class BaseActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
}

