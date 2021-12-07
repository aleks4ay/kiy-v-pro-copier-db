package org.aleks4ay.copier.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OrderNumber {

    private static final Logger logger = LoggerFactory.getLogger(OrderNumber.class);

    public static int getOrderNumberFromDocNumber(String s1) {
        String tempString = "";
        for (int i = s1.length() - 1; i >= 0; i--) {
            if (Character.isDigit(s1.charAt(i))) {
                tempString = s1.charAt(i) + tempString;
            }
            else {
                break;
            }
        }
        if (tempString.isEmpty()){
            return 0;
        }
        try {
            return Integer.parseInt(tempString);
        } catch (NumberFormatException e) {
            logger.warn("Can't parsing string '{}'. This string ending with '{}' number. {}", s1, tempString, e);
            throw new NumberFormatException("Can't parsing string '" + s1 + "'.");
        }
    }
}
