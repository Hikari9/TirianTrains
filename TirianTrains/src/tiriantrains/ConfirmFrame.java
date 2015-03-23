/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiriantrains;

// Singleton class

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmFrame extends DefaultFrame {
    
    // static methods
    private static ConfirmFrame instance = null;
    static {
        new ConfirmFrame();
    }
    public static ConfirmFrame getInstance() { return instance; }
    
    // private constructor
    private ConfirmFrame() { super("Confirm Trip Schedule", "Confirm", 30); instance = this; }
    
    // initializer here
    @Override
    protected void start() {
        add(createInfo());
    }
    
    JComponent createInfo() {
        JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        
        panel.add(new JLabel() {
            @Override
            public String getText() {
                try {
                    return "Date:   " + TrainFrame.getDate();
                }
                catch (Exception ex) {
                    return "";
                }
            }
        });
        
        panel.add(new JLabel() {
            @Override
            public String getText() {
                try {
                    Station from = BuyTicket.getFromStation();
                    Station to = BuyTicket.getToStation();
                    return "Origin/Destination:   " + from.toString() + "   TO   " + to.toString();
                }
                catch (Exception ex) {
                    return "";
                }
            }
        });
        
        panel.add(new JLabel() {
            @Override
            public String getText() {
                try {
                    String dep = TrainFrame.getDepartureTime();
                    String arr = TrainFrame.getArrivalTime();
                    return "Departure Time/Arrival Time:   " + dep + "   TO   " + arr;
                }
                catch (Exception ex) {
                    System.err.println("Error at dep/arr time: " + ex);
                    return "";
                }
            }
        });
        
        panel.add(new JLabel() {
            @Override
            public String getText() {
                try {
                    return "Cost:   " + TrainFrame.getTotalCost();
                }
                catch (Exception ex) {
                    System.err.println("Error at train: " + ex);
                    return "";
                }
            }
        });
        
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                label.setFont(label.getFont().deriveFont(Font.PLAIN));
            }
        }
        
        bigPanel.add(panel);
        
        return bigPanel;
    }
    
}
