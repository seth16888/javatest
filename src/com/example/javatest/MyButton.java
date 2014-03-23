package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * 自定义按钮类
 * @author seth16888
 *
 */
public class MyButton {
	private int x,y;
	private Bitmap bmpNormal;
	private int width, height;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public MyButton(Bitmap bmp, int x, int y) {
		this.bmpNormal = bmp;
		this.x = x;
		this.y = y;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		
	}
	
	public void draw(Canvas canvas, Paint paint){
		canvas.drawBitmap(bmpNormal, x, y,paint);
	}
	
	public boolean isPressed(MotionEvent event){
		//不同动作分别处理
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			//通过判断事件坐标，是否在按钮范围内，就是按钮被按下
			if(event.getX() <= this.getX() + this.getWidth() && event.getX() >= this.getX()){
				if(event.getY() <= this.getY() + this.getHeight() && event.getY() >= this.getY()){
					return true;
				}
			}
		}
		return false;	//没有点击
	}

}
