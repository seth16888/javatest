package com.example.javatest;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * Version 0.0.1:
 * 0.��������� MainActivity
 * 1.��Ϸ��ʼ���棬��ʾ��Ϸ�˵� WelcomeView
 * 2.����ʼ��������Ϸ������	MainView
 * 3.�������¹���
 * 4.���̿��������ƶ�
 * 5.����Ϸ�����У���Back�������ز˵����� WelcomeView
 * 6.�ڲ˵����棬��������ť������������� HelpView
 * 
 * @author seth16888
 *
 */
@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	MainView gameView;
	WelcomeView welcomeView;
	HelpView helpView;
	
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 2){
				//���յ���ӭ���淢������Ϣ���û������˿�ʼ��Ϸ��ť
				if(welcomeView != null){
					welcomeView = null;	//������ӭ����
				}
				gameView = new MainView(MainActivity.this);
				toGameView();
			}else if(msg.what == 3){	//��Ϸ���淢���ģ������ź�
				if(gameView != null){
					gameView = null;
				}
				welcomeView = new WelcomeView(MainActivity.this);
				toWelcomeView();
			}
			
		}
	};
	
    public void toWelcomeView(){//�л�����ӭ����     	
    	this.setContentView(welcomeView);
    }
    public void toGameView(){//��ʼ��Ϸ����
    	this.setContentView(gameView);
    }
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//setContentView(new MainView(this));
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new WelcomeView(MainActivity.this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
