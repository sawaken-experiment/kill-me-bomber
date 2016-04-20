package controller.game;

import java.awt.event.KeyEvent;
import java.util.Random;

import objects.chara.Chara;
import stage.Stage;
import data.ApplicationFlags;
import data.Constant;

public class AI implements Runnable {

	ApplicationFlags appflag;
	Stage stage;
	Chara body;
	int myNum;
	//乱数
	Random rand = new Random();
	
	public AI(Chara body, Stage stage, ApplicationFlags appflag, int num){
		this.body = body;
		this.appflag = appflag;
		this.stage = stage;
		this.myNum = num;
		
		Thread thread = new Thread(this);
		thread.start();
	}


	public void run() {
		
		int count = 0;
		int direct =2;
		while(true){

			if(count%20 ==0){
				direct = rand.nextInt(4) + 2;
				appflag.KeyOff(Constant.UPS[myNum]);
				appflag.KeyOff(Constant.DOWNS[myNum]);
				appflag.KeyOff(Constant.RIGHTS[myNum]);
				appflag.KeyOff(Constant.LEFTS[myNum]);
				appflag.KeyOff(Constant.ZS[myNum]);
			}
			if(direct == Constant.UP)appflag.KeyOn(Constant.UPS[myNum]);
			if(direct == Constant.DOWN)appflag.KeyOn(Constant.DOWNS[myNum]);
			if(direct == Constant.RIGHT)appflag.KeyOn(Constant.RIGHTS[myNum]);
			if(direct == Constant.LEFT)appflag.KeyOn(Constant.LEFTS[myNum]);
			
			if(body.isDie())break;
			if(appflag.getScene() != Constant.GAME)break;
				
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(++count > 1000)count=1;
		}
		
	}
}
