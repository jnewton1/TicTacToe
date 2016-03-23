import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;

/**
* Tic-Tac-Toe: Two-player Graphics version with Simple-OO
* Written for Artificial Intelligence 
* @author: Jahmal Newton
* @Contributors: Edwin Benitez
*/

@SuppressWarnings("serial")
public class TTTGraphics2P extends JFrame {
	 public static int r;
	 public static int w;
   // Named-constants for the game board
   public  int ROWS = r;  // ROWS by COLS cells
   public  int COLS = r;
 
   // Named-constants of the various dimensions used for graphics drawing
   public  int CELL_SIZE = 100; // cell width and height (square)
   public  int CANVAS_WIDTH = CELL_SIZE * COLS;  // the drawing canvas
   public  int CANVAS_HEIGHT = CELL_SIZE * ROWS;
   public  int GRID_WIDTH = 8;                   // Grid-line's width
   public int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public int CELL_PADDING = CELL_SIZE / 6;
   public int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
   public int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
 
   // Use an enumeration (inner class) to represent the various states of the game
   public enum GameState {
      PLAYING, DRAW, CROSS_WON, NOUGHT_WON
   }
   private GameState currentState;  // the current game state
 
   // Use an enumeration (inner class) to represent the seeds and cell contents
   public enum Seed {
      EMPTY, CROSS, NOUGHT
   }
   private Seed currentPlayer;  // the current player
 
   private Seed[][] board   ; // Game board of ROWS-by-COLS cells
   private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
   private JLabel statusBar;  // Status Bar
 
