package com.example.javatest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ��������
 * @author seth16888
 *
 */
public class HelpView extends SurfaceView implements SurfaceHolder.Callback {
	Paint paint;
	MainActivity activity;
	
	public HelpView(MainActivity activity) {
		super(activity);
		setFocusableInTouchMode(true);
		this.activity = activity;
		getHolder().addCallback(this);
		//���ÿɻ�ȡ����
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();	//��ȡ���㣬ӵ�в���keydown
		initView();
	}
	
	private void initView(){
		paint = new Paint();
		paint.setTextSize(12);
		paint.setColor(Color.BLUE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		myDraw();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	private void myDraw() {
		Canvas canvas = null;
		try {
			canvas = getHolder().lockCanvas(); // ͨ��SurfaceHolder��������ȡ��������Canvas����
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);// ����ɫ
				canvas.drawText("�����ǰ�������", 100, 100, paint); // ���Ʊ���ͼƬ
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				getHolder().unlockCanvasAndPost(canvas); // ����
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("1","onKeyDown");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//������Back��
			activity.myHandler.sendEmptyMessage(5);
			return true;	//���ϵͳ��Ҫ�����˳���
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//������Back��
			activity.myHandler.sendEmptyMessage(5);
			return true;	//���ϵͳ��Ҫ�����˳���
		}
		return super.onKeyUp(keyCode, event);
	}
	
	

}
