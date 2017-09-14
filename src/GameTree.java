/**
 * @author Colin Roye
 * Homework #5
 *Id: 110378271
 *recitation 08
 *Recitation TA: Mike Rizzo
 *Grading TA: Tim Zhang 
 */
public class GameTree {
	private static GameBoardNode root;
	 static GameBoardNode cursor;
	private int numLeaves;
	private static int globalCounter;
	public GameTree(){
		root = null;
		root = cursor;
		
	}
	/**
	 * this method is is the constructor of the GameTree object which instanciates the root and the curosr
	 */
	public GameTree(GameBoardNode root){
		this.root = root;
		cursor = root;
	}
	/**
	 * this method is used to set the cursor back to a previous move
	 */
	public void setCursor(GameBoardNode newCursor){
		cursor = newCursor;
	}
	/**
	 * Instead of using the make move method, I made two getNext methods which
	 * @param iPos
	 * @param jPos
	 */
	public void makeMove(){
		
	}
	/**
	 * This method is used build the game tree using GameBoardNodes
	 * @param rootNode
	 * @param turn
	 * @return
	 */
	public static GameBoardNode buildTree(GameBoardNode rootNode, Box turn){
		int counter = 0;
		boolean[][] possibleMoves = rootNode.checkSpaces();
		GameBoard tempBoard = new GameBoard();
		boolean draw = true;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(rootNode.checkSpaces()[i][j] == true){
					draw = false;
				}}}
		if(root.checkWin() == 0 || root.checkWin() == 1 || (root.checkWin() == -1 && draw == true)){
			return cursor;
		}
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				 
				tempBoard = new GameBoard();
				 
				//if the move isn't taken
				if(possibleMoves[i][j]){
					for(int n = 0; n < 3; n++){
						
						//clone the board manually
						for(int m = 0; m < 3; m++){
						tempBoard.getBoard()[n][m] = rootNode.getBoard().getBoard()[n][m];
						if(n == i && m == j){
							tempBoard.getBoard()[n][m] = turn;
						}
						//clone ended
						}}
						
					GameBoardNode tempGBN = new GameBoardNode(tempBoard, turn);
					tempGBN.setMinMax(turn);
					rootNode.setConfig(new GameBoardNode(tempBoard, turn), counter);
					counter++;
					}
				
			}
					
				}

			for(int i = 0; i < counter; i++){
				if(turn == Box.O){
				buildHelper(rootNode.getConfig()[i], Box.X);
				}
				if(turn == Box.X){
				buildHelper(rootNode.getConfig()[i], Box.O);
				}

			}
		return cursor;
		
		
	}
	/**
	 * this is a helper method for the buildTree method
	 * @param rootNode
	 * @param turn
	 */
	public static void buildHelper(GameBoardNode rootNode, Box turn){
		boolean draw = true;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(rootNode.checkSpaces()[i][j] == true){
					draw = false;
				}}}
		if(rootNode.checkWin() == 0 || rootNode.checkWin() == 1 || rootNode.checkWin() == -1 && draw == true){
			return;
		}
		buildTree(rootNode, turn);
	}
	/**
	 * this method returns the cursor of the tree
	 * @return
	 */
	public GameBoardNode getCursor(){
		return cursor;
	}
	/**
	 * this method checks whether the current GameBoardNode is a winner or not
	 * @param node
	 * @return
	 */
	public Box checkWin(GameBoardNode node){
		if(node.checkWin() == 0)
			return Box.O;
		if(node.checkWin() == 1)
			return Box.X;


		return Box.EMPTY;
	}
	/**
	 * this method returns the probability of winning from the cursors postion
	 * @return
	 */
	public double cursorProbability(){
		return cursor.getWinProb();
		
		
	}
	/**
	 * this method is used to advance the game, There is no artificial intellegence in this portion, so it is used by both the user and the computer
	 * @param i
	 * @param j
	 * @param turn
	 * @return
	 */
	public GameBoard getNext(int i, int j, Box turn) {
		//cursor.setVals();
		if(cursor.checkWin() == 1 || cursor.checkWin() == 0){
			return cursor.getBoard();}
			//not changing cursor
			cursor.getBoard().getBoard()[i][j] = turn;
			for(int m = 0; m < 9; m++){
				if(cursor.getConfig()[m]!= null){
				if(cursor.getBoard().equals(cursor.getConfig()[m].getBoard())){
					cursor = cursor.getConfig()[m];
				}
				
				}}
				cursor.setMinMax(turn);

				cursor.setVals();
				cursor.getMovesArray()[i][j] = -1;
				getCursor().printBoardStats();

			return cursor.getBoard();
	}
	/**
	 * this method uses the array created by the setMinMax method in GameBaordNode and choses the smartest move. The game cannot lose.
	 * @return
	 */
	public GameBoard getNext(){
		//Min Max
		cursor.setMinMax(Box.X);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(!cursor.checkSpaces()[i][j])
				cursor.getMovesArray()[i][j] = -100;
				}}
				
		boolean finished = true;		
		int temp = -2; 
		int iPos = 0;
		int jPos = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(cursor.getMovesArray()[i][j]!= -1)
					finished = false;
					
				if(cursor.moveScores[i][j] > temp){
					iPos = i;
					jPos = j;
					temp = cursor.getMovesArray()[i][j];
				}
			}
		}
		

		cursor.getMovesArray()[iPos][jPos] = -1;
		if(!finished)
		return getNext(iPos, jPos, Box.O);
		
		return cursor.getBoard();
		
	}
		
}
