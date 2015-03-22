/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiriantrains;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import net.sourceforge.jdatepicker.JDatePicker;

/**
 *
 * @author PC2
 */
public class ConfirmFrame extends JFrame {
    
    private static ConfirmFrame instance = new ConfirmFrame();
    public static ConfirmFrame getInstance() { return instance; }
    
    public final JDatePicker date;
    public final JLabel origin, desitination;
    public final JLabel departureTime, arrivalTime;
    public final JLabel train;
    
    private Font h1 = new Font("Serif", Font.BOLD, 30);
    private Font h3 = new Font("Serif", Font.BOLD, 20);
    private Border slender = BorderFactory.createEmptyBorder(10, 0, 10, 0);
    private Border largeTop = BorderFactory.createEmptyBorder(30, 0, 10, 0);
   
    
    private JLabel header = new JLabel("Confirm Trip Schedule") {{ this.setFont(h1); }};
    private JButton next = new JButton("Confirm -->") {{
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
        // date
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(slender);}},
        // origin
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(slender);}},
        // destination
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(slender);}},
        // departure/arrival time
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(slender);}},
        // train
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(slender);}},
        // price
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{setBorder(largeTop);}},
        new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{ add(next); }}
    };
    
    private ConfirmFrame() {
        super ("Confirm Trip Schedule");
        
        date = TirianTrains.createJDatePicker();
        
        origin = TirianTrains.getOriginStation();
        desitination = TirianTrains.getDestinationStation();
        departureTime = TirianTrains.getDepartureTime();
        arrivalTime = TirianTrains.getArrivalTime();
        train = TirianTrains.getTrain();
        
        
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
