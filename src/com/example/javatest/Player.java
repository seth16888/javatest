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
	private boolean isUp, isDown, isLeft, isRight;
	
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
		} else if (x <= 0) {
			x = 0;
		}
		//判断屏幕Y边界
		if (y + bmpPlayer.getHeight() >= MainView.screenH) {
			y = MainView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
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
