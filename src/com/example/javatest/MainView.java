package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
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
public class MainView extends SurfaceView implements Callback,Runnable,OnGestureListener{
	private SurfaceHolder sfh;	//SurfaceView的控制器
	private MainActivity activity;		//主控界面的引用
	private Canvas canvas;
	private Paint paint;
	private GameBg backGround;	//游戏背景
	private Bitmap bmpBackGround;	//背景图片
	private Player player;
	private Bitmap bmpPlayer;//游戏主角飞机
	private Bitmap bmpPlayerHp;//主角飞机血量
	private GestureDetector mGestureDetetor;	//手势检测
	private	 int verticalMinDistance = 20;  
	private	 int minVelocity         = 0; 	//滑动检测参数
	
	private Thread th;	//游戏主线程
	
	private boolean flag;	//线程结束标志
	 public static int screenW,screenH;	//屏幕宽、高
	
	public MainView(MainActivity activity) {
		super(activity);
		this.activity = activity;	//主控界面的引用
		Log.d("1","构造函数");
		sfh = this.getHolder();
		sfh.addCallback(this);	//控制器与本实例连接
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);		//初始化画笔
		//设置可获取焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置背景高亮
		this.setKeepScreenOn(true);
		
		mGestureDetetor = new GestureDetector(this);
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
		bmpBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		backGround = new GameBg(bmpBackGround);	//初始化背景类
		bmpPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		bmpPlayerHp = BitmapFactory.decodeResource(getResources(), R.drawable.hp);
		//实例主角
		player = new Player(bmpPlayer, bmpPlayerHp);
		
	}

	/**
	 * 自定义游戏逻辑
	 */
	private void logic(){
		//逻辑处理根据游戏状态不同进行不同处理
		//背景逻辑
		backGround.logic();
		//主角逻辑
		player.logic();
	}
	
	/**
	 * 自定义游戏绘制
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();		//在自定义线程中，通过SurfaceHolder控制器获取并且锁定Canvas画布
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//刷屏
				//游戏背景
				backGround.draw(canvas, paint);
				//主角绘图函数
				player.draw(canvas, paint);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);	//解锁
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("1","onKeyDown");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//按下了Back键
			activity.myHandler.sendEmptyMessage(3);
			return true;	//阻断系统不要继续退出。
		}
		//主角的按键按下事件
		player.onKeyDown(keyCode, event);
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 按键抬起事件监听
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//处理back返回按键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//按下了Back键
			activity.myHandler.sendEmptyMessage(3);
			//表示此按键已处理，不再交给系统处理，
			//从而避免游戏被切入后台
			return true;
		}
		//按键抬起事件
		player.onKeyUp(keyCode, event);
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("1","onTouchEvent");
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.i("1","onFling");

	    if	(e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) { 
	    	//像左
	    	int mmx = player.x - (int)(e1.getX() - e2.getX());
	    	player.setmXMoveTo(mmx);
	    }
	    if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) { 
	    	//向右
	    	int mmx = player.x + (int)(e2.getX() - e1.getX());
	    	player.setmXMoveTo(mmx);
	    }
	    if(e2.getY() < e1.getY()){
	    	//向上
	    	int mmy = player.y - (int)(e2.getY() - e1.getY());
	    	player.setmYMoveTo(mmy);
	    }
	    if(e2.getY() > e1.getY()){
	    	//向下
	    	int mmy = player.y + (int)(e1.getY() - e2.getY());
	    	player.setmYMoveTo(mmy);
	    }
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
