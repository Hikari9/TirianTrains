package tiriantrains;

// Note: this class is a Singleton

import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

public class TrainFrame extends DefaultFrame {
    
    public static void main(String[] args) {
        TrainFrame frame = getInstance();
        frame.setVisible(true);
    }
    
    // Members
    protected JTable table;
    protected TableRowSorter<GenericTableModel> sorter;
    
    // Constructor
    private TrainFrame() {
        super("Available Trains", "NEXT -->", 30);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    
    // static method to ensure one instance
    private static TrainFrame instance = null;
    public static TrainFrame getInstance() {
        if (instance == null)
            instance = new TrainFrame();
        return instance;
    }
    
    
    // initialization components
    @Override
    protected void start() {
        add(createTable()); // setup scrollable table
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
    }
    
    
    // run when submit button is clicked
    @Override
    public void onSubmit() {
    }
    
    // filter contents of table then sort
    public void filter() {
        Object[][] data = TirianTrains.getTrainTable();
        ((GenericTableModel) table.getModel()).setData(TirianTrains.getTrainTable());
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
    }
    
    private JComponent createTable() {
        // table info
        Object[] headers = TirianTrains.getTrainTableHeaders();
        Object[][] data = TirianTrains.getTrainTable();
        
        // table model        
        GenericTableModel model = new GenericTableModel(data, headers);
        table = new JTable(model);
                
        // setup scrollable table
        JScrollPane scroll = new JScrollPane(table);
        return scroll;
    }
    
    class GenericTableModel extends AbstractTableModel {
        Object[] headers;
        Object[][] data;
        public GenericTableModel(Object[][] data, Object[] headers) {
            this.data = data;
            this.headers = headers;
        }
        public void setData(Object[][] data) {
            this.data = data;
        }
        @Override
        public String getColumnName(int col) {
            return headers[col].toString();
        }
        @Override
        public Class getColumnClass(int col) {
             if (col >= 0 && col < getColumnCount() && getRowCount() > 0 && getValueAt(0, col) != null)
                return getValueAt(0, col).getClass();
            return Object.class;
        }
        @Override
        public int getRowCount() {
            if (data == null) return 0;
            return data.length;
        }
        @Override
        public int getColumnCount() {
            if (data == null || data.length == 0)
                return headers.length;
            else
                return data[0].length;
        }
        @Override
        public Object getValueAt(int row, int col) {
            if (row < 0 || row >= getRowCount() || col < 0 || col >= getColumnCount())
                return "";
            return data[row][col];
        }
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
    
    
}
