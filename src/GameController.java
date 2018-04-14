/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marcelorsjr
 */
enum PlayType {
    BLANK, CIRCLE, CROSS
}

class Move {

    int row;
    int col;
}

class GameController {

    public boolean gameCompleted;
    public PlayType[][] gameMatrix;
    public PlayType winner;

    public GameController() {
        gameCompleted = false;

        gameMatrix = new PlayType[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameMatrix[i][j] = PlayType.BLANK;
            }
        }
    }

    public boolean isMovesLeft() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameMatrix[i][j] == PlayType.BLANK) {
                    return true;
                }
            }
        }
        return false;
    }

    public int evaluateGameMatrix() {
        // Checking for Rows
        for (int row = 0; row < 3; row++) {
            if (gameMatrix[row][0] == gameMatrix[row][1]
                    && gameMatrix[row][1] == gameMatrix[row][2]) {
                if (gameMatrix[row][0] == PlayType.CROSS) {

                    return +10;
                } else if (gameMatrix[row][0] == PlayType.CIRCLE) {
                    return -10;
                }
            }
        }

        // Checking for Columns
        for (int col = 0; col < 3; col++) {
            if (gameMatrix[0][col] == gameMatrix[1][col]
                    && gameMatrix[1][col] == gameMatrix[2][col]) {
                if (gameMatrix[0][col] == PlayType.CROSS) {
                    return +10;
                } else if (gameMatrix[0][col] == PlayType.CIRCLE) {
                    return -10;
                }
            }
        }

        // Checking for Diagonals
        if (gameMatrix[0][0] == gameMatrix[1][1] && gameMatrix[1][1] == gameMatrix[2][2]) {
            if (gameMatrix[0][0] == PlayType.CROSS) {
                return +10;
            } else if (gameMatrix[0][0] == PlayType.CIRCLE) {
                return -10;
            }
        }

        if (gameMatrix[0][2] == gameMatrix[1][1] && gameMatrix[1][1] == gameMatrix[2][0]) {
            if (gameMatrix[0][2] == PlayType.CROSS) {
                return +10;
            } else if (gameMatrix[0][2] == PlayType.CIRCLE) {
                return -10;
            }
        }

        // Else if none of them have won then return 0
        return 0;
    }

    public int minimax(int depth, boolean isMax) {
        int score = evaluateGameMatrix();

        // If Maximizer has won the game return his/her
        // evaluated score

	// Subtracting the depth, we garantee that the fastest solution was chosen

        if (score == 10) {
            return score - depth;
        }

        // If Minimizer has won the game return his/her
        // evaluated score

	// Adding the depth, we garantee that the fastest solution was chosen
        if (score == -10) {
            return score + depth;
        }

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft() == false) {
            return 0;
        }

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (gameMatrix[i][j] == PlayType.BLANK) {
                        // Make the move
                        gameMatrix[i][j] = PlayType.CROSS;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best,
                                minimax(depth + 1, !isMax));

                        // Undo the move
                        gameMatrix[i][j] = PlayType.BLANK;
                    }
                }
            }
            return best;
        } // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (gameMatrix[i][j] == PlayType.BLANK) {
                        // Make the move
                        gameMatrix[i][j] = PlayType.CIRCLE;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best,
                                minimax(depth + 1, !isMax));

                        // Undo the move
                        gameMatrix[i][j] = PlayType.BLANK;
                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove() {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        // Traverse all cells, evalutae minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if celll is empty
                if (gameMatrix[i][j] == PlayType.BLANK) {
                    // Make the move
                    gameMatrix[i][j] = PlayType.CROSS;

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(0, false);

                    // Undo the move
                    gameMatrix[i][j] = PlayType.BLANK;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

}
