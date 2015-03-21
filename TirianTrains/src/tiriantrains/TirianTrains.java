package tiriantrains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;
import org.jdatepicker.*;

public class TirianTrains {
  
    public static void main(String[] args) {
    }
    
    public static String[] getTowns() {
        // receive town info from database here
        
        Object[][] data = null;
        
        try { data = Database.selectQuery("SELECT DISTINCT name FROM town"); }
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
                "WHERE station.name LIKE '" + station + "'"
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
        
        try { data = Database.selectQuery("SELECT DISTINCT name FROM station"); }
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
                "WHERE town.name LIKE '" + town + "'"
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
        return new Object[] {"Date", "Departure Time", "Arrival Time", "Duration", "Cost", "Train ID", "Train Model" };
    }
    
    public static Object[][] getTrainTable() {
        // column info (unfiltered) of train list
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
                // Cost
                "SUM(route.travel_cost) 'Cost', " +
                // Train ID and model
                "train.train_id 'Train ID', model.name 'Train Mode' " +
                // Train features
                // ", (model.max_speed, model.no_of_seats, model.no_of_toilets, model.reclining_seats, model.foldable_table, model.disability_access, model.luggage_storage, model.vending_machines, model.food_service) " +
                "FROM trip_assignment assignment " + 
                "INNER JOIN trip_schedule trip ON assignment.schedule_id = trip.schedule_id " +
                "INNER JOIN ticket ON assignment.ticket_number = ticket.ticket_number " +
                "INNER JOIN route ON trip.route_id = route.route_id " +
                "INNER JOIN train ON train.train_id = trip.train_id " +
                "INNER JOIN train_model model ON model.train_model_code = train.train_model_code "
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
    
    static JDateComponentFactory dateFactory = new JDateComponentFactory();
    public static JDatePicker createJDatePicker() {
        return dateFactory.createJDatePicker();
    }
    
}
