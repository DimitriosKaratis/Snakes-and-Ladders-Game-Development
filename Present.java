public class Present {
	
	private int presentId;
	private int presentSquareId;
	private int points;
	
	Present()
	{
		presentId=0;
		presentSquareId=0;
		points=0;
	}
	
	Present(int presentId,int presentSquareId,int points)
	{
		this.presentId=presentId;
		this.presentSquareId=presentSquareId;
		this.points=points;
	}
	
	//Copies.
	Present(Present p) 
	{
		presentId=p.presentId;
		presentSquareId=p.presentSquareId;
		points=p.points;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getPresentId() {
		return presentId;
	}
	
	public int getPresentSquareId() {
		return presentSquareId;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void setPresentId(int presentId) {
		this.presentId = presentId;
	}
	
	public void setPresentSquareId(int presentSquareId) {
		this.presentSquareId = presentSquareId;
	}

}
