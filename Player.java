public class Player {
	
	private int playerId;
	private String name;
	private int score;
	private Board board;
	
	//Value responsible for printing the rounds of the game (see last method of this class).
	int n=0; 
	
	Player()
	{
		playerId=0;
		name="";
		score=0;
		board=null;
	}
	
	Player(int playerId,String name,int score,Board board)
	{
		this.playerId=playerId;
		this.name=name;
		this.score=score;
		this.board=board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public void setScore(int score) {
		this.score = score;
	}	
	
	//Returns an array of integers that contains the id of the square that the
	//player is located after his move,the number of snake heads that bit him,
	//the number of ladders that he climbed,and the number of presents he got during that move.
	//Parameters: The id of the square that the player is currently on
	//as well as the value of the dice that he rolled.
	int [] move(int id,int die)
	{		
		int mat[]=new int[4];
		int next=0;
		int sncount=0,ladcount=0,prcount=0;
		
		next=id+die;		
		
		for(int i=0;i<board.getSnakes().length;i++)
		{
			//If the next move has a snakeHead go to snakeTail and increment
			//the variable responsible for the number of snakes that bit him by one.
			if(next==board.getSnakes()[i].getHeadId())
			{
				next=board.getSnakes()[i].getTailId();
				sncount++;
			}			
		}
		
		for(int i=0;i<board.getLadders().length;i++)
		{
			//If the next move has a ladderBottomSquare go to ladderTopSquare, increment
			//the variable responsible for the number of ladders that he climbed by one
			//and lastly make sure that the ladder cannot be used again by any of the players.
			if(next==board.getLadders()[i].getBottomSquareId())
			{
				//If the ladder has'nt been taken by another or the same player again.
				if(board.getLadders()[i].getBroken()!=true)
				{
					next=board.getLadders()[i].getTopSquareId();
					ladcount++;
					board.getLadders()[i].setBroken(true);
				}				
			}
		}
		
		for(int i=0;i<board.getPresents().length;i++)
		{
			//Sets the number of points that each present gives.
			board.getPresents()[i].setPoints(10);			
			
			//If the next move has a present, get present points, increment
			//the variable responsible for the number of presents he got by one
			//and lastly make sure that the present is deleted from the board.
			if(next==board.getPresents()[i].getPresentSquareId())
			{	
				//If the present has'nt been taken by another or the same player again.
				if(board.getPresents()[i].getPoints()!=0)
				{
					prcount++;
					score=score+board.getPresents()[i].getPoints();
					board.getPresents()[i].setPoints(0);
					break;
				}				
			}
		}		
		
		//Initialize the matrix with the correct values and return it.
		mat[0]=next;
		mat[1]=sncount;
		mat[2]=ladcount;
		mat[3]=prcount;
		
		return mat;		
	}
	
	//Function that does exactly the same as the previous one
	//but also prints the corresponding messages.
	//(To print the rounds we use variable n form above).
	int [] moveWithPrint(int id,int die)
	{
		int mat[]=new int[4];
		int next=0;
		int sncount=0,ladcount=0,prcount=0;
		
		next=id+die;
		
		for(int i=0;i<board.getSnakes().length;i++)
		{
			if(next==board.getSnakes()[i].getHeadId())
			{
				next=board.getSnakes()[i].getTailId();
				sncount++;
			}			
		}
		
		for(int i=0;i<board.getLadders().length;i++)
		{
			if(next==board.getLadders()[i].getBottomSquareId())
			{
				if(board.getLadders()[i].getBroken()!=true)
				{
					next=board.getLadders()[i].getTopSquareId();
					ladcount++;
					board.getLadders()[i].setBroken(true);					
				}
			}
		}
		
		for(int i=0;i<board.getPresents().length;i++)
		{
			board.getPresents()[i].setPoints(10);			
			
			if(next==board.getPresents()[i].getPresentSquareId())
			{	
				if(board.getPresents()[i].getPoints()!=0)
				{
					prcount++;
					score=score+board.getPresents()[i].getPoints();
					board.getPresents()[i].setPoints(0);
					break;
				}				
			}
		}
		
		mat[0]=next;
		mat[1]=sncount;
		mat[2]=ladcount;
		mat[3]=prcount;
		
		System.out.println();
		
		System.out.println(getName()+" rolled a "+die+" on the dice in round "+(n+1)+".");
		System.out.println("He got bit by "+mat[1]+" snake(s).");
	    System.out.println("He went up "+mat[2]+" ladder(s).");
	    System.out.println("He got "+mat[3]+" present(s).");		 
	    System.out.println("Current position: "+mat[0]+".");
		n++;
		
		return mat;
	}
}
