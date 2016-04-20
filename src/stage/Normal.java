package stage;

import java.util.Random;


import data.Constant;


public class Normal extends Stage {
	
	//乱数
	Random rand = new Random();
	
	//フィールド
	int N = 15;//横の長さ
	int M = 13;//縦の長さ
	int field_setting [][] = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	Block field [][] = new Block[M][N];
	
	public Normal(){
		
		//フィールドの初期化
		for(int i=0;i<M;i++){
			for(int j=0;j<N;j++){
				
				//柱なら飛ばす
				if(field_setting[i][j] == 1){
					field[i][j] = new Block(Constant.FIELD_PILLAR);
					continue;
				}
				
				boolean flag = false;
				//キャラのいる場所は空けておく
				if(j==1){
					if(i==1 || i==2 || i==M-3 || i==M-2)flag = true;
				}else if(j==2){
					if(i==1 || i==M-2)flag = true;
				}else if(j==N-3){
					if(i==1  || i==M-2)flag = true;
				}else if(j==N-2){
					if(i==1 || i==2 || i==M-3 || i==M-2)flag = true;
				}
				if(flag)field[i][j] = new Block(Constant.FIELD_EMPTY);
				else if(rand.nextInt(10) > 3)field[i][j] = new Block(Constant.FIELD_BLOCK);
				else field[i][j] = new Block(Constant.FIELD_EMPTY);
			}
		}
	}

	
	//そのマス目にある障害物を特定 @オーバーライド
	public boolean thereIs(int i,int j){return thereIs(i, j, -1, -1, -1, -1);}//ボム設置の際にのみ呼び出されている模様
	public boolean thereIs(int i,int j, int x, int y){return thereIs(i, j, x, y, -1, -1);}//爆風探索の際にのみ呼び出されている模様
	public synchronized boolean thereIs(int i,int j, int x, int y, int centerI, int centerJ){	//キャラ当たり判定の際にのみ呼び出されている模様
		//柱がある場合は即座に判定
		if(stageMap(i, j) == 1){
			return true;
		}
		//ブロックがある場合もダメ
		if(stageMap(i, j) == Constant.FIELD_BLOCK){
			
			//爆風処理の際にブロックに当たった場合はその壁を消す
			if(x == Constant.NOT_CD_SELF_BOMB){
				field[i][j].startMelting();
				//meltBlock.add(field[i][j]);
				addMeltBlock(field[i][j]);
			}
			return true;
		}

		
		/////////////////////////ボム判定は他の判定と比べて必要なパラメータが異色なので、後で分離するつもり。あとごちゃごちゃし過ぎ。/////////////////////////
		//ボムがある場合の判定
		for(int k=0;k<bombs.size();k++){
			
			int res[] = bombs.elementAt(k).bombPoint();
			if(res[0] == i && res[1] == j){
				
				/*
				//キャラの現在地を引数に受け取った場合、現在上に乗っているボムに関しては透過する
				if(x > -1 && y > -1){
					
					//今自分がいる中心マスを計算
					int selfMasuJ = (x + (Constant.MASU/2)) / Constant.MASU;
					int selfMasuI = (y + (Constant.MASU/2)) / Constant.MASU;
					//処理中の爆弾が、今自分がいる中心マスと同じマスにある場合
					if(res[0] == selfMasuI && res[1] == selfMasuJ){
						//OK
					}
					//今いる中心マスと違うマスにボムがある場合
					else{
						//移動先の中心マスが、ボムのあるマスだったら衝突
						if(res[0] == centerI && res[1] == centerJ){
							return true;
						}
						//外のマスからくい込まないように、爆弾の完全外部(自分の四隅の点がいずれも爆弾のマスに所属しない)からは進入不可
						int cx_left    = x / 40;
						int cx_right   = (x + 40-1)/40;

						int cy_ue   = y / 40;
						int cy_sita = (y +40 -1)/40;
						
						boolean flag = false;
						if(j==cx_left && i==cy_ue)flag = true;
						if(j==cx_left && i==cy_sita)flag = true;
						if(j==cx_right && i==cy_ue)flag = true;
						if(j==cx_right && i==cy_sita)flag = true;
						
						//外から中には入れない
						if(!flag)return true;
					}
				//爆風の広がり探索用に、自分自身とは衝突判定せず、他の爆弾は誘爆するようにする処理
				}else*/ if(x == Constant.NOT_CD_SELF_BOMB){
					if(y == bombs.elementAt(k).bombId())continue;
					else{
						bombs.elementAt(k).induce();
						return true;
					}
				}//else return true;
				
			}
		}
		
		
		return false;
		
	}

	
	public int size(String str){
		if(str.equals("width"))return N;
		if(str.equals("height"))return M;
		return 0;
	}
	//フィールド上のマス(i,j)が何であるかを返す
	public int stageMap(int i, int j){
		return field[i][j].getType();
	}
	//そのマスのメルティングカウントを返す
	public int howMelt(int i, int j){
		return field[i][j].whichPhase();
	}
	public void setMap(int i, int j, int type){
		field[i][j] = new Block(type);
	}
	
	public int startPoint(int num, int which){
		if(num == 0){
			if(which == 0)return 40;
			if(which == 1)return 40;
		}
		if(num == 1){
			if(which == 0)return 40*(N-2);
			if(which == 1)return 40*(M-2);
		}
		if(num == 2){
			if(which == 0)return 40;
			if(which == 1)return 40*(M-2);
		}
		if(num == 3){
			if(which == 0)return 40*(N-2);
			if(which == 1)return 40;
		}
		
		return -1;
	}

}
