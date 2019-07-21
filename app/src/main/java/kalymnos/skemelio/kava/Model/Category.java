package kalymnos.skemelio.kava.Model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    public final int id;
    public final String title;
    public final List<Item> items;

    public Category(int id, String title, List<Item> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }
}
