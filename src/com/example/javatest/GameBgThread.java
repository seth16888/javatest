package com.example.javatest;

public class GameBgThread extends Thread {
	MainView mv;
	private boolean flag = true;//ѭ����־
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	int span = 20;//˯�ߵĺ�����
	
	public GameBgThread(MainView mv) {
		this.mv = mv;

	}

	@Override
	public void run() {
		while (flag) {
			
		}
		try {
			Thread.sleep(span);// ˯��ָ��������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
