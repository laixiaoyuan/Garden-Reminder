package edu.scu.lwang.finalprojectscene;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by mingming on 5/9/16.
 */
public class WaterListAdapter extends CursorAdapter {

    public WaterListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String plantName = cursor.getString(cursor.getColumnIndex("PlantName"));
        String imagePath = cursor.getString(cursor.getColumnIndex("PhotoPath"));
        int fromNow = (cursor.getInt(cursor.getColumnIndex("NextWater")) - (int)Calendar.getInstance().getTimeInMillis()) / 86400000;
        String waterDay;
        if(fromNow == 0){
            waterDay = "Today";
        }else if(fromNow == 1){
            waterDay = "Tomorrow";
        }else{
            waterDay = "In 1 week";
        }
        ((ImageView)view.findViewById(R.id.imageView2)).setImageURI(Uri.parse(imagePath));
        TextView tvName = (TextView)view.findViewById(R.id.textView2);
        tvName.setText(plantName);

        TextView tvWater = (TextView)view.findViewById(R.id.textView3);
        tvWater.setText(waterDay);

        TextView tvDone = (TextView)view.findViewById(R.id.textView5);
        tvDone.setText("DONE");

//        BitmapFactory.Options optio
    }
}