/**
 * @author Colin Roye
 * Homework #5
 *Id: 110378271
 *recitation 08
 *Recitation TA: Mike Rizzo
 *Grading TA: Tim Zhang 
 */

import javafx.scene.image.Image;

public class GameBoard {
  private static final Box blank = Box.EMPTY;
	private final static int boardSize = 9;
	private
	Box[][] board = new Box[3][3];


	
	/**
	 * this is the default constructor for the GameBoard which takes in a board
	 * @param board
	 */
	public GameBoard(Box[][] board){
			this.board = board;
	}
	/**
	 * This is the second constructor for the GameBoard object. No initial Board is passed in so a new one is created to be blank
	 */
	public GameBoard(){
			board[0][0] = blank;
			board[0][1] = blank;
			board[0][2] = blank;
			board[1][0] = blank;
			board[1][1] = blank;
			board[1][2] = blank;
			board[2][0] = blank;
			board[2][1] = blank;
			board[2][2] = blank;
		}
	/**
	 * this method returns the size of the board
	 * @return
	 * boardSize
	 */
	public int getSize(){
		return boardSize;
	}
	/**
	 * this method checks if a board has all the same spaces as another board
	 * @param gb
	 * @return
	 */
	public boolean equals(GameBoard gb){
		boolean check = true;
		for(int i = 0; i< 3; i++){
			for(int j = 0; j < 3; j++){
				if(gb.getBoard()[i][j] != board[i][j]){
					check = false;
				}
			}
		}
		return check;
	}
	/**
	 * this method clones the board array of the GameBoard object
	 * @return
	 */
	public GameBoard clone(){
		Box[][] tempBox = new Box[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == Box.X)
				tempBox[i][j] = Box.X;
				if(board[i][j] == Box.O)
				tempBox[i][j] = Box.O;
				if(board[i][j] == Box.EMPTY){
				tempBox[i][j] = Box.EMPTY;
				}
			}
		}
		return new GameBoard(tempBox);
	}
	/**
	 * this method returns a string representation of the board
	 */
	public String toString(){
		String s = "\n";
		
		for(int i = 0; i < 3 ; i++){
			for(int j = 0; j < 3 ; j++){
				if(j%3 == 0){
					s+='\n';
				}
				if(board[i][j] == Box.EMPTY)
					s += ("|_|");
				if(board[i][j] == Box.X)
					s += ("|X|");
				if(board[i][j] == Box.O)
					s+=("|O|");
			}

		}
		return s + '\n';
		
	}
	/**
	 * this method sets a space on the board based on the position and turn that is passed in
	 * @param i
	 * @param j
	 * @param turn
	 */
	public void setBoardSpace(int i, int j, Box turn){
		if(turn == Box.X){
			board[i][j] = Box.X;
		}
		if(turn == Box.O){
			board[i][j] = Box.O;
		}
	}
	/**
	 * this method returns the space indicated by i and j on the board (I know i and j aren't neccisarily the best names to give for this method, but I felt it made sense considering I had created several double for loops with i and j representing the corresponding meanings elsewhere in the progream)
	 * @param i
	 * @param j
	 * @return
	 */
	public Box getSpace(int i, int j) {
		if(board[i][j] == Box.EMPTY){
			return Box.EMPTY;
		}
		if(board[i][j] == Box.X){
			return Box.X;
		}
		if(board[i][j] == Box.O){
			return Box.O;
		}

			return null;
	}
	/**
	 * this method returns the Box[][] array
	 * @return
	 * board
	 */
	public Box[][] getBoard(){
		return board;
	}
	
}
