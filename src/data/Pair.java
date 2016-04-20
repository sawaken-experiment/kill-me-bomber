package data;

public class Pair<T1, T2> {

	public T1 first;
	public T2 second;
	
	public Pair(){
		this(null, null);
	}
	
	public Pair(T1 first, T2 second){
		this.first = first;
		this.second = second;
	}
	public boolean eq(Pair<T1, T2> p){
		if(first == p.first && second == p.second)return true;
		return false;
	}
	public T1 x(){
		return first;
	}
	
	public T2 y(){
		return second;
	}
}
