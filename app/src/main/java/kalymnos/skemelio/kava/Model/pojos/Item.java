package kalymnos.skemelio.kava.Model.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable, Comparable<Item> {
    public final int categoryId;
    public final String picturePath;
    public final String title;
    public final String volume;

    public Item(int categoryId, String picturePath, String title, String volume) {
        this.categoryId = categoryId;
        this.picturePath = picturePath;
        this.title = title;
        this.volume = volume;
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
        return categoryId + p + t + v;
    }

    @Override
    public int compareTo(Item other) {
        if (categoryId < other.categoryId) {
            return -1;
        } else if (categoryId > other.categoryId) {
            return 1;
        }
        return 0;
    }

    // Parcelable implementation
    protected Item(Parcel in) {
        categoryId = in.readInt();
        picturePath = in.readString();
        title = in.readString();
        volume = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(categoryId);
        parcel.writeString(picturePath);
        parcel.writeString(title);
        parcel.writeString(volume);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
