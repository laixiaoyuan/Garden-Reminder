package edu.scu.lwang.finalprojectscene;

/**
 * Created by Lexie on 5/21/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
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
                    "  WaterInterval INTEGER" +
                    "  LastWater DATE);";

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
}
