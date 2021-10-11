package bstu.fit.baa.goodsfinder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import bstu.fit.baa.goodsfinder.adapter.GoodAdapter;
import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;

public class GoodItemListFragment extends Fragment {

    private Context context;
    private ListView listView;
    private GoodAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_item_list, container, false);
        listView = (ListView)view;

        List<GoodItem> list = GoodItemsContainer.getGoodItemsListByPattern();
        GoodItem selected = GoodItemsContainer.peekSelectedGoodItem();

        updateList(list);
        if (selected != null && list.contains(selected)){
            int index = list.indexOf(selected);
            switchSelection(index);
        }

        return view;
    }

    void updateList(List<GoodItem> list) {
        adapter = new GoodAdapter(context, list);
        listView.setAdapter(adapter);
    }

    void switchSelection(int index) {
        adapter.switchSelection(index);
    }
}