package data;

public class Constant {

	//画像データファイル名
	public static final String IMGFILE = "img/all.gif";

	//ゲームループの１フレームごとの時間(ms)
	public static final int GAME_LOOP_TIME = 10;
	//メニューの番号
	public static final int TOP_MENU = 1;
	public static final int SECOND_MENU =2;
	public static final int THIRD_MENU =3;
	public static final int GAME =4;
	public static final int RESULT =5;

	//キーコード独自定義(向きを表すマジック№にも使用)
	public static final int ENTER =1;
	public static final int CENTER =1;//向きだけに使用
	public static final int UP =2;
	public static final int DOWN =3;
	public static final int RIGHT =4;
	public static final int LEFT =5;
	public static final int Z=6;
	
	public static final int[] UPS  = {20,21,22,23,24};
	public static final int[] DOWNS  = {30,31,32,33,34};
	public static final int[] RIGHTS  = {40,41,42,43,44};
	public static final int[] LEFTS  = {50,51,52,53,54};
	public static final int[] ZS  = {60,61,62,63,64};

	//アイテムコード
	public static final int BOMB = 1;
	public static final int FIRE = 2;
	//キャラクター設定
	public static final int CHAR_DEPTH = 20;
	public static final int CHAR_WIDTH = 40;
	public static final int CHAR_HEIGHT = 40;
	//歩行アニメーション用定数(どちらの足を出しているか)
	public static final int W_BR = 0;
	public static final int W_R = 1;
	public static final int W_BL = 2;
	public static final int W_L = 3;

	//フィールドのマスの大きさ
	public static final int MASU = 40;
	public static final int C_H = 60;

	//フィールドコード
	public static final int FIELD_EMPTY = 0;
	public static final int FIELD_PILLAR = 1;
	public static final int FIELD_BLOCK = 2;
	
	//ボムの種類コード
	public static final int BOMB_NORMAL = 11;
	public static final int BOMB_KANTU = 12;
	
	//ボムの状態
	//public static final int BOMB_WAIT = 1;
	//public static final int BOMB_BURST = 2;

	//ボムの状態(表示のときの大きさ)
	//public static final int BOMB_SMALL = 1;//(使ってない？)
	//public static final int BOMB_BIG = 2;//(使ってない？)
	
	//ボムが爆発するまでの時間
	public static final int BOMB_TIMER= 200;
	public static final int FIRE_TIMER = 50;
	public static final int NOT_CD_SELF_BOMB = -10;

	//死亡判定
	public static final int NO_ONE = -1;
	
	//ブロックの溶解段階
	public static final int MELT_NOT = 0;
	public static final int MELT_BURNING = 1;
	public static final int MELT_BREAKING = 2;

}
