package com.example.javatest;

public class Diamond {
	public int type;		//��ʯ���
	public int row,col;	//�У���λ��
	public boolean xuanKong = false;	//����״̬
	public boolean isDead = false; 	//������
	public boolean isMove = false; 	//�ƶ���λ��
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
