package kalymnos.skemelio.kava.Model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    public static final String TAG = "Category";

    public final int id;
    public final String title;
    private final List<Item> items;

    public Category(int id, String title, List<Item> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }

    public Item getItemAt(int position) {
        return items.get(position);
    }

    public List<Item> getItemList() {
        return items;
    }
}
