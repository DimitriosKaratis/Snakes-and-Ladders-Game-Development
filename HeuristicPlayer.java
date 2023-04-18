import java.util.ArrayList;

public class HeuristicPlayer extends Player{
	
	//ArrayList of integer arrays,one for each HeuristicPlayer's move.
	//Provides information for the dice,the number of points that the move gave,
	//the number of steps that the player made,the number of presents he got,
	//the number of snakes that bit him, the number of ladders that he climbed
	//and lastly HeuristicPlayer's next location(after his move)->my addition,in that exact order.
	private ArrayList<Integer[]> path;
	
	//Value responsible for printing the rounds (see last method of this class)
	int Round = 0;	
	
	void setPath(ArrayList<Integer[]> path)
	{
		this.path=path;
	}
	
	ArrayList<Integer[]> getPath()
	{
		return path;
	}
	
	HeuristicPlayer()
	{
		super();
		path=null;
	}
	
	HeuristicPlayer(int playerId,String name,int score,Board board)
	{
		super(playerId,name,score,board);
		path=new ArrayList<Integer[]>();
	}
	
	//Evaluates the move of HeuristicPlayer for a specific dice roll.
	//Parameters: The current position of the player and the value of the dice he picked.
	//Returns the evaluation of the move (as a double value),which is based on the "goal" function:
	//f(steps,gainPoints)=steps*0.60+gainPoints*040.
	double evaluate(int currentPos,int dice)
	{
		int points=0;
		int mat1[]=new int[4];
		int next1=0,sncount1=0,ladcount1=0,prcount1=0;
		
		next1=currentPos+dice;
		
		for(int i=0;i<getBoard().getSnakes().length;i++)
		{
			//If the next move has a snakeHead "go" to snakeTail and increment
			//the variable responsible for the number of snakes that bit him by one.
			if(next1==getBoard().getSnakes()[i].getHeadId())
			{
				next1=getBoard().getSnakes()[i].getTailId();
				sncount1++;
			}			
		}
		
		for(int i=0;i<getBoard().getLadders().length;i++)
		{
			//If the next move has a ladderBottomSquare "go" to ladderTopSquare, increment
			//the variable responsible for the number of ladders that he climbed by one.
			if(next1==getBoard().getLadders()[i].getBottomSquareId())
			{
				//If the ladder has'nt been taken by another or the same player again.
				if(getBoard().getLadders()[i].getBroken()!=true)
				{
					next1=getBoard().getLadders()[i].getTopSquareId();
					ladcount1++;
				}				
			}
		}
		
		for(int i=0;i<getBoard().getPresents().length;i++)
		{
			//Sets the number of points that each present gives.
			getBoard().getPresents()[i].setPoints(10);
			
			points=getBoard().getPresents()[i].getPoints();
			
			//If the next move has a present, get present points, increment
			//the variable responsible for the number of presents he got by one.
			if(next1==getBoard().getPresents()[i].getPresentSquareId())
			{	
				//If the present has'nt been taken by another or the same player again.
				if(getBoard().getPresents()[i].getPoints()!=0)
				{
					prcount1++;
				}				
			}
		}		

		//Initialize the matrix with the correct values.
		mat1[0]=next1;
		mat1[1]=sncount1;
		mat1[2]=ladcount1;
		mat1[3]=prcount1;	
		
		//Return the evaluation of the move based on
		//f(steps,gainPoints)=steps*0.60+gainPoints*040 function.		
		return((next1-currentPos)*0.60+prcount1*points*0.40);
	}
	
	 //This function is responsible for the final move of the player,
	 //after the evaluation of all the possible ones,which he can make.
	 //Parameters: The current position of the player.
	 //Returns the new position of the player on the board.
	 int getNextMove(int currentPos)
	 {
		 //Matrix that returns double value,named mat,in which we will store
		 //all of the evaluations of the player's possible moves.
		 double[]mat=new double[6];
		 
		 //Matrix that returns integer value,named mat2,
		 //used to store data after the player's move.
		 int[]mat2=new int[4];
		 
		 //Matrix that returns integer value,named arr,
		 //used to store data after the player's move,
		 //in order to then add them to the ArrayList named path.
		 Integer []arr=new Integer[7];
		
		 double d,max;
		 int maxPos=0;
		 
		 //Evaluate all of the possible player's moves.
		 for(int i=1;i<=6;i++)
		 {
			 d=evaluate(currentPos,i);
			 mat[i-1]=d;			 
		 }
		 
		 max=mat[0];
		 
		 for(int i=0;i<6;i++)
		 {
			 if(mat[i]>max)
			 {
				 max=mat[i];
				 maxPos=i;
			 }
		 }
		 
		 //Make the move with the best evaluation value.
		 mat2=move(currentPos,maxPos+1);
		 
		 //Initialize the arr matrix with the correct values 
		 //and add them to the ArrayList named path.
		 arr[0]=maxPos+1;
		 arr[1]=mat2[3]*getBoard().getPresents()[0].getPoints();
	     arr[2]=mat2[0]-currentPos;
		 arr[3]=mat2[3];
		 arr[4]=mat2[1];
		 arr[5]=mat2[2];
		 arr[6]=mat2[0];
		 
		 path.add(arr);
		 
		 //Finally return the new position of the player in board.
		 return mat2[0];
	 }
	 
	 //Prints the statistics for a specific HeuristicPlayer's move.
	 //To print the rounds we use variable Round form above.
	 void statistics()
	 {		 
		 System.out.println(super.getName()+" has set the dice equal to "+path.get(Round)[0]+" in round "+(Round+1)+".");
		 System.out.println("He got bit by "+path.get(Round)[4]+" snake(s).");
		 System.out.println("He went up "+path.get(Round)[5]+" ladder(s).");
		 System.out.println("He got "+path.get(Round)[3]+" present(s).");
		 System.out.println("Current position: "+path.get(Round)[6]+".");
		 Round++;
	 }
}
