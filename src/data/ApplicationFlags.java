package data;

import graphic.Graphic;

import java.util.HashMap;

import controller.panel.MainPanel;

public class ApplicationFlags {

	//現在シーン
	int now_scene;
	
	//メニュー選択項目
	int how_many_chars;//キャラの人数(NPC含む)
	int how_many_players;//プレイヤーの人数
	
	//結果を格納
	int winner;
	
	//キー押下フラグ
	HashMap<Integer, Boolean> keyIsOn = new HashMap<Integer, Boolean>();
	
	//グラフィックインスタンス参照
	Graphic graphic;
	//メインパネル参照
	MainPanel mainPanel;
	
	public ApplicationFlags(){
		init();
	}
	
	public void init(){
		now_scene = Constant.TOP_MENU;
		how_many_chars = 4;
		how_many_players = 2;
		winner = Constant.NO_ONE;
	}
	
	
	
	
	public void setScene(int anker){
		now_scene = anker;
	}
	public int getScene(){
		return now_scene;
	}
	
	
	//キー押下管理
	public void KeyOn(int key){
		keyIsOn.put(key, true);
	}
	public void KeyOff(int key){
		keyIsOn.put(key, false);
	}
	public boolean KeyIs(int key){
		if(keyIsOn.containsKey(key)){
			return keyIsOn.get(key);
		}
		return false;
	}


	public int howMany(String key){
		if(key.equals("charas"))return how_many_chars;
		if(key.equals("players"))return how_many_players;
		
		return -1;
	}

	public void setWinner(int i){
		winner = i;
	}
	public int getWinner(){
		return winner;
	}

	//Graphic参照　ゲッターとセッター
	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}
	public Graphic getGraphic() {
		return graphic;
	}

	//MainPanel参照　ゲッターとセッター
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	public MainPanel getMainPanel() {
		return mainPanel;
	}
}