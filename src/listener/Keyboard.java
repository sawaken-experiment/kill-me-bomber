package listener;

import java.awt.event.*;

import data.ApplicationFlags;
import data.Constant;

public class Keyboard implements KeyListener{
	ApplicationFlags appflag;
	
	public Keyboard(ApplicationFlags appflag){
		this.appflag = appflag;
	}
	

	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		 
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		                                  
		if(key == KeyEvent.VK_SPACE){/*System.exit(0);*/}
		else if(key == KeyEvent.VK_ENTER){
			appflag.KeyOn(Constant.ENTER);
		}
		else if(key == KeyEvent.VK_UP){
			appflag.KeyOn(Constant.UPS[0]);
		}
		else if(key == KeyEvent.VK_DOWN){
			appflag.KeyOn(Constant.DOWNS[0]);
		}
		else if(key == KeyEvent.VK_RIGHT){
			appflag.KeyOn(Constant.RIGHTS[0]);
		}
		else if(key == KeyEvent.VK_LEFT){
			appflag.KeyOn(Constant.LEFTS[0]);
		}
		else if(key == KeyEvent.VK_Z){
			appflag.KeyOn(Constant.ZS[0]);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ENTER){
			appflag.KeyOff(Constant.ENTER);
		}
		else if(key == KeyEvent.VK_UP){
			appflag.KeyOff(Constant.UPS[0]);
		}
		else if(key == KeyEvent.VK_DOWN){
			appflag.KeyOff(Constant.DOWNS[0]);
		}
		else if(key == KeyEvent.VK_RIGHT){
			appflag.KeyOff(Constant.RIGHTS[0]);
		}
		else if(key == KeyEvent.VK_LEFT){
			appflag.KeyOff(Constant.LEFTS[0]);
		}
		else if(key == KeyEvent.VK_Z){
			appflag.KeyOff(Constant.ZS[0]);
		}
	}
	
	
	

}
