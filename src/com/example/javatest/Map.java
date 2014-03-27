package com.example.javatest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * 地图类
 * @author seth16888
 *
 */
public class Map {
	int rows = 12;
	int cols = 10;
	public int[][] gameMap = new int[rows][cols];
	private Rect[][] positionRect = new Rect[rows][cols];
	private Bitmap[] dBitmap ;	//宝石图片
	private int leftSpan = 10;
	private int topSpan = 50;
	int diamondWidth = 28;	//钻石宽度
	int diamondHeight = 28;	//钻石高度
	int cs = 2;		//列间距
	int rs = 4;		//行间距
	
	public Diamonds diamonds;
	MainView mv;
	
	public Map(MainView mv) {
		this.mv  = mv;
		gameMap[5][6] = 1;
		diamonds = new Diamonds();
		initMap();	//初始化地图网格
		
	}
	
	private void initMap(){
		int preX = leftSpan;
		int preY = topSpan; 	//前一个的坐标
		for(int r = 0; r < rows; r++){
			int pY = preY + rs;
			preX = leftSpan;
			cs = 0;
			for(int c = 0; c < cols; c++){
				int pX = preX +cs;
				int left = pX;
				int top = pY;
				int right = pX + diamondWidth;
				int bottom = pY + diamondHeight;
				positionRect[r][c] = new Rect(left, top, right, bottom);
				//canvas.drawText(String.valueOf(diamonds.diamonds[r-1][c-1].type), pX, pY, paint);
				cs = 2;
				preX = right;
			}
			rs = 4;
			preY = pY + diamondHeight;
		}
		//初始化图像
		dBitmap = new Bitmap[5];
		dBitmap[0] = BitmapFactory.decodeResource(mv.getResources(), R.drawable.d1);
		dBitmap[1] = BitmapFactory.decodeResource(mv.getResources(), R.drawable.d2);
		dBitmap[2] = BitmapFactory.decodeResource(mv.getResources(), R.drawable.d3);
		dBitmap[3] = BitmapFactory.decodeResource(mv.getResources(), R.drawable.d4);
		dBitmap[4] = BitmapFactory.decodeResource(mv.getResources(), R.drawable.d5);
	}

	public void myDraw(Canvas canvas,Paint paint){
		//int cs = 0;
		//int rs = 0;
		for(int r = 1; r <= rows; r++){
			int pY = topSpan + (r-1) * diamondHeight + rs;
			for(int c = 1; c <= cols; c++){
				int pX = leftSpan + (c-1) * diamondWidth +cs;
				//canvas.drawRect(positionRect[r-1][c-1], paint);
				//canvas.drawText(String.valueOf(diamonds.diamonds[r-1][c-1].type), positionRect[r-1][c-1].centerX(), positionRect[r-1][c-1].centerY(), paint);
				int tmp = diamonds.diamonds[r-1][c-1].type;
				canvas.drawBitmap(dBitmap[tmp-1],positionRect[r-1][c-1].left,positionRect[r-1][c-1].top,paint);
				cs = 2;
			}
			rs = 4;
		}
		
	}
	
	public void playAction(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN) {
			return;
		}
		boolean isOk = false;
		int pX = -1, pY = -1;
		int x = (int) event.getX();
		int y = (int) event.getY();
		for (int r = 1; r <= rows; r++) {
			for (int c = 1; c <= cols; c++) {
				if (positionRect[r - 1][c - 1].contains(x, y)) {
					isOk = true;
					pX = r - 1;
					pY = c - 1;
					break;
				}
			}// for
		}// for

