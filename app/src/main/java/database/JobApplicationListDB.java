package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.careernetworkingassistant.JobApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.JobApplicationSchema.JobApplicationTable;

/**
 * Created by EdwardLichtman on 4/10/16.
 */
public class JobApplicationListDB {
        private static JobApplicationListDB sJobApplicationListDB;
        private Context mContext;
        private SQLiteDatabase mDatabase;

        public static JobApplicationListDB get(Context context) {
            if (sJobApplicationListDB == null) {
                sJobApplicationListDB = new JobApplicationListDB(context);
            }
            return sJobApplicationListDB;
        }

        private JobApplicationListDB(Context context) {
            mContext = context.getApplicationContext();
            mDatabase = new JobDBHelper(mContext).getWritableDatabase();
        }

        public void addJobApplication(JobApplication j) {
            ContentValues values = getContentValues(j);
            mDatabase.insert(JobApplicationTable.NAME, null, values);
        }

        public void updateJobApplication(JobApplication j) {
            String uuidString = j.getId().toString();
            ContentValues values = getContentValues(j);

            mDatabase.update(JobApplicationTable.NAME, values,
                    JobApplicationTable.Cols.strUUID + " = ?",
                    new String[]{uuidString});
        }

        public void deleteJobApplication(JobApplication j) {
            String uuidString = j.getId().toString();

            mDatabase.delete(JobApplicationTable.NAME,
                    JobApplicationTable.Cols.strUUID + " = ?",
                    new String[]{uuidString});
        }

        public List<JobApplication> getJobApplications() {
            List<JobApplication> jobApplications = new ArrayList<>();

            JobApplicationCursorWrapper cursor = queryApplications(null, null);

            try {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    jobApplications.add(cursor.getApplication());
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }

            return jobApplications;
        }



        public JobApplication getJobApplication(UUID id) {

            JobApplicationCursorWrapper cursor = queryApplications(
                    JobApplicationTable.Cols.strUUID + " = ? ",
                    new String[] { id.toString() }
            );

            try {
                if (cursor.getCount() == 0) {
                    return null;
                }
                cursor.moveToFirst();
                return cursor.getApplication();
            } finally {
                cursor.close();
            }
        }

        private JobApplicationCursorWrapper queryApplications(String whereClause, String[] whereArgs) {
            Cursor cursor = mDatabase.query(
                    JobApplicationTable.NAME,
                    null, // Columns
                    whereClause,
                    whereArgs,
                    null, // groupBy
                    null, // having
                    null // orderBy
            );
            return new JobApplicationCursorWrapper(cursor);
        }

        private static ContentValues getContentValues(JobApplication jobApplication) {
            ContentValues values = new ContentValues();
            values.put(JobApplicationTable.Cols.strUUID, jobApplication.getId().toString());
            values.put(JobApplicationTable.Cols.strCOMPANY_NAME, jobApplication.getCompanyName());
            values.put(JobApplicationTable.Cols.strPOSITION_TITLE, jobApplication.getPositionTitle());
            values.put(JobApplicationTable.Cols.blnINTERVIEW,
                    jobApplication.getInterviewOrganized() ? 1 : 0);
            values.put(JobApplicationTable.Cols.strCITY, jobApplication.getCity());
            values.put(JobApplicationTable.Cols.strSTATE, jobApplication.getState());
            values.put(JobApplicationTable.Cols.dtmLISTED, jobApplication.getListedDate().getTime());
            values.put(JobApplicationTable.Cols.dtmAPPLIED, jobApplication.getAppliedDate().getTime());

            return values;
        }
}
