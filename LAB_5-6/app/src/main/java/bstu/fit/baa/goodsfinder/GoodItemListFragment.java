package bstu.fit.baa.goodsfinder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import bstu.fit.baa.goodsfinder.adapter.GoodAdapter;
import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class GoodItemListFragment extends Fragment {

    private Context context;
    private ListView listView;
    private GoodAdapter adapter;
    private List<GoodItem> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_item_list, container, false);
        listView = (ListView)view;

        updateList(list);

        return view;
    }

    void setList(List<GoodItem> list) {
        this.list = list;
    }

    void updateList(List<GoodItem> list) {
        adapter = new GoodAdapter(context, list);
        listView.setAdapter(adapter);
    }

}