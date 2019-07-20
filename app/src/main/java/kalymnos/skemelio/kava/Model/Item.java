package kalymnos.skemelio.kava.Model;

public abstract class Item {
    public final int categoryId;
    public final String picturePath;
    public final String title;

    public Item(int categoryId, String picturePath, String title) {
        this.categoryId = categoryId;
        this.picturePath = picturePath;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        Item item = (Item) obj;
        if (item == null) return false;
        return hasSameValuesWith(item);
    }

    protected boolean hasSameValuesWith(Item item) {
        return categoryId == item.categoryId && picturePath == item.picturePath && title == item.title;
    }
}
