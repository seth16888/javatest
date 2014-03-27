package com.example.javatest;

/**
 * 根据地图，随机生成钻石，钻石地图位置的维护，钻石的位置交换，钻石的消除
 * @author seth16888
 *
 */
public class Diamonds {
	public Diamond[][] diamonds = new Diamond[12][10];
	public Diamonds() {
		initDiamonds();
	}
	
	private void initDiamonds(){
		Diamond[] dms = createDiamond(120);
		diamonds[0][0] =new Diamond(5);
		diamonds[0][1] =new Diamond(5);
		diamonds[0][2] =new Diamond(5);
		diamonds[0][3] =new Diamond(1);
		diamonds[0][4] =new Diamond(5);
		diamonds[0][5] =new Diamond(5);
		diamonds[0][6] =new Diamond(2);
		diamonds[0][7] =new Diamond(4);
		diamonds[0][8] =new Diamond(1);
		diamonds[0][9] =new Diamond(4);

		diamonds[1][0] =new Diamond(1);
		diamonds[1][1] =new Diamond(3);
		diamonds[1][2] =new Diamond(5);
		diamonds[1][3] =new Diamond(3);
		diamonds[1][4] =new Diamond(3);
		diamonds[1][5] =new Diamond(3);
		diamonds[1][6] =new Diamond(1);
		diamonds[1][7] =new Diamond(5);
		diamonds[1][8] =new Diamond(5);
		diamonds[1][9] =new Diamond(2);
		
		diamonds[2][0] =new Diamond(2);
		diamonds[2][1] =new Diamond(5);
		diamonds[2][2] =new Diamond(3);
		diamonds[2][3] =new Diamond(5);
		diamonds[2][4] =new Diamond(2);
		diamonds[2][5] =new Diamond(3);
		diamonds[2][6] =new Diamond(2);
		diamonds[2][7] =new Diamond(2);
		diamonds[2][8] =new Diamond(5);
		diamonds[2][9] =new Diamond(2);
		
		diamonds[3][0] =new Diamond(3);
		diamonds[3][1] =new Diamond(5);
		diamonds[3][2] =new Diamond(4);
		diamonds[3][3] =new Diamond(5);
		diamonds[3][4] =new Diamond(1);
		diamonds[3][5] =new Diamond(1);
		diamonds[3][6] =new Diamond(2);
		diamonds[3][7] =new Diamond(5);
		diamonds[3][8] =new Diamond(2);
		diamonds[3][9] =new Diamond(1);
		
		diamonds[4][0] =new Diamond(3);
		diamonds[4][1] =new Diamond(3);
		diamonds[4][2] =new Diamond(5);
		diamonds[4][3] =new Diamond(2);
		diamonds[4][4] =new Diamond(5);
		diamonds[4][5] =new Diamond(3);
		diamonds[4][6] =new Diamond(2);
		diamonds[4][7] =new Diamond(2);
		diamonds[4][8] =new Diamond(3);
		diamonds[4][9] =new Diamond(2);
		
		diamonds[5][0] =new Diamond(1);
		diamonds[5][1] =new Diamond(2);
		diamonds[5][2] =new Diamond(4);
		diamonds[5][3] =new Diamond(5);
		diamonds[5][4] =new Diamond(5);
		diamonds[5][5] =new Diamond(4);
		diamonds[5][6] =new Diamond(1);
		diamonds[5][7] =new Diamond(5);
		diamonds[5][8] =new Diamond(4);
		diamonds[5][9] =new Diamond(3);
		
		diamonds[6][0] =new Diamond(2);
		diamonds[6][1] =new Diamond(3);
		diamonds[6][2] =new Diamond(2);
		diamonds[6][3] =new Diamond(4);
		diamonds[6][4] =new Diamond(4);
		diamonds[6][5] =new Diamond(1);
		diamonds[6][6] =new Diamond(5);
		diamonds[6][7] =new Diamond(1);
		diamonds[6][8] =new Diamond(4);
		diamonds[6][9] =new Diamond(4);
		
		diamonds[7][0] =new Diamond(5);
		diamonds[7][1] =new Diamond(5);
		diamonds[7][2] =new Diamond(5);
		diamonds[7][3] =new Diamond(3);
		diamonds[7][4] =new Diamond(5);
		diamonds[7][5] =new Diamond(2);
		diamonds[7][6] =new Diamond(4);
		diamonds[7][7] =new Diamond(2);
		diamonds[7][8] =new Diamond(1);
		diamonds[7][9] =new Diamond(2);
		
		diamonds[8][0] =new Diamond(4);
		diamonds[8][1] =new Diamond(5);
		diamonds[8][2] =new Diamond(3);
		diamonds[8][3] =new Diamond(4);
		diamonds[8][4] =new Diamond(2);
		diamonds[8][5] =new Diamond(5);
		diamonds[8][6] =new Diamond(4);
		diamonds[8][7] =new Diamond(4);
		diamonds[8][8] =new Diamond(3);
		diamonds[8][9] =new Diamond(4);
		
		diamonds[9][0] =new Diamond(1);
		diamonds[9][1] =new Diamond(4);
		diamonds[9][2] =new Diamond(4);
		diamonds[9][3] =new Diamond(4);
		diamonds[9][4] =new Diamond(2);
		diamonds[9][5] =new Diamond(2);
		diamonds[9][6] =new Diamond(3);
		diamonds[9][7] =new Diamond(5);
		diamonds[9][8] =new Diamond(1);
		diamonds[9][9] =new Diamond(5);
		
		diamonds[10][0] =new Diamond(1);
		diamonds[10][1] =new Diamond(4);
		diamonds[10][2] =new Diamond(4);
		diamonds[10][3] =new Diamond(3);
		diamonds[10][4] =new Diamond(4);
		diamonds[10][5] =new Diamond(2);
		diamonds[10][6] =new Diamond(5);
		diamonds[10][7] =new Diamond(3);
		diamonds[10][8] =new Diamond(3);
		diamonds[10][9] =new Diamond(3);
		
		diamonds[11][0] =new Diamond(1);
		diamonds[11][1] =new Diamond(1);
		diamonds[11][2] =new Diamond(4);
		diamonds[11][3] =new Diamond(1);
		diamonds[11][4] =new Diamond(4);
		diamonds[11][5] =new Diamond(3);
		diamonds[11][6] =new Diamond(4);
		diamonds[11][7] =new Diamond(5);
		diamonds[11][8] =new Diamond(5);
		diamonds[11][9] =new Diamond(1);
		/*int ps = 0;
			for(int row=0;row<12;row++){
				for(int col=0;col<10;col++){
					diamonds[row][col] = dms[ps];
					ps++;
				}
			}*/
	}
	
	/**
	 * 随机生成钻石
	 * @param count
	 */
	public Diamond[] createDiamond(int count){
		Diamond[] diamonds= new Diamond[count];
		for(int i = 1; i <= count; i++){
			int rnd = (int)(Math.random() * 5 + 1);
			Diamond dm = new Diamond(rnd);
			diamonds[i-1] = dm;
		}
		return diamonds;
	}

}
