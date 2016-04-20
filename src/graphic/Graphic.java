package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.Vector;

import javax.swing.ImageIcon;

//import javax.swing.ImageIcon;

import objects.chara.Chara;
import stage.Stage;
import data.ApplicationFlags;
import data.Constant;

public class Graphic {

	Image img;
	Stage stage;
	ApplicationFlags appflag;
	
	Vector<Chara> cs = new Vector<Chara>();
	Chara chara1;
	boolean flag = false;
	
	//コンストラクタ
	public Graphic(ApplicationFlags appflag){
		this.appflag = appflag;
		
		ImageIcon imgicon = new ImageIcon(getClass().getClassLoader().getResource(Constant.IMGFILE));
		img = imgicon.getImage();
		
		//透過処理
		img = getAlphaImage(img,new Color(255,0,255));
		

	}
	
	//オブジェクトを画面表示状態にする
	public void setPaintObject(Object ob, String type){
		if(type.equals("Chara")) cs.add((Chara) ob);
		else if(type.equals("Stage")) stage = (Stage) ob;
	}
	//キャラのベクターを初期化する
	public void clearPaintChara() {
		cs = new Vector<Chara>();
	}
	//実描写
	public void letsPaint(Graphics g) {
		
		g.setColor(new Color(105, 183, 20));
		g.fillRect(0, 0, 600, 520);
		if(appflag.getScene() == Constant.TOP_MENU){
			
			//描写位置の左上の座標
			int dx1=110, dy1=40;
			//右下の座標
			int dx2=470, dy2=280;
			
			//元画像における位置
			int sx1=7*Constant.MASU,sy1=280;
			int sx2=10*Constant.MASU,sy2=360;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 28));
			g.drawString("PUSH ENTER", 215,320);
		}
		if(appflag.getScene() == Constant.SECOND_MENU){
			
			
			g.drawImage(img, 80, 80, 120, 140, 0, 0, 40, 60, null);
			g.drawImage(img, 80, 160, 120, 220, 0, 60, 40, 120, null);

			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.drawString("\"KILLER\" CONTROLLED BY YOU", 140,115);
			g.drawString("×3  \"NINJA\" USING \"KAGEBUNSHIN\" (CPU CONTROLLED)", 140,195);
			
			g.drawString("YOU CAN SET BOMB BY \"Z\" KEY", 170,300);
			
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 28));
			g.drawString("PUSH ENTER", 215,440);
		}
		else if(appflag.getScene() == Constant.GAME){
			//ステージ描写
			int height = stage.size("height");
			int width = stage.size("width");
			for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					
					int thisNum = stage.stageMap(i, j);
					if(thisNum == Constant.FIELD_EMPTY) drawPoint("floor", g, img, i, j);
					if(thisNum == Constant.FIELD_PILLAR) drawPoint("pillar", g, img, i, j);
					if(thisNum == Constant.FIELD_BLOCK){
						if(stage.howMelt(i, j) == Constant.MELT_NOT)drawPoint("block", g, img, i, j);
						else if(stage.howMelt(i, j) == Constant.MELT_BURNING)drawPoint("block-burning", g, img, i, j);
						else if(stage.howMelt(i, j) == Constant.MELT_BREAKING){
							drawPoint("floor", g, img, i, j);
							drawPoint("block-breaking", g, img, i, j);
						}
					}
				}
			}
			
			//爆風描写
			for(int i=0;i<stage.getFireNumber();i++){
				//String fireName = stage.getFire(i).fireName();
				drawFire(g, img, stage.getFire(i).i, stage.getFire(i).j, stage.getFire(i).direct, stage.getFire(i).isEnd);
			}
			
			//爆弾描写
			int BombsNum = stage.getBombNumber();
			for(int i=0;i<BombsNum;i++){
				//ボムの情報入手(配列の中身は{i,j})
				int [] bombData = stage.getBomb(i).bombPoint();
				String bombName = stage.getBomb(i).bombName();
				drawPoint(bombName, g, img, bombData[0],bombData[1]);
				//デバッグ用
				//g.setColor(Color.white);
				//g.drawString(""+stage.getBomb(i).bombId(),bombData[0]*Constant.MASU+20,  bombData[1]*Constant.MASU+20);
			}
			
			//キャラクター描写
			for(int i=0;i<cs.size();i++){
				Chara ch = cs.elementAt(i);
				
				//不可視になっていたらスキップ
				if(!ch.isVisible())continue;
				
				int x = ch.get("x");
				int y = ch.get("y");
				int muki = ch.get("muki");
				int motion = ch.get("motion");
				String name = ch.getName();
				drawChar(name, g, img, x, y, muki, motion);
			}
		
		//デバッグ用
		//g.setColor(Color.blue);
		//g.drawString(""+stage.chars[0].showStock(),275,210);
		}
		else if(appflag.getScene() == Constant.RESULT){
			g.setColor(Color.white);
			
			int winner = appflag.getWinner();
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 28));
			
			if(winner == -1)g.drawString("DRAW", 240,220);
			else if(winner == 0)g.drawString("YOU WIN" , 230,220);
			else g.drawString("YOU LOSE" , 230,220);
			
			g.drawString("PUSH ENTER TO REPLAY" , 120,440);
		}
	}
	
	//文字列キーとマス座標を受け取って描写する
	void drawPoint(String str, Graphics g, Image img, int i, int j){
		//描写位置の左上の座標
		int dx1=j*Constant.MASU, dy1=i*Constant.MASU;
		//右下の座標
		int dx2=dx1+Constant.MASU, dy2=dy1+Constant.MASU;
		
		if(str.equals("floor")){
			//元画像における位置
			int sx1=0,sy1=240;
			int sx2=40,sy2=280;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if(str.equals("pillar")){
			//元画像における位置
			int sx1=40,sy1=240;
			int sx2=80,sy2=280;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if(str.equals("block")){
			//元画像における位置
			int sx1=80,sy1=240;
			int sx2=120,sy2=280;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if(str.equals("block-burning")){
			//元画像における位置
			int sx1=120,sy1=240;
			int sx2=160,sy2=280;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if(str.equals("block-breaking")){
			//元画像における位置
			int sx1=160,sy1=240;
			int sx2=200,sy2=280;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
		else if(str.equals("Normal-Big")){
			//元画像における位置
			int sx1=0,sy1=280;
			int sx2=40,sy2=320;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

		}
		else if(str.equals("Normal-Small")){
			//元画像における位置
			int sx1=40,sy1=280;
			int sx2=80,sy2=320;

			g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

		}
		//デバッグ用
				
		//	g.setColor(Color.blue);
		//	g.drawString(str,dx1,dy1);
		
		

	}
	//爆風描写専用
	void drawFire(Graphics g, Image img, int i, int j, int direct, boolean isEnd){
		//描写位置の左上の座標
		int dx1=j*Constant.MASU, dy1=i*Constant.MASU;
		//右下の座標
		int dx2=dx1+Constant.MASU, dy2=dy1+Constant.MASU;
		
		int p1=0, p2=0;
		if(direct == Constant.CENTER){
			p2=0;
		}
		else if(direct == Constant.UP){
			if(isEnd)p2=4;
			else p2=2;
		}
		else if(direct == Constant.DOWN){
			if(isEnd)p2=6;
			else p2=2;
		}
		else if(direct == Constant.RIGHT){
			if(isEnd)p2=3;
			else p2=1;
		}
		else if(direct == Constant.LEFT){
			if(isEnd)p2=5;
			else p2=1;
		}
		//元画像における位置をセット
		int sx1=p2*Constant.MASU,sy1=320+p1*Constant.MASU;
		int sx2=sx1+Constant.MASU,sy2=sy1+Constant.MASU;
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	
	//キャラ名の点座標等からキャラクターを描写する
	void drawChar(String str, Graphics g, Image img, int x, int y , int muki, int motion){
		//描写位置の左上の座標
		int dx1=x, dy1=y+(Constant.MASU - Constant.C_H);
		//右下の座標
		int dx2=dx1+Constant.MASU, dy2=dy1+Constant.C_H;
		
		//元画像の位置を特定するためのゴニョゴニョ
		int p1;
		if(muki == Constant.DOWN)p1 = 0;
		else if(muki == Constant.UP)p1 = 1;
		else if(muki == Constant.RIGHT)p1 = 2;
		else p1 = 3;
		
		int p2;
		if(motion == Constant.W_BR || motion == Constant.W_BL)p2 = 0;
		else if(motion == Constant.W_R)p2 = 1;
		else p2 = 2;
		
		int p3;
		if(str.equals("sonya"))p3 = 0;
		else if(str.equals("agiri"))p3 = 60;
		else p3 = 1;
		
		//元画像における位置をセット
		int sx1=p1*(Constant.MASU *3)+p2*Constant.MASU,sy1=p3;
		int sx2=sx1+Constant.MASU,sy2=p3+Constant.C_H;
		
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		
	}
	
	
	//画像透過処理
	//書いてる余裕がないので以下のサイトより拝借
	//http://www.geocities.jp/ntaka329/java/faq/ques_image_alpha.html
	public Image getAlphaImage(Image img,Color alpha) {
		if (img == null) return null;
		int width = img.getWidth(null);
		int height = img.getHeight(null);
		//ピクセル値取得
		int[] pixel = new int[width*height];
		PixelGrabber pg = new PixelGrabber(img,0,0,width,height,pixel,0,width);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			return null;
		}
		//透明化
		int color = alpha.getRGB();
		for(int i=0;i<width*height;i++){
			if (pixel[i] == color) {
				pixel[i] = pixel[i] & 0x00FFFFFF;
			}
		}

		//イメージに戻す
		Image alphaImage = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixel, 0, width));
		return alphaImage;
	}


}
