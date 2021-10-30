package bstu.fit.baa.goodsfinder.adapter;


import static bstu.fit.baa.goodsfinder.MainActivity.NewStateReason.UPDATE_LIST;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.Date;

import bstu.fit.baa.goodsfinder.InfoGoodItem;
import bstu.fit.baa.goodsfinder.MainActivity;
import bstu.fit.baa.goodsfinder.R;
import bstu.fit.baa.goodsfinder.database.DateTypeConverter;
import bstu.fit.baa.goodsfinder.database.UUIDTypeConverter;
import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class GoodCursorAdapter extends SimpleCursorAdapter {

    private static final int favoriteColor = Color.argb(100, 255, 235, 59);
    //private static final int noFavoriteColor = Color.colorSpace(R.color.design_default_color_on_primary);

    private static final int layout = R.layout.good_row;
    private static final String[] from = new String[] { "image", "name", "findDate", "findPlace" };
    private static final int[] to = new int[] { R.id.good_image, R.id.good_name, R.id.good_find_date, R.id.good_find_place };

    public GoodCursorAdapter(Context context, Cursor c) {
        super(context, layout, c, from, to, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int favoriteIndex = cursor.getColumnIndex("favorite");
        boolean favorite = cursor.getInt(favoriteIndex) == 1;

        if (favorite) {
            view.setBackgroundColor(favoriteColor);
        }

        ((Activity)context).registerForContextMenu(view);
        view.setId(cursor.getPosition());
        view.setTag(favorite);
        GoodItem goodItem = new GoodItem(
                UUIDTypeConverter.toUUID( cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getBlob(4), DateTypeConverter.toDate(cursor.getLong(5)),
                cursor.getString(6), cursor.getString(7), cursor.getInt(8) == 1);
        view.setOnClickListener(view1 -> {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((MainActivity)context).newStateOfFragments(UPDATE_LIST, goodItem);
            }
            else {
                Intent intent = new Intent(context, InfoGoodItem.class);
                intent.putExtra("good", goodItem);
                context.startActivity(intent);
            }
        });

        int imageIndex = cursor.getColumnIndex(from[0]);
        int nameIndex = cursor.getColumnIndex(from[1]);
        int findDateIndex = cursor.getColumnIndex(from[2]);
        int findPlaceIndex = cursor.getColumnIndex(from[3]);

        String name = cursor.getString(nameIndex);
        byte[] byteImage = cursor.getBlob(imageIndex);
        Bitmap image = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        Date findDate = DateTypeConverter.toDate(cursor.getLong(findDateIndex));
        String findDateFormat = new SimpleDateFormat("dd.MM.").format(findDate) + findDate.getYear();
        String findPlace = cursor.getString(findPlaceIndex);

        ((ImageView) view.findViewById(to[0])).setImageBitmap(image);
        ((TextView) view.findViewById(to[1])).setText(name);
        ((TextView) view.findViewById(to[2])).setText(findDateFormat);
        ((TextView) view.findViewById(to[3])).setText(findPlace);
    }
}