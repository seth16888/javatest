package com.example.javatest;

public class GameBgThread extends Thread {
	MainView mv;
	private boolean flag = true;//循环标志
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	int span = 20;//睡眠的毫秒数
	
	public GameBgThread(MainView mv) {
		this.mv = mv;

	}

	@Override
	public void run() {
		while (flag) {
			
		}
		try {
			Thread.sleep(span);// 睡觉指定毫秒数
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
