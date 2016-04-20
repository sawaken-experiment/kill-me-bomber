package controller.game;



import objects.chara.*;
import stage.Stage;
import data.ApplicationFlags;
import data.Constant;

public class CharManager {

	//受け取り用参照変数
	int charaNum;
	//死体の数
	int deadNum=0;
	
	Chara chars[] = new Chara[5];	

	
	
	ApplicationFlags appflag;
	Stage stage;
	
	public CharManager(ApplicationFlags appflag, Stage stage){
		
		//オブジェクトの参照受け取り
		this.appflag = appflag;
		this.stage = stage;
		
		//キャラの人数を取得
		charaNum = appflag.howMany("charas");
		
		//グラフィックに登録されているオブジェクトを初期化
		appflag.getGraphic().clearPaintChara();
		
		String[] char_name = new String[5];
		String[] char_brain = new String[5];
		char_name[0] = "sonya"; char_brain[0] = "player";
		char_name[1] = "agiri"; char_brain[1] = "NPC";
		char_name[2] = "agiri"; char_brain[2] = "NPC";
		char_name[3] = "agiri"; char_brain[3] = "NPC";
		for(int i=0;i<charaNum;i++){
			//stage上にキャラ(0番)オブジェクトを作成
			chars[i] = stage.newChar(i, char_brain[i] , char_name[i]);
			appflag.getGraphic().setPaintObject(chars[i], "Chara");
			if(char_brain[i].equals("NPC")){
				new AI(chars[i], stage, appflag, i);
			}
		}
		
	}
	
	public void check(){
		keyCheck();
		dieCheck();
		
		//if(chars[0].get_location(1) >= 100){System.exit(0);}
		//if(chars[0].get_location(0) >= 100){System.exit(0);}
	}
	public void dieCheck(){
		
		//デバッグ用
		if(appflag.getWinner() != Constant.NO_ONE)return;
		
		for(int i=0;i<charaNum;i++){
			
			//まだ死んでないなら死亡判定
			if(!chars[i].isDie()) chars[i].ifDie();
			//もう死んでるならカウントダウン(カウントが０になったら消滅)
			else if(chars[i].dieCountDown()){
				chars[i].setVisible(false);
				deadNum++;
				//暫定設定(1Pが死んだらゲームオーバー)
				if(i == 0){
					appflag.setWinner(2);
					appflag.setScene(Constant.RESULT);
				}
			}
			
		}
		
		//生存者が一人になったら
		if(charaNum - deadNum < 2){
			
			//生きてる奴を特定(誰も生きてなかったら-1)
			int suvivor = Constant.NO_ONE;
			for(int i=0;i<charaNum;i++){
				if(!chars[i].isDie())suvivor = i;
			}
			appflag.setWinner(suvivor);
			appflag.setScene(Constant.RESULT);
		}
		
	}
	public void keyCheck(){
		
		for(int i=0;i<charaNum;i++){	
			//組み合わせ４方向
			if(appflag.KeyIs(Constant.UPS[i]) && appflag.KeyIs(Constant.RIGHTS[i]))		chars[i].move(Constant.UP, Constant.RIGHT);
			else if(appflag.KeyIs(Constant.UPS[i]) && appflag.KeyIs(Constant.LEFTS[i]))		chars[i].move(Constant.UP, Constant.LEFT);
			else if(appflag.KeyIs(Constant.DOWNS[i]) && appflag.KeyIs(Constant.RIGHTS[i]))	chars[i].move(Constant.DOWN, Constant.RIGHT);
			else if(appflag.KeyIs(Constant.DOWNS[i]) && appflag.KeyIs(Constant.LEFTS[i]))	chars[i].move(Constant.DOWN, Constant.LEFT);
	
			//単純４方向
			else if(appflag.KeyIs(Constant.UPS[i]))		chars[i].move(Constant.UP);
			else if(appflag.KeyIs(Constant.DOWNS[i]))	chars[i].move(Constant.DOWN);
			else if(appflag.KeyIs(Constant.RIGHTS[i]))	chars[i].move(Constant.RIGHT);
			else if(appflag.KeyIs(Constant.LEFTS[i]))	chars[i].move(Constant.LEFT);
			
			//1Pの爆弾設置
			if(appflag.KeyIs(Constant.ZS[i])){
				appflag.KeyOff(Constant.ZS[i]);
				chars[i].setBomb();
			}
		}
		
		if(appflag.KeyIs(Constant.ENTER))chars[1].move(Constant.DOWN);
	}
}
