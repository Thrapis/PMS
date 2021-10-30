package bstu.fit.baa.goodsfinder.adapter;

import static bstu.fit.baa.goodsfinder.MainActivity.NewStateReason.SELECT_INFO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bstu.fit.baa.goodsfinder.InfoGoodItem;
import bstu.fit.baa.goodsfinder.MainActivity;
import bstu.fit.baa.goodsfinder.R;
import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class GoodCardAdapter extends RecyclerView.Adapter<GoodCardAdapter.GoodItemHolder> {

    private final Context context;
    private final List<GoodItem> goodItems;

    public GoodCardAdapter(Context context, List<GoodItem> goodItems) {
        this.goodItems = goodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public GoodItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding vdb = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.good_card,
                parent, false
        );
        return new GoodItemHolder(vdb);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodItemHolder holder, int position) {
        GoodItem goodItem = goodItems.get(position);
        holder.goodItemBinding.setVariable(BR.good, goodItem);
        ImageView imageView = holder.goodItemBinding.getRoot().findViewById(R.id.imageCard);
        imageView.setImageBitmap(goodItem.getImageAsBitmap());
        holder.goodItem = goodItem;

        ((Activity)context).registerForContextMenu(holder.goodItemBinding.getRoot());
        holder.goodItemBinding.getRoot().setId(position);
        holder.goodItemBinding.getRoot().setTag(goodItem.isFavorite());
    }

    @Override
    public int getItemCount() {
        return goodItems.size();
    }

    class GoodItemHolder extends RecyclerView.ViewHolder {

        ViewDataBinding goodItemBinding;
        GoodItem goodItem;

        public GoodItemHolder(@NonNull ViewDataBinding goodItemBinding){
            super(goodItemBinding.getRoot());
            this.goodItemBinding = goodItemBinding;

            goodItemBinding.getRoot().setOnClickListener(view -> {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((MainActivity)context).newStateOfFragments(SELECT_INFO, goodItem);
                }
                else {
                    Intent intent = new Intent(context, InfoGoodItem.class);
                    intent.putExtra("good", goodItem);
                    context.startActivity(intent);
                }
            });
        }
    }
}
