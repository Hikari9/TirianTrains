package tiriantrains;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

// Singleton
public class TrainFrame extends JFrame {
    
    public static void main(String[] args) {
        JFrame frame = TrainFrame.getInstance();
        frame.setVisible(true);
    }
    
    private static TrainFrame instance = new TrainFrame();
    public static TrainFrame getInstance() { return instance; }
    
    private Font h1 = new Font("Serif", Font.BOLD, 30);
    private Font h3 = new Font("Serif", Font.BOLD, 20);
    private Border slender = BorderFactory.createEmptyBorder(10, 0, 10, 0);
    private JLabel header = new JLabel("Train List") {{ this.setFont(h1); }};
    private JButton next = new JButton("next -->") {{
        this.setFont(h3);
        // EDIT THIS
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // JFrame nextFrame = TicketFrame.getInstance();
                // addActionListener(new TirianTrains.FrameOpener(getInstance(), nextFrame));
            }
        });
    }};
    
    final JPanel[] panels = new JPanel[] {
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{ add(header); }},
        new JPanel(new GridLayout(1, 3)) {{setBorder(slender);}},
        new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{ add(next); }}
    };
    
    private TrainFrame() {
        super ("Train List");
        
        // add padding to frame
        int pad = 50;
        Border padding = BorderFactory.createEmptyBorder(pad, pad, pad, pad);
        JPanel content = new JPanel(new GridLayout(panels.length, 1));
        content.setBorder(padding);
        setContentPane(content);
        
        // finally add all panels
        for (JPanel panel : panels)
            add(panel);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
}
