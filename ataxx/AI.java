/* Skeleton code copyright (C) 2008, 2022 Paul N. Hilfinger and the
 * Regents of the University of California.  Do not distribute this or any
 * derivative work without permission. */

package ataxx;

import java.util.Random;

import static ataxx.PieceColor.*;
import static java.lang.Math.min;
import static java.lang.Math.max;

/** A Player that computes its own moves.
 *  @author Tyler Marino
 */
class AI extends Player {

    /** Maximum minimax search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 4;
    /** A position magnitude indicating a win (for red if positive, blue
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI for GAME that will play MYCOLOR. SEED is used to initialize
     *  a random-number generator for use in move computations.  Identical
     *  seeds produce identical behaviour. */
    AI(Game game, PieceColor myColor, long seed) {
        super(game, myColor);
        _random = new Random(seed);
    }

    @Override
    boolean isAuto() {
        return true;
    }

    @Override
    String getMove() {
        if (!getBoard().canMove(myColor())) {
            game().reportMove(Move.pass(), myColor());
            return "-";
        }
        Main.startTiming();
        Move move = findMove();
        Main.endTiming();
        game().reportMove(move, myColor());
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(getBoard());
        _lastFoundMove = null;
        if (myColor() == RED) {
            minMax(b, 1, true, 1, -INFTY, INFTY);
        } else {
            minMax(b, 1, true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to the findMove method
     *  above. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth, boolean saveMove, int sense,
                       int alpha, int beta) {
        /* We use WINNING_VALUE + depth as the winning value so as to favor
         * wins that happen sooner rather than later (depth is larger the
         * fewer moves have been made. */
        if (depth == 0 || board.getWinner() != null) {
            return staticScore(board, WINNING_VALUE + depth);
        }
        boolean first = !board.canMove(BLUE);
        boolean second = !board.canMove(RED);
        if ((board.numMoves() == 0) && first && second) {
            board.endGame(RED);
        }
        Move best;
        best = null;
        int bestScore;
        bestScore = 0;
        for (char j = 'a'; j < 'h'; j++) {
            for (char i = '1'; i < '8'; i++) {
                for (char z = (char) (j - 2); z <= (char) (j + 2); z++) {
                    for (char k = (char) (i - 2); k <= (char) (i + 2); k++) {
                        Move m;
                        if ((z == j) && (i == k)) {
                            m = Move.pass();
                        } else {
                            m = Move.move(j, i, z, k);
                        }
                        boolean legal_move = board.legalMove(m);
                        if (board.legalMove(m)) {
                            System.out.println(m.toString());
                            board.makeMove(m);
                            int response = minMax(board, depth - 1,
                                    false, sense * (-1),
                                    alpha, beta);
                            int redNum = board.redPieces();
                            int blueNum = board.bluePieces();
                            if (m.isJump()) {
                                if (board.get(j, i) == RED) {
                                    response += (5 * (redNum - blueNum));
                                } else {
                                    response -= (5 * (redNum - blueNum));
                                }
                            }
                            board.undo();
                            if (sense == 1) {
                                if (response > bestScore) {
                                    best = m;
                                    bestScore = response;
                                    alpha = max(alpha, bestScore);
                                    if (alpha >= beta) {
                                        break;
                                    }

                                }
                            } else {
                                if (response < bestScore) {
                                    best = m;
                                    bestScore = response;
                                    beta = min(beta, bestScore);
                                    if (alpha >= beta) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (saveMove) {
            _lastFoundMove = best;
        }
        return bestScore;
    }

    /**private void helper1 (int res, int BS, int al, int beta, Move a, Move
     * m) {=
        if (res < BS) {
            a = m;
            BS = res;
            beta = min(beta, BS);
            if (al >= beta) {
                break;
            }
        }
    }*/


    /** Return a heuristic value for BOARD.  This value is +- WINNINGVALUE in
     *  won positions, and 0 for ties. */
    private int staticScore(Board board, int winningValue) {
        PieceColor winner = board.getWinner();
        if (winner == EMPTY) {
            if (board.whoseMove() == RED) {
                return -2;
            } else {
                return 2;
            }
        }
        if (winner != null) {
            return switch (winner) {
                case RED -> winningValue + 100;
                case BLUE -> -winningValue - 100;
                default -> 0;
            };
        } else {
            int redPieces = board.redPieces();
            int bluePieces = board.bluePieces();
            int diffOfPieces = redPieces - bluePieces;
            if (board.whoseMove() == BLUE) {
                return diffOfPieces + winningValue;
            } else {
                return diffOfPieces - winningValue;
            }
        }
    }


    /** Pseudo-random number generator for move computation. */
    private Random _random = new Random();
}
