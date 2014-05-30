

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Created by julia on 30.05.2014.
 */
public class ApplicationPanel extends JComponent {
    BlockWorld blockWorld;
    ScorePanel scorePanel;

    public ApplicationPanel() {
        scorePanel = new ScorePanel();
        blockWorld = new BlockWorld(10, 16, 20);
        this.setLayout(new MigLayout("fill, wrap 1"));
        add(scorePanel);
        add(blockWorld);
    }


    class ScorePanel extends JPanel {
        public ScorePanel () {
            setLayout(new MigLayout("fill, wrap 2"));
            add(new JLabel("BLOXX"), "wrap");
            add(new JLabel("Score"));
        }

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
