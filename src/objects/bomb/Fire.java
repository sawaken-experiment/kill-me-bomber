package objects.bomb;

import data.Constant;

public class Fire {

	public int bombId;
	public int direct;
	public boolean isEnd;
	public int count;
	public int i, j;
	
	public Fire(int direct, int i, int j, int bombId, boolean isEnd){
		this.bombId = bombId;
		this.direct = direct;
		this.i = i;
		this.j = j;
		this.isEnd = isEnd;
		count = Constant.FIRE_TIMER;
	}
	

}
