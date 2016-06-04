package edu.scu.lwang.finalprojectscene;

/**
 * Created by Sarahwang on 5/24/16.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

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

    public ImageAdapter(Context c) {
        mContext = c;
        mThumbIds = new ArrayList<Plant>();

        PlantDBHelper dbHelper = new PlantDBHelper(mContext);
        Cursor cursor = dbHelper.fetchAll();

        while (cursor.moveToNext())
        {
            //System.out.println("test 4" );
            int id= cursor.getInt(0);
            String plantName= cursor.getString(1);
            String plantPicPath= cursor.getString(2);
            int waterInterval = cursor.getInt(3);
            int lastWater = cursor.getInt(4);
//            int date = cursor.getInt(9);
            //CREATE PLAYER
            Plant contactI= new Plant(id, plantName, plantPicPath, new Date(), 0, new Date());

            //ADD TO PLAYERS
            mThumbIds.add(contactI);
        }
    }

    public int getCount() {
        return mThumbIds.size();
        //return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds.get(position);
        //return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

//        Bitmap output = Bitmap.createBitmap(imageView.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas();
//        Paint paint = new Paint();
//        Rect rect = new Rect(0, 0, imageView.getWidth(), imageView.getHeight());
        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//            canvas.drawARGB(0, 0, 0, 0);
//            canvas.drawCircle(imageView.getWidth()/2, imageView.getHeight()/2, imageView.getWidth()/2, paint );
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //imageView.setImageResource(mThumbIds[position]);

        System.out.println("Before creating pic " + mThumbIds.get(position).getPhotoPath());
        if(mThumbIds.get(position).getPhotoPath().charAt(0) == 'h'){
            new DownloadImageTask(imageView)
                    .execute(mThumbIds.get(position).getPhotoPath());

        }else{
            imageView.setImageURI(Uri.parse(mThumbIds.get(position).getPhotoPath()));
        }

        System.out.println("After creating pic");

        return imageView;
    }

//    private Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//    };

    // references to our images
    private ArrayList<Plant> mThumbIds; //= {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
    // };
}
