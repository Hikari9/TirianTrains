package tiriantrains;

import java.awt.Component;
import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import org.jdatepicker.JDatePicker;

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
    
    // Constructor
    private BuyTicket() {
        super("Buy Tickets", "PICK TRAINS -->", 30);
    }

    
    // static method to ensure one instance
    private static BuyTicket instance = null;
    public static BuyTicket getInstance() {
        if (instance == null)
            instance = new BuyTicket();
        return instance;
    }
    
    // Initialize components
    @Override
    protected void start() {
        createComboBoxes(); // setup from-to stations and towns
        createStationFilters(); // setup station filter based on town
        createDepartureInfo(); // setup departure date and time
        
        // add components to panels
        add(createRow("From:", fromTown, fromStation));
        add(createRow("To:", toTown, toStation));
        add(createRow("Date of Departure:", departureDate));
        add(createRow("Time of Departure", departureTime));
    }
    
    // run when submit button is clicked
    @Override
    public void onSubmit() {
        
    }
    
    private void createComboBoxes() {
        // setup from-to stations and towns
        String[] towns = TirianTrains.getTowns();
        String[] full = new String[towns.length + 1];
        
        // enable full to have a select town at the start
        full[0] = "Select town...";
        for (int i = 1; i < full.length; ++i)
            full[i] = towns[i - 1];
        
        fromTown = new JComboBox(full);
        toTown = new JComboBox(full);
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