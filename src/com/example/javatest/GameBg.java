package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * @author seth16888
 * ��Ϸ������
 * ���Ʊ���ͼ������LOGO������ʱ, �ײ�:��Ч���أ��������ֿ��أ���ǰ�ؿ������͹ؿ���
 * 
 */
public class GameBg {
	MainView vw;
	//��Ϸ������ͼƬ��Դ
	private Bitmap bmpBackGround1;
	//��Ϸ��������
	private int bg1x, bg1y;

	//��Ϸ�������캯��
	public GameBg(MainView view, Bitmap bmpBackGround) {
		vw = view;
		this.bmpBackGround1 = bmpBackGround;
		//�����õ�һ�ű����ײ���������������Ļ
		bg1y = -Math.abs(bmpBackGround1.getHeight() - MainView.screenH);
	}
	//��Ϸ�����Ļ�ͼ����
	public void draw(Canvas canvas, Paint paint) {
		Paint pt = new Paint();
		pt.setColor(Color.WHITE);
		pt.setStyle(Style.STROKE);
		pt.setStyle(Style.FILL);
		//���Ʊ���
		//canvas.drawBitmap(bmpBackGround1, bg1x, bg1y, paint);
		canvas.drawColor(Color.WHITE);
		canvas.drawRect(0,0,MainView.screenW,50,pt);
		canvas.drawRect(0,MainView.screenH - 50,MainView.screenW, MainView.screenH, pt);
		pt.setColor(Color.BLUE);
		//���ƶ���
		canvas.drawText("LOGO", 10, 10, pt);
		//���Ƶײ�
		canvas.drawText("�ɼ�:", 10, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.score), 60, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.SelectFirst != null ? vw.SelectFirst.x : 0), 90, MainView.screenH - 10, pt);
		canvas.drawText(String.valueOf(vw.SelectFirst != null ? vw.SelectFirst.y : 0), 110, MainView.screenH - 10, pt);
	}
	//��Ϸ�������߼�����
	public void logic() {

	}
}
