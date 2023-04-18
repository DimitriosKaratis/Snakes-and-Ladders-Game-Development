public class Snake {
	
	private int snakeId;
	private int headId;
	private int tailId;
	
	Snake()
	{
		snakeId=0;
		headId=0;
		tailId=0;
	}
	
	Snake(int snakeId,int headId,int tailId)
	{
		this.tailId=tailId;
		this.headId=headId;
		this.snakeId=snakeId;
	}
	
	//Copies.
	Snake(Snake s) 
	{
		snakeId=s.snakeId;
		headId=s.headId;
		tailId=s.tailId;
	}
	
	public int getHeadId() {
		return headId;
	}
	
	public int getSnakeId() {
		return snakeId;
	}
	
	public int getTailId() {
		return tailId;
	}
	
	public void setHeadId(int headId) {
		this.headId = headId;
	}
	
	public void setSnakeId(int snakeId) {
		this.snakeId = snakeId;
	}
	
	public void setTailId(int tailId) {
		this.tailId = tailId;
	}	

}
