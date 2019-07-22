package kalymnos.skemelio.kava.Model;

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

    public void addAtom() {
        ++quantity.atom;
    }

    public void addContainer() {
        ++quantity.container;
    }

    public void removeAtom() {
        if (quantity.atom == 0) {
            return;
        }
        --quantity.atom;
    }

    public void removeContainer() {
        if (quantity.container == 0) {
            return;
        }
        --quantity.container;
    }

    public int getAtom() {
        return quantity.atom;
    }

    public int getContainer() {
        return quantity.container;
    }

    public String getQuantityText(String containersLabel, String atomsLabel) {
        return String.format("%s %d, %s %d", containersLabel, getContainer(), atomsLabel, getAtom());
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

    protected boolean hasSameValuesWith(Item item) {
        return categoryId == item.categoryId
                && picturePath.equals(item.picturePath)
                && title.equals(item.title)
                && volume.equals(item.volume);
    }

    private static class Quantity implements Serializable {
        int atom, container;

        Quantity(int atom, int container) {
            this.atom = atom;
            this.container = container;
        }
    }
}
