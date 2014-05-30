

import net.miginfocom.swing.MigLayout;
import sun.misc.JavaLangAccess;

import javax.swing.*;

/**
 * Created by julia on 30.05.2014.
 */
public class ApplicationPanel extends JComponent {
    BlockWorld blockWorld;
    int tetrisScore;
    final JLabel heading = new JLabel();
    final JLabel scoreLabel = new JLabel();

    public ApplicationPanel() {
        blockWorld = new BlockWorld(10, 16, 20);
        tetrisScore = 0;

        BlockWorldModel.Listener scoreChangeListener = new BlockWorldModel.Listener() {
            @Override
            public void update(final int score) {
                tetrisScore = score;
                scoreLabel.setText("Score:  " +String.valueOf(tetrisScore));
            }
        };
        blockWorld.getModel().register(scoreChangeListener);
        setLayout(new MigLayout("wrap 1", "[][]"));
        heading.setText("BLOXX");
        scoreLabel.setText("Score:  " + String.valueOf(tetrisScore));
        add(heading, "wrap");
        add(scoreLabel);
        add(blockWorld);
    }



    private void createAndShowGUI() {
        JFrame frame = new JFrame("Bloxx");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
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
