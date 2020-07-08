package ChessApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Special Features:
//Logs when a player captures another piece
//Has a start over button, which resets the board for a new game, without having to re-run the program
//Has a quit button, to directly quit the program
//All the pieces function as they should, under real life piece restrictions (i.e. pawns can't move diagonally)
//You can't capture your own teammate
//There will be JOptionPanels warning you if you violate either of the above 2 conditions

public class ChessScreen extends JFrame implements MouseListener, ActionListener {

    private JLayeredPane board;
    private JFrame frame;
    private Box updates; //New
    private JPanel temp;
    private JPanel temp2;
    private JDialog rulesAlert1; //New
    private JDialog rulesAlert2; //New

    private Player1 p1 = new Player1();
    private Player2 p2 = new Player2();
    private PieceRules pr = new PieceRules(); //New

    private Map<Integer, Color> map;
    private Map<Component, ImageIcon> wherePiecesAre;
    private boolean pieceSelected;
    private int old;

    ChessScreen() {
        wherePiecesAre = new HashMap<>();

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1000, 550));
        board = new JLayeredPane();
        board.addMouseListener(this);

        board.setPreferredSize(new Dimension(500, 500));
        frame.getContentPane().add(board);

        frame.setLayout(new FlowLayout()); //New
        updates = Box.createVerticalBox(); //New
        frame.add(updates); //New

        JOptionPane badMove = new JOptionPane("Sorry, you can't move that piece there!", JOptionPane.WARNING_MESSAGE); //New st
        rulesAlert1 = badMove.createDialog("Rules Alert!");
        rulesAlert1.setAlwaysOnTop(true);

        JOptionPane badCapture = new JOptionPane("You can't capture pieces from your own team!", JOptionPane.WARNING_MESSAGE);
        rulesAlert2 = badCapture.createDialog("Rules Alert!");
        rulesAlert2.setAlwaysOnTop(true); //New end

        JButton reset = new JButton("Start Over");
        reset.addActionListener(this);
        JButton quit = new JButton("Main Menu");
        quit.addActionListener(this);
        frame.add(reset);
        frame.add(quit);
        frame.pack();

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        board.setLayout(new GridLayout(8, 8));
        ArrayList<Color> colors = new ArrayList<>();
        map = new HashMap<>();

        //Add black and white checkers to the list, to be added to the board later
        int count = 0; //TODO
        for (int i = 0; i < 64; i++) {
            if ((count / 8) % 2 == 0) {
                if (count % 2 == 0) {
                    colors.add(Color.BLACK);
                } else {
                    colors.add(Color.WHITE);
                }
            } else {
                if (count % 2 == 0) {
                    colors.add(Color.WHITE);
                } else {
                    colors.add(Color.BLACK);
                }
            }
            count++;
        }

        //Add the colors from the list to the board
        for (int i = 0; i < 64; i++) {
            JPanel color = new JPanel(new BorderLayout());
            color.setBackground(colors.get(i));
            board.add(color);
            map.put(i, colors.get(i));
        }

        //Adds MouseListener to all components
        for (int i = 0; i < 64; i++) {
            temp = (JPanel) board.getComponent(i);
            temp.addMouseListener(this);
        }

        //Adds all the pieces to the board
        p1.addToBoard(board, wherePiecesAre);
        p2.addToBoard(board, wherePiecesAre);
    }

    //Done
    @Override
    public void mouseClicked(MouseEvent e) {
        pieceSelected = false;
        if (e.getComponent().getBackground() == Color.red) {
            e.getComponent().setBackground(Color.red);
            for (int i = 0; i < 64; i++) {
                if (e.getComponent() == board.getComponent(i)) {
                    old = i;
                    temp = (JPanel) board.getComponent(old);
                }
            }
            if (hasPiece(e.getComponent())) {
                pieceSelected = true;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean hasPiece = false;
        if (pieceSelected) {
            Component s = null;
            ImageIcon ic = null;
            if ((p1.pieces.contains(wherePiecesAre.get(board.getComponent(old))) && p1.pieces.contains(wherePiecesAre.get(e.getComponent())))
                    || (p2.pieces.contains(wherePiecesAre.get(board.getComponent(old))) && p2.pieces.contains(wherePiecesAre.get(e.getComponent())))) {
                rulesAlert2.setVisible(true);
                pieceSelected = false;
            } else if (!pr.pieceRestrictions(board, wherePiecesAre, board.getComponent(old), e.getComponent(), p1, p2, map)) {
                rulesAlert1.setVisible(true);
                pieceSelected = false;
            } else {
                temp = (JPanel) board.getComponent(old);
                board.getComponent(old).setVisible(false);
                temp.removeAll();
                //wherePiecesAre.remove(board.getComponent(old));
                board.getComponent(old).setVisible(true);
                for (Component c : board.getComponents()) {
                    if (e.getComponent() == c) {
                        for (Map.Entry<Component, ImageIcon> entry : wherePiecesAre.entrySet()) {
                            if (entry.getKey() == board.getComponent(old)) {
                                s = e.getComponent(); //which box in the chess board
                                ic = entry.getValue(); //which icon is in that box
                                if (hasPiece(s)) {
                                    hasPiece = true;
                                } else {
                                    hasPiece = false;
                                }
                                temp2 = (JPanel) s;
                                temp2.removeAll();
                                temp2.add(new JLabel(ic));
                                break;
                            }
                        }
                    }
                }
                updates.add(new JLabel(pieceCaptured(s)));
                wherePiecesAre.remove(board.getComponent(old));

                if (hasPiece) {
                    wherePiecesAre.replace(s, ic);
                } else {
                    wherePiecesAre.put(s, ic);
                }
            }
        }
    }

    public String pieceCaptured(Component c) {
        String pieceTaken = "";
        for (Map.Entry<Component, ImageIcon> entry : wherePiecesAre.entrySet()) {
            if (entry.getKey() == c) {
                if (entry.getValue() == p1.getKnight()) { //Should probably find more efficient way
                    pieceTaken = "Player 1's knight captured!";
                } else if (entry.getValue() == p1.getBishop()) {
                    pieceTaken = "Player 1's bishop captured!";
                } else if (entry.getValue() == p1.getPawn()) {
                    pieceTaken = "Player 1's pawn captured!";
                } else if (entry.getValue() == p1.getRook()) {
                    pieceTaken = "Player 1's rook captured!";
                } else if (entry.getValue() == p1.getKing()) {
                    pieceTaken = "Player 1's king captured!";
                } else if (entry.getValue() == p1.getQueen()) {
                    pieceTaken = "Player 1's queen captured!";
                } else if (entry.getValue() == p2.getKnight()) {
                    pieceTaken = "Player 2's knight captured!";
                } else if (entry.getValue() == p2.getBishop()) {
                    pieceTaken = "Player 2's bishop captured!";
                } else if (entry.getValue() == p2.getPawn()) {
                    pieceTaken = "Player 2's pawn captured!";
                } else if (entry.getValue() == p2.getRook()) {
                    pieceTaken = "Player 2's rook captured!";
                } else if (entry.getValue() == p2.getKing()) {
                    pieceTaken = "Player 2's king captured!";
                } else if (entry.getValue() == p2.getQueen()) {
                    pieceTaken = "Player 2's queen captured!";
                }
            }
        }
        return pieceTaken;
    }

    //Done
    @Override
    public void mouseReleased(MouseEvent e) {
        if (pieceSelected) {
            pieceSelected = false;
        }
    }

    //Done
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!pieceSelected) {
            for (int i = 0; i < 64; i++) {
                if (board.getComponent(i).getBackground() == Color.red) {
                    board.getComponent(i).setBackground(map.get(i));
                }
                e.getComponent().setBackground(Color.red);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public boolean hasPiece(Component c) {
        int count = 0;
        for (Map.Entry<Component, ImageIcon> entry : wherePiecesAre.entrySet()) {
            if (c == entry.getKey()) {
                count++;
            }
        }
        return count != 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start Over")) {
            frame.setVisible(false);
            new ChessScreen();
        }
        if (e.getActionCommand().equals("Main Menu")) {
            System.out.println("Quit button clicked.");
            frame.setVisible(false);
            try {
                new ChessGreeting();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}