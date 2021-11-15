package bstu.fit.baa.myrecipes;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bstu.fit.baa.myrecipes.adapter.RecipeCardAdapter;
import bstu.fit.baa.myrecipes.entity.Recipe;

public class DisplayRecipeCardsFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    List<Recipe> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    public void setList(List<Recipe> list) {
        this.list = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_recipe_cards, container, false);
        recyclerView = view.findViewById(R.id.cardContainer);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        RecipeCardAdapter adapter = new RecipeCardAdapter(context, list);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }
}