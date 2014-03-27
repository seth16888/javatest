package com.example.javatest;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * Version 0.0.1:
 * 0.程序入口类 MainActivity
 * 1.游戏开始画面，显示游戏菜单 WelcomeView
 * 2.按开始，进入游戏主界面	MainView
 * 3.背景向下滚动
 * 4.键盘控制主角移动
 * 5.在游戏进行中，按Back键，返回菜单界面 WelcomeView
 * 6.在菜单界面，按帮助按钮，进入帮助界面 HelpView
 * 
 * @author seth16888
 *
 */
public class MainActivity extends Activity {

	MainView gameView;
	WelcomeView welcomeView;
	HelpView helpView;
	
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;	//屏幕宽度，高度
	public static int DENSITY_DPI;	//屏幕密度DPI
	public static float DENSITY;		//屏幕密度
	
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 2){
				//接收到欢迎界面发来的消息，用户按下了开始游戏按钮
				if(welcomeView != null){
					welcomeView = null;	//结束欢迎界面
				}
				gameView = new MainView(MainActivity.this);
				toGameView();
			}else if(msg.what == 3){	//游戏界面发来的，返回信号
				if(gameView != null){
					gameView = null;
				}
				welcomeView = new WelcomeView(MainActivity.this);
				toWelcomeView();
			}else if(msg.what == 4){
				//接收到欢迎界面发来的消息，用户按下了帮助菜单
				if(welcomeView != null){
					welcomeView = null;	//结束欢迎界面
				}
				helpView = new HelpView(MainActivity.this);
				toHelpView();
			}else if(msg.what == 5){	//帮助界面返回欢迎界面
				if(helpView != null){
					helpView = null;
				}
				welcomeView = new WelcomeView(MainActivity.this);
				toWelcomeView();
			}
			
		}
	};
	
    public void toWelcomeView(){//切换到欢迎界面     	
    	this.setContentView(welcomeView);
    }
    public void toGameView(){//初始游戏界面
    	this.setContentView(gameView);
    }
	public void toHelpView(){		//切换到帮助界面
		this.setContentView(helpView);
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		//setContentView(new MainView(this));
		 DisplayMetrics metric = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metric);
	     SCREEN_WIDTH = metric.widthPixels;     // 屏幕宽度（像素）
	     SCREEN_HEIGHT = metric.heightPixels;   // 屏幕高度（像素）
	     DENSITY = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
	     DENSITY_DPI = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
	        
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new WelcomeView(MainActivity.this));
	}


}
