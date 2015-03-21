package tiriantrains;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.jdatepicker.*;

public class TirianTrains {
  
    public static void main(String[] args) {
    }
    
    public static String[] getTowns() {
        // receive town info from database here
        return new String[] {"Quezon City"};
    }
    
    public static String getTown(String station) {
        // receive station info from database with particular station
        return "";
    }
    
    public static String[] getStations() {
        // receive all station info from database here
        return new String[] {""};
    }
    
    public static String[] getStations(String town) {
        // receive station info from database with particular town
        return new String[] {"Katipunan", "Araneta Center-Cubao", "Anonas"};
    }
    
    public static String[] getTrainTableHeaders() {
        return new String[] {};
    }
    public static Object[][] getFilteredTrainTable() {
        return new Object[][] {};
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
