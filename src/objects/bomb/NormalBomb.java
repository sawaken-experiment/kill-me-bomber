package objects.bomb;

import stage.Stage;
import data.Constant;

public class NormalBomb implements Bomb {
	
	Stage stage;
	
	int bombId;
	int waitCount;	
	int owner;
	int fire;
	boolean size = true;//trueなら大きい、falseなら小さい
	int I, J;
	

	public NormalBomb(Stage stage, int owner, int fire, int  i, int j, int bombId){
		//状態の初期化
		waitCount = Constant.BOMB_TIMER;
		this.stage = stage;
		this.fire = fire;
		this.owner = owner;
		this.bombId = bombId;
		I = i;
		J = j;
		

	}
	
	

	public boolean checked(){
		waitCount--;
		if(waitCount%50 == 0)size = !size;
		
		if(waitCount <= 0){
			startBurning(Constant.CENTER, I, J, fire);
			return true;
		}
		return false;
	}
	private void startBurning(int direct, int i, int j, int fire) {
		
		//火力がマイナスなら終了
		if(fire < 0)return;
		//(i,j)に障害物があったら終了
		if(stage.thereIs(i, j, Constant.NOT_CD_SELF_BOMB, bombId))return;
		
		if(direct == Constant.CENTER){
			startBurning(Constant.UP, i-1, j, fire-1);
			startBurning(Constant.RIGHT, i, j+1, fire-1);
			startBurning(Constant.DOWN, i+1, j, fire-1);
			startBurning(Constant.LEFT, i, j-1, fire-1);
		}else{
			int di=0, dj=0;
			if(direct == Constant.UP)di--;
			else if(direct == Constant.RIGHT)dj++;
			else if(direct == Constant.DOWN)di++;
			else if(direct == Constant.LEFT)dj--;
			startBurning(direct, i+di, j+dj, fire-1);
		}
		//端かどうかを変数に取る
		boolean isEnd = false;
		if(fire == 0)isEnd = true;
		//オブジェクトを作成してstageに送る
		stage.addFire(new Fire(direct, i, j, bombId, isEnd));
		
	}
	
	//誘爆
	public void induce(){
		if(waitCount > 5)waitCount=5;
	}
	
	
	
	
/////////////////////////////ゲッター///////////////////////////////
	public int[] bombPoint(){
		int [] res = {I, J};
		return res;	
	}
	public String bombName(){
		if(size)return "Normal-Big";
		else return "Normal-Small";
	}
	 
	public int bombOwner(){
		return owner;
	}
	public int bombId(){
		return bombId;
	}
	
}
