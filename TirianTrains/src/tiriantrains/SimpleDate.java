package tiriantrains;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SimpleDate extends Date {
    
    private static final String datePattern = "yyyy-MM-dd";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    
    public SimpleDate() {
        super(System.currentTimeMillis());
    }
    
    public SimpleDate(int year, int month, int day) {
        super(year - 1900, month, day);
    }
    
    public SimpleDate(String s) {
        this(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(5, 7)) - 1, Integer.parseInt(s.substring(8)));
    }
    
    @Override
    public String toString() {
        return dateFormatter.format(this);
    }
    
}
