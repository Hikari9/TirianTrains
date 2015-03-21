package tiriantrains;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.*;

// Singleton
public class TicketFrame extends JFrame {
    
    
    public static void main(String[] args) {
        JFrame frame = TicketFrame.getInstance();
        frame.setVisible(true);
    }
    
    private static TicketFrame instance = new TicketFrame();
    public static TicketFrame getInstance() { return instance; }
    
    
    public final JComboBox fromTown, fromStation;
    public final JComboBox toTown, toStation;
    public final JDatePicker departureDate;
    public final JFormattedTextField departureTime;
    
    private Font h1 = new Font("Serif", Font.BOLD, 30);
    private Font h3 = new Font("Serif", Font.BOLD, 20);
    private Border slender = BorderFactory.createEmptyBorder(10, 0, 10, 0);
    private JLabel header = new JLabel("Buy Tickets") {{ this.setFont(h1); }};
    private JButton pickTrains = new JButton("PICK TRAINS -->") {{
        this.setFont(h3);
        // EDIT THIS
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame nextFrame = TrainFrame.getInstance();
                addActionListener(new TirianTrains.FrameOpener(getInstance(), nextFrame));
            }
        });
    }};
    
    final JPanel[] panels = new JPanel[] {
        new JPanel(new FlowLayout(FlowLayout.LEFT)) {{ add(header); }},
        new JPanel(new GridLayout(1, 3)) {{setBorder(slender);}},
        new JPanel(new GridLayout(1, 3)) {{setBorder(slender);}},
        new JPanel(new GridLayout(1, 2)) {{setBorder(slender);}},
        new JPanel(new GridLayout(1, 2)) {{setBorder(slender);}},
        new JPanel(new FlowLayout(FlowLayout.RIGHT)) {{ add(pickTrains); }}
    };
    
    // private because singleton
    private TicketFrame() {
        
        super("Buy Tickets");
        
        // setup from-to stations and towns
        fromTown = new JComboBox(TirianTrains.getTowns());
        toTown = new JComboBox(TirianTrains.getTowns());
        fromStation = new JComboBox();
        toStation = new JComboBox();
        
        // setup station filter based on town
        fromTown.addItemListener(new StationFilter(fromTown, fromStation));
        toTown.addItemListener(new StationFilter(toTown, toStation));  
        
        
        // setup departure date and time
        departureDate = TirianTrains.createJDatePicker();
        
        JFormattedTextField temp = null;
        try { temp = new JFormattedTextField(new MaskFormatter("##:##") {{ setPlaceholderCharacter('0'); }}); }
        catch (ParseException e) { }
        
        departureTime = temp;
        
        // add components to panels
        int i = 1;
        panels[i].add(new JLabel("From:"));
        panels[i].add(fromTown);
        panels[i].add(fromStation);
        i++;
        
        panels[i].add(new JLabel("To:"));
        panels[i].add(toTown);
        panels[i].add(toStation);
        i++;
        
        panels[i].add(new JLabel("Date of Departure:"));
        panels[i].add((JComponent) departureDate);
        i++;
        
        panels[i].add(new JLabel("Time of Departure"));
        panels[i].add(departureTime);
        i++;
        
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
    
    class StationFilter implements ItemListener {
        JComboBox<String> town, station;
        public StationFilter(JComboBox<String> town, JComboBox<String> station) {
            this.town = town;
            this.station = station;
            filter();
        }
        
        public void filter() {
            String theTown = (String) town.getSelectedItem();
            String[] stations = TirianTrains.getStations(theTown);
            if (stations == null)
                station.setModel(new DefaultComboBoxModel());
            else
                station.setModel(new DefaultComboBoxModel(stations));
        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                filter();
            }
        }
    }
    
}
