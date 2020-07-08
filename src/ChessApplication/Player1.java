package ChessApplication;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Player1 {

    private ImageIcon knight;
    private ImageIcon pawn;
    private ImageIcon king;
    private ImageIcon queen;
    private ImageIcon bishop;
    private ImageIcon rook;
    public ArrayList<ImageIcon> pieces;

    Player1() {
        knight = new ImageIcon("./images/knight.png");
        pawn = new ImageIcon("./images/pawn.png");
        king = new ImageIcon("./images/king.png");
        queen = new ImageIcon("./images/queen.png");
        bishop = new ImageIcon("./images/bishop.png");
        rook = new ImageIcon("./images/rook.png");
        pieces = new ArrayList<>();
    }

    void addToBoard(JLayeredPane board, Map<Component, ImageIcon> wherePiecesAre) {

        //Knights
        JPanel temp = (JPanel) board.getComponent(1);
        temp.add(new JLabel(knight));
        pieces.add(knight);
        wherePiecesAre.put(board.getComponent(1), knight);

        temp = (JPanel) board.getComponent(6);
        temp.add(new JLabel(knight));
        pieces.add(knight);
        wherePiecesAre.put(board.getComponent(6), knight);

        //Pawns
        for (int i = 8; i < 16; i++) {
            temp = (JPanel) board.getComponent(i);
            temp.add(new JLabel(pawn));
            wherePiecesAre.put(board.getComponent(i), pawn);
            pieces.add(pawn);
        }

        //King
        temp = (JPanel) board.getComponent(3);
        temp.add(new JLabel(king));
        wherePiecesAre.put(board.getComponent(3), king);
        pieces.add(king);

        //Queen
        temp = (JPanel) board.getComponent(4);
        temp.add(new JLabel(queen));
        wherePiecesAre.put(board.getComponent(4), queen);
        pieces.add(queen);

        //Bishops
        temp = (JPanel) board.getComponent(2);
        temp.add(new JLabel(bishop));
        wherePiecesAre.put(board.getComponent(2), bishop);
        pieces.add(bishop);

        temp = (JPanel) board.getComponent(5);
        temp.add(new JLabel(bishop));
        wherePiecesAre.put(board.getComponent(5), bishop);
        pieces.add(bishop);

        //Rooks
        temp = (JPanel) board.getComponent(0);
        temp.add(new JLabel(rook));
        wherePiecesAre.put(board.getComponent(0), rook);
        pieces.add(rook);

        temp = (JPanel) board.getComponent(7);
        temp.add(new JLabel(rook));
        wherePiecesAre.put(board.getComponent(7), rook);
        pieces.add(rook);

    }

    //New
    ImageIcon getKnight() {
        return knight;
    }

    ImageIcon getPawn() {
        return pawn;
    }

    ImageIcon getKing() {
        return king;
    }

    ImageIcon getQueen() {
        return queen;
    }

    ImageIcon getBishop() {
        return bishop;
    }

    ImageIcon getRook() {
        return rook;
    }

}