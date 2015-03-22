package tiriantrains;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.sql.Date;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
import net.sourceforge.jdatepicker.JDatePicker;

// Note: this class is a Singleton
public class BuyTicket extends DefaultFrame {
    
    public static void main(String[] args) {
        BuyTicket frame = new BuyTicket();
        frame.setVisible(true);
    }
    
    // Members
    JComboBox fromTown, fromStation;
    JComboBox toTown, toStation;
    JDatePicker departureDate;
    JFormattedTextField departureTime;
    
    // Accessors
    
    public static Station getFromStation() {
        return new Station(getInstance().fromStation.getSelectedItem().toString());
    }
    
    public static Station getToStation() {
        return new Station(getInstance().toStation.getSelectedItem().toString());
    }
    
    public static Date getDepartureDate() {
        return (Date) getInstance().departureDate.getModel().getValue();
    }
    
    // military time
    public static int getDepartureTime() {
        String depTime = getInstance().departureTime.getText();
        int hours = Integer.parseInt(depTime.substring(0, 2));
        int minutes = Integer.parseInt(depTime.substring(3));
        // * 100 for viewing purposes
        return hours * 100 + minutes;
    }

    // static method to ensure one instance
    private static BuyTicket instance = null;
    public static BuyTicket getInstance() {
        return instance;
    }
    
    static { new BuyTicket(); }
    
    // Constructor
    private BuyTicket() {
        super("Buy Tickets", "PICK TRAINS -->", 30);
        instance = this;
    }
    
    // initialize components
    @Override
    protected void start() {
        createComboBoxes(); // setup from-to stations and towns
        createStationFilters(); // setup station filter based on town
        createDepartureInfo(); // setup departure date and time
        createFilterer();
        
        // add components to panels
        add(createRow("From:", fromTown, fromStation));
        add(createRow("To:", toTown, toStation));
        add(createRow("Date of Departure:", departureDate));
        add(createRow("Time of Departure", departureTime));
        
    }
    
    // run when submit button is clicked
    @Override
    public void onSubmit() {
        // setVisible(false);
        TrainFrame.getInstance().filter();
        TrainFrame.getInstance().setVisible(true);
    }
    
    private void createComboBoxes() {
        // setup from-to stations and towns
        String[] towns = TirianTrains.getTowns();
        
        fromTown = new JComboBox(towns);
        toTown = new JComboBox(towns);
        fromStation = new JComboBox();
        toStation = new JComboBox();
    }
    
    private void createStationFilters() {
        // setup station filter based on town
        fromTown.addItemListener(new StationFilter(fromTown, fromStation));
        toTown.addItemListener(new StationFilter(toTown, toStation));  
    }
    
    private void createDepartureInfo() {
        // setup departure date and time
        departureDate = TirianTrains.createJDatePicker();
        
        JFormattedTextField temp = null;
        try { temp = new JFormattedTextField(new MaskFormatter("##:##") {{ setPlaceholderCharacter('0'); }}); }
        catch (ParseException e) {}
        
        departureTime = temp;
        departureTime.setText("08:00");
    }
    
    private void createFilterer() {
        
        class Filterer implements ItemListener, PropertyChangeListener, ChangeListener, Runnable {
            
            @Override
            public void run() {
                if (TrainFrame.getInstance().isVisible()) {
                    TrainFrame.getInstance().filter();
                }
            }

            @Override
            public void itemStateChanged(ItemEvent e) {
                run();
            }

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                run();
            }            

            @Override
            public void stateChanged(ChangeEvent e) {
                run();
            }
            
        }
        
        Filterer filterer = new Filterer();
        
        fromTown.addItemListener(filterer);
        toTown.addItemListener(filterer);
        fromStation.addItemListener(filterer);
        toStation.addItemListener(filterer);
        departureDate.getModel().addChangeListener(filterer);
        departureTime.addPropertyChangeListener("value", filterer);

    }
    
    // creates a panel holding a row of components
    private JPanel createRow(Object... list) {
        JPanel panel = new JPanel(new GridLayout(1, list.length));
        for (Object object : list) {
            if (object instanceof Component) {
                Component component = (Component) object;
                panel.add(component);
            }
            else {
                // add as JLabel
                JLabel label = new JLabel(object.toString());
                panel.add(label);
            }
        }
        return panel;
    }
    
}