   /** Constructor to setup the game and the GUI components */
   public TTTGraphics2P() {
      canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
 
      // The canvas (JPanel) fires a MouseEvent upon mouse-click
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int rowSelected = mouseY / CELL_SIZE;
            int colSelected = mouseX / CELL_SIZE;
 
            if (currentState == GameState.PLAYING) {
               if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                     && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                  board[rowSelected][colSelected] = currentPlayer; // Make a move
                  updateGame(currentPlayer, rowSelected, colSelected); // update state
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {       // game over
               initGame(); // restart the game
            }
            // Refresh the drawing canvas
            repaint();  // Call-back paintComponent().
         }
      });
 
      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("  ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();  // pack all the components in this JFrame
      setTitle("Tic Tac Toe");
      setVisible(true);  // show this JFrame
 
      board = new Seed[ROWS][COLS]; // allocate array
      initGame(); // initialize the game board contents and game variables
   }
 
   /** Initialize the game-board contents and the status */
   public void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = Seed.EMPTY; // all cells empty
         }
      }
      currentState = GameState.PLAYING; // ready to play
      currentPlayer = Seed.CROSS;       // cross plays first
   }
 
   /** Update the currentState after the player with "theSeed" has placed on
       (rowSelected, colSelected). */
   public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
      if (hasWon(theSeed, rowSelected, colSelected)) {  // check for win
         currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = GameState.DRAW;
      }
      // Otherwise, no change to current state (still GameState.PLAYING).
   }
 
   /** Return true if it is a draw (i.e., no more empty cell) */
   public boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == Seed.EMPTY) {
               return false; // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no more empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (rowSelected, colSelected) */
   public boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
	   if (w == 3 && r==3){
      return (board[rowSelected][0] == theSeed  // 3-in-the-row
            && board[rowSelected][1] == theSeed
            && board[rowSelected][2] == theSeed
       || board[0][colSelected] == theSeed      // 3-in-the-column
            && board[1][colSelected] == theSeed
            && board[2][colSelected] == theSeed
       || rowSelected == colSelected            // 3-in-the-diagonal
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
       || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
	   }
	   
	   else if (w ==3 && r==4){
		   //for board 4x4
		      return (board[rowSelected][0] == theSeed  // 3-in-the-row
		            && board[rowSelected][1] == theSeed
		            && board[rowSelected][2] == theSeed
		       || board[0][colSelected] == theSeed      // 3-in-the-column
		            && board[1][colSelected] == theSeed
		            && board[2][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[0][0] == theSeed
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed
		            
		       || board[rowSelected][1] == theSeed  // 3-in-the-row
		            && board[rowSelected][2] == theSeed
		            && board[rowSelected][3] == theSeed
		       || board[1][colSelected] == theSeed      // 3-in-the-column
		            && board[2][colSelected] == theSeed
		            && board[3][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed
		            && board[3][3] == theSeed
		       || rowSelected + colSelected == 3  // 3-in-the-opposite-diagonal
		            && board[1][2] == theSeed
		            && board[2][1] == theSeed
		            && board[3][0] == theSeed
		       || rowSelected + colSelected == 3  // 3-in-the-opposite-diagonal
		            && board[0][3] == theSeed
		            && board[1][2] == theSeed
		            && board[2][1] == theSeed
			   || rowSelected + colSelected == 2 // 3-in-the-opposite-diagonal
			            && board[0][2] == theSeed
			            && board[1][1] == theSeed
			            && board[2][0] == theSeed);
			   }
	   
	   else if (w == 3 && r==5){
		   //for board 5x5
		      return (board[rowSelected][0] == theSeed  // 3-in-the-row
		            && board[rowSelected][1] == theSeed
		            && board[rowSelected][2] == theSeed
		       || board[0][colSelected] == theSeed      // 3-in-the-column
		            && board[1][colSelected] == theSeed
		            && board[2][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[0][0] == theSeed
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed
		            
		            
		       || board[rowSelected][1] == theSeed  // 3-in-the-row
		            && board[rowSelected][2] == theSeed
		            && board[rowSelected][3] == theSeed
		       || board[1][colSelected] == theSeed      // 3-in-the-column
		            && board[2][colSelected] == theSeed
		            && board[3][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed
		            && board[3][3] == theSeed
		           
		            
		       || board[rowSelected][2] == theSeed  // 3-in-the-row
		            && board[rowSelected][3] == theSeed
		            && board[rowSelected][4] == theSeed
		       || board[2][colSelected] == theSeed      // 3-in-the-column
		            && board[3][colSelected] == theSeed
		            && board[4][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[2][2] == theSeed
		            && board[3][3] == theSeed
		            && board[4][4] == theSeed
		       || rowSelected + colSelected == 6  // 3-in-the-opposite-diagonal
		            && board[2][4] == theSeed
		            && board[3][3] == theSeed
		            && board[4][2] == theSeed
		       || rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
		            && board[1][4] == theSeed
		            && board[3][2] == theSeed
		            && board[2][3] == theSeed
		       || rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
		            && board[4][1] == theSeed
		            && board[3][2] == theSeed
		            && board[2][3] == theSeed
		        || rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
		            && board[4][0] == theSeed
		            && board[3][1] == theSeed
		            && board[2][2] == theSeed
		        || rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
		            && board[0][4] == theSeed
		            && board[1][3] == theSeed
		            && board[2][2] == theSeed
				|| rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
			            && board[1][3] == theSeed
			            && board[2][2] == theSeed
			            && board[3][1] == theSeed
			    || rowSelected + colSelected == 3  // 3-in-the-opposite-diagonal
					    && board[0][3] == theSeed
					    && board[1][2] == theSeed
					    && board[2][1] == theSeed
			    || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
				            && board[0][2] == theSeed
				            && board[1][1] == theSeed
				            && board[2][0] == theSeed);
			   }
	   if (w == 3 && r==6){
		   //for board 6x6
		      return (board[rowSelected][0] == theSeed  // 3-in-the-row
		            && board[rowSelected][1] == theSeed
		            && board[rowSelected][2] == theSeed
		       || board[0][colSelected] == theSeed      // 3-in-the-column
		            && board[1][colSelected] == theSeed
		            && board[2][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[0][0] == theSeed
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed

		            
		            
		       || board[rowSelected][1] == theSeed  // 3-in-the-row
		            && board[rowSelected][2] == theSeed
		            && board[rowSelected][3] == theSeed
		       || board[1][colSelected] == theSeed      // 3-in-the-column
		            && board[2][colSelected] == theSeed
		            && board[3][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[1][1] == theSeed
		            && board[2][2] == theSeed
		            && board[3][3] == theSeed

		           
		            
		       || board[rowSelected][2] == theSeed  // 3-in-the-row
		            && board[rowSelected][3] == theSeed
		            && board[rowSelected][4] == theSeed
		       || board[2][colSelected] == theSeed      // 3-in-the-column
		            && board[3][colSelected] == theSeed
		            && board[4][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[2][2] == theSeed
		            && board[3][3] == theSeed
		            && board[4][4] == theSeed

		            
		    		  
		       || board[rowSelected][3] == theSeed  // 3-in-the-row
		            && board[rowSelected][4] == theSeed
		            && board[rowSelected][5] == theSeed
		       || board[3][colSelected] == theSeed      // 3-in-the-column
		            && board[4][colSelected] == theSeed
		            && board[5][colSelected] == theSeed
		       || rowSelected == colSelected            // 3-in-the-diagonal
		            && board[3][3] == theSeed
		            && board[4][4] == theSeed
		            && board[5][5] == theSeed
		     		       || rowSelected + colSelected == 6  // 3-in-the-opposite-diagonal
				            && board[2][4] == theSeed
				            && board[3][3] == theSeed
				            && board[4][2] == theSeed
				       || rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
				            && board[1][4] == theSeed
				            && board[3][2] == theSeed
				            && board[2][3] == theSeed
				       || rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
				            && board[4][1] == theSeed
				            && board[3][2] == theSeed
				            && board[2][3] == theSeed
				        || rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
				            && board[4][0] == theSeed
				            && board[3][1] == theSeed
				            && board[2][2] == theSeed
				        || rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
				            && board[0][4] == theSeed
				            && board[1][3] == theSeed
				            && board[2][2] == theSeed
						|| rowSelected + colSelected == 4  // 3-in-the-opposite-diagonal
					            && board[1][3] == theSeed
					            && board[2][2] == theSeed
					            && board[3][1] == theSeed
					    || rowSelected + colSelected == 3  // 3-in-the-opposite-diagonal
							    && board[0][3] == theSeed
							    && board[1][2] == theSeed
							    && board[2][1] == theSeed
					    || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
						            && board[0][2] == theSeed
						            && board[1][1] == theSeed
						            && board[2][0] == theSeed
						|| rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
						            && board[0][5] == theSeed
						            && board[1][4] == theSeed
						            && board[2][3] == theSeed
						|| rowSelected + colSelected == 5  // 3-in-the-opposite-diagonal
						            && board[5][0] == theSeed
						            && board[4][1] == theSeed
						            && board[3][2] == theSeed);
			   }
	   
	   else if(w==4 && r==4)
	   {
		   //for board 4x4
	return (board[rowSelected][0] == theSeed  // 4-in-the-row
       && board[rowSelected][1] == theSeed
       && board[rowSelected][2] == theSeed
       && board[rowSelected][3] == theSeed
  || board[0][colSelected] == theSeed      // 4-in-the-column
       && board[1][colSelected] == theSeed
       && board[2][colSelected] == theSeed
       && board[3][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[0][0] == theSeed
       && board[1][1] == theSeed
       && board[2][2] == theSeed
       && board[3][3]== theSeed
  || rowSelected + colSelected == 3   // 4-in-the-opposite-diagonal
       && board[0][3] == theSeed
       && board[1][2] == theSeed
       && board[2][1] == theSeed
       && board[3][0]== theSeed);
	}
	   
	   else if(w==4 && r==5)
	   {
		   //for board 5x5
	return (board[rowSelected][0] == theSeed  // 4-in-the-row
       && board[rowSelected][1] == theSeed
       && board[rowSelected][2] == theSeed
       && board[rowSelected][3] == theSeed
  || board[0][colSelected] == theSeed      // 4-in-the-column
       && board[1][colSelected] == theSeed
       && board[2][colSelected] == theSeed
       && board[3][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[0][0] == theSeed
       && board[1][1] == theSeed
       && board[2][2] == theSeed
       && board[3][3]== theSeed

       
  || board[rowSelected][1] == theSeed  // 4-in-the-row
       && board[rowSelected][2] == theSeed
       && board[rowSelected][3] == theSeed
       && board[rowSelected][4] == theSeed
  || board[1][colSelected] == theSeed      // 4-in-the-column
       && board[2][colSelected] == theSeed
       && board[3][colSelected] == theSeed
       && board[4][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[1][1] == theSeed
       && board[2][2] == theSeed
       && board[3][3] == theSeed
       && board[4][4]== theSeed
  || rowSelected + colSelected == 5  // 4-in-the-opposite-diagonal
       && board[1][4] == theSeed
       && board[2][3] == theSeed
       && board[3][2] == theSeed
       && board[4][1]== theSeed
  || rowSelected + colSelected == 3   // 4-in-the-opposite-diagonal
       && board[0][3] == theSeed
       && board[1][2] == theSeed
       && board[2][1] == theSeed
       && board[3][0]== theSeed
  || rowSelected + colSelected == 4   // 4-in-the-opposite-diagonal
       && board[0][4] == theSeed
       && board[1][3] == theSeed
       && board[2][2] == theSeed
       && board[3][1]== theSeed
  || rowSelected + colSelected == 4   // 4-in-the-opposite-diagonal
       && board[4][0] == theSeed
       && board[3][1] == theSeed
       && board[2][2] == theSeed
       && board[1][3]== theSeed);
	   }
	   
	   else if(w==4 && r==6)
	   {
		   //for board 6x6
	return (board[rowSelected][0] == theSeed  // 4-in-the-row
       && board[rowSelected][1] == theSeed
       && board[rowSelected][2] == theSeed
       && board[rowSelected][3] == theSeed
  || board[0][colSelected] == theSeed      // 4-in-the-column
       && board[1][colSelected] == theSeed
       && board[2][colSelected] == theSeed
       && board[3][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[0][0] == theSeed
       && board[1][1] == theSeed
       && board[2][2] == theSeed
       && board[3][3]== theSeed

       
			
  || board[rowSelected][1] == theSeed  // 4-in-the-row
       && board[rowSelected][2] == theSeed
       && board[rowSelected][3] == theSeed
       && board[rowSelected][4] == theSeed
  || board[1][colSelected] == theSeed      // 4-in-the-column
       && board[2][colSelected] == theSeed
       && board[3][colSelected] == theSeed
       && board[4][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[1][1] == theSeed
       && board[2][2] == theSeed
       && board[3][3] == theSeed
       && board[4][4]== theSeed

       
			
  || board[rowSelected][2] == theSeed  // 4-in-the-row
       && board[rowSelected][3] == theSeed
       && board[rowSelected][4] == theSeed
       && board[rowSelected][5] == theSeed
  || board[2][colSelected] == theSeed      // 4-in-the-column
       && board[3][colSelected] == theSeed
       && board[4][colSelected] == theSeed
       && board[5][colSelected] == theSeed
  || rowSelected == colSelected            // 4-in-the-diagonal
       && board[2][2] == theSeed
       && board[3][3] == theSeed
       && board[4][4] == theSeed
       && board[5][5]== theSeed
  || rowSelected + colSelected == 7   // 4-in-the-opposite-diagonal
       && board[2][5] == theSeed
       && board[3][4] == theSeed
       && board[4][3] == theSeed
       && board[5][2]== theSeed
  || rowSelected + colSelected == 3   // 4-in-the-opposite-diagonal
       && board[0][3] == theSeed
       && board[1][2] == theSeed
       && board[2][1] == theSeed
       && board[3][0]== theSeed
  || rowSelected + colSelected == 5  // 4-in-the-opposite-diagonal
       && board[1][4] == theSeed
       && board[2][3] == theSeed
       && board[3][2] == theSeed
       && board[4][1]== theSeed
  || rowSelected + colSelected == 5  // 4-in-the-opposite-diagonal
       && board[0][5] == theSeed
       && board[1][4] == theSeed
       && board[2][3] == theSeed
       && board[3][2]== theSeed
  || rowSelected + colSelected == 5  // 4-in-the-opposite-diagonal
       && board[5][0] == theSeed
       && board[4][1] == theSeed
       && board[3][2] == theSeed
       && board[2][3]== theSeed);
	   }
	   
	   else if(w==5 && r==5)
	   {
	return (board[rowSelected][0] == theSeed  // 5-in-the-row
		&& board[rowSelected][1] == theSeed
		&& board[rowSelected][2] == theSeed
		&& board[rowSelected][3] == theSeed
		&& board[rowSelected][4] == theSeed
	|| board[0][colSelected] == theSeed      // 5-in-the-column
		&& board[1][colSelected] == theSeed
		&& board[2][colSelected] == theSeed
		&& board[3][colSelected] == theSeed
		&& board[4][colSelected] == theSeed
	|| rowSelected == colSelected            // 5-in-the-diagonal
		&& board[0][0] == theSeed
		&& board[1][1] == theSeed
		&& board[2][2] == theSeed
		&& board[3][3]== theSeed
		&& board[4][4]== theSeed
	|| rowSelected + colSelected == 4   // 5-in-the-opposite-diagonal
		&& board[0][4] == theSeed
		&& board[1][3] == theSeed
		&& board[2][2] == theSeed
		&& board[3][1]== theSeed
		&& board[4][0]== theSeed); 
	   }
	   
	   else if(w==5 && r==6)
	   {
		   //for board 6x6
	return (board[rowSelected][0] == theSeed  // 5-in-the-row
		&& board[rowSelected][1] == theSeed
		&& board[rowSelected][2] == theSeed
		&& board[rowSelected][3] == theSeed
		&& board[rowSelected][4] == theSeed
	|| board[0][colSelected] == theSeed      // 5-in-the-column
		&& board[1][colSelected] == theSeed
		&& board[2][colSelected] == theSeed
		&& board[3][colSelected] == theSeed
		&& board[4][colSelected] == theSeed
	|| rowSelected == colSelected            // 5-in-the-diagonal
		&& board[0][0] == theSeed
		&& board[1][1] == theSeed
		&& board[2][2] == theSeed
		&& board[3][3]== theSeed
		&& board[4][4]== theSeed

			
	|| board[rowSelected][1] == theSeed  // 5-in-the-row
		&& board[rowSelected][2] == theSeed
		&& board[rowSelected][3] == theSeed
		&& board[rowSelected][4] == theSeed
		&& board[rowSelected][5] == theSeed
	|| board[1][colSelected] == theSeed      // 5-in-the-column
		&& board[2][colSelected] == theSeed
		&& board[3][colSelected] == theSeed
		&& board[4][colSelected] == theSeed
		&& board[5][colSelected] == theSeed
	|| rowSelected == colSelected            // 5-in-the-diagonal
		&& board[1][1] == theSeed
		&& board[2][2] == theSeed
		&& board[3][3] == theSeed
		&& board[4][4]== theSeed
		&& board[5][5]== theSeed
	|| rowSelected + colSelected == 6   // 5-in-the-opposite-diagonal
		&& board[1][5] == theSeed
		&& board[2][4] == theSeed
		&& board[3][3] == theSeed
		&& board[4][2]== theSeed
		&& board[5][1]== theSeed
	|| rowSelected + colSelected == 5   // 5-in-the-opposite-diagonal
		&& board[0][5] == theSeed
		&& board[1][4] == theSeed
		&& board[2][3] == theSeed
		&& board[3][2]== theSeed
		&& board[4][1]== theSeed
    || rowSelected + colSelected == 4   // 5-in-the-opposite-diagonal
		&& board[0][4] == theSeed
		&& board[1][3] == theSeed
		&& board[2][2] == theSeed
		&& board[3][1]== theSeed
		&& board[4][0]== theSeed
    || rowSelected + colSelected == 5   // 5-in-the-opposite-diagonal
		&& board[5][0] == theSeed
		&& board[4][1] == theSeed
		&& board[3][2] == theSeed
		&& board[2][3]== theSeed
		&& board[1][4]== theSeed); 
	   }
	   
	   
	   else if(w==6 && r==6){
		   //for board 6x6
	return (board[rowSelected][0] == theSeed  // 6-in-the-row
		&& board[rowSelected][1] == theSeed
		&& board[rowSelected][2] == theSeed
		&& board[rowSelected][3] == theSeed
		&& board[rowSelected][4] == theSeed
		&& board[rowSelected][5] == theSeed
	|| board[0][colSelected] == theSeed      // 6-in-the-column
		&& board[1][colSelected] == theSeed
		&& board[2][colSelected] == theSeed
		&& board[3][colSelected] == theSeed
		&& board[4][colSelected] == theSeed
		&& board[5][colSelected] == theSeed
	|| rowSelected == colSelected            // 6-in-the-diagonal
		&& board[0][0] == theSeed
		&& board[1][1] == theSeed
		&& board[2][2] == theSeed
		&& board[3][3]== theSeed
		&& board[4][4]== theSeed
		&& board[5][5]== theSeed
	|| rowSelected + colSelected == 5   // 6-in-the-opposite-diagonal
		&& board[0][5] == theSeed
		&& board[1][4] == theSeed
		&& board[2][3] == theSeed
		&& board[3][2]== theSeed
		&& board[4][1]== theSeed
		&& board[5][0]== theSeed);
	   }

	   
	return rootPaneCheckingEnabled;
   }
 
   /**
    *  Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
    */
   class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {  // invoke via repaint()
         super.paintComponent(g);    // fill background
         setBackground(Color.WHITE); // set its background color
 
         // Draw the grid-lines
         g.setColor(Color.LIGHT_GRAY);
         for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                  CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                  GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
         }
 
         // Draw the Seeds of all the cells if they are not empty
         // Use Graphics2D which allows us to set the pen's stroke
         Graphics2D g2d = (Graphics2D)g;
         g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
               BasicStroke.JOIN_ROUND));  // Graphics2D only
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               int x1 = col * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
               if (board[row][col] == Seed.CROSS) {
                  g2d.setColor(Color.RED);
                  int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                  int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                  g2d.drawLine(x1, y1, x2, y2);
                  g2d.drawLine(x2, y1, x1, y2);
               } else if (board[row][col] == Seed.NOUGHT) {
                  g2d.setColor(Color.BLUE);
                  g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               }
            }
         }
 
         // Print status-bar message
         if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Seed.CROSS) {
               statusBar.setText("X's Turn");
            } else {
               statusBar.setText("O's Turn");
            }
         } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
         } else if (currentState == GameState.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
         } else if (currentState == GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
         }
      }
   }
 
   /** The entry main() method */
   public static void main(String[] args) {
	   Scanner sc = new Scanner(System.in);
  	   System.out.println("Enter the size of the board (3-6): ");
         r = sc.nextInt();
         Scanner wc = new Scanner(System.in);
         System.out.println("Enter numbers for winning (3-6): ");
         w = wc.nextInt();
	  
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
    	  
         @Override
         public void run() {
            new TTTGraphics2P(); // Let the constructor do the job
         }
      });
   }
}