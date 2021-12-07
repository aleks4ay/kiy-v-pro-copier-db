package org.aleks4ay.copier;

import org.aleks4ay.copier.tools.Constants;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CopyNewData1CTest {

    @Test
    public void isChangedTest_IfFirstCall() {
        boolean actual = new CopyNewData1C().isChanged(Constants.DBF_PATH + Constants.TMC_FILE, "timeTmc");
        assertTrue(actual);
    }

    @Test
    public void isChangedTest_MustReturnFalseIfListTimesAlreadyContainTheSameTimeOfFileModification() {
        CopyNewData1C application = new  CopyNewData1C();
        Long fileTime = new File(Constants.DBF_PATH + Constants.TMC_FILE).lastModified();
        application.getTimes().put("timeTmc", fileTime);
        boolean actual = application.isChanged(Constants.DBF_PATH + Constants.TMC_FILE, "timeTmc");
        assertFalse(actual);
    }

    @Test
    public void isChangedTest_MustReturnFalseIfCallTwoTimeWithTheSameFileModification() {
        CopyNewData1C application = new  CopyNewData1C();
        boolean actual;
        actual = application.isChanged(Constants.DBF_PATH + Constants.TMC_FILE, "timeTmc");
        assertTrue(actual); //if new file modification
        actual = application.isChanged(Constants.DBF_PATH + Constants.TMC_FILE, "timeTmc");
        assertFalse(actual); //if already old file modification
    }
}