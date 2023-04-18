public class Ladder {
	
	private int ladderId;
	private int topSquareId;
	private int bottomSquareId;
	private boolean broken;
	
	Ladder()
	{
		ladderId=0;
		topSquareId=0;
		bottomSquareId=0;
		broken=false;
	}
	
	Ladder(int ladderId,int topSquareId,int bottomSquareId,boolean broken)
	{
		this.ladderId=ladderId;
		this.topSquareId=topSquareId;
		this.bottomSquareId=bottomSquareId;
		this.broken=broken;
	}
	
	//Copies.
	Ladder(Ladder l) 
	{
		ladderId=l.ladderId;
		topSquareId=l.topSquareId;
		bottomSquareId=l.bottomSquareId;
		broken=l.broken;
	}
	
	public int getBottomSquareId() {
		return bottomSquareId;
	}
	
	public int getLadderId() {
		return ladderId;
	}
	
	public int getTopSquareId() {
		return topSquareId;
	}
	
	public boolean getBroken()
	{
		return broken;
	}
	
	public void setBottomSquareId(int bottomSquareId) {
		this.bottomSquareId = bottomSquareId;
	} 
	
	public void setBroken(boolean broken) {
		this.broken = broken;
	} 
	
	public void setLadderId(int ladderId) {
		this.ladderId = ladderId;
	}
	
	public void setTopSquareId(int topSquareId) {
		this.topSquareId = topSquareId;
	}	

}
