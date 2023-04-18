public class Board {
	
	private int M,N;	
	private int[][] squares; 	
	private Snake[] snakes;
	private Ladder[] ladders;
	private Present[] presents;
	
	public int getN() {
		return N;
	}
	
	public Ladder[] getLadders() {
		return ladders;
	}
	
	public int getM() {
		return M;
	}
	
	public Present[] getPresents() {
		return presents;
	}
	
	public Snake[] getSnakes() {
		return snakes;
	}
	
	public int[][] getSquares() {
		return squares;
	}
	
	public void setLadders(Ladder[] ladders) {
		this.ladders = ladders;
	}
	
	public void setM(int m) {
		M = m;
	}
	
	public void setN(int n) {
		N = n;
	}
	
	public void setPresents(Present[] presents) {
		this.presents = presents;
	}
	
	public void setSnakes(Snake[] snakes) {
		this.snakes = snakes;
	}
	
	public void setSquares(int[][] squares) {
		this.squares = squares;
	}
	
	Board()
	{		
		M=N=0;
		squares=null;
		snakes=null;
		ladders=null;
		presents=null;
	}
	
	Board(int n,int m,int s,int l,int p)
	{
		N=n;
		M=m;
		squares=new int[M][N];
		snakes=new Snake[s];
		ladders=new Ladder[l];
		presents=new Present[p];
	}
	
	//Copies.
	Board(Board b) 
	{
		N=b.N;
		M=b.M;		
		squares=b.squares;
		
		snakes=new Snake[b.getSnakes().length];
		ladders=new Ladder[b.getLadders().length];
		presents=new Present[b.getPresents().length];
		
		for(int i=0;i<getSnakes().length;i++)
		{
			snakes[i]=new Snake(b.getSnakes()[i]);
		}
		
		for(int i=0;i<getLadders().length;i++)
		{
			ladders[i]=new Ladder(b.getLadders()[i]);
		}
		
		for(int i=0;i<getPresents().length;i++)
		{
			presents[i]=new Present(b.getPresents()[i]);
		}			
	}
	
	//Creates the board by initializing squares matrix,and also by
	//initializing snakes,ladders and presents matrices.
	void createBoard()
	{
		//Initialization of squares matrix.
		int count=1;
		
		for(int i=M-1;i>=0;i--)
		{
			if(!(i%2==0))
			{
				for(int j=0;j<N;j++)
				{
					squares[i][j]=count;
					count++;
				}				
			}
			else
			{
				for(int j=N-1;j>=0;j--)
				{
					squares[i][j]=count;
					count++;
				}
			}					
		}
				
		//Initialization of snakes matrix
		for(int i=0;i<getSnakes().length;i++) 
		{			
			snakes[i]=new Snake();
			
			//Returns a random value between 1+getN() and getN()*getM().
			//(Snake head cannot be set at any square of the last row of squares matrix).
			int num1=(int)(Math.random()*(getN()*getM()-(getN()+1)))+(getN()+1); 
			snakes[i].setHeadId(num1);
			snakes[i].setSnakeId(i);
			
			//If snakeHead is NOT located at the first columns of squares matrix that are even,
			//or if it is NOT located at the last columns of squares matrix that are odd.
			if(num1%getN()!=0) 
			{		
				//setTailId variable must be between 2 and num1-(num1%getN()).
				//(Snake head must be one or more levels above snake tail).
				snakes[i].setTailId((int)(Math.random()*((num1-(num1%getN()))-2))+2);
			}
			//If snakeHead is located at the first columns of squares matrix that are even,
			//or at the last columns of squares matrix that are odd.
			else
			{
				//setTailId variable must be between 2 and num1-getN().
				snakes[i].setTailId((int)(Math.random()*((num1-getN())-2))+2);
			}				
		}
		
		//Initialization of ladders matrix
		for(int i=0;i<getLadders().length;i++)
		{
			ladders[i]=new Ladder();
			
			//Returns a random value between 1+getN() and getN()*getM().
			//(ladderTopSquare cannot be set at the last row of squares matrix).
			int num2=(int)(Math.random()*(getN()*getM()-(getN()+1)))+(getN()+1);
			ladders[i].setTopSquareId(num2);
			ladders[i].setLadderId(i);
			
			//If ladderTopSquare is NOT located at the first columns of squares matrix that are even,
			//or if it is NOT located at the last columns of squares matrix that are odd.
			if(num2%getN()!=0) 
			{		
				//ladderBottomSquare variable must be between 2 and num2-(num2%getN()).
				//(ladderTopSquare must be one or more levels above ladderBottomSquare).
				ladders[i].setBottomSquareId((int)(Math.random()*((num2-(num2%getN()))-2))+2);
			}
			//If ladderTopSquare is located at the first columns of squares matrix that are even,
			//or if it is located at the last columns of squares matrix that are odd.
			else
			{	
				//setBottomSquareId must be between 2 and num2-getN().
				ladders[i].setBottomSquareId((int)(Math.random()*((num2-getN())-2))+2);
			}			
		}
		
		//Initialization of presents matrix.
		for(int i=0;i<getPresents().length;i++)
		{
			presents[i]= new Present();
			
			//Returns a random value between 2 and getN()*getM().
			int num3=(int)(Math.random()*(getN()*getM()-2))+2;
			presents[i].setPresentSquareId(num3);
			presents[i].setPresentId(i);
		}				
	}
	
	//Creates, initializes and prints 3 matrices.
	//One for snakes, one for ladders and one for presents matrices.
	//Each of them contains an alphanumeric that represents the location of 
	//snakes,ladders and squares variables (heads,tails,id's etc.) on the board.
	void createElementBoard()
	{
		int head=0,tail=0,ids=0,hi=0,hj=0,ti=0,tj=0;
		int tops=0,bots=0,idl=0,topsi=0,topsj=0,botsi=0,botsj=0;
		int pressq=0,idp=0,pressqi=0,pressqj=0;
		
		String[][] elementBoardSnakes;
		String[][] elementBoardLadders;
		String[][] elementBoardPresents;
		
		elementBoardSnakes=new String[getM()][getN()];
		elementBoardLadders=new String[getM()][getN()];
		elementBoardPresents=new String[getM()][getN()];
		
		//Set every element of all matrices="___".
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				elementBoardSnakes[i][j]="___";
				elementBoardLadders[i][j]="___";
				elementBoardPresents[i][j]="___";
			}
		}
		
		for(int l=0;l<getSnakes().length;l++)
		{
			head=snakes[l].getHeadId();
			tail=snakes[l].getTailId();
			ids=snakes[l].getSnakeId();
			
			//Find the location of each snake head and tail in squares matrix.
			for(int i=0;i<M;i++) 
			{
				for(int j=0;j<N;j++)
				{
					if(squares[i][j]==head)
					{
						hi=i;
						hj=j;
						break;
					}
					if(squares[i][j]==tail)
					{
						ti=i;
						tj=j;
						break;
					}					
				}
			}
			
			//For the locations that you have found set a custom alphanumeric
			//in order to pinpoint the exact location of the piece in board.
			elementBoardSnakes[hi][hj]="SH"+ids;
			elementBoardSnakes[ti][tj]="ST"+ids;					
		}
		
		for(int l=0;l<getLadders().length;l++)
		{
			tops=ladders[l].getTopSquareId();
			bots=ladders[l].getBottomSquareId();
			idl=ladders[l].getLadderId();
			
			//Find the location of each ladder top and bottom square in squares matrix.
			for(int i=0;i<M;i++) 
			{
				for(int j=0;j<N;j++)
				{
					if(squares[i][j]==tops)
					{
						topsi=i;
						topsj=j;
						break;
					}
					if(squares[i][j]==bots)
					{
						botsi=i;
						botsj=j;
						break;
					}					
				}
			}		
			
			//For the locations that you have found set a custom alphanumeric
			//in order to pinpoint the exact location of the piece in board.
			elementBoardLadders[topsi][topsj]="LU"+idl;
			elementBoardLadders[botsi][botsj]="LD"+idl;					
		}
		
		for(int l=0;l<getPresents().length;l++)
		{
			pressq=presents[l].getPresentSquareId();
			idp=presents[l].getPresentId();
		
			//Find the location of each present in squares matrix.
			for(int i=0;i<M;i++) 
			{
				for(int j=0;j<N;j++)
				{
					if(squares[i][j]==pressq)
					{
						pressqi=i;
						pressqj=j;
						break;
					}					
				}
			}
			
			//For the locations that you have found set a custom alphanumeric
			//in order to pinpoint the exact location of the piece in board.
			elementBoardPresents[pressqi][pressqj]="PR"+idp;					
		}
		
		//Print the values of elementBoardSnakes matrix.
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(elementBoardSnakes[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		//Print the values of elementBoardLadders matrix.
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(elementBoardLadders[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		//Print the values of elementBoardPresents matrix.
		for(int i=0;i<M;i++)
		{
			for(int j=0;j<N;j++)
			{
				System.out.print(elementBoardPresents[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();		
	}		
}
