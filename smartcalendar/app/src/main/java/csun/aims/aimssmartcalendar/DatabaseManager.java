package csun.aims.aimssmartcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
        public static final String KEY_DONE = "Done";
        public static final String KEY_TYPE = "Type";
        public static final String KEY_READING = "Reading";
        public static final String KEY_STARTTIME = "STime";
        public static final String KEY_ENDTIME = "ETime";
        public static final String KEY_UNITS = "Units";
        public static final String KEY_DIFFICULTY = "Difficulty";

        private static final String DATABASE_NAME = "StudentData";
        private static final String ASSIGNMENTS_TABLE = "Assignments";
        private static final String CLASSES_TABLE = "Classes";
        private static final int DATABASE_VERSION = 2;
        private Context context;
        private static final String DATABASE_CREATE = "CREATE TABLE "+ASSIGNMENTS_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "+KEY_TITLE+" VARCHAR(255) not null, "+KEY_DUEDATE+" VARCHAR(255), "+KEY_DUETIME+" VARCHAR(255), "+KEY_COURSE+" VARCHAR(255),  "+KEY_DONE+" BOOL, "+KEY_TYPE+" INTEGER );";
        private static final String DATABASE_CREATE2 = "CREATE TABLE "+CLASSES_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "+KEY_TITLE+" VARCHAR(255) not null, "+KEY_STARTTIME+" VARCHAR(255), "+KEY_ENDTIME+" VARCHAR(255), "+KEY_DIFFICULTY+" integer,  "+KEY_UNITS+" integer, "+KEY_READING+" BOOL );";

        private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " +ASSIGNMENTS_TABLE;
        private static final String DATABASE_DROP2 = "DROP TABLE IF EXISTS " +CLASSES_TABLE;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            Log.d(LOGCAT,"ASSIGNMENTS CREATED");

            db.execSQL(DATABASE_CREATE2);
            Log.d(LOGCAT,"CLASSES CREATED");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE);

            Log.d(LOGCAT,"ASSIGNMENTS UPGRADED");

            db.execSQL("DROP TABLE IF EXISTS " + CLASSES_TABLE);
            Log.d(LOGCAT,"CLASSES UPGRADED");

            onCreate(db);

        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + ASSIGNMENTS_TABLE);

            Log.d(LOGCAT,"ASSIGNMENTS UPGRADED");

            db.execSQL("DROP TABLE IF EXISTS " + CLASSES_TABLE);
            Log.d(LOGCAT,"CLASSES UPGRADED");

            onCreate(db);
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
        //---retrieves all the records---
        public Cursor getAllAssignments() {
            SQLiteDatabase db = this.getReadableDatabase();
            return db.query(ASSIGNMENTS_TABLE, new String[]{KEY_ROWID, KEY_TITLE,
                    KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE}, null, null, null, null, null);
        }
//
//        //---retrieves a particular record---
        public Cursor getAssignment(long rowId) throws SQLException {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor mCursor =
                    db.query(true, ASSIGNMENTS_TABLE, new String[]{KEY_ROWID,
                                    KEY_TITLE, KEY_DUEDATE,KEY_DUETIME, KEY_COURSE, KEY_TYPE},
                            KEY_ROWID + "=" + rowId, null, null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }
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

    //---retrieves all the records---
    // gets the first string from each row.
    public List<String> getAllAssignments() {
        Cursor c = DBhelper.getAllAssignments();
        List<String> s = new LinkedList<String>();
        if(c.moveToFirst()) {
            do {
                s.add(c.getString(1));
            } while (c.moveToNext());
        }
        return s;
    }

    //TODO: this needs to be changed so that it gets the right assignment
    public Cursor getAssignment(long rowId){
        SQLiteDatabase db = DBhelper.getReadableDatabase();
            Cursor mCursor =
                    db.query(true, DBhelper.ASSIGNMENTS_TABLE, new String[]{DBhelper.KEY_ROWID,
                                    DBhelper.KEY_TITLE, DBhelper.KEY_DUEDATE,DBhelper.KEY_DUETIME, DBhelper.KEY_COURSE, DBhelper.KEY_TYPE},
                            DBhelper.KEY_ROWID + "=" + rowId, null, null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = DBhelper.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
    //---insert a record into the database---
    public long insertAssignment(String title, String duedate, String time, String course, boolean notes, int type) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBhelper.KEY_TITLE, title);
        initialValues.put(DBhelper.KEY_DUEDATE, duedate);
        initialValues.put(DBhelper.KEY_DUETIME, time);
        initialValues.put(DBhelper.KEY_COURSE, course);
        initialValues.put(DBhelper.KEY_DONE, notes);
        initialValues.put(DBhelper.KEY_TYPE, type);
        long id = db.insert(DBhelper.ASSIGNMENTS_TABLE, null, initialValues);
        return id;
    }

    //---insert a record into the database---
    public long insertClass(String title, String startTime, String endTime, int difficulty, int units, boolean reading) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBhelper.KEY_TITLE, title);
        initialValues.put(DBhelper.KEY_STARTTIME, startTime);
        initialValues.put(DBhelper.KEY_ENDTIME, endTime);
        initialValues.put(DBhelper.KEY_DIFFICULTY, difficulty);
        initialValues.put(DBhelper.KEY_UNITS, units);
        initialValues.put(DBhelper.KEY_READING, reading);
        long id = db.insert(DBhelper.CLASSES_TABLE, null, initialValues);
        return id;
    }


}