		if (isOk) {
			// 一个目标被点击了
			if (mv.SelectFirst.x == -1 && mv.SelectFirst.y == -1) {
				// 如果是第一个被选中目标
				mv.SelectFirst.x = pX;
				mv.SelectFirst.y = pY;
			} else if (mv.SelectFirst.x != -1 && mv.SelectFirst.y != -1) {
				// 之前已经选中一个目标，这就是第二个目标
				mv.SelectTarget.x = pX;
				mv.SelectTarget.y = pY;
				int r1,c1,r2,c2;
				r1 = mv.SelectFirst.x;
				c1 = mv.SelectFirst.y;
				r2 = mv.SelectTarget.x;
				c2 = mv.SelectTarget.y;
				if (chkNeg(r1,c1,r2,c2) == true){
					//交换之前，停止接受玩家动作
					mv.actionFlag = false;
					//先交换，再判断能否消除，不能再交换回来
					goExchange(r1,c1,r2,c2);
					//交换的2个钻石，能否构成消除动作
					if(chkExchange(r1, c1) == true || chkExchange(r2,c2) == true){
						delDiamond(r1, c1);
						delDiamond(r2, c2);
						while(!noDeadDiamond()){
							addDiamond();	//新增宝石，删除已被消除的宝石
							autoDel();		//自动消除
						}
					}else{
						//不能消除，换回原位
						goExchange(r1, c1, r2, c2);
					}
					mv.SelectFirst.x = -1;
					mv.SelectFirst.y = -1;
					mv.SelectTarget.x = -1;
					mv.SelectTarget.y = -1;
					mv.actionFlag = true;
				} else {
					//不是邻居 不可以换位
					// 这次点击的不是上次目标的相邻的上、下、左、右，这次点击视为重新选择第一个目标
					mv.SelectFirst.x = pX;
					mv.SelectFirst.y = pY;
					mv.SelectTarget.x = -1;
					mv.SelectTarget.y = -1;
				}
			} // else if
		}
		
	}
	
	/**
	 * 有没有已被消除的宝石在地图上
	 * @return
	 */
	public boolean noDeadDiamond(){
		boolean result = true;
		for(int rr = rows-1; rr  >= 0; rr--){
			for(int cc = 0; cc <= cols-1; cc++){
				if(diamonds.diamonds[rr][cc].isDead){	
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 自动消除
	 */
	private void autoDel(){
		for(int rr = rows-1; rr  >= 0; rr--){
			for(int cc = 0; cc <= cols-1; cc++){
				if(diamonds.diamonds[rr][cc].isMove && !diamonds.diamonds[rr][cc].isDead
						 && !diamonds.diamonds[rr][cc].xuanKong){	//移动了位置的宝石
					delDiamond(rr, cc);	//消除相连的宝石
				}
			}
		}
	}
	
	private void addDiamond(){
		//从最下一行，到最上一行，isDead的宝石与上一个换位
		for(int r = rows -1; r>=0;  r--){
			for(int c=0; c <= cols-1; c++){
				boolean dm = diamonds.diamonds[r][c].isDead;
				if(dm){
					int x1,y1, x2, y2;
					if(r > 0){
						x1 = r;
						y1 = c;
						x2 = r -1;
						y2 = c;
						goExchange(x1, y1, x2, y2);
					}else if (r == 0){
						//顶行
						Diamond diamond = new Diamond();
						diamond.isMove =true;
						diamonds.diamonds[r][c] = diamond;
					}
				}//if
			}//for
		}
	}
	
	/**
	 * 消除相邻宝石
	 * 以宝石为中心，一直取到上下边界或不同的宝石或遇到悬空状态的或0，取到的宝石数大于2,这几个都消除（行列分别处理）
	 * @param r
	 * @param c
	 */
	private void delDiamond(int r, int c){
		int dm = diamonds.diamonds[r][c].type;
		int[] delRow = new int[cols];
		int[] delCol = new int[rows];
		int ri = 0;
		int ci = 0;
		if(c > 0){
			for(int i = c; i >= 0; i--){ //包括自己
				//往左
				int tmp = diamonds.diamonds[r][i].type;
				boolean stat = diamonds.diamonds[r][i].xuanKong;
				boolean isDead = diamonds.diamonds[r][i].isDead;
				if(tmp == dm && !stat && !isDead ){
					delRow[ri] = i+1;
					ri ++;
				}else{
					break;	//终止
				}
			}
		}
		if(c < cols -1){
			for(int i = c +1; i <= cols -1; i++){
				//往右
				int tmp = diamonds.diamonds[r][i].type;
				boolean stat = diamonds.diamonds[r][i].xuanKong;
				boolean isDead = diamonds.diamonds[r][i].isDead;
				if(tmp == dm && !stat && !isDead ){
					delRow[ri] = i+1;
					ri ++;
				}else{
					break;	//终止
				}
			}
		}//if
		if(r >= 0){
			for(int i = r; i>= 0; i--){  //包括自己
				//往上
				int tmp = diamonds.diamonds[i][c].type;
				boolean stat = diamonds.diamonds[i][c].xuanKong;
				boolean isDead = diamonds.diamonds[i][c].isDead;
				if(tmp == dm && !stat && !isDead ){
					delCol[ci] = i+1;
					ci ++;
				}else{
					break;	//终止
				}
			}
		}//if
		if(r < rows -1){
			for(int i = r+1; i<= rows -1; i++){
				//往下
				int tmp = diamonds.diamonds[i][c].type;
				boolean stat = diamonds.diamonds[i][c].xuanKong;
				boolean isDead = diamonds.diamonds[i][c].isDead;
				if(tmp == dm && !stat && !isDead ){
					delCol[ci] = i+1;
					ci ++;
				}else{
					break;	//终止
				}
			}
		}//if
		//消除
		int rok = 0;
		for(int i=0; i< delRow.length-1; i++){
			if(delRow[i] > 0){
				rok++;
			}
		}
		if(rok >= 3){
			for(int i = 0; i< rok; i++){
				int cc = delRow[i] -1;
				diamonds.diamonds[r][cc].setDead(true);
				mv.score += 10;
			}
		}
		rok = 0;
		for(int i=0; i< delCol.length-1; i++){
			if(delCol[i] > 0){
				rok++;
			}
		}
		if(rok >= 3){
			for(int i=0;i <rok;i++){
				int cc = delCol[i] -1;
				diamonds.diamonds[cc][c].setDead(true);
				mv.score += 10;
			}
		}
		
		//设置悬空
		setXuanKong();
	}
	
	/**
	 * 设置悬空状态
	 * 宝石相同列的上方宝石，都设置为悬空状态
	 * @param r
	 * @param c
	 */
	private void setXuanKong(){
		for(int cc = 0; cc<= cols-1;  cc++){
			for(int rr  = rows-1; rr>= 0; rr--){
				if(diamonds.diamonds[rr][cc].isDead){	//遇到一个已消除的宝石，其上面的宝石，都是悬空的,不能参加自动消除
					for(int trr = rr -1; trr >= 0; trr --){
						if(!diamonds.diamonds[trr][cc].isDead){	//非已经消除的宝石
							diamonds.diamonds[trr][cc].xuanKong = true;
						}
					}
					break;	//最底下一个已消除的宝石，其上所有都是悬空，一次就设置完了, 应该开始下一列处理
				}else{
					diamonds.diamonds[rr][cc].xuanKong = false;
				}
			}//for
		}//for
	}
	
	/**
	 * 检查2个物体是不是相邻的可交换物体
	 * @return
	 */
	private boolean chkNeg(int r1, int c1, int r2, int c2){
		boolean result = false;
		if(r1 == r2 && Math.abs(c1 - c2) == 1){
			//同一行的相邻
			return true;
		}else if(c1 == c2 && Math.abs(r1 - r2) == 1){
			//同一列的相邻
			return true;
		}
		
		return result;
	}
	
	
	/**
	 * 2个目标换位
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void goExchange(int x1, int y1, int x2, int y2){
		//Diamond dm = diamonds.diamonds[x1][y1];
		//diamonds.diamonds[x1][y1] = diamonds.diamonds[x2][y2];
		//diamonds.diamonds[x2][y2] = dm;
		int dmt1 = diamonds.diamonds[x1][y1].type;
		boolean isDead = diamonds.diamonds[x1][y1].isDead; 	//已消除
		diamonds.diamonds[x1][y1].type = diamonds.diamonds[x2][y2].type;
		diamonds.diamonds[x1][y1].isDead = diamonds.diamonds[x2][y2].isDead;
		diamonds.diamonds[x2][y2].type = dmt1;
		diamonds.diamonds[x2][y2].isDead = isDead;
		//改变悬空状态
		setXuanKong();
		//设置移动了位置
		diamonds.diamonds[x1][y1].isMove = true;
		diamonds.diamonds[x2][y2].isMove = true;
	}
	
	/**
	 * 检测能否换位
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private boolean chkExchange(int r, int c){
		boolean result = false;
		int dm = diamonds.diamonds[r][c].type;
		int[] delRow = new int[cols];
		int[] delCol = new int[rows];
		int ri = 0;
		int ci = 0;
		if(c > 0){
			for(int i = c; i >= 0; i--){ //包括自己
				//往左
				int tmp = diamonds.diamonds[r][i].type;
				boolean stat = diamonds.diamonds[r][i].xuanKong;
				boolean isDead = diamonds.diamonds[r][i].isDead;
				if(tmp == dm && !stat && !isDead ){
					delRow[ri] = i+1;
					ri ++;
				}else{
					break;	//终止
				}
			}
		}
		if(c < cols -1){
			for(int i = c +1; i <= cols -1; i++){
				//往右
				int tmp = diamonds.diamonds[r][i].type;
				boolean stat = diamonds.diamonds[r][i].xuanKong;
				boolean isDead = diamonds.diamonds[r][i].isDead;
				if(tmp == dm && !stat && !isDead ){
					delRow[ri] = i+1;
					ri ++;
				}else{
					break;	//终止
				}
			}
		}//if
		if(r >= 0){
			for(int i = r; i>= 0; i--){  //包括自己
				//往上
				int tmp = diamonds.diamonds[i][c].type;
				boolean stat = diamonds.diamonds[i][c].xuanKong;
				boolean isDead = diamonds.diamonds[i][c].isDead;
				if(tmp == dm && !stat && !isDead ){
					delCol[ci] = i+1;
					ci ++;
				}else{
					break;	//终止
				}
			}
		}//if
		if(r < rows -1){
			for(int i = r+1; i<= rows -1; i++){
				//往下
				int tmp = diamonds.diamonds[i][c].type;
				boolean stat = diamonds.diamonds[i][c].xuanKong;
				boolean isDead = diamonds.diamonds[i][c].isDead;
				if(tmp == dm && !stat && !isDead ){
					delCol[ci] = i+1;
					ci ++;
				}else{
					break;	//终止
				}
			}
		}//if

		int rok = 0;
		for(int i=0; i< delRow.length-1; i++){
			if(delRow[i] > 0){
				rok++;
			}
		}
		if(rok >= 3){
			return true;
		}
		rok = 0;
		for(int i=0; i< delCol.length-1; i++){
			if(delCol[i] > 0){
				rok++;
			}
		}
		if(rok >= 3){
			return true;
		}
	/*	Diamond[][] dms = new Diamond[12][10];
		for(int r = 0; r < 12; r++){
			for(int c = 0; c< 10 ; c++){
				dms[r][c] = diamonds.diamonds[r][c];
			}
		}
		//换位
		//Diamond dm = dms[x1][y1];
		//dms[x1][y1] = dms[x2][y2];
		//dms[x2][y2] = dm;
		//int dmt1 = dms[x1][y1].type;
		//int dmt2 = dms[x2][y2].type;
		//dms[x1][y1].type = dmt2;
		//dms[x2][y2].type = dmt1;
		
		int t1 = dms[x1][y1].type;
		int tl1 = dms[x1][y1-1].type;
		int tl2 = dms[x1][y1-2].type;
		if(t1 == tl1 && t1 == tl2){	result =  true; }
		int tr1 = dms[x1][y1+1].type;
		int tr2 = dms[x1][y1+2].type;
		if(t1 == tr1 && t1 == tr2) {	result =  true;}
		int tt1 = dms[x1-1][y1].type;
		int tt2 = dms[x1-2][y1].type;
		if(t1 == tt1 && t1 == tt2) {result =  true;}
		int tb1 = dms[x1+1][y1].type;
		int tb2 = dms[x1+2][y1].type;
		if(t1 == tb1 && t1 == tb2) {result =  true;}
		int tv1 = dms[x1-1][y1].type;
		int tv2 = dms[x1+1][y1].type;
		if(t1 == tv1 && t1 == tv2) {result =  true;}
		int th1 = dms[x1][y1-1].type;
		int th2 = dms[x1][y1+1].type;
		if(t1 == th1 && t1 == th2) {result =  true;}
		//另一点
		x1 = x2;
		y1 = y2;
		t1 = dms[x1][y1].type;
		tl1 = dms[x1][y1-1].type;
		tl2 = dms[x1][y1-2].type;
		if(t1 == tl1 && t1 == tl2){	result =  true; }
		tr1 = dms[x1][y1+1].type;
		tr2 = dms[x1][y1+2].type;
		if(t1 == tr1 && t1 == tr2) {result =  true;}
		tt1 = dms[x1-1][y1].type;
		tt2 = dms[x1-2][y1].type;
		if(t1 == tt1 && t1 == tt2) {result =  true;}
		tb1 = dms[x1+1][y1].type;
		tb2 = dms[x1+2][y1].type;
		if(t1 == tb1 && t1 == tb2) {result =  true;}
		tv1 = dms[x1-1][y1].type;
		tv2 = dms[x1+1][y1].type;
		if(t1 == tv1 && t1 == tv2) {result =  true;}
		th1 = dms[x1][y1-1].type;
		th2 = dms[x1][y1+1].type;
		if(t1 == th1 && t1 == th2) {result =  true;}*/
		
		return result;
	}
	
	
}
