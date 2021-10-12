package bstu.fit.baa.goodsfinder;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import bstu.fit.baa.goodsfinder.entity.GoodItem;

public class GoodItemDetailFragment extends Fragment {

    private Context context;
    private View view;
    private GoodItem goodItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        if (savedInstanceState != null)
            goodItem = (GoodItem) savedInstanceState.getSerializable("good");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (goodItem != null)
            outState.putSerializable("good", goodItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_good_item_detail, container, false);

        if (goodItem != null)
            applySelectedGoodItem();

        return view;
    }

    void setSelectedGoodItem(GoodItem goodItem) {
        this.goodItem = goodItem;
    }

    private void applySelectedGoodItem() {

        TextView idField = view.findViewById(R.id.good_id);
        TextView nameField = view.findViewById(R.id.good_name);
        TextView descriptionField = view.findViewById(R.id.good_description);
        TextView findPlaceField = view.findViewById(R.id.good_find_place);
        ImageView imageField = view.findViewById(R.id.good_image);
        TextView findDateField = view.findViewById(R.id.good_find_date);
        TextView finderField = view.findViewById(R.id.good_finder);
        TextView receiptPlaceField = view.findViewById(R.id.good_receipt_place);

        idField.setText(goodItem.getId().toString());
        nameField.setText(goodItem.getName());
        descriptionField.setText(goodItem.getDescription());
        findPlaceField.setText(goodItem.getFindPlace());
        imageField.setImageBitmap(goodItem.getImageAsBitmap());
        findDateField.setText(goodItem.getFindDateInFormat());
        finderField.setText(goodItem.getFinder());
        receiptPlaceField.setText(goodItem.getReceiptPlace());
    }
}