import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Game {
	
	private int round;
	
	Game()
	{
		round=1;
	}
	
	Game(int round)
	{
		this.round=round;
	}
	
	public int getRound() {
		return round;
	}
	
	public void setRound(int round) {
		this.round = round;
	}
	
	//This function is responsible for setting the turns in which players will start the game.
	//The player who rolled the least amount of dice value plays first etc.
	//Parameters: An ArrayList of the number of players.
	//Returns a map with the id's of the players and the value of the dice each of them rolled,
	//sorted based on the value of their dice.
	//(first set of <keys,values> of the map must be the one with the least amount of dice value,etc).
	//The procedure that we use is simple: We first make an ordinary HashMap in which we'll store the id's
	//of the players as it's keys and the worth of their first dice roll as it's values.
	//We then use a matrix,in order to store the values of the HashMap(the dice roll of each player)
	//and after that we sort the matrix accordingly, using bubble sort.
	//Lastly,we utilize a LinkedHashMap to store the values of the now sorted matrix
	//that contains the values of our HashMap(the dice rolls of each of the players) and then we return it.
	Map<Integer,Integer> setTurns(ArrayList<Player> players)
	{
		int id=0,dice=0,n,c=0;
		boolean b=false;
		
		//Instantiations of two maps,one being a HashMap and the other one being a LinkedHashMap.
		//(We use a LinkedHashMap in order to sort the map based on it's values rather than it's keys,
		//which is the typical classification that the HashMap class provides us with).
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		Map<Integer,Integer> map2=new LinkedHashMap<Integer,Integer>();
		
		//For each of the players find the corresponding set of
		//<id's,value of dice they rolled> and put it in the HashMap.
		for(int i=0;i<players.size();i++)
		{
			id=players.get(i).getPlayerId();
			
			//Get a random value between 1 and 6.
			dice=(int)(Math.random()*(7-1)+1);
			
			map.put(id, dice);
		}
		
		n=map.size();		
		int mat[]=new int[n];
		
		//Initialize the matrix,mat,with the not sorted values of the HashMap.
		for(int i=0;i<n;i++)
		{
			mat[i]=map.get(players.get(i).getPlayerId());
		}
		
		//Do a bubble sort in order to sort the previous matrix.
		do
		{
			b=true;
			
			for(int i=0;i<n-1;i++)
			{
				if(mat[i]>mat[i+1])
				{
					c=mat[i];
					mat[i]=mat[i+1];
					mat[i+1]=c;
					
					b=false;
				}
			}
		
		} while(b==false);
		
		//Find to which id each value of the now sorted matrix mat is mapped to
		//and then put each of the newly arranged sets in the LinkedHashMap that we created.
		for(int j=0;j<n;j++)
		{
			for(int i=0;i<n;i++)
			{
				if(mat[j]==map.get(players.get(i).getPlayerId()))
				{
					id=players.get(i).getPlayerId();
					map2.put(id, mat[j]);
				}
			}
		}
		
		//Return the LinkedHashMap.
		return map2;		
	}	

	public static void main(String[] args) {
		
		int first,round=1,dice=0,n=0,step=0;
		int nextSquareP1=0,nextSquareP2=0;
		
		//Matrix that we use to store data of each of the player_1's ("random" player) moves.
		int matP1[]=new int[4];
		
		//ArrayList of matrices type integer that we use to store data 
		//of each of the player_2's (HeuristicPlayer) moves. 
		ArrayList<Integer[]> matP2=new ArrayList<Integer[]>();
		
		Game g=new Game();
		
		//N=10 and M=20.
		Board b=new Board(10,20,3,3,6); 
		
		//Create board and elementBoard.
		b.createBoard();
		b.createElementBoard();
		
		//Create two players,one that plays randomly (type Player) and one who plays with strategy (type HeuristicPlayer).   
		Player p1= new Player(1,"Player_1",0,b);
		HeuristicPlayer p2= new HeuristicPlayer(2,"Player_2",0,b);
				
		//ArrayList in which we store the two players.
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(p2);
		
		//Map that we use to decide the turns that the players will start the game.
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		map=g.setTurns(players);
		first=(int)map.keySet().toArray()[0];
		
		//If player_1 ("random" player) plays first.
		if(first==p1.getPlayerId())
		{
			//While none of them has finished, or while the rounds that they played are less than 100.
			do
			{
				//Set and print the number of round the are currently on,using round variable.
				g.setRound(round++);
				System.out.println("Round: "+g.getRound());
			
				//Get a random value between 1 and 6 for the player_1's move.
				//Make the move and store it's data on matrix matP1 for later use.
				//Store the value of the next player_1's square location in variable nextSquareP1.
				//(we set nextSquareP1=matP1[0]-1 so that for each iteration of the loop 
				//player_1's move is given by the call of the function: moveWithPrint(matP1[0],dice),
				//which is the actual value of the next player_1's square location).
				dice=(int)(Math.random()*(7-1))+1;
				matP1=p1.moveWithPrint(1+nextSquareP1, dice);
				nextSquareP1=matP1[0]-1;
				System.out.println();
				
				//If during this move player_1 has finished the game, break.
				if(nextSquareP1>=b.getM()*b.getN()) break;
				
				//Decide player_2's (HeuristicPlayer's) move and store his new square location in variable step.
				//Store the data of the player_2's move in the ArrayList matP2
				//and initialize nextSquareP2 the same way we did above.
				step=p2.getNextMove(1+nextSquareP2);
				matP2=p2.getPath();
				nextSquareP2=matP2.get(n)[6]-1;
				
				//Print out the statistics of HeuresticPlayer's move.
				p2.statistics();
				
				System.out.println();
				System.out.println();
				System.out.println();
				
				//Increment variable n,which is responsible for 
				//the number of the move that player_2 makes.
				n++;								
			} while((g.getRound()<100) && (nextSquareP1<(b.getM()*b.getN())) && (nextSquareP2<(b.getM()*b.getN())));
			//If player_1 ("random" player) finishes first.
			if(nextSquareP1>=b.getM()*b.getN())
			{
				System.out.println();
				System.out.println();
				System.out.println("____________________________");
				System.out.println(p1.getName()+" has finished first!");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				if((b.getM()*b.getN()*0.6+p1.getScore()*0.4)>(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				else if((b.getM()*b.getN()*0.6+p1.getScore()*0.4)<(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->player_1.
				else
				{
					System.out.println(p1.getName()+" has won!");
				}
			}
			//If player_2 (HeuristicPlayer) finishes first.
			if(nextSquareP2>=b.getM()*b.getN())
			{
				System.out.println("____________________________");
				System.out.println(p2.getName()+" has finished first!");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				if((matP1[0]*0.6+p1.getScore()*0.4)>(b.getM()*b.getN()*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				else if((matP1[0]*0.6+p1.getScore()*0.4)<(b.getM()*b.getN()*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->player_2.
				else
				{
					System.out.println(p2.getName()+" has won!");
				}
			}
			//If none of the players manages to finish the game (if round is greater than 100).
			if(matP1[0]<b.getM()*b.getN() && step<b.getM()*b.getN())
			{
				System.out.println("__________________________");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				if((matP1[0]*0.6+p1.getScore()*0.4)<(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				else if((matP1[0]*0.6+p1.getScore()*0.4)>(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->
				//the one with the greater value of square.
				else
				{
					//If player_1's last square value (matP1[0]) is bigger than player_2's (step),player_1 has won.
					if(matP1[0]>step)
					{
						System.out.println(p1.getName()+" has won!");
					}
					//If player_1's last square value (matP1[0]) is smaller than player_2's (step),player_2 has won.
					if(matP1[0]<step)
					{
						System.out.println(p2.getName()+" has won!");
					}					
				}
			}			
		}
		//If player_2 (HeuristicPlayer player) plays first.
		else if(first==p2.getPlayerId())
		{
			//While none of them has finished, or while the rounds that they played are less than 100.
			do
			{	
				//Set and print the number of round the are currently on,using round variable.
				g.setRound(round++);
				System.out.println("Round: "+g.getRound());
				System.out.println();
				
				//Decide player_2's (HeuristicPlayer's) move and store his new square location in variable step.
				//Store the data of the player_2's move in the ArrayList matP2
				//and initialize nextSquareP2 the same way we did before,so that for each iteration of the loop
				//player_1's move is given by the call of the function: getNextMove(matP2.get(n)[6]),
				//which is the actual value of the next player_1's square location.
				step=p2.getNextMove(1+nextSquareP2);
				matP2=p2.getPath();
				nextSquareP2=matP2.get(n)[6]-1;
				p2.statistics();
				System.out.println();
				
				//If during this move player_2 has finished the game, break.
				if(nextSquareP2>=b.getM()*b.getN()) break;
				
				//Get a random value between 1 and 6 for the player_1's move.
				//Make the move and store it's data on  matrix matP1 for later use.
				//Store the value of the next player_1's square location in variable nextSquareP1.
				//(we set nextSquareP1=matP1[0]-1 so that for each iteration of the loop 
				//player_1's move is given by the call of the function: moveWithPrint(matP1[0],dice),
				//which is the actual value of the next player_1's square location).
				dice=(int)(Math.random()*(7-1))+1;
				matP1=p1.moveWithPrint(1+nextSquareP1, dice);
				nextSquareP1=matP1[0]-1;
				System.out.println();
				System.out.println();
				System.out.println();				
				
				//Increment variable n,which is responsible for 
				//the number of the move that player_2 makes.
				n++;				
			} while((g.getRound()<100) && (nextSquareP1<(b.getM()*b.getN())) && (nextSquareP2<(b.getM()*b.getN())));
			//If player_1 ("random" player) finishes first.
			if(nextSquareP1>=b.getM()*b.getN())
			{	
				System.out.println();
				System.out.println();
				System.out.println("____________________________");
				System.out.println(p1.getName()+" has finished first!");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040,that we have previously set.
				if((b.getM()*b.getN()*0.6+p1.getScore()*0.4)>(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040,that we have previously set.
				else if((b.getM()*b.getN()*0.6+p1.getScore()*0.4)<(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->player_1.
				else
				{
					System.out.println(p1.getName()+" has won!");
				}
			}
			//If player_2 (HeuristicPlayer) finishes first.
			if(nextSquareP2>=b.getM()*b.getN())
			{	
				System.out.println();
				System.out.println();
				System.out.println("____________________________");
				System.out.println(p2.getName()+" has finished first!");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040,that we have previously set.
				if((matP1[0]*0.6+p1.getScore()*0.4)>(b.getM()*b.getN()*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040,that we have previously set.
				else if((matP1[0]*0.6+p1.getScore()*0.4)<(b.getM()*b.getN()*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->player_2.
				else
				{
					System.out.println(p2.getName()+" has won!");
				}
			}
			//If none of the players manages to finish the game (if round is greater than 100).
			if(matP1[0]<b.getM()*b.getN() && step<b.getM()*b.getN())
			{
				System.out.println();
				System.out.println();
				System.out.println("__________________________");
				System.out.println(p1.getName()+"'s final score: "+ p1.getScore());
				System.out.println(p2.getName()+"'s final score: "+ p2.getScore());
				
				//If player_2 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				if((matP1[0]*0.6+p1.getScore()*0.4)<(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p2.getName()+" has won!");
				}
				//If player_1 has won,based on the "goal" function:
				//f(steps,gainPoints)=steps*0.60+gainPoints*040 that we have previously set.
				else if((matP1[0]*0.6+p1.getScore()*0.4)>(step*0.6+p2.getScore()*0.4))
				{
					System.out.println(p1.getName()+" has won!");
				}
				//If it's a tie,based on the function,the winner is the one that finished first->
				//the one with the greater value of square.
				else
				{
					//If player_1's last square value (matP1[0]) is bigger than player_2's (step),player_1 has won.
					if(matP1[0]>step)
					{
						System.out.println(p1.getName()+" has won!");
					}
					//If player_1's last square value (matP1[0]) is smaller than player_2's (step),player_2 has won.
					if(matP1[0]<step)
					{
						System.out.println(p2.getName()+" has won!");
					}					
				}
			}			
		}
	}
}
