package bstu.fit.baa.goodsfinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bstu.fit.baa.goodsfinder.InfoGoodItem;
import bstu.fit.baa.goodsfinder.R;
import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;

public class GoodAdapter extends ArrayAdapter {

    private Context context;
    private List<GoodItem> objects;
    private LayoutInflater inflater;
    private boolean[] selections;

    private static final int selectedColor = Color.argb(255, 200,200,200);
    private static final int notSelectedColor = Color.argb(255, 255,255,255);

    public GoodAdapter(Context context, ArrayList<GoodItem> products) {
        super(context, 0);
        this.context = context;
        this.objects = products;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selections = new boolean[products.size()];
    }

    public GoodAdapter(Context context, List<GoodItem> products) {
        super(context, 0);
        this.context = context;
        this.objects = products;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selections = new boolean[products.size()];
    }

    public void switchSelection(int position) {
        selections[position] = !selections[position];
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.good_row, parent, false);
        }

        GoodItem goodItem = (GoodItem) getItem(position);

        ((ImageView) view.findViewById(R.id.good_image)).setImageBitmap(goodItem.getImageAsBitmap());
        ((TextView) view.findViewById(R.id.good_name)).setText(goodItem.getName());
        ((TextView) view.findViewById(R.id.good_find_date)).setText(goodItem.getFindDateInFormat());
        ((TextView) view.findViewById(R.id.good_find_place)).setText(goodItem.getFindPlace());

        if (selections[position]) {
            view.setBackgroundColor(selectedColor);
        } else {
            view.setBackgroundColor(notSelectedColor);
        }

        ((Activity)context).registerForContextMenu(view);
        view.setId(position);
        view.setTag(goodItem.getId());
        view.setOnClickListener(view1 -> {
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GoodItemsContainer.pushSelectedGoodItem(goodItem);
            }
            else {
                GoodItemsContainer.pushSelectedGoodItem(goodItem);
                Intent intent = new Intent(context, InfoGoodItem.class);
                intent.putExtra("good", goodItem);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
