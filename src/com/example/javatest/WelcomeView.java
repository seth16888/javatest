package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ��ӭ����
 * @author seth16888
 *
 */
public class WelcomeView extends SurfaceView  implements SurfaceHolder.Callback{
	MainActivity activity; //activity������
	Paint paint;//����
	Bitmap bgBitmap;	//����ͼƬ
	Bitmap btnStartGame;	//��ʼ��Ϸ��ťͼƬ
	Bitmap btnStartGamePressed;	//�����˵Ŀ�ʼ��Ϸ��ťͼƬ
	MyButton btnStartButton;		//��ʼ��Ϸ��ť
	
	int btnStartGameX,btnStartGameY;	//��ʼ��Ϸ��ť��x,y����
	
	public WelcomeView(MainActivity activity){
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		initBitmap();
	}

	/**
	 * ��ʼ��ͼƬ��Դ
	 */
	public void initBitmap(){
		paint = new Paint();
		paint.setTextSize(12);		//���������С
		bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome);
		btnStartGame = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		btnStartGamePressed = BitmapFactory.decodeResource(getResources(), R.drawable.button_press);
		btnStartGameX = 320/2 - btnStartGame.getWidth()/2;	//��ʼ��ťx����
		btnStartGameY = 480 - btnStartGame.getHeight() -40;	//��ʼ��ťy����
		
		btnStartButton = new MyButton(btnStartGame,btnStartGameX,btnStartGameY);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		myDraw();	//���ƽ���
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �Զ�����ƽ���
	 * ���Ʊ���ͼƬ����ʼ��Ϸ��ť
	 */
	private void myDraw() {
		Canvas canvas = null;
		try {
			canvas = getHolder().lockCanvas(); // ͨ��SurfaceHolder��������ȡ��������Canvas����
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);// ����ɫ
				canvas.drawBitmap(bgBitmap, 0, 0, paint);	//���Ʊ���ͼƬ
				//canvas.drawBitmap(btnStartGame, btnStartGameX,btnStartGameY, paint);
				btnStartButton.draw(canvas, paint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				getHolder().unlockCanvasAndPost(canvas); // ����
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*if(event.getAction() == MotionEvent.ACTION_DOWN){
			//�ڴ������ϰ���
			double x = event.getX();	//�õ�X����
			double y = event.getY();	//�õ�Y����
			
			if(x > btnStartGameX && x<btnStartGameX + btnStartGame.getWidth()
					&& y > btnStartGameY && y < btnStartGameY + btnStartGame.getHeight()){
				//�����ڰ�ť��,��ʾ���˿�ʼ��Ϸ��
				activity.myHandler.sendEmptyMessage(2);		//���������棬���˿�ʼ��Ϸ��
			}
			
		}*/
		if(btnStartButton.isPressed(event)){
			//��ʼ��ť������
			activity.myHandler.sendEmptyMessage(2);		//���������棬���˿�ʼ��Ϸ��
		}
		
		return super.onTouchEvent(event);
	}
	
	
	
	

}
