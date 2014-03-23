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
 * 欢迎界面
 * @author seth16888
 *
 */
public class WelcomeView extends SurfaceView  implements SurfaceHolder.Callback{
	MainActivity activity; //activity的引用
	Paint paint;//画笔
	Bitmap bgBitmap;	//背景图片
	Bitmap btnStartGame;	//开始游戏按钮图片
	Bitmap btnStartGamePressed;	//按下了的开始游戏按钮图片
	MyButton btnStartButton;		//开始游戏按钮
	
	int btnStartGameX,btnStartGameY;	//开始游戏按钮的x,y坐标
	
	public WelcomeView(MainActivity activity){
		super(activity);
		this.activity = activity;
		getHolder().addCallback(this);
		initBitmap();
	}

	/**
	 * 初始化图片资源
	 */
	public void initBitmap(){
		paint = new Paint();
		paint.setTextSize(12);		//设置字体大小
		bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome);
		btnStartGame = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		btnStartGamePressed = BitmapFactory.decodeResource(getResources(), R.drawable.button_press);
		btnStartGameX = 320/2 - btnStartGame.getWidth()/2;	//开始按钮x坐标
		btnStartGameY = 480 - btnStartGame.getHeight() -40;	//开始按钮y坐标
		
		btnStartButton = new MyButton(btnStartGame,btnStartGameX,btnStartGameY);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		myDraw();	//绘制界面
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 自定义绘制界面
	 * 绘制背景图片、开始游戏按钮
	 */
	private void myDraw() {
		Canvas canvas = null;
		try {
			canvas = getHolder().lockCanvas(); // 通过SurfaceHolder控制器获取并且锁定Canvas画布
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);// 背景色
				canvas.drawBitmap(bgBitmap, 0, 0, paint);	//绘制背景图片
				//canvas.drawBitmap(btnStartGame, btnStartGameX,btnStartGameY, paint);
				btnStartButton.draw(canvas, paint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null)
				getHolder().unlockCanvasAndPost(canvas); // 解锁
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*if(event.getAction() == MotionEvent.ACTION_DOWN){
			//在触摸屏上按下
			double x = event.getX();	//得到X坐标
			double y = event.getY();	//得到Y坐标
			
			if(x > btnStartGameX && x<btnStartGameX + btnStartGame.getWidth()
					&& y > btnStartGameY && y < btnStartGameY + btnStartGame.getHeight()){
				//坐标在按钮上,表示按了开始游戏键
				activity.myHandler.sendEmptyMessage(2);		//告诉主界面，按了开始游戏键
			}
			
		}*/
		if(btnStartButton.isPressed(event)){
			//开始按钮被按下
			activity.myHandler.sendEmptyMessage(2);		//告诉主界面，按了开始游戏键
		}
		
		return super.onTouchEvent(event);
	}
	
	
	
	

}
