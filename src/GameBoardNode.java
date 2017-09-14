
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.media.MediaPlayer;
/**
 * @author Colin Roye
 * Homework #5
 *Id: 110378271
 *recitation 08
 *Recitation TA: Mike Rizzo
 *Grading TA: Tim Zhang 
 */
public class GameBoardNode {
	private GameBoard board;
	private boolean isEnd;
	private Box currentTurn;
	private Box winner;
	private double winProb;//=the number of winning leaves/number of total leaves in the subtree from this node
	private double loseProb;//=the number of losing leaves/number of total leaves in the subtree from this node
	private double drawProb;//=the number of draw leaves/number of total leaves in the subtree from this node
	private GameBoardNode config[] = new GameBoardNode[9];
	
	private double totalLeaves = 0; 
	private double numWins = 0;
	private double numLoses = 0;
	private double numDraws = 0;
	static int[][] moveScores = new int[3][3];

	


	/**
	 * This is the default constructor for the gameBoardNode class which initializes a new gameboard for the node
	 */
	public GameBoardNode(){
		board = new GameBoard();
		
	}
	/**
	 * This method takes in a gameboard object and a turn and set the board object of this class equal to it
	 * @param board
	 * @param currentTurn
	 */
	public GameBoardNode(GameBoard board, Box currentTurn){
		this.setBoard(board);
		this.currentTurn = currentTurn;
	
	

		//loseProb = numLoses()/setTotalLeaves();
		//drawProb = numDraws()/setTotalLeaves();
	}
	/**
	 * This method is used to set the values of total Leaves, numLoses, numWins, and numDraws
	 */
	public void setVals(){
		totalLeaves =setTotalLeaves();
		numWins = numWins();
		numLoses = numLoses();
		numDraws = numDraws();
		
		loseProb = Math.round( numLoses/totalLeaves * 100.0 ) / 100.0;
		winProb = Math.round( numWins/totalLeaves * 100.0 ) / 100.0;
		drawProb = Math.round( numDraws/totalLeaves * 100.0 ) / 100.0;

		
 		
	}
	/**
	 * This returns turn that was passed into the GameBoardNode object
	 * @return
	 * current turn
	 */
	public Box getCurrentTurn(){
		return currentTurn;
	}
	/**
	 * this method returns the probability of  winning form this board position
	 * @return
	 */
	public double getWinProb(){
		return winProb;
	}
	/**
	 * this method returns the probability of  losing form this board position
	 * @return
	 */
	public double getLoseProb(){
		return loseProb;
	}
	/**
	 * this method returns the probability of  drawing form this board position
	 * @return
	 */
	public double getDrawProb(){
		return drawProb;
	}
	/**
	 * this method checks if a game has been one or not, it returns a 1 if the user has one, a 0 if the computer has one, and a -1 otherwise
	 * @return
	 */
	public int checkWin(){
		//problem with 02, 12, and 2,1

			if(board.getSpace(0, 0) == board.getSpace(0, 1) && board.getSpace(0, 1) == board.getSpace(0, 2) && board.getSpace(0, 0) != Box.EMPTY){ //first Horizontal
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(1, 0) == board.getSpace(1, 1) && board.getSpace(1, 1) == board.getSpace(1, 2) && board.getSpace(1, 0) != Box.EMPTY){//second Horizontal
				if(currentTurn == Box.O){
					return 0;
				}	
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(2, 0) == board.getSpace(2, 1) && board.getSpace(2, 1) == board.getSpace(2, 2)&& board.getSpace(2, 0) != Box.EMPTY){//third Horizontal
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(0, 0) == board.getSpace(1, 0) && board.getSpace(2, 0) == board.getSpace(1, 0) && board.getSpace(0, 0) != Box.EMPTY){//first vert
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(0, 1) == board.getSpace(1, 1) && board.getSpace(2, 1) == board.getSpace(1, 1) && board.getSpace(0, 1) != Box.EMPTY){//second vert
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(0, 2) == board.getSpace(1, 2) && board.getSpace(2, 2) == board.getSpace(1, 2) && board.getSpace(0, 2) != Box.EMPTY){//third vert
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(2, 0) == board.getSpace(1, 1) && board.getSpace(2, 2) == board.getSpace(1, 1)&& board.getSpace(1, 1) != Box.EMPTY){//cross 1
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					return 1;
				}
			}
			if(board.getSpace(0, 2) == board.getSpace(1, 1) && board.getSpace(2, 0) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){//cross 2
				if(currentTurn == Box.O){
					return 0;
				}
				if(currentTurn == Box.X){
					
					return 1;
				}
			}
			return -1;
		}

	
	/**
	 * this method returns  boolean 
	 * @return
	 */
	public boolean[][] checkSpaces(){
		boolean possibleMoves[][] = new boolean[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(getBoard().getSpace(i, j) == Box.EMPTY){
					possibleMoves[i][j] = true;
				}
				else{
					possibleMoves[i][j] = false;
				}
			}
		}
		if(possibleMoves != null)
		return possibleMoves;
		return new boolean[3][3];//possible bug
	}
	/**
	 * this method sets a GameBoardNode to the config array to build the game tree
	 * @param childNode
	 * @param counter
	 */
	public void setConfig(GameBoardNode childNode, int counter){
		config[counter] = childNode;
			;}
	/**
	 * this method returns the config array
	 * @return
	 * config
	 */
	public GameBoardNode[] getConfig(){
		return config;
	}
	/**
	 * This method returns the total leaves of the tree counting this as the root
	 * @return
	 */
	public double getTotalLeaves(){
		return totalLeaves;
	}
	/**
	 * this method returns the number of wins possible from this point in the tree
	 * @return
	 * total leaves
	 */
	public double getNumWins(){
		return numWins;
	}
	/**
	 * This method returns the number of losses possible from this point in the array
	 * @return
	 * numWins
	 */
	public double getNumLoses(){
		return numLoses;
	}
	/**
	 * This method returns the number of possible draws from this position in the array
	 * @return
	 * numLoses
	 */
	public double getNumDraws(){
		return numDraws;
	}
	/**
	 * This method returns the GameBoard object of wrapped by this class
	 * @return
	 * board
	 */
	public GameBoard getBoard() {
		return board;
	}
	/**
	 * this method sets the board object of this to a given GameBoard
	 * @param board
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}
	/**
	 * This is the method used to print the statistics of the game
	 */
	public void printBoardStats(){
		//setVals();
		System.out.println("__________________");
		System.out.println("Lose Probability: " + loseProb);
		System.out.println("Win Probability: " + winProb);
		System.out.println("Draw Probability: " + drawProb);

		System.out.println("Total Leaves: " + totalLeaves);
		System.out.println("Total Wins: " + numWins);
		System.out.println("Total Loses: " + numLoses);
		System.out.println("Total Draws: " + numDraws);
		
		System.out.println("__________________");

	}
	/**
	 * this method is used to clone the GameBoard object
	 */
	@Override
	public GameBoardNode clone(){
		return new GameBoardNode(board.clone(), currentTurn);
	}
	/**
	 * this method is used to calculate the number of losses possible from this position in the tree
	 * @return
	 * numLoses
	 */
	public double numLoses(){
		if(config[0] == null && checkWin() == 0){
			return 1;
		}
		for(int i =0; i < 9; i++){
			if(config[i] != null){
				numLoses += config[i].numLoses();
			}
		}
		return numLoses;
	}
	/**
	 * this method is used to calculate the number of wins possible from this position in the tree
	 * @return
	 * numWins
	 */
	public double numWins(){
		if(config[0] == null && checkWin() == 1){
			return 1;
		}
		for(int i =0; i < 9; i++){
			if(config[i] != null){
				numWins += config[i].numWins();}
			
		}
		return numWins;
		
		}
	
	
	
	/**
	 * this is used to calculate the number of draws possible form this postion in the tree
	 * @return
	 * numDraws
	 */
	public double numDraws(){
		boolean draw = true;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(checkSpaces()[i][j] == true){
					draw = false;
				}}}
		if(config[0] == null && checkWin() == -1 && draw){
			return 1;
		}
		
		for(int i =0; i < 9; i++){
			if(config[i] != null){
		
				numDraws += config[i].numDraws();
			}
		}
		return numDraws;
		}
	
	/**
	 * this method is used to calculate the total number of leaaves form this point in the tree
	 * @return
	 */
	public double setTotalLeaves(){	
	if(config[0] == null){
			return 1;
	
	
	}
	for(int i =0; i < 9; i++){
		if(config[i] != null){
			totalLeaves += config[i].setTotalLeaves();
		}
	}
	return totalLeaves;
	}
	/**
	 * this method is responsible for the artificial intelligence of the game. It is based off of the min max algorithm.
	 * @param turn
	 */
	public void setMinMax(Box turn) {
		boolean[][] possibleMoves = checkSpaces();
		boolean draw = true;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				moveScores[i][j] = -1;
				if(checkSpaces()[i][j]){
					moveScores[i][j] = 1;
				}}}
		if(possibleMoves[1][1]){
			moveScores[1][1] = 100;
			}
		if(board.getSpace(0, 0) == board.getSpace(0, 1) && board.getSpace(0, 1) != Box.EMPTY){
			moveScores[0][2] = 100; 
			}
			if(board.getSpace(0, 0) == board.getSpace(0, 2) && board.getSpace(0, 2) != Box.EMPTY) {
			moveScores[0][1] = 100; 
			}
			if(board.getSpace(0, 2) == board.getSpace(0, 1) && board.getSpace(0, 1) != Box.EMPTY){

			moveScores[0][0] = 100;
			}


			if(board.getSpace(1, 2) == board.getSpace(1, 1) && board.getSpace(1, 1)!= Box.EMPTY){

			moveScores[1][0] = 100;
			}
			if(board.getSpace(1, 0) == board.getSpace(1, 2) && board.getSpace(1, 2 ) != Box.EMPTY){

			moveScores[1][1] = 100;
			}
			if(board.getSpace(1, 0) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){ 
			moveScores[1][2] = 100;
			}

			if(board.getSpace(2, 0) == board.getSpace(2, 1) && board.getSpace(2, 1) != Box.EMPTY){

			moveScores[2][2] = 100;
			}
			if(board.getSpace(2, 2) == board.getSpace(2, 1) && board.getSpace(2, 1) != Box.EMPTY){
			moveScores[2][0] = 100;
			}
			if(board.getSpace(2, 0) == board.getSpace(2, 2) && board.getSpace(2, 2) != Box.EMPTY){

			moveScores[2][1] = 100;
			}
			if(board.getSpace(0, 0) == board.getSpace(1, 0) && board.getSpace(1, 0) != Box.EMPTY){

			moveScores[2][0] = 100;
			}
			if(board.getSpace(2, 0) == board.getSpace(1, 0) && board.getSpace(1, 0) != Box.EMPTY){
			moveScores[0][0] = 100; 
			}
			if(board.getSpace(0, 0) == board.getSpace(2, 0) && board.getSpace(2, 0) != Box.EMPTY){

			moveScores[1][0] = 100;
			}

			if(board.getSpace(1, 1) == board.getSpace(0, 1) && board.getSpace(1, 0) != Box.EMPTY){

			moveScores[2][1] = 100;
			}
			if(board.getSpace(0, 1) == board.getSpace(2, 1) && board.getSpace(2, 1) != Box.EMPTY){

			moveScores[1][1] = 100;
			}
			if(board.getSpace(2, 1) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){

			moveScores[0][1] = 100;
			}

			if(board.getSpace(0, 2) == board.getSpace(1, 2) && board.getSpace(1, 2) != Box.EMPTY){

			moveScores[2][2] = 100;
			}
			if(board.getSpace(2, 2) == board.getSpace(1, 2) && board.getSpace(1, 2) != Box.EMPTY){

			moveScores[0][2] = 100;
			}
			if(board.getSpace(0, 2) == board.getSpace(2, 2) && board.getSpace(2, 2) != Box.EMPTY){

			moveScores[1][2] = 100;
			}
			if(board.getSpace(2, 0) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){

			moveScores[2][2] = 100;
			}
			if(board.getSpace(2, 2) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){

			moveScores[2][0] = 100;
			}
			if(board.getSpace(2, 0) == board.getSpace(2, 2) && board.getSpace(2, 2) != Box.EMPTY){//

			moveScores[1][1] = 100;
			}

			if(board.getSpace(0, 2) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){

			moveScores[2][0] = 100;
			}
			if(board.getSpace(0, 2) == board.getSpace(2, 0) && board.getSpace(2, 0) != Box.EMPTY){

			moveScores[1][1] = 100;
			}
			if(board.getSpace(0, 2) == board.getSpace(1, 1) && board.getSpace(1, 1) != Box.EMPTY){

			moveScores[2][0] = 100;
			}
			
			if(board.getSpace(0, 1) == Box.EMPTY && moveScores[0][1] < 100){
				moveScores[0][1] = 2;
			}
			if(board.getSpace(1, 0) == Box.EMPTY && moveScores[1][0] < 100){
				moveScores[1][0] = 2;
			}
			if(board.getSpace(2, 1) == Box.EMPTY && moveScores[2][1] < 100){
				moveScores[2][1] = 2;
			}
			if(board.getSpace(1, 2) == Box.EMPTY && moveScores[1][2] < 100){
				moveScores[1][2] = 2;
			}
			if(board.getSpace(0, 0) == Box.EMPTY && moveScores[0][0] < 100){
				moveScores[0][0] = 3;
			}	
			if(board.getSpace(2, 2) == Box.EMPTY && moveScores[2][2] < 100){
				moveScores[2][2] = 3;
			}
			if(board.getSpace(2, 0) == Box.EMPTY && moveScores[2][0] < 100){
				moveScores[2][0] = 3;
			}
			if(board.getSpace(0, 2) == Box.EMPTY && moveScores[0][2] < 100){
				moveScores[0][2] = 3;
			}
			if(board.getSpace(0, 1) == board.getSpace(1, 1) && board.getSpace(0, 1) == Box.X  && board.getSpace(2, 1) == Box.EMPTY){
				moveScores[2][1] = 1000;

			}
			if(board.getSpace(2, 0) == board.getSpace(1, 1) && board.getSpace(2, 0) == Box.X  && board.getSpace(2, 2) == Box.EMPTY){
				moveScores[0][2] = 1000;

			}
			if(board.getSpace(0, 2) == board.getSpace(1, 1) && board.getSpace(0, 2) == Box.EMPTY  && board.getSpace(1, 1) == Box.X){
				moveScores[1][1] = 1000;

			}
			if(board.getSpace(0, 0) == board.getSpace(2, 2) && board.getSpace(2, 2) == Box.X  && board.getSpace(1, 1) == Box.EMPTY){
				moveScores[1][1] = 1000;

			}
				//special
			if(board.getSpace(0, 2) == board.getSpace(2, 0) && board.getSpace(0, 2) == Box.X  && board.getSpace(1, 1) == Box.O){
				if(board.getSpace(1,2) == Box.EMPTY)
					moveScores[1][2] = 10000;
			}
			/*if(board.getSpace(0, 0) == board.getSpace(2, 1) ){
				if(board.getSpace(2,2) == Box.EMPTY)
					moveScores[2][2] = 100000;
			}*/
			if(board.getSpace(0, 0) == board.getSpace(2, 2) && board.getSpace(2, 2) == Box.X  && board.getSpace(1, 1) == Box.O){
				if(board.getSpace(1,2) == Box.EMPTY)
					moveScores[1][2] = 10000;
			}
		
	}
	/**
	 * this method returns the array of move scores
	 * @return
	 *  moveScores
	 */
	public int[][] getMovesArray() {
		return moveScores;
	}

	
	
	/*
	 * exam question
	 * you are given a tree, each node has a color... write a program to determine wehter it is red black or not. very good recursive exercise.
	 */
	
}