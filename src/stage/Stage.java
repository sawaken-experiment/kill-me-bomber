package stage;


import java.util.Vector;
import objects.bomb.Bomb;
import objects.bomb.Fire;
import objects.bomb.NormalBomb;
import objects.chara.Chara;
import data.Constant;

public class Stage {

	//キャラクターオブジェクト格納(最大５つ)
	public Chara chars[] = new Chara[5];//デバッグ用にpublicにしてあるので、後で外す
	
	//アイテムオブジェクト格納用ベクター

	//ボムオブジェクト格納用ベクター
	Vector<Bomb> bombs;
	//爆風格納用ベクター
	Vector<Fire> fires;
	//= new Vector<Fire>();
	
	//高速化のために配列、爆風があるかないかだけ管理する
	int danger[][] = new int [30][30]; 
	
	//溶解途中ブロック格納用
	Vector<Block> meltBlock;

	//int field [][];
	
	//ボムに通し番号を付けるため、IDを保持する
	int bombId = 0;

	public Stage(){
		bombs = new Vector<Bomb>();
		fires = new Vector<Fire>();
		meltBlock = new Vector<Block>();
	}

	
	//新規キャラクターをセット
	public Chara newChar(int num, String type, String name){
		if(type.equals("player")){
			chars[num] = new Chara(this, name, num);
		}
		
		else if(type.equals("NPC")){
			chars[num] = new Chara(this, name, num);
		}
		
		return chars[num];
	}

	//キャラの左上の座標からの当たり判定
	////////////////////////////thereIs()との噛み合わせが非常に悪くなってきたので、大幅に変更する必要がある
	////////////////////////////具体的には、ボムの透過判定はここでやってしまい、thereIs()からボムの透過処理を削除する
	public boolean CD(int x, int y, int X, int Y) {

		x +=0;
		y +=0;
		int yoko = 40;
		int tate = 40;

		int cx_left    = x / 40;
		int cx_right   = (x + yoko-1)/40;

		int cy_ue   = y / 40;
		int cy_sita = (y +tate -1)/40;

		boolean res = true;

		int centerJ = (x + (Constant.MASU/2)) / Constant.MASU;
		int centerI = (y + (Constant.MASU/2)) / Constant.MASU;
		if(thereIs(cy_ue, cx_left, X, Y, centerI, centerJ))res = false;//左上
		if(thereIs(cy_ue, cx_right, X, Y, centerI, centerJ))res = false;//右上
		if(thereIs(cy_sita, cx_left, X, Y, centerI, centerJ))res = false;//左下
		if(thereIs(cy_sita, cx_right, X, Y, centerI, centerJ))res = false;//右下
		
		//ボム判定
		

		return res;
	}

	//ボムの設置命令を受け取り、その位置に置けるかどうかを判定し、置けるならオブジェクトを生成
	public synchronized boolean exeBomb(int owner, int bombType, int fire, int x, int y) {

		//受け取った左上の座標からマス目を求める
		int masuI = (y+(Constant.CHAR_WIDTH/2)) / Constant.MASU;
		int masuJ = (x+(Constant.CHAR_HEIGHT/2)) / Constant.MASU;

		if(!thereIs(masuI, masuJ)){

			//ボムを追加
			if(++bombId > 100)bombId = 1;
			bombs.add(new NormalBomb(this, owner, fire, masuI, masuJ, bombId));

			return true;
		}else {
			return false;
		}

	}
	///////////////ボム格納ベクターへの処理/////////////////
	public Bomb getBomb(int index){
		return bombs.elementAt(index);
	}
	public int getBombNumber(){
		return bombs.size();
	}
	public void delBomb(int i) {
		int owner = bombs.elementAt(i).bombOwner();
		bombs.remove(i);
		chars[owner].incStock();
	}
	///////////////爆風格納ベクター及び危険地帯格納配列への処理/////////////////
	public void addFire(Fire fire){
		danger[fire.i][fire.j] = fire.bombId;
		fires.add(fire);
	}
	public Fire getFire(int index){
		return fires.elementAt(index);
	}
	public int getFireNumber(){
		return fires.size();
	}
	public void delFire(int index) {
		int i = fires.elementAt(index).i, j = fires.elementAt(index).j;
		if(danger[i][j] == fires.elementAt(index).bombId)danger[i][j] = 0;
		fires.remove(index);
	}
	public boolean thereIsFire(int x, int y){
		if(danger[y / 40][x / 40] > 0)return true;
		return false;
	}
	///////////////溶解ブロック格納ベクターへの処理/////////////////
	public void addMeltBlock(Block block){
		meltBlock.add(block);
	}
	public int getMeltBlockNumber(){
		return meltBlock.size();
	}
	public Block getMeltBlockRef(int i) {
		Block block = meltBlock.elementAt(i);
		return block;
	}

	
///////////////////////////////////////抽象メソッド/////////////////////////////////////
	
	//そのマス目にある障害物を特定(実体なし)
	public boolean thereIs(int i,int j, int x, int y, int centerI, int centerJ){return true;}
	public boolean thereIs(int i,int j, int x, int y){return true;}
	public boolean thereIs(int i,int j){return true;}
	//ステージの縦横サイズ(実体なし)
	public int size(String str){return -1;}
	//ステージの情報を返す(実体なし)
	public int stageMap(int i, int j){return -1;}
	//スタート地点を返す
	public int startPoint(int num, int which){return -1;}
	//そのマスのメルティングカウントを返す
	public int howMelt(int i, int j){return -1;}


}
