package com.bignerdranch.android.careernetworkingassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by edwardlichtman on 9/15/15.
 */
public class CareerToolboxDataBase extends SQLiteOpenHelper {

    private static final String TAG = "CareerToolboxDataBase ";

    // Database Info
    private static final String DATABASE_NAME = "DBCareerToolboxInfo";
    private static final int DATABASE_VERSION = 1;
    private static CareerToolboxDataBase sInstance;
    private static Context thisContext;
    private static JobApplication jobApp;

    // Table Names
    private static final String TABLE_APPLICATIONS = "TBJobApplications";

    // Post Table Columns
    public static final String[] COL_APPLICATIONS = {

            "_id", "INTEGER",
            "strCompanyName", "TEXT",
            "strPositionTitle", "TEXT",
            "blnInterview", "INTEGER",
            "strCity", "TEXT",
            "strState", "TEXT",
            "strNetworkContact", "TEXT",
            "dtmListed", "INTEGER",
            "dtmApplied", "INTEGER"
    };
    // Find Values that exist upon instantiation
    public static String[] VALUES_APPLICATIONS = {
            //There's a null pointer exception caused by JobApplicationActivity Line 95
            jobApp.getCompanyName(),
            jobApp.getPositionTitle(),
            String.valueOf(jobApp.getInterviewOrganized()),
            jobApp.getCity(),
            jobApp.getState(),
            jobApp.getContactName(),
            String.valueOf(jobApp.getListedDate()),
            String.valueOf(jobApp.getAppliedDate()),
            jobApp.getWhereYouApply()
    };

    //Create the Create Query
    public static String CREATE_TABLE_QUERY;

    //Pass this value a string to find, and it will find the int placemarker of
    //The entity number in the array which contains the correct value
    public int arrayKeyStringFinder(String toFind, String[] columnsAndTypes) {

        if (Arrays.asList(columnsAndTypes[0]).contains(toFind)) {
            return 0;
        }
        for (int i = 0; i < columnsAndTypes.length; i ++) {

            if (i < columnsAndTypes.length && Arrays.asList(columnsAndTypes[i]).contains(toFind)) {
                return i;
            }

        }
        return -1;

    }

    // Build a Create Table Query
    public String buildCreateTable(String tableName, String columnsAndTypes[]) {
        Log.d(TAG, "buildCreateTable");
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tableName).append(" ( ")
                .append(columnsAndTypes[0]).append(' ').append(columnsAndTypes[1]).append(" PRIMARY KEY AUTOINCREMENT");
        for(int i = 2; i < columnsAndTypes.length; i+=2)
            sql.append(", ").append(columnsAndTypes[i]).append(' ').append(columnsAndTypes[i + 1]);
        sql.append(" )");
        return sql.toString();
    }

    //Build a Select * Query
    public String buildSelectAllTable(String tableName, String columnsAndTypes[]) {
        Log.d(TAG, "buildCreateTable");
        StringBuilder sql = new StringBuilder();
        sql.append("Select ").append(columnsAndTypes[0]);
        for(int i = 2; i < columnsAndTypes.length; i+=2)
            sql.append(", ").append(columnsAndTypes[i]).append(columnsAndTypes[i + 1]);
        sql.append(" FROM ").append(TABLE_APPLICATIONS);
        return sql.toString();
    }


    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buildCreateTable(TABLE_APPLICATIONS, COL_APPLICATIONS));
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
            onCreate(db);
        }
    }


    /**
     * CRUD Job Applications
     *
     */

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.

    public long addUpdateApplication() {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        //Create Instance so I can pass in static values
        sInstance = new CareerToolboxDataBase(thisContext);

        //If Exists, the _idJobApplication
        long appId = -1;

        //The keys in the table to search for. This is their Int Locations
        int SearchKey1 = sInstance.arrayKeyStringFinder("strCompanyName", COL_APPLICATIONS),
                SearchKey2 = sInstance.arrayKeyStringFinder("strPositionTitle", COL_APPLICATIONS),
                SearchKey3 = sInstance.arrayKeyStringFinder("dtmListed", COL_APPLICATIONS);

        //The Where clause to stick into update, among other places.
        String clauseWhere = String.format(" %s = ? AND %s = ? AND %s = ? ",
                COL_APPLICATIONS[SearchKey1],
                COL_APPLICATIONS[SearchKey2],
                COL_APPLICATIONS[SearchKey3]);

        //Reinitialize the Search Keys to build the arguments
        SearchKey1 = sInstance.arrayKeyStringFinder(jobApp.getCompanyName(), VALUES_APPLICATIONS);
        SearchKey2 = sInstance.arrayKeyStringFinder(jobApp.getPositionTitle(), VALUES_APPLICATIONS);
        SearchKey3 = sInstance.arrayKeyStringFinder(String.valueOf(jobApp.getListedDate()), VALUES_APPLICATIONS);

        //The Strings[] that contains the args for updating
        String[] argsWhere = {

                VALUES_APPLICATIONS[SearchKey1],
                VALUES_APPLICATIONS[SearchKey2],
                VALUES_APPLICATIONS[SearchKey2]

        };



        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            //Insert Information into Values
            values.put(COL_APPLICATIONS[1], jobApp.getCompanyName());
            values.put(COL_APPLICATIONS[2], jobApp.getPositionTitle());
            values.put(COL_APPLICATIONS[3], jobApp.getInterviewOrganized());
            values.put(COL_APPLICATIONS[4], jobApp.getCity());
            values.put(COL_APPLICATIONS[5], jobApp.getState());
            values.put(COL_APPLICATIONS[6], jobApp.getContactName());
            values.put(COL_APPLICATIONS[7], jobApp.getListedDate().getTime());
            values.put(COL_APPLICATIONS[8], jobApp.getAppliedDate().getTime());


            // Check if row exists
            //Create Select Query. If true it will return _idJobApplication
            String usersSelectQuery = String.format("SELECT %s FROM %s ",
                        COL_APPLICATIONS[0], TABLE_APPLICATIONS);
            usersSelectQuery += clauseWhere;

            //Call a cursor to check if data exists. if it exists, set appId
            Cursor cursor = db.rawQuery(usersSelectQuery + " WHERE " + clauseWhere, argsWhere);
                try {
                    if (cursor.moveToFirst()) {
                        appId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }

            //If appId is unchanged, ergo key does not exist, insert values
            if (appId == -1) {
                // user with this userName did not already exist, so insert new user
                appId = db.insertOrThrow(TABLE_APPLICATIONS, null, values);
                db.setTransactionSuccessful();
            } else {
                db.update(TABLE_APPLICATIONS, values, clauseWhere, argsWhere);

            }


        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return appId;
    }

    public static synchronized CareerToolboxDataBase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new CareerToolboxDataBase(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Querying Records
     */
    /*
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        // SELECT ALL FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                        TABLE_APPLICATIONS,
                        TABLE_USERS,
                        TABLE_APPLICATIONS, KEY_POST_USER_ID_FK,
                        TABLE_USERS, KEY_USER_ID);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    User newUser = new User();
                    newUser.userName = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
                    newUser.profilePictureUrl = cursor.getString(cursor.getColumnIndex(KEY_USER_PROFILE_PICTURE_URL));

                    Post newPost = new Post();
                    newPost.text = cursor.getString(cursor.getColumnIndex(KEY_POST_TEXT));
                    newPost.user = newUser;
                    posts.add(newPost);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }//*/



    /**
     * Deleting Records
     */
    public void deleteAllApplications() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_APPLICATIONS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }


    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    public CareerToolboxDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        thisContext = context;
    }
}
