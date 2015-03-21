package tiriantrains;

// Note: this class is a Singleton

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
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
    private TrainFrame() { super("Available Trains", "NEXT -->", 30); }
    
    // static method to ensure one instance
    private static TrainFrame instance = null;
    public static TrainFrame getInstance() {
        if (instance == null)
            instance = new TrainFrame();
        instance.filter();
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
        RowFilter<GenericTableModel, Integer> rowFilter, dateFilter, timeFilter, dateTimeFilter, afterDateFilter;
        
        /// show only on specific date provided
        dateFilter = RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, BuyTicket.getDepartureDate(), 0);
        timeFilter = RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, BuyTicket.getDepartureTime(), 1);
        timeFilter = RowFilter.notFilter(timeFilter);
        
        // join as an AND clause
        ArrayList<RowFilter<GenericTableModel, Integer>> and = new ArrayList<>();
        and.add(dateFilter);
        and.add(timeFilter);
        dateTimeFilter = RowFilter.andFilter(and);
        
        // show also on dates beyond provided
        afterDateFilter = RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, BuyTicket.getDepartureDate(), 0);
        
        // join two filters as an or clause
        // ie. rowFilter = (dateFilter & timeFilter) | afterDateFilter
        ArrayList<RowFilter<GenericTableModel, Integer>> or = new ArrayList<>();
        or.add(dateTimeFilter);
        or.add(afterDateFilter);
        rowFilter = RowFilter.orFilter(or);
        
        if (sorter != null) {
            table.setRowSorter(sorter);
            final ArrayList<SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new SortKey(0, SortOrder.ASCENDING)); // Date
            sortKeys.add(new SortKey(1, SortOrder.ASCENDING)); // Departure Time
            sortKeys.add(new SortKey(2, SortOrder.ASCENDING)); // Arrival Time
            sorter.setSortKeys(sortKeys);
            sorter.setRowFilter(rowFilter);
        }
    }
    
    private JComponent createTable() {
        // table info
        Object[] headers = TirianTrains.getTrainTableHeaders();
        Object[][] data = TirianTrains.getTrainTable();
        
        // table model        
        GenericTableModel model = new GenericTableModel(data, headers);
        table = new JTable(model);
        
        // setup sorter
        sorter = new TableRowSorter<>(model);
        
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
        @Override
        public String getColumnName(int col) {
            return headers[col].toString();
        }
        @Override
        public Class getColumnClass(int col) {
             if (col >= 0 && col < getColumnCount() && getRowCount() > 0)
                return getValueAt(0, col).getClass();
            return Object.class;
        }
        @Override
        public int getRowCount() {
            return data.length;
        }
        @Override
        public int getColumnCount() {
            return headers.length;
        }
        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
    
    
    
    
}
