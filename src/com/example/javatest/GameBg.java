package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * @author seth16888
 * 游戏场景类
 * 绘制背景图，顶部LOGO、倒计时, 底部:音效开关，背景音乐开关，当前关卡分数和关卡名
 * 
 */
public class GameBg {
	MainView vw;
	//游戏背景的图片资源
	private Bitmap bmpBackGround1;
	//游戏背景坐标
	private int bg1x, bg1y;

	//游戏背景构造函数
	public GameBg(MainView view, Bitmap bmpBackGround) {
		vw = view;
		this.bmpBackGround1 = bmpBackGround;
		//首先让第一张背景底部正好填满整个屏幕
		bg1y = -Math.abs(bmpBackGround1.getHeight() - MainView.screenH);
	}
	//游戏背景的绘图函数
	public void draw(Canvas canvas, Paint paint) {
		Paint pt = new Paint();
		pt.setColor(Color.WHITE);
		pt.setStyle(Style.STROKE);
		pt.setStyle(Style.FILL);
		//绘制背景
		//canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
		canvas.drawColor(Color.WHITE);
		canvas.drawRect(0,0,MainView.screenW,50,pt);
		canvas.drawRect(0,MainView.screenH - 50,MainView.screenW, MainView.screenH, pt);
		pt.setColor(Color.BLUE);
		//绘制顶部
		canvas.drawText("LOGO", 10, 10, pt);
		//绘制底部
		canvas.drawText("成绩:", 10, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.score), 60, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.SelectFirst != null ? vw.SelectFirst.x : 0), 90, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.SelectFirst != null ? vw.SelectFirst.y : 0), 110, MainView.screenH - 10, pt);
	}
	//游戏背景的逻辑函数
	public void logic() {

	}
}
