package edu.scu.lwang.finalprojectscene;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sarah on 5/23/2016.
 */

public class PlantsGridView extends AppCompatActivity {
    final int notificationId = 1111;

    Context context;
    //    PendingIntent pendingIntent;
    AlarmManager alarm_manager;
    //    ArrayList<AlarmManager> alarmList = new ArrayList<>();
    Calendar calendar;
//    ArrayList<Calendar> calList = new ArrayList<>();

    String fileName;
    String userChoosenTask;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.plantsgridview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  addNewPlant();
                Intent intent=new Intent(PlantsGridView.this, RecognitionActivity.class);
                startActivity(intent);
            }
        });

        final GridView gridview = (GridView) findViewById(R.id.pgridview);
        gridview.setAdapter(new ImageAdapter(this));

        System.out.println("got to tthe on create in PlantsGridView");
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Plant p = (Plant) gridview.getAdapter().getItem(position);
                Intent i = new Intent(PlantsGridView.this, PlantMenu.class);
                System.out.println("this is the ID after touch in the PlantsGridView: " + p.getId());
                Bundle b = new Bundle();
                b.putInt("_id", p.getId());
                i.putExtras(b);

                startActivity(i);
//                Toast.makeText(HelloGridView.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
            }
        });
        // set callback for create notification button


        calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 19);



        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // create an intent to ReminderReceiver
        Intent toReceiver = new Intent(this, ReminderReceiver.class);

        // create a pending intent to delay the intent until the reminder time
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PlantsGridView.this, 0, toReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the remainder manager
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);

        acquireRunTimePermissions();


    }

//    private void addNewPlant(){
//        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
//                "Cancel" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(PlantsGridView.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                //boolean result = Utility.checkPermission(PlantsGridView.this);
//                //System.out.println("hi i passed with " + result);
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask="Take Photo";
//                    //   if(result)
//                    cameraIntent();
//                } else if (items[item].equals("Choose from Gallery")) {
//                    userChoosenTask="Choose from Gallery";
//                    //   if(result)
//                    galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private void cameraIntent(){
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getPackageManager()) == null) {
//            Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        fileName = getOutputFileName();
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));
//
//        startActivityForResult(intent, 1234);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234 || resultCode != RESULT_OK) return;

        Intent intent=new Intent(PlantsGridView.this, PlantFrameName.class);//connect to xiaoyuan's recognition activity for image recognitions

        Bundle bundle = new Bundle();
        bundle.putString("plantPicPath", fileName);
        System.out.println("THIS IS THE PATH: " + fileName);
        intent.putExtras(bundle);

        startActivity(intent);
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        imageView.setImageURI(Uri.parse(fileName));
//        imageView.setRotation(180f);
    }


    private String getOutputFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        Log.i("lwang", filename);
        return filename;
    }

    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }



    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        //startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_waterList:
                Intent home = new Intent(PlantsGridView.this,WaterList.class);
                startActivity(home);
                break;
//            case R.id.home:
//                Intent home = new Intent(MainActivity.this,ZooInfo.class);
//                startActivity(home);
//                break;
            default:
        }

        return true;
    }


}
