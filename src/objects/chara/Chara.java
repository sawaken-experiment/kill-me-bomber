package objects.chara;

import java.util.Vector;

import objects.item.Item;

import stage.Stage;
import data.Constant;
import data.Pair;


public class Chara {

	Stage stage;
	//キャラのID
	int id;
	//キャラの種類
	String name;
	//位置(左上座標)
	int X;
	int Y;
	//位置(マス目)
	Pair<Integer, Integer> P1 = new Pair<Integer, Integer>(0,0);
	Pair<Integer, Integer> P2 = new Pair<Integer, Integer>(0,0);


	//状態
	int muki = Constant.DOWN;
	int changeCount = 0;
	int motion = Constant.W_BR;
	int walkCount = 0;

	//アイテム保持ベクター
	Vector<Item> itemBox = new Vector<Item>();

	//能力値
	int speed = 1;
	int fire = 3;
	int stock = 3;
	int bombType = Constant.BOMB_NORMAL;

	//死亡フラグ
	boolean die;
	int dyingCount = 20;
	boolean visible;
	
	public Chara(Stage stage, String name, int num){
		this.stage = stage;
		this.name = name;
		id = num;
		
		X = stage.startPoint(num, 0);
		Y = stage.startPoint(num, 1);
		
		//命を与える
		die = false;
		visible = true;
	}

	//デバッグ用
	public int showStock(){
		return stock;
	}

	/*
	 *　自身の移動命令をキャッチ
	 *　stageに当たり判定を問い合わせ、移動を完了させる
	 */
	public void move(int direct) {move(direct, direct);}
	public void move(int direct1, int direct2) {

		//死んでたら動けません
		if(die)return;
		
		int move_x = 0;
		int move_y = 0;
		int move_x1 = 0;
		int move_y1 = 0;

		int move_dist = speed*2;

		/*二方向の移動命令に対応するために二通りの当たり判定を行う
				・一つ目の結果はcd1, そのときの移動量はmove_x1,move_y1に退避させる(分かりにくいね)
				・二つ目の結果はcd2, そのときの移動量はそのままmove_x,move_yに残ってる(ややこしいね)
		*/
		int direct = direct1;
		boolean cd1=true, cd2=true;
		for(int i=0;i<2;i++){

			if(i == 1)direct = direct2;

			//方向
			if(direct == Constant.UP){
				move_x = 0;
				move_y = -move_dist;
			}
			else if(direct == Constant.DOWN){
				move_x = 0;
				move_y = move_dist;
			}
			else if(direct == Constant.RIGHT){
				move_x = move_dist;
				move_y = 0;
			}
			else if(direct == Constant.LEFT){
				move_x = -move_dist;
				move_y = 0;
			}
			else{return;}

			//当たり判定
			if(i == 0){
				cd1 = stage.CD(X + move_x, Y + move_y, X, Y);
				if(cd1){move_x1 = move_x;move_y1 = move_y;}
			}
			else cd2 =  stage.CD(X + move_x, Y + move_y, X, Y);
		}

		//

		//二つ目の方向には進めない場合、一つ目の方向だけを考慮する
		if(cd1 && !cd2){
			move_x = move_x1;
			move_y = move_y1;
			direct = direct1;
		}
		//どちらの方向にも行ける場合、現在向いていない方に行く (changeCountはマジキチな挙動を回避するための苦肉の策)
		else if((cd1 && cd2) && (direct1 != muki) && changeCount > 10){
			move_x = move_x1;
			move_y = move_y1;
			direct = direct1;
			changeCount = 0;
		}

		else if((cd1 && cd2) && (direct2 != muki)){
			if(changeCount > 10)changeCount = 0;
			else{
				move_x = move_x1;
				move_y = move_y1;
				direct = direct1;

			}
		}

		//方向が一つだけで、尚且つ進めない場合は、スライド処理を試す
		if(direct1 == direct2 && !cd2){
			int [] xy ={move_x, move_y};
			if(slide(xy, direct2, move_dist)){
				cd2 = true;
				direct = direct2;
				move_x = xy[0];
				move_y = xy[1];

			}
			//else direct = Constant.RIGHT;
		}


		//当たり判定を突破したなら移動
		if(cd1 || cd2){
			X = X + move_x;
			Y = Y + move_y;
			/*
			//アイテム加算
			if(cd.gotItem > 0){
				if(cd.gotItem == Constant.BOMB){item_bomb++;}
				else if(cd.gotItem == Constant.FIRE){item_fire++;}
			}

			//当たり判定時にマスを移動していたら、受け取った新マス目に更新
			if(cd.changed){
				P1 = cd.P1;
				P2 = cd.P2;
			}
			*/

		}

		//てくてく歩くための処理
		if(walkCount > 15){
			if(motion == Constant.W_BL)motion = Constant.W_L;
			else if(motion == Constant.W_L)motion = Constant.W_BR;
			else if(motion == Constant.W_BR)motion = Constant.W_R;
			else if(motion == Constant.W_R)motion = Constant.W_BL;
			walkCount = 0;
		}
		walkCount++;
		changeCount++;//方向転換の回数が多いと変なので制限を掛けるためのカウンタ

		//最後に向きを変える
		muki = direct;


	}

	//進行方向に壁の角がある時のスライド処理　(操作性向上のため)
	boolean slide(int[] xy, int direct, int dist){

		if(direct == Constant.DOWN || direct == Constant.UP){
			for(int i=dist;i<=20;i+=dist){
				if(stage.CD(X + xy[0]+i, Y + xy[1], X, Y)){ xy[0]+=dist; return true;}
				if(stage.CD(X + xy[0]-i, Y + xy[1], X, Y)){ xy[0]-=dist; return true;}
			}
		}
		if(direct == Constant.RIGHT || direct == Constant.LEFT){
			for(int i=dist;i<=20;i+=dist){
				if(stage.CD(X + xy[0], Y + xy[1]+i, X, Y)){ xy[1]+=dist; return true;}
				if(stage.CD(X + xy[0], Y + xy[1]-i, X, Y)){ xy[1]-=dist; return true;}
			}
		}
		return false;
	}
	
	//爆弾を産み落とす
	public void setBomb() {
		
		//死んでいたら爆弾は置けない
		if(die)return;
		
		if(stock > 0){
			if(stage.exeBomb(id, bombType, fire, X, Y))stock--;
		}
	}
	//ストックを１増やす(産んだ爆弾が爆発したら呼ばれる)
	public void incStock(){
		stock++;
	}
	//XとYのゲッター
	public int get(String key){

		if(key.equals("x")){
			return X;
		}else if(key.equals("y")){
			return Y;
		}else if(key.equals("muki")){
			return muki;
		}else if(key.equals("motion")){
			return motion;
		}


		return -1;
	}
	public String getName(){
		return name;
	}

	//死亡チェック
	//キャラの死亡判定の際にキャラマネージャーから呼ばれる
	public void ifDie(){
		if(stage.thereIsFire(X+(Constant.MASU)/2, Y+(Constant.MASU)/2)){
			die = true;
		}
	}
	public boolean isDie(){
		return die;
	}
	public boolean isRealDie(){
		if(dyingCount <= 0)return true;
		else return false;
	}
	public boolean dieCountDown(){
		
		//これ以上カウントダウンできないときは偽
		if(dyingCount <= 0)return false;
		//カウントダウンしてちょうど０になったら真
		else if(--dyingCount == 0)return true;
		//それ以外は偽
		else return false;
		
	}
	
	public void setVisible(boolean a){
		visible = a;
	}
	public boolean isVisible(){
		return visible;
	}
	



}
