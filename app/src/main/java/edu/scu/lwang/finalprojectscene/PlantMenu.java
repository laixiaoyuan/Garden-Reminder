package edu.scu.lwang.finalprojectscene;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PlantMenu extends AppCompatActivity implements View.OnClickListener{
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    PlantDBHelper db;
    final String myPreference = "wateve";
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    final int requestCode = 1234;
    final String albumName = "L11-camera-external-file";
    String fileName = "";
    int id;
    String userChoosenTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantmenu);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("_id");
        System.out.println("this is _id after fetching it in PlantMenu: " + id);
        PlantDBHelper db=new PlantDBHelper(this);
        Plant plant = db.fetchPlantWithId(id);

        ImageView iv = (ImageView) findViewById(R.id.plantImage);

        if(plant.getPhotoPath().charAt(0) == 'h'){
            new DownloadImageTask(iv)
                    .execute(plant.getPhotoPath());

        }else{
            iv.setImageURI(Uri.parse(plant.getPhotoPath()));
        }

        Button button3 = (Button) findViewById(R.id.button3);
        Button addPlantPhotoB = (Button) findViewById(R.id.addPlantPhoto);
        addPlantPhotoB.setOnClickListener(this);

        acquireRunTimePermissions();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlantMenu.this);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("Plant details")
                        .setMessage("Sun tolerance: full sun" +
                                "Water frequency: once a week" +
                                "Hardiness zone: 7-10")
                        .setCancelable(true)
                ;
                builder.create().show();
            }
        });


        Button button2 = (Button) findViewById(R.id.button2);
        //------------------->Mingming put code here to appear the next water date
        //
        //
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(PlantMenu.this);
//                //    db = new dbHelper(this);
//                //    cursor = db.fetchAll();
//
//                builder.setIcon(R.mipmap.ic_launcher)
//                        .setTitle("Next Watering date")
//                        .setMessage("May 5, 2016")
//                        //            .setMessage(cursor.getString(cursor.getColumnIndex("lastWater")))
//                        .setCancelable(true)
//                ;
//                builder.create().show();
//            }
//        });

    }
    //        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                readEditPreference();
//            }
//        });
//
//
//        mySharedPreferences = getSharedPreferences(myPreference, Activity.MODE_PRIVATE);
    @Override
    public void onClick(View v) {

        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(PlantMenu.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result = Utility.checkPermission(plantsGridView.this);
                //System.out.println("hi i passed with " + result);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    //   if(result)
                    cameraIntent();
                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask="Choose from Gallery";
                    //   if(result)
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

    }

    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
            return;
        }

        fileName = getOutputFileName();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));

        startActivityForResult(intent, 2345);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 2345 || resultCode != RESULT_OK) return;

        Intent intent=new Intent(PlantMenu.this, PlantMenu.class);

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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
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
}