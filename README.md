# Snakes-and-Ladders-Game-Development
Implementing the game "Snakes and Ladders" using Java.

Snakes and Ladders is a board game for two or more players regarded as a worldwide classic.It is played on a game board with numbered, gridded squares.A number of "ladders" and "snakes" are pictured on the board, each connecting two specific board squares.The object of the game is to navigate one's game piece, according to die rolls, from the start (bottom square) to the finish (top square), helped by climbing ladders but hindered by falling down snakes.
In the code that is presented above there are two players. One being the "Random" Player, that plays randomly, according to his die roll, and one being the "Strategic" or "Heuristic" player, that decides on his own (based on the current evaluation of the board) the die roll that suits him best, in order to be ahead of his rival at any moment. In my approach, I also added "presents", which, if stepped upon reward the player with a number of points. Winner of the game is the one who manages to get the biggest "overall" score. "Overall" score is a mix between the id (number) of the square each player holds when the game ends (or after a certain number of rounds) and between the number of points each player has gathered up until that time, based on a specific evaluation function.
