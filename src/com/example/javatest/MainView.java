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
 * SurfaceView��ͼ��Ϸ�ܹ�ѵ��
 * SurfaceView�����ͼ�����ݡ�canvas�ȣ���SurfaceHolder����
 * 1.����SurfaceView��������
 * 2.������ʱ����Back����Home�����ٷ���Ӧ�ã�SurfaceView�������
 * @author seth16888
 *
 */
public class MainView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder sfh;	//SurfaceView�Ŀ�����
	
	private Canvas canvas;
	private Paint paint;
	
	private Thread th;	//��Ϸ���߳�
	
	private boolean flag;	//�߳̽�����־
	 public static int screenW,screenH;	//��Ļ����
	
	public MainView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);	////�������뱾ʵ������
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);		//��ʼ������
		//���ÿɻ�ȡ����
		setFocusable(true);
		setFocusableInTouchMode(true);
		// ���ñ�������
		this.setKeepScreenOn(true);
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
		
	}

	/**
	 * �Զ�����Ϸ�߼�
	 */
	private void logic(){
		//�߼����������Ϸ״̬��ͬ���в�ͬ����
		
	}
	
	/**
	 * �Զ�����Ϸ����
	 */
	public void myDraw(){
		try {
			canvas = sfh.lockCanvas();		//ͨ��SurfaceHolder��������ȡ��������Canvas����
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);	//ˢ��
				//��ͼ����������Ϸ״̬��ͬ���в�ͬ����
				canvas.drawText("��Ϸ���", 10, 10, paint);
				
			}
		} catch (Exception e) {
			// TODO: handle exception	
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);	//����
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
