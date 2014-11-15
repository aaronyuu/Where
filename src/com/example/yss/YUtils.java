package com.example.yss;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class YUtils {
	public static OnClickListener gotoOtherActivity(final Context mContext){
		OnClickListener titleOnClick = new OnClickListener() {

			private Intent intent;

			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btnAbout:
					intent = new Intent(mContext, AboutActivity.class);
					mContext.startActivity(intent);
					break;
				case R.id.btnList:
					intent = new Intent(mContext, ListActivity.class);
					mContext.startActivity(intent);
					break;

				case R.id.btnLocal:
					intent = new Intent(mContext, MainActivity.class);
					mContext.startActivity(intent);
					break;

				default:
					break;
				}
			}
		};
		
		return titleOnClick;
	}
}
