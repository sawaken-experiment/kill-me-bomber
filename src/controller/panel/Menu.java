package controller.panel;

import controller.game.GameManager;
import data.ApplicationFlags;
import data.Constant;

public class Menu implements Runnable {

	ApplicationFlags appflag;

	public Menu(ApplicationFlags appflag){
		this.appflag = appflag;

		Thread thread = new Thread(this);
		thread.start();
	}


	public void run() {
		
		while(true){
			
			if(appflag.getScene() == Constant.TOP_MENU)Menu1();
			else if(appflag.getScene() == Constant.SECOND_MENU)Menu2();
			else if(appflag.getScene() == Constant.THIRD_MENU)StartGame();

				
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	void Menu1(){
		
		if(appflag.KeyIs(Constant.ENTER)){
			appflag.setScene(Constant.SECOND_MENU);
			appflag.KeyOff(Constant.ENTER);
		}
		appflag.getMainPanel().repaint();
	}

	void Menu2(){

		if(appflag.KeyIs(Constant.ENTER)){
			appflag.setScene(Constant.THIRD_MENU);
			appflag.KeyOff(Constant.ENTER);

		}
		appflag.getMainPanel().repaint();
	}

	void StartGame(){
		appflag.setScene(Constant.GAME);
		new GameManager(appflag);
	}
}
