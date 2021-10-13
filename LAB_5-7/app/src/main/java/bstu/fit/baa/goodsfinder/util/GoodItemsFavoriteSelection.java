package bstu.fit.baa.goodsfinder.util;

public enum GoodItemsFavoriteSelection {
    ALL,
    FAVORITES,
    NO_FAVORITES;

    public boolean matches(boolean favorite) {
        if (this == ALL)
            return true;
        else if (this == FAVORITES && favorite)
            return true;
        else if (this == NO_FAVORITES && !favorite)
            return true;
        else
            return false;
    }

    public int[] getFavoriteStatements() {
        switch (this) {
            case FAVORITES: return new int[] {1};
            case NO_FAVORITES: return new int[] {0};
            default: return new int[] {0, 1};
        }
    }
}
