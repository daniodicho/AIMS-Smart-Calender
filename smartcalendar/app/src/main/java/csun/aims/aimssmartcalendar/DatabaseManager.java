package csun.aims.aimssmartcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Dani on 1/25/2015.
 */
public class DatabaseManager{

    DatabaseHelper DBhelper;

     public DatabaseManager(Context context) {

        DBhelper = new DatabaseHelper(context);

    }

    static class DatabaseHelper extends SQLiteOpenHelper  {

        public String LOGCAT = "UDB";
        public static final String KEY_ROWID = "id";
        public static final String KEY_TITLE = "Title";
        public static final String KEY_DUEDATE = "DDate";
        public static final String KEY_DUETIME = "DTime";
        public static final String KEY_COURSE = "Course";
        public static final String KEY_NOTES = "Notes";
        public static final String KEY_TYPE = "Type";
        private static final String DATABASE_NAME = "StudentData";
        private static final String ASSIGNMENTS_TABLE = "Assignments";
        private static final String CLASSES_TABLE = "Classes";
        private static final int DATABASE_VERSION = 1;
        private Context context;
        private static final String DATABASE_CREATE = "CREATE TABLE "+ASSIGNMENTS_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "+KEY_TITLE+" VARCHAR(255) not null, "+KEY_DUEDATE+" VARCHAR(255), "+KEY_DUETIME+" VARCHAR(255), "+KEY_COURSE+" VARCHAR(255),  "+KEY_NOTES+" VARCHAR(255), "+KEY_TYPE+" VARCHAR(255) );";
        private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " +ASSIGNMENTS_TABLE;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            Log.d(LOGCAT,"ASSIGNMENTS CREATED");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DATABASE_DROP);
            onCreate(db);
            Log.d(LOGCAT,"ASSIGNMENTS CHANGED");
        }



//        //---opens the database---
//        public DatabaseManager open() throws SQLException {
//            db = DBhelper.getWritableDatabase();
//            return this;
//        }
//
//        //---closes the database---
//        public void close() {
//            DBhelper.close();
//        }
//
//
//        //---deletes a particular record---
//        public boolean deleteAssignment(long rowId) {
//            return db.delete(ASSIGNMENTS_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//        }
//
//        //---retrieves all the records---
//        public Cursor getAllAssignments() {
//            return db.query(ASSIGNMENTS_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
//                    KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE}, null, null, null, null, null);
//        }
//
//        //---retrieves a particular record---
//        public Cursor getAssignment(long rowId) throws SQLException {
//            Cursor mCursor =
//                    db.query(true, ASSIGNMENTS_TABLE, new String[]{KEY_ROWID,
//                                    KEY_TITLE, KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE},
//                            KEY_ROWID + "=" + rowId, null, null, null, null, null);
//            if (mCursor != null) {
//                mCursor.moveToFirst();
//            }
//            return mCursor;
//        }
//
//        //---updates a record---
//        public boolean updateAssignment(long rowId, String title, String duedate, String time, String course, String notes) {
//            ContentValues args = new ContentValues();
//            args.put(KEY_TITLE, title);
//            args.put(KEY_DUEDATE, duedate);
//            args.put(KEY_DUETIME, time);
//            args.put(KEY_COURSE, course);
//            args.put(KEY_TYPE, notes);
//            return db.update(ASSIGNMENTS_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
//        }
    }

    //---insert a record into the database---
    public long insertAssignment(String title, String duedate, String time, String course, String notes, String type) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBhelper.KEY_TITLE, title);
        initialValues.put(DBhelper.KEY_DUEDATE, duedate);
        initialValues.put(DBhelper.KEY_DUETIME, time);
        initialValues.put(DBhelper.KEY_COURSE, course);
        initialValues.put(DBhelper.KEY_NOTES, notes);
        initialValues.put(DBhelper.KEY_TYPE, type);
        long id = db.insert(DBhelper.ASSIGNMENTS_TABLE, null, initialValues);
        return id;
    }

}

