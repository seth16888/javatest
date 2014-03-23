package com.example.javatest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 帮助界面
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
		//设置可获取焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();	//获取焦点，拥有捕获keydown
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
			canvas = getHolder().lockCanvas(); // 通过SurfaceHolder控制器获取并且锁定Canvas画布
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);// 背景色
				canvas.drawText("这里是帮助界面", 100, 100, paint); // 绘制背景图片
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				getHolder().unlockCanvasAndPost(canvas); // 解锁
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("1","onKeyDown");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//按下了Back键
			activity.myHandler.sendEmptyMessage(5);
			return true;	//阻断系统不要继续退出。
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//按下了Back键
			activity.myHandler.sendEmptyMessage(5);
			return true;	//阻断系统不要继续退出。
		}
		return super.onKeyUp(keyCode, event);
	}
	
	

}
