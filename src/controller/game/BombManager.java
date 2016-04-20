package controller.game;

import stage.Stage;
import data.ApplicationFlags;
import data.Constant;

public class BombManager {

	ApplicationFlags appflag;
	Stage stage;



	public BombManager(ApplicationFlags appflag, Stage stage) {
		this.appflag = appflag;
		this.stage = stage;
	}



	public void check() {
		
		//爆発待機中のボムを総当りで検査
		for(int i=0;i<stage.getBombNumber();i++){
			//チェック要請
			if(stage.getBomb(i).checked()){
				//爆発したらオブジェクトは削除する
				stage.delBomb(i);				
			}
		}
		//爆風を総当りで検査
		for(int i=0;i<stage.getFireNumber();i++){
			//チェック
			if(--stage.getFire(i).count < 0){
				stage.delFire(i);				
			}
		}
		//溶けてるブロックを総当りで検査
		for(int i=0;i<stage.getMeltBlockNumber();i++){
			//チェック
			stage.getMeltBlockRef(i).melting();
			if(stage.getMeltBlockRef(i).isBroken()){
				stage.getMeltBlockRef(i).init(Constant.FIELD_EMPTY);
			}
		}
		
		
	}






}
