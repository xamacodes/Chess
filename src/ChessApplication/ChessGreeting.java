package ChessApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChessGreeting extends JLabel implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JButton playButton;
    private JButton quitButton;

    ChessGreeting() throws IOException {
        frame = new JFrame();
        panel = new JPanel();

        panel.setPreferredSize(new Dimension(700, 550));
        frame.getContentPane().add(panel);
        frame.pack();

        String image = "./images/chess.png";
        frame.setContentPane(new JLabel(new ImageIcon(image)));

        playButton = new JButton("Play!");
        playButton.addActionListener(this);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(playButton);
        frame.getContentPane().add(quitButton);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Play!")) {
            System.out.println("Play button clicked.");
            frame.setVisible(false);
            new ChessScreen();
        }

        if (e.getActionCommand().equals("Quit")) {
            System.out.println("Quit button clicked.");
            frame.setVisible(false);
            frame.dispose();
        }
    }

}
