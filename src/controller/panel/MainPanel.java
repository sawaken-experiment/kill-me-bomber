package controller.panel;

import graphic.Graphic;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import data.ApplicationFlags;
import listener.Keyboard;


public class MainPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	Graphic graphic;
	
	public MainPanel(){
		setPreferredSize(new Dimension(600,520));
		setFocusable(true);
		
		//アプリケーション統括フラグの初期化
		ApplicationFlags appflag = new ApplicationFlags();		
		
		//キーボードリスナ
		addKeyListener(new Keyboard(appflag));
		
		//グラフィックを作成してappflagに登録しておく
		graphic = new Graphic(appflag);
		appflag.setGraphic(graphic);
		
		//メインパネル(これ)の参照もappflagに登録しておく
		appflag.setMainPanel(this);
		

		//メニュー起動
		//Menu menu = 
		new Menu(appflag);
	}

	public void paintComponent(Graphics g){		
		graphic.letsPaint(g);
	}

	
}
