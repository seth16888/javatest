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
 * SurfaceView��ͼ��Ϸ�ܹ�ѵ��
 * SurfaceView�����ͼ�����ݡ�canvas�ȣ���SurfaceHolder����
 * 1.����SurfaceView��������
 * 2.������ʱ����Back����Home�����ٷ���Ӧ�ã�SurfaceView�������
 * @author seth16888
 *
 */
public class MainView extends SurfaceView implements Callback,Runnable,OnGestureListener{
	private SurfaceHolder sfh;	//SurfaceView�Ŀ�����
	private MainActivity activity;		//���ؽ��������
	private Canvas canvas;
	private Paint paint;
	private GameBg backGround;	//��Ϸ����
	private Bitmap bmpBackGround;	//����ͼƬ
	private Player player;
	private Bitmap bmpPlayer;//��Ϸ���Ƿɻ�
	private Bitmap bmpPlayerHp;//���Ƿɻ�Ѫ��
	private GestureDetector mGestureDetetor;	//���Ƽ��
	private	 int verticalMinDistance = 20;  
	private	 int minVelocity         = 0; 	//����������
	
	private Thread th;	//��Ϸ���߳�
	
	private boolean flag;	//�߳̽�����־
	 public static int screenW,screenH;	//��Ļ����
	
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
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ���ñ�������
		this.setKeepScreenOn(true);
		
		mGestureDetetor = new GestureDetector(this);
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
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		Log.d("1", "surfaceDestroyed");
		flag = false;	//�˳�ʱ��������Ϸ�߳�
	}
	
	/**
	 * �Զ������Ϸ��ʼ������
	 */
	private void initGame(){
		bmpBackGround = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		backGround = new GameBg(bmpBackGround);	//��ʼ��������
		bmpPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		bmpPlayerHp = BitmapFactory.decodeResource(getResources(), R.drawable.hp);
		//ʵ������
		player = new Player(bmpPlayer, bmpPlayerHp);
		
	}

	/**
	 * �Զ�����Ϸ�߼�
	 */
	private void logic(){
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
		//�����߼�
		backGround.logic();
		//�����߼�
		player.logic();
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
				//���ǻ�ͼ����
				player.draw(canvas, paint);
				
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
		//���ǵİ��������¼�
		player.onKeyDown(keyCode, event);
		
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
		//����̧���¼�
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
	    	//����
	    	int mmx = player.x - (int)(e1.getX() - e2.getX());
	    	player.setmXMoveTo(mmx);
	    }
	    if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) { 
	    	//����
	    	int mmx = player.x + (int)(e2.getX() - e1.getX());
	    	player.setmXMoveTo(mmx);
	    }
	    if(e2.getY() < e1.getY()){
	    	//����
	    	int mmy = player.y - (int)(e2.getY() - e1.getY());
	    	player.setmYMoveTo(mmy);
	    }
	    if(e2.getY() > e1.getY()){
	    	//����
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
