package com.example.javatest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * SurfaceView视图游戏架构训练
 * SurfaceView存放视图的数据、canvas等，靠SurfaceHolder控制
 * 1.测试SurfaceView生命周期
 * 2.在运行时，按Back键、Home键，再返回应用，SurfaceView运行情况
 * @author seth16888
 *
 */
public class MainView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder sfh;	//SurfaceView的控制器
	
	private Canvas canvas;
	private Paint paint;
	
	private Thread th;	//游戏主线程
	
	private boolean flag;	//线程结束标志
	 public static int screenW,screenH;	//屏幕宽、高
	
	public MainView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);	////控制器与本实例连接
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);		//初始化画笔
		//设置可获取焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置背景高亮
		this.setKeepScreenOn(true);
	}
	
	@Override
	public void run(){
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();		//游戏绘制
			logic();	//游戏逻辑处理
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));		//为了保持休眠时间一致
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.d("1", "surfaceChanged");
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.d("1", "surfaceCreated");
		screenW = this.getWidth();
		screenH = this.getHeight();	//在surfaceCreated时，才能换取高宽
		initGame();	//初始化游戏
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("1", "surfaceDestroyed");
		flag = false;	//退出时，结束游戏线程
	}
	
	/**
	 * 自定义的游戏初始化函数
	 */
	private void initGame(){
		
	}

	/**
	 * 自定义游戏逻辑
	 */
	private void logic(){
		//逻辑处理根据游戏状态不同进行不同处理
		
	}
	
	/**
	 * 自定义游戏绘制
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();		//通过SurfaceHolder控制器获取并且锁定Canvas画布
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//刷屏
				//绘图函数根据游戏状态不同进行不同绘制
				canvas.drawText("游戏框架", 10, 10, paint);
				
			}
		} catch (Exception e) {
			// TODO: handle exception	
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);	//解锁
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("2","onKeyDown");
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("2","onTouchEvent");
		return super.onTouchEvent(event);
	}
	
	

}
