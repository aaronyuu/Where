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

		//���ü����¼�
		btnLocal.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnList.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		btnAbout.setOnClickListener(YUtils.gotoOtherActivity(mContext));
		
	}

	private void showDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("��������");
		builder.setMessage("ȷ�������ֲе����?����һ�ź��ҩ�԰�!!!��Ҫ�ǲ�֪�����ĸ���ť,�ԹԵ��ֻ��ķ��ذ�����!!");
		builder.setPositiveButton("û��ҩ,��Ҫ����!", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DBUtils.resetGPSInfo(mContext);			
				Toast.makeText(mContext, "���óɹ�", Toast.LENGTH_SHORT).show();
			}
		});
	builder.setNegativeButton("��ҩ��ó�!", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(mContext, "δ����", Toast.LENGTH_SHORT).show();
			}
		});
	AlertDialog dialog=builder.create();
	dialog.show();
	}
}
