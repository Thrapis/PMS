package bstu.fit.baa.goodsfinder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import bstu.fit.baa.goodsfinder.adapter.GoodAdapter;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;
import bstu.fit.baa.goodsfinder.util.GoodItemsSortType;

public class GoodItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    public GoodItemFragment() {
    }

    @SuppressWarnings("unused")
    public static GoodItemFragment newInstance(int columnCount) {
        GoodItemFragment fragment = new GoodItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_item_list, container, false);

        if (view instanceof ListView) {
            Context context = view.getContext();
            ListView listView = (ListView) view;
            listView.setAdapter(new GoodAdapter(context, GoodItemsContainer.getGoodItemsList()));
        }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.no_sort_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.NO_SORT);
                updateList();
                return true;
            case R.id.sort_by_name_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_NAME);
                updateList();
                return true;
            case R.id.sort_by_find_date_settings:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.setSortType(GoodItemsSortType.SORT_BY_FIND_DATE);
                updateList();
                return true;
            case R.id.load_settings:
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.importFromJson(this);
                updateList();
                return true;
            case R.id.save_settings:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                GoodItemsContainer.exportToJson(this);
                updateList();
                return true;
            case R.id.add_settings:
                Intent intent = new Intent(this, InsertGoodItemForm.class);
                startActivityForResult(intent, ADD_NEW_GOOD_ITEM);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void updateList() {
        ListView list = getContext().findViewById(R.id.itemsList);
        list.removeAllViewsInLayout();
        GoodAdapter adapter = new GoodAdapter(this, GoodItemsContainer.getGoodItemsList(pattern));
        list.setAdapter(adapter);
    }
}