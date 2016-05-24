package edu.scu.lwang.finalprojectscene;

/**
 * Created by Sarahwang on 5/24/16.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class IdentifyOutcome extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int maxRecId;
    IdentifyOutcomeAdaptor identifyOutcomeAdaptor;
    IdentifyOutcomeDBHelper identifyOutcomeDBHelper;
    Cursor identifyCursor;

    PlantCollectionDBHelper collectionDBHelper;

    PlantDBHelper plantDBHelper;
    //    PlantAdaptor plantAdaptor;
    Cursor plantCursor;

    Hashtable<String, String> plantPhotoHash;
    Hashtable<String, Integer> waterIntervalHash;
    ListView list;
    Plant plant;
//    String plantName;
//    String photoPath;
//    Date lastWater;
//    int waterInterval;
    int plantId;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_outcome);

        plantDBHelper = new PlantDBHelper(this);
        plantCursor = plantDBHelper.fetchAll();
//        plantAdaptor = new PlantAdaptor(this, plantCursor, 0);
        plantId = plantDBHelper.getMaxRecID() + 1;

        identifyOutcomeDBHelper = new IdentifyOutcomeDBHelper(this);
        identifyCursor = identifyOutcomeDBHelper.fetchAll();
        identifyOutcomeAdaptor = new IdentifyOutcomeAdaptor(this, identifyCursor, 0);

        collectionDBHelper = new PlantCollectionDBHelper(this);
        plantPhotoHash = collectionDBHelper.fetchPlantName();
        waterIntervalHash = collectionDBHelper.fetchWaterInterval();

//        plant = new Plant(id, plantName, photoPath, date, waterInterval, lastWater);

        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(identifyOutcomeAdaptor);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String plantName = identifyCursor.getString(identifyCursor.getColumnIndex("plantName"));
        String photoPath = identifyCursor.getString(identifyCursor.getColumnIndex("imageTakePath"));

        int waterInterval = waterIntervalHash.get(plantName);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String plantNameDB = plantName;
        String photoPathDB = photoPath;
        Date lastWaterDB = date;
        int waterIntervalDB = waterInterval;

        plant = new Plant(plantId, plantNameDB, photoPathDB, date, waterIntervalDB, lastWaterDB);

        plantDBHelper.add(plant);
        plantCursor.requery();
//        plantAdaptor.notifyDataSetChanged();


        Intent intent = new Intent(IdentifyOutcome.this, MainActivity.class);
        startActivity(intent);

    }


}
