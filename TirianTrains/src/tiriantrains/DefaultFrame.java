package tiriantrains;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

public class DefaultFrame extends JFrame {
    
    public static void main(String[] args) {
        DefaultFrame fr = new DefaultFrame() {
            @Override
            public void start() {
                add(new JLabel("Testing"));
            }
            @Override
            public void onSubmit() {
                JOptionPane.showMessageDialog(this, "Top", "Text", JOptionPane.INFORMATION_MESSAGE, null);
            }
        };
        fr.setVisible(true);
    }
    
    // Members
    protected String header, submit;
    protected final int padding;
    
    // Static
    protected static final Font basicFont = new Font("Serif", Font.PLAIN, 30);
    protected static final int[] h = new int[] {35, 30, 25, 20, 15, 10}; // for fonting, h1->h6
    public static Font getHeaderFont(int index) { return basicFont.deriveFont(Font.BOLD, h[index - 1]); }
    
    // Constructors
    public DefaultFrame() {
        this("Header", "Submit", 30);
    }
    public DefaultFrame(String header, String submit, int padding) {
        
        // initialize components
        super(header);
        this.header = header;
        this.submit = submit;
        this.padding = padding;
        
        // create fundamental template
        createPadding();
        createHeader();
        start();
        createSlenderBorders();
        createSubmit();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        
    }
    
    // put initialization steps here
    protected void start() {
        
    }
    
    // event when submit button is clicked
    public void onSubmit() {
        
    }
    
    // create padding and content pane
    private void createPadding() {
        JPanel content = new JPanel();
        
        // position contents vertically
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
        // add padding
        Border padBorder = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        content.setBorder(padBorder);
        
        setContentPane(content);
    }
    
    private void createHeader() {
        JLabel headerLabel = new JLabel(header);
        headerLabel.setFont(getHeaderFont(1));
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(headerLabel);
        
        getContentPane().add(panel);
    }
    
    private void createSlenderBorders() {
        Border slender = BorderFactory.createEmptyBorder(10, 0, 10, 0);
        for (Component component : getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                panel.setBorder(slender);
            }
        }
    }
    
    // create's the button for submission
    private void createSubmit() {
        final JButton submitButton = new JButton(submit);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(submitButton);
        
        getContentPane().add(panel);
        
        // create an actionlistener on submit
        // invoke later in case of reference of script to self
        // (which will not work because script is still constructing the object)
        
        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
               submitButton.addActionListener(new ActionListener() {
                  @Override
                   public void actionPerformed(ActionEvent e) {
                      onSubmit(); // just override the method
                  } 
               });
            } 
        });
    }
    
}
