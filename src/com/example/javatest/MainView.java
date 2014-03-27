package com.example.javatest;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
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
 * +
 * 游戏架构：
 * GameBg类，游戏背景类 , 绘制背景图，顶部LOGO、倒计时, 底部:音效开关，背景音乐开关，当前关卡分数和关卡名
 * GameBgThread类，游戏背景更新
 * Map类，根据关卡载入底层地图(10*12地图数组)，过关需要的特殊格子，维护过关格子的状态。
 * Diamonds类，根据地图，随机生成钻石，钻石地图位置的维护，钻石的位置交换，钻石的消除。
 * MapDraw类，地图绘制，包括底层地图，地图上的钻石
 * Diamond类，钻石类
 * MainView类，维护游戏进程，接收玩家动作。
 * @author seth16888
 *
 */
public class MainView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder sfh;	//SurfaceView的控制器
	private MainActivity activity;		//主控界面的引用
	private Canvas canvas;
	private Paint paint;
	public GameBg backGround;	//游戏背景
	private Bitmap bmpBackGround;	//背景图片
	public Map map;
	//private GestureDetector mGestureDetetor;	//手势检测
	//private	 int verticalMinDistance = 20;  
	//private	 int minVelocity         = 0; 	//滑动检测参数
	MediaPlayer mpWelcome;		//背景播放器
	
	private Thread th;	//游戏主线程
	
	private boolean flag;	//线程结束标志
	 public static int screenW,screenH;	//屏幕宽、高
	//成绩
	 public int score;
	 public Point SelectFirst;	//选中的第一个
	 public Point SelectTarget;	//选中的第二个位置交换的目标
	 public boolean actionFlag;	//是否接收用户动作
	 
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
		//setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();	//获取焦点，拥有捕获keydown
		// 设置背景高亮
		this.setKeepScreenOn(true);
		SelectFirst = new Point();
		SelectTarget = new Point();
		SelectFirst.x = -1;
		SelectFirst.y = -1;
		SelectTarget.x = -1;
		SelectTarget.y =-1;
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
		//接收用户动作
		actionFlag = true;
		
		mpWelcome = MediaPlayer.create(getContext(), R.raw.gamestart);	//音乐播放器初始化
		mpWelcome.setLooping(true);
		try {
			if(mpWelcome != null){
				mpWelcome.stop();
			}
			mpWelcome.prepare();	//音乐预处理
			mpWelcome.start();		//播放音乐
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("1", "surfaceDestroyed");
		flag = false;	//退出时，结束游戏线程
		if(mpWelcome != null){
			mpWelcome.stop();
			mpWelcome.release();
			mpWelcome = null;
		}
	}
	
	/**
	 * 自定义的游戏初始化函数
	 */
	private void initGame(){
		bmpBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		backGround = new GameBg(this, bmpBackGround);	//初始化背景类
		map = new Map(this);

	}

	/**
	 * 自定义游戏逻辑
	 */
	private void logic(){
		//逻辑处理根据游戏状态不同进行不同处理
		//score += Math.random() * 10;
		//背景逻辑
		backGround.logic();
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
				//mapDraw.myDraw(canvas, paint);
				map.myDraw(canvas, paint);
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
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("1","onTouchEvent");
		if(actionFlag){	//正常接收用户动作的状态
			map.playAction(event);
		}
		return super.onTouchEvent(event);
	}

	
	

}
