package edu.scu.lwang.finalprojectscene;

/**
 * Created by Lexie on 5/21/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by mingming on 5/9/16.
 */
public class PlantDBHelper extends SQLiteOpenHelper {

    static private final int VERSION=3;
    static private final String DB_NAME="PlantData.db";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE plant (" +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  PlantName TEXT," +
                    "  PhotoPath TEXT," +
                    "  WaterInterval INTEGER," +
                    "  LastWater INTEGER," +
                    "  NextWater INTEGER);";

    static private final String SQL_DROP_TABLE = "DROP TABLE plant";

    Context context;

    public PlantDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);     // we use default cursor factory (null, 3rd arg)
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // a simple crude implementation that does not preserve data on upgrade
        db.execSQL(SQL_DROP_TABLE);
        db.execSQL(SQL_CREATE_TABLE);

//        Toast.makeText(context, "Upgrading DB and dropping data!!!", Toast.LENGTH_SHORT).show();
    }

    public int getMaxRecID() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM plant;", null);

        if (cursor.getCount() == 0) {
            return 0;
        } else {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM plant;", null);
    }

    public Cursor waterList(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            // arguments injected by manual string concatenation
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.add(Calendar.HOUR_OF_DAY, 3 * 24); // 3 days after today
            long in3day = cal.getTimeInMillis();

            String mySQL = " select *"
                    + "   from plant "
                    + "  where NextWater <= "  + in3day
                    ;

            Cursor c1 = db.rawQuery(mySQL, null);

            return c1;

        } catch (Exception e) {
            Log.e("\nError: " , e.getMessage().toString());
            return null;

        }

    }

    public void add(Plant pi) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PlantName", pi.plantName);
        contentValues.put("PhotoPath", pi.photoPath);
        contentValues.put("WaterIntervel", pi.waterInterval);
        contentValues.put("LastWater", pi.lastWater.getTime());
        contentValues.put("NextWater", pi.nextWater.getTime());

        db.insert("plant", null, contentValues);


//        String SQL_ADD =
//                "INSERT INTO plant (PlantName, PhotoPath, WaterInterval, LastWater) VALUES ('"
//                        + pi.plantName + "', '"
//                        + pi.photoPath + "', '"
//                        + pi.waterInterval + "', '"
//                        + pi.lastWater +"');";
//        db.execSQL(SQL_ADD);

    }


    public Hashtable<String, String> fetchPlantName() {
        Hashtable<String, String> plantNameHash = new Hashtable<String, String>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String mypath = DB_Path + DB_NAME;
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        Cursor cursor = fetchAll();
        if (cursor.moveToFirst()) {
            do {
                plantNameHash.put(cursor.getString(cursor.getColumnIndex("plantName")), cursor.getString(cursor.getColumnIndex("photoPath")));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        return plantNameHash;
    }

    public Hashtable<String, Integer> fetchWaterInterval() {
        Hashtable<String, Integer> plantWaterInterval = new Hashtable<String, Integer>();
        Cursor cursor = fetchAll();
        if (cursor.moveToFirst()) {
            do {
                plantWaterInterval.put(cursor.getString(cursor.getColumnIndex("plantName")), cursor.getInt(cursor.getColumnIndex("waterInterval")));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        return plantWaterInterval;
    }

    public Plant fetchPlantWithId(int _id){
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("this is the id to get from fetching plant after touch: " + _id);
        Cursor cursor = db.rawQuery("SELECT * FROM plant WHERE _id = ?;", new String[]{String.valueOf(_id)});
        cursor.moveToFirst();

        int id= cursor.getInt(0);
        String plantName= cursor.getString(1);
        String plantPicPath= cursor.getString(2);
        int waterInterval = cursor.getInt(3);
        int lastWater = cursor.getInt(4);
      //  int date = cursor.getInt(9);

        Plant plant= new Plant(id, plantName, plantPicPath, new Date(), 0, new Date());


        return plant;
    }
    public void delete(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("plant", "_id=?", new String[]{String.valueOf(id)});

        /*
        String SQL_DELETE="DELETE FROM contact WHERE _id=" + id + ";";
        db.execSQL(SQL_DELETE);
         */
    }

//    public Hashtable<String, String> fetchPlantName() {
//        Hashtable<String, String> plantNameHash = new Hashtable<String, String>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
////        Cursor result = db.query(true, "plant", new String[] {"plantName"}, null, null, null, null, null, null);
////        if (result.moveToFirst()) {
////            do {
////                plantNameHash.put(result.getString(result.getColumnIndex("plantName")), );
////            } while (result.moveToNext());
////        }
////        else {
////            return null;
////        }
////        return plantNameHash;
//        Cursor cursor = db.rawQuery("SELECT * FROM plant;", null);
//        if (cursor.moveToFirst()) {
//            do {
//                plantNameHash.put(cursor.getString(cursor.getColumnIndex("PlantName")), cursor.getString(cursor.getColumnIndex("PhotoPath")));
//            } while (cursor.moveToNext());
//        }
//        else {
//            return null;
//        }
//        return plantNameHash;
//    }
public void waterToday(int id) {
    // action query performed using execSQL
    // add 'XXX' to the name of person whose phone is 555-1111
//        txtMsg.append("\n-updateDB");

    try {
        SQLiteDatabase db = this.getReadableDatabase();
        int date = (int) (Calendar.getInstance().getTimeInMillis());
        String query = " update plant set lastWater =" + date
                + " where _id = " + id;
        db.execSQL(query);

    } catch (Exception e) {
        Log.e("\nError updateDB: ", e.getMessage());

    }

}

}
