package bstu.fit.baa.goodsfinder;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import bstu.fit.baa.goodsfinder.adapter.GoodCursorAdapter;

public class GoodItemListFragment extends Fragment {

    private Context context;
    private ListView listView;
    //private GoodAdapter adapter;
    private GoodCursorAdapter adapter;
    //private List<GoodItem> list = new ArrayList<>();
    private Cursor cursor = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_item_list, container, false);
        listView = (ListView)view;

        //updateList(list);
        if (cursor != null)
            updateList(cursor);

        return view;
    }

    /*void setList(List<GoodItem> list) {
        this.list = list;
    }*/

    void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    void updateList(Cursor cursor) {
        adapter = new GoodCursorAdapter(context, cursor);
        listView.setAdapter(adapter);
    }

    /*void updateList(List<GoodItem> list) {
        adapter = new GoodAdapter(context, list);
        listView.setAdapter(adapter);
    }*/

}