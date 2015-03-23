package tiriantrains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.*;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

public class TirianTrains {
  
    public static void main(String[] args) {
    }
    
    public static String[] getTowns() {
        // receive town info from database here
        
        Object[][] data = null;
        
        try { data = Database.selectQuery("SELECT DISTINCT name FROM town ORDER BY name ASC"); }
        catch (SQLException ex) {
            System.err.println("An exception occured: " + ex);
            return null;
        }
        
        String[] towns = new String[data.length];
        for (int i = 0; i < data.length; ++i)
            towns[i] = data[i][0].toString();
        return towns;
    }
    
    public static String getTown(String station) {
        // receive station info from database with particular station
        Object[][] data;
        
        try {
            data = Database.selectQuery(
                "SELECT town.name FROM station " +
                "INNER JOIN town ON station.town_id = town.town_id " +
                "WHERE station.name LIKE '" + station + "' " +
                "ORDER BY 1 ASC"
            );
        }
        catch (SQLException ex) {
            System.err.println("An exception occured: " + ex);
            return null;
        }
        
        return data[0][0].toString();
    }
    
    public static String[] getStations() {
        // receive all station info from database here
        Object[][] data;
        
        try { data = Database.selectQuery("SELECT DISTINCT name FROM station ORDER BY name ASC"); }
        catch (SQLException ex) {
            System.err.println("An exception occured: " + ex);
            return null;
        }
        
        String[] stations = new String[data.length];
        for (int i = 0; i < data.length; ++i)
            stations[i] = data[i][0].toString();
        return stations;
    }
    
    public static String[] getStations(String town) {
        // receive station info from database with particular town
        Object[][] data;
        
        try {
            data = Database.selectQuery(
                "SELECT DISTINCT station.name FROM town " +
                "INNER JOIN station ON station.town_id = town.town_id " +
                "WHERE town.name LIKE '" + town + "' " +
                "ORDER BY 1 ASC"
            );
        }
        catch (SQLException ex) {
            System.err.println("An exception occured: " + ex);
            return null;
        }
        
        String[] stations = new String[data.length];
        for (int i = 0; i < data.length; ++i)
            stations[i] = data[i][0].toString();
        
        return stations;
    }
    
    public static Object[] getTrainTableHeaders() {
        // column headers of train list
        return new Object[] {"Date", "Departure Time", "Arrival Time", "Duration (mins)", "Cost", "Train ID", "Train Model" };
    }
    
    public static Object[][] getTrainTable() {
        // column info (filtered) of train list
        Object[][] data;
        
        try {
            data = Database.selectQuery(
                "SELECT " +
                // Date
                "CAST(CONCAT(trip.year, '-', trip.month, '-', trip.day) AS DATE) 'Date', " +
                // Departure Time // times 100 for viewing purposes
                "(trip.departure_hour * 100 + trip.departure_minute) 'Departure Time', " +
                // Arrival Time
                "(trip.arrival_hour * 100 + trip.arrival_minute) 'Arrival Time', " +
                // Duration
                "(trip.arrival_hour * 60 + trip.arrival_minute) - (trip.departure_hour * 60 - trip.departure_minute) 'Duration', " +
                // Cost
                "SUM(route.travel_cost) 'Cost', " +
                // Train ID and model
                "train.train_id 'Train ID', model.name 'Train Model' " +
                // Train features
                ", model.max_speed, model.no_of_seats, model.no_of_toilets, model.reclining_seats, model.foldable_table, model.disability_access, model.luggage_storage, model.vending_machines, model.food_service " +
                "FROM trip_schedule trip " +                        
                        
                "INNER JOIN route ON trip.route_id = route.route_id " +
                "INNER JOIN station origin ON origin.station_id = route.origin_station_id " +
                "INNER JOIN station destination ON destination.station_id = route.destination_station_id " +
                "INNER JOIN town origin_town ON origin_town.town_id = origin.town_id " +
                "INNER JOIN town dest_town ON dest_town.town_id = destination.town_id " +
                "INNER JOIN train ON train.train_id = trip.train_id " +
                "INNER JOIN train_model model ON model.train_model_code = train.train_model_code " +
                // start of filter
                        
                "WHERE (CAST(CONCAT(trip.year, '-', trip.month, '-', trip.day) AS DATE) > CAST('" + BuyTicket.getDepartureDate().toString() + "' AS DATE) " +
                "OR (" +
                    "CAST(CONCAT(trip.year, '-', trip.month, '-', trip.day) AS DATE) = CAST('" + BuyTicket.getDepartureDate().toString() + "' AS DATE) " +
                    "AND (trip.departure_hour * 100 + trip.departure_minute) >= " + BuyTicket.getDepartureTime() +
                "))" + 
                "AND origin.name = '" + BuyTicket.getFromStation().getName() + "' " +
                "AND destination.name = '" + BuyTicket.getToStation().getName() + "' " +
                "AND origin_town.name = '" + BuyTicket.getFromStation().getTownName() + "' " +
                "AND dest_town.name = '" + BuyTicket.getToStation().getTownName() + "' " + 
                "GROUP BY trip.schedule_id " + 
                "ORDER BY 1 ASC, 2 ASC, 3 ASC"
            );
        }
        catch (SQLException ex) {
            System.err.println("An exception occured: " + ex);
            return null;
        }
        
        return data;
    }
    
    
    static JLabel originStation = new JLabel();
    public static JLabel getOriginStation() {
        return originStation;
    }
    
    static JLabel destinationStation = new JLabel();
    public static JLabel getDestinationStation() {
        return destinationStation;
    }
    
    static JLabel departureTime = new JLabel();
    public static JLabel getDepartureTime() {
        return departureTime;
    }
    
    static JLabel arrivalTime = new JLabel();
    public static JLabel getArrivalTime() {
        return arrivalTime;
    }
    
    static JLabel train = new JLabel();
    static JLabel getTrain() {
        return train;
    }
    
    // action listener that enables navigation through frames
    public static class FrameOpener implements ActionListener {
        final JFrame first, second;
        public FrameOpener(JFrame first, JFrame second) {
            this.first = first;
            this.second = second;
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (first == null || second == null)
                return;
            first.setVisible(false);
            first.dispose();
            second.setVisible(true);
        }
    }
    
    public static JDatePicker createJDatePicker() {
        SqlDateModel model = new SqlDateModel();
        JDatePanelImpl panel = new JDatePanelImpl(model);
        JDatePickerImpl picker = new JDatePickerImpl(panel);
        
        model.setValue(new SimpleDate());
        return picker;
    }
    
}
