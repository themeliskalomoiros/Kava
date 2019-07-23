package kalymnos.skemelio.kava.Model.pojos;

import android.util.Log;

import java.io.Serializable;

public class Item implements Serializable {
    public final int categoryId;
    public final String picturePath;
    public final String title;
    public final String volume;
    private final Quantity quantity;

    public Item(int categoryId, String picturePath, String title, String volume) {
        this.categoryId = categoryId;
        this.picturePath = picturePath;
        this.title = title;
        this.volume = volume;
        quantity = new Quantity(0, 0);
    }

    @Override
    public String toString() {
        return title + ", " + volume;
    }

    @Override
    public boolean equals(Object obj) {
        Item otherItem = (Item) obj;
        if (otherItem == null) return false;
        return hasSameValuesWith(otherItem);
    }

    private boolean hasSameValuesWith(Item item) {
        return categoryId == item.categoryId
                && picturePath.equals(item.picturePath)
                && title.equals(item.title)
                && volume.equals(item.volume);
    }

    @Override
    public int hashCode() {
        int p = picturePath != null ? picturePath.hashCode() : 0;
        int t = title.hashCode();
        int v = volume.hashCode();

        int hash = categoryId+p+t+v;

        Log.d(Item.class.getSimpleName(), "CategoryId is " + categoryId);
        Log.d(Item.class.getSimpleName(), "Picturepath "+picturePath+" hash is " + p);
        Log.d(Item.class.getSimpleName(), title + " hash is " + title.hashCode());
        Log.d(Item.class.getSimpleName(), volume + " hash is " + volume.hashCode());
        Log.d(Item.class.getSimpleName(), title + "," + volume+" hash is " + hash);
        return hash;
    }
}
