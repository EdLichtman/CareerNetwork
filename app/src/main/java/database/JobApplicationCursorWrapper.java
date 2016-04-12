package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.careernetworkingassistant.JobApplication;

import java.util.Date;
import java.util.UUID;

import database.JobApplicationSchema.JobApplicationTable;

/**
 * Created by EdwardLichtman on 4/10/16.
 */
public class JobApplicationCursorWrapper extends CursorWrapper {

    public JobApplicationCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public JobApplication getApplication() {
        String uuidString = getString(getColumnIndex(JobApplicationTable.Cols.strUUID));
        String companyName = getString(getColumnIndex(JobApplicationTable.Cols.strCOMPANY_NAME));
        String positionTitle = getString(getColumnIndex(JobApplicationTable.Cols.strPOSITION_TITLE));
        String contactName = getString(getColumnIndex(JobApplicationTable.Cols.strCONTACT_NAME));
        int interviewOrganized = getInt(getColumnIndex(JobApplicationTable.Cols.blnINTERVIEW));
        String city = getString(getColumnIndex(JobApplicationTable.Cols.strCITY));
        String state = getString(getColumnIndex(JobApplicationTable.Cols.strSTATE));
        long listedDate = getLong(getColumnIndex(JobApplicationTable.Cols.dtmLISTED));
        long appliedDate = getLong(getColumnIndex(JobApplicationTable.Cols.dtmAPPLIED));

        JobApplication application = new JobApplication(UUID.fromString(uuidString));
        application.setCompanyName(companyName);
        application.setPositionTitle(positionTitle);
        application.setContactName(contactName);
        application.setInterviewOrganized(interviewOrganized != 0);
        application.setCity(city);
        application.setState(state);
        application.setListedDate(new Date(listedDate));
        application.setAppliedDate(new Date(appliedDate));

        return application;
    }
}
