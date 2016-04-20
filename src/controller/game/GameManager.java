package controller.game;

import controller.panel.Menu;
import stage.*;
import data.ApplicationFlags;
import data.Constant;

public class GameManager implements Runnable{

	ApplicationFlags appflag;

	CharManager charManager;
	BombManager bombManager;
	Stage stage;

	public GameManager(ApplicationFlags appflag){
		this.appflag = appflag;
		
		

		//データ管理オブジェクトの初期化

		stage = new Normal();
		appflag.getGraphic().setPaintObject(stage, "Stage");
		charManager = new CharManager(appflag, stage);
		bombManager = new BombManager(appflag, stage);		

		Thread thread = new Thread(this);
		thread.start();

	}

	void loop(){

		if(appflag.getScene() == Constant.GAME){
			charManager.check();
			bombManager.check();
		}
		appflag.getMainPanel().repaint();
	}



	public void run(){
		
		long startTime,endTime,spentTime, waitTime;
		long overTime = 0;
		
		//デバッグ用　ループ時間測定器
		long loopTime;
		long[] timestock = new long[10];
		int index = 0;
		
		while(true){
			
			startTime = System.nanoTime();
					
			//結果画面ならエンターで初期化
			if(appflag.getScene() == Constant.RESULT){
				if(appflag.KeyIs(Constant.ENTER)){
					appflag.KeyOff(Constant.ENTER);
					appflag.init();
					break;
				}
			}
				
			loop();
			
			//メイン処理にかかった時間(ms)
			endTime = System.nanoTime();
			spentTime = (endTime - startTime)/1000000;

			//sleep時間計算
			waitTime = Constant.GAME_LOOP_TIME - spentTime - overTime;
			if(waitTime < 2)waitTime = 2;
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			overTime = (System.nanoTime() - startTime)/1000000  - Constant.GAME_LOOP_TIME;
			
			/*デバッグ用
			timestock[index] = overTime;
			if(++index == 10){
				long sum = 0;
				
				for(int i=0;i<10;i++)sum += timestock[i];
				index = 0;
				System.out.println(sum/10);
			}
			*/

		}
		

	}
}
