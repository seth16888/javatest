package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;

/**
 * @author seth16888
 *
 */
public class Player {
	//主角的血量与血量位图
	//默认3血
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	//主角的坐标以及位图
	public int x, y;
	private Bitmap bmpPlayer;
	//主角移动速度
	private int speed = 5;
	//主角移动标识（基础章节已讲解，你懂得）
	public boolean isUp, isDown, isLeft, isRight;
	public boolean isMoving = false;
	private int mXMove,mYMove;	//X,Y坐标移动距离
	public int getmXMove() {
		return mXMove;
	}

	public void setmXMove(int mXMove) {
		//判断屏幕X边界
		int mX = x + mXMove;
				if (mX + bmpPlayer.getWidth() >= MainView.screenW) {
					this.setmXMoveTo(MainView.screenW - bmpPlayer.getWidth()) ;
					this.mXMove = MainView.screenW - bmpPlayer.getWidth() - x;
				} else if (mX <= 0) {
					this.setmXMoveTo(0);
				}
		this.mXMove = mXMove;
	}

	public int getmYMove() {
		return mYMove;
	}

	public void setmYMove(int mYMove) {
		this.mYMove = mYMove;
	}

	public int getmXMoveTo() {
		return mXMoveTo;
	}

	public void setmXMoveTo(int mXMoveTo) {
		this.mXMoveTo = mXMoveTo;
	}

	public int getmYMoveTo() {
		return mYMoveTo;
	}

	public void setmYMoveTo(int mYMoveTo) {
		this.mYMoveTo = mYMoveTo;
	}

	private int mXMoveTo = -1,mYMoveTo = -1;
	
	
	//主角的构造函数
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MainView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MainView.screenH - bmpPlayer.getHeight();
	}

	//主角的绘图函数
	public void draw(Canvas canvas, Paint paint) {
		//绘制主角
		canvas.drawBitmap(bmpPlayer, x, y, paint);
		//绘制主角血量
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MainView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}

	//实体按键
	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = true;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = true;
		}
	}

	//实体按键抬起
	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isUp = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isDown = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isLeft = false;
		}
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isRight = false;
		}
	}

	//主角的逻辑
	public void logic() {
		//处理主角移动
		if(mXMoveTo > 0 && mXMoveTo < x){
			isLeft = true;
		}
		if(mXMoveTo > 0 && mXMoveTo > x){
			isRight = true;
		}
		if(mYMoveTo > 0 && mYMoveTo > y){
			isDown = true;
		}
		if(mYMoveTo > 0 && mYMoveTo < y){
			isUp = true;
		}
		if (isLeft) {
			x -= speed;
		}
		if (isRight) {
			x += speed;
		}
		if (isUp) {
			y -= speed;
		}
		if (isDown) {
			y += speed;
		}
		//判断屏幕X边界
		if (x + bmpPlayer.getWidth() >= MainView.screenW) {
			x = MainView.screenW - bmpPlayer.getWidth();
			mXMoveTo = -1;
		} else if (x <= 0) {
			x = 0;
			mXMoveTo = -1;
		}
		//判断屏幕Y边界
		if (y + bmpPlayer.getHeight() >= MainView.screenH) {
			y = MainView.screenH - bmpPlayer.getHeight();
			mYMoveTo = -1;
		} else if (y <= 0) {
			y = 0;
			mYMoveTo = -1;
		}
		
		if(x == mXMoveTo && y == mYMoveTo){
			mYMoveTo = -1;
			mXMoveTo = -1;
		}
	}
    
	//设置主角血量
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	//获取主角血量
	public int getPlayerHp() {
		return playerHp;
	}

}
