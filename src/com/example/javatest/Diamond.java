package com.example.javatest;

public class Diamond {
	public int type;		//钻石类别
	public int row,col;	//行，列位置
	public boolean xuanKong = false;	//悬空状态
	public boolean isDead = false; 	//已消除
	public boolean isMove = false; 	//移动了位置
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
		this.type = 0;
	}
	public Diamond(int type) {
		this.type = type;
	}
	
	public Diamond(){
		this.type = (int)(Math.random() * 5 + 1);
	}

}
