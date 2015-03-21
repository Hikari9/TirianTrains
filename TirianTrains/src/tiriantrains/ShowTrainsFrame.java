package tiriantrains;

// Note: this class is a Singleton

import javax.swing.JTable;

public class ShowTrainsFrame extends DefaultFrame {
    
    public static void main(String[] args) {
        ShowTrainsFrame frame = getInstance();
        frame.setVisible(true);
    }
    
    // Members
    JTable table = new JTable(TirianTrains.getFilteredTrainTable(), TirianTrains.getTrainTableHeaders());
    
    // Constructor
    private ShowTrainsFrame() { super("Available Trains", "NEXT -->", 30); }
    
    // static method to ensure one instance
    private static ShowTrainsFrame instance = null;
    public static ShowTrainsFrame getInstance() {
        if (instance == null)
            instance = new ShowTrainsFrame();
        return instance;
    }
    
}
