package bstu.fit.baa.goodsfinder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bstu.fit.baa.goodsfinder.adapter.GoodCardAdapter;
import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.util.ItemsPresentation;

public class GoodItemCardViewFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private List<GoodItem> list = new ArrayList<>();
    private GoodCardAdapter adapter;
    private ItemsPresentation presentation = ItemsPresentation.GRID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_good_item_card_view, container, false);
        recyclerView = view.findViewById(R.id.cardContainer);
        updateCardContainer();

        return view;
    }

    void setList(List<GoodItem> list) {
        this.list = list;
    }

    void setPresentation(ItemsPresentation presentation) {
        this.presentation = presentation;
    }

    void updateCardContainer() {
        adapter = new GoodCardAdapter(context, list);

        if (presentation == ItemsPresentation.GRID) {
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
        }
        else if (presentation == ItemsPresentation.LIST) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setAdapter(adapter);
    }
}