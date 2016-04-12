package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.JobApplicationSchema.JobApplicationTable;

/**
 * Created by EdwardLichtman on 4/10/16.
 */
public class JobDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "JobApplications.db";

    public JobDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder execSql = new StringBuilder();
        execSql.append("create table " + JobApplicationTable.NAME + " ( ");
        execSql.append(" _id integer primary key autoincrement, ");
        execSql.append(JobApplicationTable.Cols.strUUID + ", ");
        execSql.append(JobApplicationTable.Cols.strCOMPANY_NAME + ", ");
        execSql.append(JobApplicationTable.Cols.strPOSITION_TITLE + ", ");
        execSql.append(JobApplicationTable.Cols.strCONTACT_NAME + ", ");
        execSql.append(JobApplicationTable.Cols.blnINTERVIEW + ", ");
        execSql.append(JobApplicationTable.Cols.strCITY + ", ");
        execSql.append(JobApplicationTable.Cols.strSTATE + ", ");
        execSql.append(JobApplicationTable.Cols.dtmLISTED + ", ");
        execSql.append(JobApplicationTable.Cols.dtmAPPLIED + ")");

        db.execSQL(execSql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
