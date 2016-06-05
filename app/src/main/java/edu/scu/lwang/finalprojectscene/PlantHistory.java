package edu.scu.lwang.finalprojectscene;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
<<<<<<< HEAD


        ImageButton home = (ImageButton)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                startActivity(new Intent(PlantHistory.this, PlatsGridView.class));
            }
        });
=======
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("ActionBar demo");
//        actionBar.setSubtitle("this is a demo");
//        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_background));
        actionBar.setIcon(R.mipmap.home);

//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
//Home button
//        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
//
//        button4.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                finish();
//                startActivity(new Intent(WaterList.this, PlatsGridView.class));
//            }
//        });
>>>>>>> 3d8289bbb1d085333bf5ecf8bd826e0852c77364


        calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        //acquireRunTimePermissions();
    }



}

