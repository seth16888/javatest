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
	//���ǵ�Ѫ����Ѫ��λͼ
	//Ĭ��3Ѫ
	private int playerHp = 3;
	private Bitmap bmpPlayerHp;
	//���ǵ������Լ�λͼ
	public int x, y;
	private Bitmap bmpPlayer;
	//�����ƶ��ٶ�
	private int speed = 5;
	//�����ƶ���ʶ�������½��ѽ��⣬�㶮�ã�
	private boolean isUp, isDown, isLeft, isRight;
	
	//���ǵĹ��캯��
	public Player(Bitmap bmpPlayer, Bitmap bmpPlayerHp) {
		this.bmpPlayer = bmpPlayer;
		this.bmpPlayerHp = bmpPlayerHp;
		x = MainView.screenW / 2 - bmpPlayer.getWidth() / 2;
		y = MainView.screenH - bmpPlayer.getHeight();
	}

	//���ǵĻ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		//��������
		canvas.drawBitmap(bmpPlayer, x, y, paint);
		//��������Ѫ��
		for (int i = 0; i < playerHp; i++) {
			canvas.drawBitmap(bmpPlayerHp, i * bmpPlayerHp.getWidth(), MainView.screenH - bmpPlayerHp.getHeight(), paint);
		}
	}

	//ʵ�尴��
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

	//ʵ�尴��̧��
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

	//���ǵ��߼�
	public void logic() {
		//���������ƶ�
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
		//�ж���ĻX�߽�
		if (x + bmpPlayer.getWidth() >= MainView.screenW) {
			x = MainView.screenW - bmpPlayer.getWidth();
		} else if (x <= 0) {
			x = 0;
		}
		//�ж���ĻY�߽�
		if (y + bmpPlayer.getHeight() >= MainView.screenH) {
			y = MainView.screenH - bmpPlayer.getHeight();
		} else if (y <= 0) {
			y = 0;
		}
	}
    
	//��������Ѫ��
	public void setPlayerHp(int hp) {
		this.playerHp = hp;
	}

	//��ȡ����Ѫ��
	public int getPlayerHp() {
		return playerHp;
	}

}
