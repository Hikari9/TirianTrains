package tiriantrains;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

// filters a station combobox based from a town combobox
public class StationFilter implements ItemListener {
    
    // References
    JComboBox town, station;
    
    public StationFilter(JComboBox town, JComboBox station) {
        this.town = town;
        this.station = station;
        filter();
    }

    public void filter() {
        // filter station combobox based from town
        String theTown = (String) town.getSelectedItem();
        String[] stations = TirianTrains.getStations(theTown);
        if (stations == null)
            station.setModel(new DefaultComboBoxModel());
        else
            station.setModel(new DefaultComboBoxModel(stations));
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getStateChange() == ItemEvent.SELECTED)
            filter();
    }
}