package stage;

import data.Constant;

public class Block {

	int type;
	
	//壊れるブロック用設定
	boolean isMelting;
	int meltingCount;
	int meltPhase;
	
	public Block(int type){
		init(type);
	}
	
	public void init(int type){
		this.type = type;
		isMelting = false;
		meltingCount = 40;
		meltPhase = Constant.MELT_NOT;
	}
	
	//タイプを返す(壁、空白、ブロック等)
	public int getType(){
		return type;
	}
	//溶けている最中かどうかを返す(使ってない？)
	public boolean isMelting(){
		return isMelting;
	}
	//完全に壊れきっているかを返す
	public boolean isBroken(){
		if(meltingCount <= 0)return true;
		return false;
	}
	//溶かし始める
	public void startMelting(){
		isMelting = true;
		meltPhase = Constant.MELT_BURNING;
	}
	//溶解の段階を返す
	public int  whichPhase(){
		return meltPhase;
	}
	//溶けている最中の場合、カウントを減らす
	public boolean melting(){
		if(type != Constant.FIELD_BLOCK)return false;
		if(!isMelting)return false;
		if(meltingCount <= 0)return false;
		
		meltingCount--;
		if(meltingCount < 10)meltPhase = Constant.MELT_BREAKING;
		return true;
		
	}
	
}
