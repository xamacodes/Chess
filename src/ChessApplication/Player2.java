package ChessApplication;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Player2 {

    private ImageIcon knight;
    private ImageIcon pawn;
    private ImageIcon king;
    private ImageIcon queen;
    private ImageIcon bishop;
    private ImageIcon rook;
    public ArrayList<ImageIcon> pieces;

    Player2() {
        knight = new ImageIcon("./images/knight2.png");
        pawn = new ImageIcon("./images/pawn2.png");
        king = new ImageIcon("./images/king2.png");
        queen = new ImageIcon("./images/queen2.png");
        bishop = new ImageIcon("./images/bishop2.png");
        rook = new ImageIcon("./images/rook2.png");
        pieces = new ArrayList<>();
    }

    void addToBoard(JLayeredPane board, Map<Component, ImageIcon> wherePiecesAre) {

        //Knights
        JPanel temp = (JPanel) board.getComponent(57);
        temp.add(new JLabel(knight));
        wherePiecesAre.put(board.getComponent(57), knight);
        pieces.add(knight);

        temp = (JPanel) board.getComponent(62);
        temp.add(new JLabel(knight));
        wherePiecesAre.put(board.getComponent(62), knight);
        pieces.add(knight);

        //Pawns
        for (int i = 48; i < 56; i++) {
            temp = (JPanel) board.getComponent(i);
            temp.add(new JLabel(pawn));
            wherePiecesAre.put(board.getComponent(i), pawn);
            pieces.add(pawn);
        }

        //King
        temp = (JPanel) board.getComponent(59);
        temp.add(new JLabel(king));
        wherePiecesAre.put(board.getComponent(59), king);
        pieces.add(king);

        //Queen
        temp = (JPanel) board.getComponent(60);
        temp.add(new JLabel(queen));
        wherePiecesAre.put(board.getComponent(60), queen);
        pieces.add(queen);

        //Bishops
        temp = (JPanel) board.getComponent(58);
        temp.add(new JLabel(bishop));
        wherePiecesAre.put(board.getComponent(58), bishop);
        pieces.add(bishop);

        temp = (JPanel) board.getComponent(61);
        temp.add(new JLabel(bishop));
        wherePiecesAre.put(board.getComponent(61), bishop);
        pieces.add(bishop);

        //Rooks
        temp = (JPanel) board.getComponent(56);
        temp.add(new JLabel(rook));
        wherePiecesAre.put(board.getComponent(56), rook);
        pieces.add(rook);

        temp = (JPanel) board.getComponent(63);
        temp.add(new JLabel(rook));
        wherePiecesAre.put(board.getComponent(63), rook);
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
