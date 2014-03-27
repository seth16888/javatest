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
 * SurfaceView��ͼ��Ϸ�ܹ�ѵ��
 * SurfaceView�����ͼ�����ݡ�canvas�ȣ���SurfaceHolder����
 * 1.����SurfaceView��������
 * 2.������ʱ����Back����Home�����ٷ���Ӧ�ã�SurfaceView�������
 * +
 * ��Ϸ�ܹ���
 * GameBg�࣬��Ϸ������ , ���Ʊ���ͼ������LOGO������ʱ, �ײ�:��Ч���أ��������ֿ��أ���ǰ�ؿ������͹ؿ���
 * GameBgThread�࣬��Ϸ��������
 * Map�࣬���ݹؿ�����ײ��ͼ(10*12��ͼ����)��������Ҫ��������ӣ�ά�����ظ��ӵ�״̬��
 * Diamonds�࣬���ݵ�ͼ�����������ʯ����ʯ��ͼλ�õ�ά������ʯ��λ�ý�������ʯ��������
 * MapDraw�࣬��ͼ���ƣ������ײ��ͼ����ͼ�ϵ���ʯ
 * Diamond�࣬��ʯ��
 * MainView�࣬ά����Ϸ���̣�������Ҷ�����
 * @author seth16888
 *
 */
public class MainView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder sfh;	//SurfaceView�Ŀ�����
	private MainActivity activity;		//���ؽ��������
	private Canvas canvas;
	private Paint paint;
	public GameBg backGround;	//��Ϸ����
	private Bitmap bmpBackGround;	//����ͼƬ
	public Map map;
	//private GestureDetector mGestureDetetor;	//���Ƽ��
	//private	 int verticalMinDistance = 20;  
	//private	 int minVelocity         = 0; 	//����������
	MediaPlayer mpWelcome;		//����������
	
	private Thread th;	//��Ϸ���߳�
	
	private boolean flag;	//�߳̽�����־
	 public static int screenW,screenH;	//��Ļ����
	//�ɼ�
	 public int score;
	 public Point SelectFirst;	//ѡ�еĵ�һ��
	 public Point SelectTarget;	//ѡ�еĵڶ���λ�ý�����Ŀ��
	 public boolean actionFlag;	//�Ƿ�����û�����
	 
	public MainView(MainActivity activity) {
		super(activity);
		this.activity = activity;	//���ؽ��������
		Log.d("1","���캯��");
		sfh = this.getHolder();
		sfh.addCallback(this);	//�������뱾ʵ������
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);		//��ʼ������
		//���ÿɻ�ȡ����
		//setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();	//��ȡ���㣬ӵ�в���keydown
		// ���ñ�������
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
			myDraw();		//��Ϸ����
			logic();	//��Ϸ�߼�����
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));		//Ϊ�˱�������ʱ��һ��
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
		screenH = this.getHeight();	//��surfaceCreatedʱ�����ܻ�ȡ�߿�
		initGame();	//��ʼ����Ϸ
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
		//�����û�����
		actionFlag = true;
		
		mpWelcome = MediaPlayer.create(getContext(), R.raw.gamestart);	//���ֲ�������ʼ��
		mpWelcome.setLooping(true);
		try {
			if(mpWelcome != null){
				mpWelcome.stop();
			}
			mpWelcome.prepare();	//����Ԥ����
			mpWelcome.start();		//��������
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("1", "surfaceDestroyed");
		flag = false;	//�˳�ʱ��������Ϸ�߳�
		if(mpWelcome != null){
			mpWelcome.stop();
			mpWelcome.release();
			mpWelcome = null;
		}
	}
	
	/**
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame(){
		bmpBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		backGround = new GameBg(this, bmpBackGround);	//��ʼ��������
		map = new Map(this);

	}

	/**
	 * �Զ�����Ϸ�߼�
	 */
	private void logic(){
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
		//score += Math.random() * 10;
		//�����߼�
		backGround.logic();
	}
	
	/**
	 * �Զ�����Ϸ����
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();		//���Զ����߳��У�ͨ��SurfaceHolder��������ȡ��������Canvas����
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//ˢ��
				//��Ϸ����
				backGround.draw(canvas, paint);
				//mapDraw.myDraw(canvas, paint);
				map.myDraw(canvas, paint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);	//����
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("1","onKeyDown");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//������Back��
			activity.myHandler.sendEmptyMessage(3);
			return true;	//���ϵͳ��Ҫ�����˳���
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * ����̧���¼�����
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//������Back��
			activity.myHandler.sendEmptyMessage(3);
			//��ʾ�˰����Ѵ������ٽ���ϵͳ����
			//�Ӷ�������Ϸ�������̨
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("1","onTouchEvent");
		if(actionFlag){	//���������û�������״̬
			map.playAction(event);
		}
		return super.onTouchEvent(event);
	}

	
	

}
