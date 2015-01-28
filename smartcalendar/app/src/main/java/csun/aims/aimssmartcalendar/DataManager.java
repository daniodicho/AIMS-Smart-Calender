package csun.aims.aimssmartcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Dani on 1/25/2015.
 */
public class DataManager {
    public static final String KEY_ROWID = "id";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_DUEDATE = "Due Date";
    public static final String KEY_DUETIME = "Due Time";
    public static final String KEY_COURSE = "Course";
    public static final String KEY_TYPE = "Type";
    private static final String DATABASE_NAME = "StudentData";
    private static final String ASSIGNMENTS_TABLE = "Assignments";
    private static final String CLASSES_TABLE = "Classes";


    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE =
            "create table if not exists assignments (id integer primary key autoincrement, "
                    + "title VARCHAR not null, duedate date, time time, course VARCHAR, notes VARCHAR);";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DataManager(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DB", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DataManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a record into the database---
    public long insertAssignment(String title, String duedate, String time, String course, String notes) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_DUEDATE, duedate);
        initialValues.put(KEY_DUETIME, time);
        initialValues.put(KEY_COURSE, course);
        initialValues.put(KEY_TYPE, notes);
        return db.insert(ASSIGNMENTS_TABLE, null, initialValues);
    }


    //---deletes a particular record---
    public boolean deleteAssignment(long rowId) {
        return db.delete(ASSIGNMENTS_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the records---
    public Cursor getAllAssignments() {
        return db.query(ASSIGNMENTS_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
                KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE}, null, null, null, null, null);
    }

    //---retrieves a particular record---
    public Cursor getAssignment(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, ASSIGNMENTS_TABLE, new String[]{KEY_ROWID,
                                KEY_TITLE, KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE},
                        KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a record---
    public boolean updateAssignment(long rowId, String title, String duedate, String time, String course, String notes) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_DUEDATE, duedate);
        args.put(KEY_DUETIME, time);
        args.put(KEY_COURSE, course);
        args.put(KEY_TYPE, notes);
        return db.update(ASSIGNMENTS_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}

