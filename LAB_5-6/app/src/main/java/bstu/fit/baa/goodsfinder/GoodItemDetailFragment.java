package bstu.fit.baa.goodsfinder;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import bstu.fit.baa.goodsfinder.entity.GoodItem;
import bstu.fit.baa.goodsfinder.util.GoodItemsContainer;

public class GoodItemDetailFragment extends Fragment {

    private Context context;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_good_item_detail, container, false);

        List<GoodItem> list = GoodItemsContainer.getGoodItemsListByPattern();
        GoodItem selected = GoodItemsContainer.peekSelectedGoodItem();
        if (selected != null && list.contains(selected)) {
            setSelectedGoodItem(selected);
        }

        return view;
    }

    void setSelectedGoodItem(GoodItem goodItem) {

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

    void clearSelectedGoodItem() {

        TextView idField = view.findViewById(R.id.good_id);
        TextView nameField = view.findViewById(R.id.good_name);
        TextView descriptionField = view.findViewById(R.id.good_description);
        TextView findPlaceField = view.findViewById(R.id.good_find_place);
        ImageView imageField = view.findViewById(R.id.good_image);
        TextView findDateField = view.findViewById(R.id.good_find_date);
        TextView finderField = view.findViewById(R.id.good_finder);
        TextView receiptPlaceField = view.findViewById(R.id.good_receipt_place);

        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        findPlaceField.setText("");
        imageField.setImageBitmap(null);
        findDateField.setText("");
        finderField.setText("");
        receiptPlaceField.setText("");
    }
}