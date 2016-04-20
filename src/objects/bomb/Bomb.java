package objects.bomb;



public interface Bomb {
	
	

	public int[] bombPoint();
	public String bombName();
	public int bombId();
	public boolean checked();
	public int bombOwner();
	public void induce();

}
