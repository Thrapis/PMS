package bstu.fit.baa.myrecipes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bstu.fit.baa.myrecipes.BR;
import bstu.fit.baa.myrecipes.R;
import bstu.fit.baa.myrecipes.entity.Recipe;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeHolder> {

    private final Context context;
    private final List<Recipe> recipes;

    public RecipeCardAdapter(Context context, List<Recipe> recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding vdb = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.recipe_card,
                parent, false
        );
        return new RecipeHolder(vdb);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.recipeBinding.setVariable(BR.recipe, recipe);
        holder.recipe = recipe;
        holder.recipeBinding.getRoot().setTag(recipe.id);
        holder.recipeBinding.getRoot().setId(position);
        ((Activity)context).registerForContextMenu(holder.recipeBinding.getRoot());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {

        ViewDataBinding recipeBinding;
        Recipe recipe;

        public RecipeHolder(@NonNull ViewDataBinding recipeBinding) {
            super(recipeBinding.getRoot());
            this.recipeBinding = recipeBinding;
        }
    }
}
