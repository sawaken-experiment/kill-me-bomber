package controller.panel;

import java.awt.Container;
import javax.swing.JFrame;

public class Bomber extends JFrame{

	//eclipseエラー回避
	private static final long serialVersionUID = 1L;

	public Bomber(){
		super("KILL ME BOMBER Var0.00(β)");
		MainPanel panel = new MainPanel();
		Container container = getContentPane();
		container.add(panel);
		pack();
	}
	
	
	public static void main(String [] args){
		Bomber game = new Bomber();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	}
	
}
