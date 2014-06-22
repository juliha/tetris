

import net.miginfocom.swing.MigLayout;
import sun.misc.JavaLangAccess;

import javax.swing.*;
import java.awt.*;

/**
 * Created by julia on 30.05.2014.
 */
public class ApplicationPanel extends JPanel {
    BlockWorld blockWorld;
    int tetrisScore;
    final JLabel heading = new JLabel();
    final JLabel scoreLabel = new JLabel();

    public ApplicationPanel() {
        setBackground(Color.BLACK);

        blockWorld = new BlockWorld(10, 16, 20);
        tetrisScore = 0;
        BlockWorldModel.Listener scoreChangeListener = new BlockWorldModel.Listener() {
            @Override
            public void update(final int score) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tetrisScore = score;
                        scoreLabel.setText("Score:  " +String.valueOf(tetrisScore));
                    }
                });

            }
        };
        blockWorld.getModel().register(scoreChangeListener);
        setLayout(new MigLayout(" fill, insets 0, wrap 1", "5[][]"));
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font(getName(), Font.BOLD, 16));
        heading.setText("BLOXX");
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setText("Score:  " + String.valueOf(tetrisScore));
        add(heading, "align center, wrap");
        add(scoreLabel,"align center");
        add(blockWorld, "align center");
    }



    private void createAndShowGUI() {
        JFrame frame = new JFrame("Bloxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        blockWorld.startGame();
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationPanel ap = new ApplicationPanel();
                ap.createAndShowGUI();
            }
        });
    }
}
