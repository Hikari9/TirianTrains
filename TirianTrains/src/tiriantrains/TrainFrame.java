package tiriantrains;

// Note: this class is a Singleton

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    static {
        instance = new TrainFrame();
    }
    public static TrainFrame getInstance() {
        return instance;
    }
    
    // static accessors
    public static Object[] getSelectedInfo() {
        int sel = getInstance().table.getSelectedRow();
        if (sel == -1) return null;
        GenericTableModel model = (GenericTableModel) getInstance().table.getModel();
        return model.data[sel];
    }
    
    public static Date getDate() {
        return (Date) getSelectedInfo()[0];
    }
    
    public static String getDepartureTime() {
        Object[] data = getSelectedInfo();
        Long t = (Long) data[1];
        System.out.println(t);
        return String.format("%02d:%02d", t / 100, t % 100);
    }
    
    public static String getArrivalTime() {
        Object[] data = getSelectedInfo();
        Long t = (Long) data[2];
        return String.format("%02d:%02d", t / 100, t % 100);
    }
    
    public static int getDuration() {
        Object[] data = getSelectedInfo();
        return (((Number) data[3]).intValue());
    }
    
    public static float getTotalCost() {
        Object[] data = getSelectedInfo();
        return ((Number) data[4]).floatValue();
    }
    
    static String getTrain() {
        return getSelectedInfo()[6].toString();
    }
    
    // initialization components
    @Override
    protected void start() {
        add(parallelInfo());
        add(createTable()); // setup scrollable table
        add(complementaryInfo());
        table.setPreferredScrollableViewportSize(new Dimension(800, 300));
        
    }
    
    
    // run when submit button is clicked
    @Override
    public void onSubmit() {
        if (table.getSelectedRows().length != 1) {
            JOptionPane.showMessageDialog(this, "You need to select an available train.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        else {
            ConfirmFrame.getInstance().setVisible(true);
        }
        
    }
    
    // filter contents of table then sort
    public void filter() {
        Object[][] data = TirianTrains.getTrainTable();
        ((GenericTableModel) table.getModel()).setData(TirianTrains.getTrainTable());
        ((AbstractTableModel) table.getModel()).fireTableDataChanged();
        repaint();
    }
    
    private JComponent parallelInfo() {
        JPanel bigPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        panel.add(new JLabel() {
            @Override
            public String getText() {
                Station st = BuyTicket.getFromStation();
                return "From: " + st.getName() + " of <" + st.getTownName() + ">";
            }
        });
        panel.add(new JLabel() {
            @Override
            public String getText() {
                Station st = BuyTicket.getToStation();
                return "To: " + st.getName() + " of <" + st.getTownName() + ">";
            }
        });
        panel.add(new JLabel() {
            @Override
            public String getText() {
                return "Date of Departure: " + BuyTicket.getDepartureDate().toString();
            } 
        });
        panel.add(new JLabel() {
            @Override
            public String getText() {
                return "Wanted time of Departure: " + BuyTicket.getDepartureTime();
            }
        });
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setFont(label.getFont().deriveFont(Font.PLAIN));
            }
        }
        bigPanel.add(panel);
        return bigPanel;
    }
    
    private JComponent createTable() {
        // table info
        Object[] headers = TirianTrains.getTrainTableHeaders();
        Object[][] data = TirianTrains.getTrainTable();
        
        // table model        
        GenericTableModel model = new GenericTableModel(data, headers);
        table = new JTable(model);
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                repaint();
            }
        });
        
        // setup scrollable table
        JScrollPane scroll = new JScrollPane(table);
        return scroll;
    }
    
    private JComponent complementaryInfo() {
        
        JPanel bigPanel = new JPanel(new GridLayout(2, 2));
        
        bigPanel.add(new JLabel("Train Information"));
        bigPanel.add(new JLabel());
        
        
        JPanel panel = new JPanel(new GridLayout(5, 2));
        
        class InfoLabel extends JLabel {
            int i;
            public InfoLabel(int i) { this.i = i; }
            @Override
            public String getText() {
                try {
                    // if (table == null) return "---";
                    if (instance == null) return "What";
                    else return getSelectedInfo()[i].toString();
                }
                catch (Exception ex) {
                    System.err.println("Error at " + i + ": " + ex);
                    return "---";
                }
            }
        }
        
        int i = 6;
        panel.add(new JLabel("Model:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Max Speed:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("No of Seats:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("No of Toilets:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Reclining Seats:"));
        panel.add(new InfoLabel(i++));
        
        bigPanel.add(panel);
        
        panel = new JPanel(new GridLayout(5, 2));
        
        panel.add(new JLabel("Foldable Tables:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Disability Access:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Luggage Storage:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Vending Machines:"));
        panel.add(new InfoLabel(i++));
        panel.add(new JLabel("Food Service:"));
        panel.add(new InfoLabel(i++));
        
        bigPanel.add(panel);
        
        return bigPanel;
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
            if (data == null || data.length == 0 || data[0].length > headers.length)
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
