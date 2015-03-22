/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiriantrains;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Rico
 */
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {


    @Override
    public Object stringToValue(String s) throws ParseException {
        return new SimpleDate(s);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            if (value instanceof Calendar)
                return SimpleDate.dateFormatter.format(((Calendar) value).getTime());
            else if (value instanceof SimpleDate) {
                return value.toString();
            }
        }
        return "";
    }
    
}
