package edu.scu.lwang.finalprojectscene;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarahwang on 6/3/16.
 */
public class PlantHistory extends AppCompatActivity {
    final int notificationId = 1111;

    Context context;
    String plantHistoryName;

    //AlarmManager alarm_manager;
    //    ArrayList<AlarmManager> alarmList = new ArrayList<>();
    Calendar calendar;
//    ArrayList<Calendar> calList = new ArrayList<>();

    String fileName;
    String userChoosenTask;
    TextView plantNameText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.planthistory);

        Bundle bundle = getIntent().getExtras();
        plantHistoryName = bundle.getString("plantHistoryName");
        // System.out.println("printsth");
        plantNameText = (TextView) findViewById(R.id.plantTextName);
        //assert plantNameText != null;
        plantNameText.setText(plantHistoryName);
        final GridView gridview = (GridView) findViewById(R.id.historygridview);
        gridview.setAdapter(new HistoryAdapter(this, plantHistoryName));

        System.out.println("got to the on create in PlantsGridView");

        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //acquireRunTimePermissions();
    }



}

