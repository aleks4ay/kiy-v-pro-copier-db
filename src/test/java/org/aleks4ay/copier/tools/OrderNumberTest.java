package org.aleks4ay.copier.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderNumberTest {

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return asList (new Object[][] {
                {"743"}, {"-000000273"}, {"СН-0000-83"}, {"КИ--317"}, {"KI-811"},
                {"KI-,, 125"}, {"KI- 170"}, {"KI-73707"}, {"KI-2234"}, {"KI-0002234"}
        });
    }

    @Parameterized.Parameter
    public String badNumber;

    @Test
    public void getOrderNumberFromDocNumber() {
        int actualNumber = OrderNumber.getOrderNumberFromDocNumber(badNumber);
        System.out.printf("%s -> %s\n", badNumber, actualNumber);
        assertTrue(actualNumber > 0);
    }
}
