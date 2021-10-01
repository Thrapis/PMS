package bstu.fit.baa.goodsfinder.ui;

import android.view.ContextMenu;

import java.util.UUID;

public class GoodItemContextMenuInfo implements ContextMenu.ContextMenuInfo {
    public UUID id;

    public GoodItemContextMenuInfo(UUID id) {
        this.id = id;
    }
}
