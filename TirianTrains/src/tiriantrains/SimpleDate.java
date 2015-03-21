package tiriantrains;

import java.sql.Date;

public class SimpleDate extends Date {
    public SimpleDate(int year, int month, int day) {
        super(year - 1900, month, day);
    }
}
