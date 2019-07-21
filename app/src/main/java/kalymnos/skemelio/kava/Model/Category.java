package kalymnos.skemelio.kava.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Category{

    public final int id;
    public final String title;
    public final List<Item> items;

    public Category(int id, String title, List<Item> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }
}
