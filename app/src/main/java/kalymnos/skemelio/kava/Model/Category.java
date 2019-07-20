package kalymnos.skemelio.kava.Model;

import java.util.HashMap;

public class Category {

    public final int id;
    public final String title;
    public final HashMap<Item, Quantity> items;

    public Category(int id, String title, HashMap<Item, Quantity> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }
}
