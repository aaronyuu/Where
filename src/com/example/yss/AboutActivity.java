package com.example.yss;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends Activity {

	Button btnLocal;
	Button btnList;
	Button btnAbout;
	Context mContext;
	private Button buttonReset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		mContext=this;
		titleInit();
		
		buttonReset=(Button) findViewById(R.id.buttonReset);
		buttonReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog();
				
			}
		});
	}

	private void titleInit() {
		btnLocal = (Button) findViewById(R.id.btnLocal);
		btnList = (Button) findViewById(R.id.btnList);
		btnAbout = (Button) findViewById(R.id.btnAbout);

		//设置监听事件
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		
	}

	private void showDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("重置数据");
		builder.setMessage("确定不是手残点错了?给你一颗后悔药吃吧!!!你要是不知道点哪个按钮,乖乖点手机的返回按键吧!!");
		builder.setPositiveButton("没吃药,就要重置!", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DBUtils.resetGPSInfo(mContext);			
				Toast.makeText(mContext, "重置成功", Toast.LENGTH_SHORT).show();
			}
		});
	builder.setNegativeButton("这药真好吃!", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(mContext, "未重置", Toast.LENGTH_SHORT).show();
			}
		});
	AlertDialog dialog=builder.create();
	dialog.show();
	}
}
