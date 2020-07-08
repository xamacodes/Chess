package ChessApplication;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class PieceRules {

    public boolean pieceRestrictions(JLayeredPane board, Map<Component, ImageIcon> wherePiecesAre, Component old,
                                     Component n, Player1 p1, Player2 p2, Map<Integer, Color> map) {

        int indexOfOld = 0;
        int indexOfNew = 0;

        for (int i = 0; i < 64; i++) {
            if (board.getComponent(i) == old) {
                indexOfOld = i;
            }
            if (board.getComponent(i) == n) {
                indexOfNew = i;
            }
        }

        for (Map.Entry<Component, ImageIcon> entry : wherePiecesAre.entrySet()) {
            if (entry.getKey() == old) {
                //Pawns
                if ((entry.getValue() == p1.getPawn())) {
                    if ((indexOfNew - indexOfOld < 7) || (indexOfNew - indexOfOld > 9)) { //Can't move anywhere other than straight or diagonal
                        return false;
                    } else if ((indexOfNew - indexOfOld == 8) && hasPiece(n, wherePiecesAre)) { //Can only move straight if there's no piece there
                        return false;
                    } else if (((indexOfNew - indexOfOld == 7) || (indexOfNew - indexOfOld == 9)) && !hasPiece(n, wherePiecesAre)) { //Can only move diagonal if there's a piece
                        return false;
                    }
                } else if (entry.getValue() == p2.getPawn()) {
                    if ((indexOfOld - indexOfNew < 7) || (indexOfOld - indexOfNew > 9)) {
                        return false;
                    } else if ((indexOfOld - indexOfNew == 8) && hasPiece(n, wherePiecesAre)) {
                        return false;
                    } else if (((indexOfOld - indexOfNew == 7) || (indexOfOld - indexOfNew == 9)) && !hasPiece(n, wherePiecesAre)) {
                        return false;
                    }

                    /* NEXT PIECE */

                    //Bishops
                } else if (entry.getValue() == p1.getBishop()) {
                    //Moves in factors of 7 and 9
                    if (((indexOfNew - indexOfOld) % 7 != 0) && ((indexOfNew - indexOfOld) % 9 != 0)) { //Can't move piece if it's not trying to go diagonal
                        return false;
                    } else if (areDiagonal(old, n, board, map)) { //If the piece going diagonal
                        if ((indexOfNew - indexOfOld) > 9) { //If it's going diagonal forwards
                            if ((indexOfNew - indexOfOld) % 7 == 0) { //If it's going diagonal right
                                int i = 0;
                                for (i = indexOfOld + 7; i <= indexOfNew - 7; i = i + 7) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfNew - indexOfOld) % 9 == 0) { //If it's going diagonal left
                                int i = 0;
                                for (i = indexOfOld + 9; i <= indexOfNew - 9; i = i + 9) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -9) { //If it's going diagonal backwards
                            if ((indexOfOld - indexOfNew) % 7 == 0) { //If it's going diagonal right
                                int i = 0;
                                for (i = indexOfNew + 7; i <= indexOfOld - 7; i = i + 7) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfOld - indexOfNew) % 9 == 0) { //If it's going diagonal left
                                int i = 0;
                                for (i = indexOfNew + 9; i <= indexOfOld - 9; i = i + 9) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        }
                    } else if (!areDiagonal(old, n, board, map)) { //Last check to make sure the piece is diagonal to its destination
                        return false;
                    }
                } else if (entry.getValue() == p2.getBishop()) {
                    //Moves in factors of 7 and 9
                    if (((indexOfOld - indexOfNew) % 7 != 0) && ((indexOfOld - indexOfNew) % 9 != 0)) {
                        return false;
                    } else if ((indexOfOld - indexOfNew) > 9) {
                        if ((indexOfOld - indexOfNew) % 7 == 0) {
                            int i = 0;
                            for (i = indexOfNew + 7; i <= indexOfOld - 7; i = i + 7) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                    return false;
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) % 9 == 0) {
                            int i = 0;
                            for (i = indexOfNew + 9; i <= indexOfOld - 9; i = i + 9) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if ((indexOfNew - indexOfOld) < -9) {
                        if ((indexOfNew - indexOfOld) % 7 == 0) {
                            int i = 0;
                            for (i = indexOfOld + 7; i <= indexOfNew - 7; i = i + 7) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                    return false;
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) % 9 == 0) {
                            int i = 0;
                            for (i = indexOfOld + 9; i <= indexOfNew - 9; i = i + 9) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                //Rooks
                else if (entry.getValue() == p1.getRook()) {
                    //Can't jump over people backwards
                    if (((indexOfNew - indexOfOld) % 7 == 0) && ((indexOfNew - indexOfOld) % 9 == 0) && (indexOfNew - indexOfOld) % 8 != 0
                            || (indexOfNew - indexOfOld) % -8 != 0 && (!inSameRow(old, n, board) && !inSameColumn(old, n, board))) { //Making sure not diagonal,
                        //that it's going in straight direction, and that the piece is in the same row/column as the destination
                        return false;
                    } else if (inSameRow(old, n, board)) { //If the piece and its destination are in the same row
                        if ((indexOfNew - indexOfOld) > 1) { //If it's moving to the left
                            for (int i = indexOfOld; i < indexOfNew; i++) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -1) { //If it's moving to the right
                            for (int i = indexOfNew; i < indexOfOld; i++) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (inSameColumn(old, n, board)) { //If the piece and the destination are in the same column
                        if ((indexOfNew - indexOfOld) > 1) { //If it's moving to the left
                            for (int i = indexOfOld; i < indexOfNew; i = i + 8) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -1) { //If it's moving to the right
                            for (int i = indexOfNew; i < indexOfOld; i = i + 8) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (entry.getValue() == p2.getRook()) {
                    //Can't move diagonally
                    //Can't jump over people
                    if (((indexOfOld - indexOfNew) % 7 == 0) && ((indexOfOld - indexOfNew) % 9 == 0) && (indexOfOld - indexOfNew) % 8 != 0
                            || (indexOfOld - indexOfNew) % -8 != 0 && (!inSameRow(old, n, board) && !inSameColumn(old, n, board))) {
                        return false;
                    } else if (inSameRow(old, n, board)) {
                        if ((indexOfOld - indexOfNew) > 1) {
                            for (int i = indexOfNew; i < indexOfOld; i++) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) < -1) {
                            for (int i = indexOfOld; i < indexOfNew; i++) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (inSameColumn(old, n, board)) {
                        if ((indexOfOld - indexOfNew) > 1) {
                            for (int i = indexOfNew; i < indexOfOld; i = i + 8) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) < -1) {
                            for (int i = indexOfOld; i < indexOfNew; i = i + 8) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                //Kings
                else if (entry.getValue() == p1.getKing() || entry.getValue() == p2.getKing()) { //Works for both p1 and p2
                    int diff = indexOfNew - indexOfOld;
                    if (diff != 8 && diff != 9 && diff != 1 && diff != -7
                            && diff != -8 && diff != -9 && diff != -1 && diff != 7) { //If it's not trying to go to a destination that's not right near it, it can't move
                        return false;
                    }
                }
                //Queens
                else if (entry.getValue() == p1.getQueen()) {
                    if (inSameRow(old, n, board)) { //If the piece and destination are in the same row
                        if ((indexOfNew - indexOfOld) > 1) { //If it's moving to the left
                            for (int i = indexOfOld; i < indexOfNew; i++) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -1) { //If it's moving to the right
                            for (int i = indexOfNew; i < indexOfOld; i++) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (inSameColumn(old, n, board)) { //If the piece and destination are in the same column
                        if ((indexOfNew - indexOfOld) > 1) { //Must check to see if there are any pieces in between (if there are, it can't move)
                            for (int i = indexOfOld; i < indexOfNew; i = i + 8) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -1) { //If it's moving ot the left
                            for (int i = indexOfNew; i < indexOfOld; i = i + 8) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (areDiagonal(old, n, board, map)) { //If the piece and destination are diagonal
                        if ((indexOfNew - indexOfOld) > 9) { //If it's moving diagonal forwards
                            if ((indexOfNew - indexOfOld) % 7 == 0) { //If it's moving diagonal left
                                int i = 0;
                                for (i = indexOfOld + 7; i <= indexOfNew - 7; i = i + 7) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfNew - indexOfOld) % 9 == 0) { //If it's moving diagonal right
                                int i = 0;
                                for (i = indexOfOld + 9; i <= indexOfNew - 9; i = i + 9) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        } else if ((indexOfNew - indexOfOld) < -9) { //If it's moving diagonal backwards
                            if ((indexOfOld - indexOfNew) % 7 == 0) { //If it's moving diagonal left
                                int i = 0;
                                for (i = indexOfNew + 7; i <= indexOfOld - 7; i = i + 7) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfOld - indexOfNew) % 9 == 0) { //If it's moving diagonal right
                                int i = 0;
                                for (i = indexOfNew + 9; i <= indexOfOld - 9; i = i + 9) { //Must check to see if there are any pieces in between (if there are, it can't move)
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        }
                    } else if (((!inSameRow(old, n, board) && !inSameColumn(old, n, board)) && !areDiagonal(old, n, board, map))) { //Last check to make sure the piece and its destination are both either
                        //in the same row, in the same column, or diagonal
                        return false;
                    }
                } else if (entry.getValue() == p2.getQueen()) {
                    if (inSameRow(old, n, board)) {
                        if ((indexOfOld - indexOfNew) > 1) {
                            for (int i = indexOfNew; i < indexOfOld; i++) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) < -1) {
                            for (int i = indexOfOld; i < indexOfNew; i++) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (inSameColumn(old, n, board)) {
                        if ((indexOfOld - indexOfNew) > 1) {
                            for (int i = indexOfNew; i < indexOfOld; i = i + 8) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) < -1) {
                            for (int i = indexOfOld; i < indexOfNew; i = i + 8) {
                                if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != old && board.getComponent(i) != n) {
                                    return false;
                                }
                            }
                        }
                    } else if (areDiagonal(old, n, board, map)) {
                        if ((indexOfOld - indexOfNew) > 9) {
                            if ((indexOfOld - indexOfNew) % 7 == 0) {
                                int i = 0;
                                for (i = indexOfNew + 7; i <= indexOfOld - 7; i = i + 7) {
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfOld - indexOfNew) % 9 == 0) {
                                int i = 0;
                                for (i = indexOfNew + 9; i <= indexOfOld - 9; i = i + 9) {
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        } else if ((indexOfOld - indexOfNew) < -9) {
                            if ((indexOfNew - indexOfOld) % 7 == 0) {
                                int i = 0;
                                for (i = indexOfOld + 7; i <= indexOfNew - 7; i = i + 7) {
                                    if (hasPiece(board.getComponent(i), wherePiecesAre)) {
                                        return false;
                                    }
                                }
                            } else if ((indexOfNew - indexOfOld) % 9 == 0) {
                                int i = 0;
                                for (i = indexOfOld + 9; i <= indexOfNew - 9; i = i + 9) {
                                    if (hasPiece(board.getComponent(i), wherePiecesAre) && board.getComponent(i) != n) {
                                        return false;
                                    }
                                }
                            }
                        }
                    } else if (((!inSameRow(old, n, board) && !inSameColumn(old, n, board)) && !areDiagonal(old, n, board, map))) {
                        return false;
                    }
                } else if (entry.getValue() == p1.getKnight()) {
                    int diff = indexOfNew - indexOfOld;
                    if ((diff != 10 && diff != 6 && diff != 17 && diff != 15) &&
                            (diff != -10 && diff != -6 && diff != -17 && diff != -15)) { //It can only move to destinations that are one "L-shape" away
                        return false;
                    }
                } else if (entry.getValue() == p2.getKnight()) {
                    int diff = indexOfOld - indexOfNew;
                    if ((diff != 10 && diff != 6 && diff != 17 && diff != 15) &&
                            (diff != -10 && diff != -6 && diff != -17 && diff != -15)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean hasPiece(Component c, Map<Component, ImageIcon> wherePiecesAre) {
        int count = 0;
        for (Map.Entry<Component, ImageIcon> entry : wherePiecesAre.entrySet()) {
            if (c == entry.getKey()) {
                count++;
            }
        }
        return count != 0;
    }

    public boolean inSameRow(Component a, Component b, JLayeredPane board) {
        int boxA = 0;
        int boxB = 0;
        for (int i = 0; i < 64; i++) {
            if (board.getComponent(i) == a) { //Checks the board for the given Component a, finds its index
                boxA = i;
            }
            if (board.getComponent(i) == b) { //Checks the board for the given Component b, finds its index
                boxB = i;
            }
        }

        //Checking if Component A and B are in the same row
        if (boxA <= 7 && boxB <= 7) {
            return true;
        } else if (boxA >= 8 && boxA <= 15 && boxB >= 8 && boxB <= 15) {
            return true;
        } else if (boxA >= 16 && boxA <= 23 && boxB >= 16 && boxB <= 23) {
            return true;
        } else if (boxA >= 24 && boxA <= 31 && boxB >= 24 && boxB <= 31) {
            return true;
        } else if (boxA >= 32 && boxA <= 39 && boxB >= 32 && boxB <= 39) {
            return true;
        } else if (boxA >= 40 && boxA <= 47 && boxB >= 40 && boxB <= 47) {
            return true;
        } else if (boxA >= 48 && boxA <= 55 && boxB >= 48 && boxB <= 55) {
            return true;
        } else if (boxA >= 56 && boxA <= 63 && boxB >= 56 && boxB <= 63) {
            return true;
        }
        return false;
    }

    public boolean inSameColumn(Component a, Component b, JLayeredPane board) {
        int boxA = 0;
        int boxB = 0;
        for (int i = 0; i < 64; i++) {
            if (board.getComponent(i) == a) { //Checks the board for the given Component a, finds its index
                boxA = i;
            }
            if (board.getComponent(i) == b) { //Checks the board for the given Component a, finds its index
                boxB = i;
            }
        }

        if ((boxA - boxB) % 8 == 0 || (boxA - boxB % -8 == 0)) { //If the Component a and Component b are separated by a factor of 8, they're in the same column
            return true;
        }

        //All these ArrayLists are efficient, but they do the job. Makes an array of all the numbers in every column
        ArrayList<Integer> c1 = new ArrayList<>();
        for (int i = 0; i <= 56; i = i + 8) {
            c1.add(i);
        }
        ArrayList<Integer> c2 = new ArrayList<>();
        for (int i = 1; i <= 57; i = i + 8) {
            c2.add(i);
        }
        ArrayList<Integer> c3 = new ArrayList<>();
        for (int i = 2; i <= 58; i = i + 8) {
            c3.add(i);
        }
        ArrayList<Integer> c4 = new ArrayList<>();
        for (int i = 3; i <= 59; i = i + 8) {
            c4.add(i);
        }
        ArrayList<Integer> c5 = new ArrayList<>();
        for (int i = 4; i <= 60; i = i + 8) {
            c5.add(i);
        }
        ArrayList<Integer> c6 = new ArrayList<>();
        for (int i = 5; i <= 61; i = i + 8) {
            c6.add(i);
        }
        ArrayList<Integer> c7 = new ArrayList<>();
        for (int i = 6; i <= 62; i = i + 8) {
            c7.add(i);
        }
        ArrayList<Integer> c8 = new ArrayList<>();
        for (int i = 7; i <= 63; i = i + 8) {
            c8.add(i);
        }

        //Checks to see if the column contains both Component a and Component b
        if (c1.contains(boxA) && c1.contains(boxB)) {
            return true;
        } else if (c2.contains(boxA) && c2.contains(boxB)) {
            return true;
        } else if (c3.contains(boxA) && c3.contains(boxB)) {
            return true;
        } else if (c4.contains(boxA) && c4.contains(boxB)) {
            return true;
        } else if (c5.contains(boxA) && c5.contains(boxB)) {
            return true;
        } else if (c6.contains(boxA) && c6.contains(boxB)) {
            return true;
        } else if (c7.contains(boxA) && c7.contains(boxB)) {
            return true;
        } else if (c8.contains(boxA) && c8.contains(boxB)) {
            return true;
        }
        return false;
    }

    public boolean areDiagonal(Component a, Component b, JLayeredPane board, Map<Integer, Color> map) {
        int boxA = 0;
        int boxB = 0;
        Color boxAColor = null;
        Color boxBColor = null;
        for (int i = 0; i < 64; i++) {
            if (board.getComponent(i) == a) { //Checks the board for the given Component a, finds its index
                boxA = i;
            }
            if (board.getComponent(i) == b) { //Checks the board for the given Component b, finds its index
                boxB = i;
            }
        }

        for (Map.Entry<Integer, Color> entry : map.entrySet()) { //Searching the map, which maps the index of each component to its checker color
            if (entry.getKey() == boxA) { //Finding the color of the Component a's box
                boxAColor = entry.getValue();
            }
            if (entry.getKey() == boxB) { //Finding the color of Component b's box
                boxBColor = entry.getValue();
            }
        }

        if (boxAColor != boxBColor) { //Checks the colors to see if they're the same. Diagonal components must have the same color checker
            return false;
        }

        if ((boxA - boxB) % 7 == 0 || (boxA - boxB) % -7 == 0 || (boxA - boxB) % 9 == 0 || (boxA - boxB) % -9 == 0) { //Makes sure the difference between
            //the components is a factor of 7 or 9
            return true;
        }
        return false;
    }
}

/* Leftover code (don't delete, might need later)
 || ((indexOfNew - indexOfOld) % 7 != 0 &&
    (indexOfNew - indexOfOld) % 9 != 0 && (indexOfNew - indexOfOld) % 8 != 0 && (indexOfNew - indexOfOld) % -9 != 0 &&
    (indexOfNew - indexOfOld) % -8 != 0 && (indexOfNew - indexOfOld) % -7 != 0)
*/