package bstu.fit.baa.goodsfinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bstu.fit.baa.goodsfinder.InfoGoodItem;
import bstu.fit.baa.goodsfinder.R;
import bstu.fit.baa.goodsfinder.entitie.GoodItem;

public class GoodAdapter extends BaseAdapter {

    Context context;
    List<GoodItem> objects;
    LayoutInflater inflater;

    public GoodAdapter(Context context, ArrayList<GoodItem> products) {
        this.context = context;
        this.objects = products;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GoodAdapter(Context context, List<GoodItem> products) {
        this.context = context;
        this.objects = products;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int i, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.good_row, parent, false);
        }

        GoodItem goodItem = (GoodItem) getItem(i);

        ((ImageView) view.findViewById(R.id.good_image)).setImageBitmap(goodItem.getImageAsBitmap());
        ((TextView) view.findViewById(R.id.good_name)).setText(goodItem.getName());
        ((TextView) view.findViewById(R.id.good_find_date)).setText(goodItem.getFindDateInFormat());
        ((TextView) view.findViewById(R.id.good_find_place)).setText(goodItem.getFindPlace());

        ((Activity)context).registerForContextMenu(view);
        view.setId(i);
        view.setTag(goodItem.getId());
        view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, InfoGoodItem.class);
            intent.putExtra("good", goodItem);
            context.startActivity(intent);
        });

        return view;
    }
}
