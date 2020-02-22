package kalymnos.skemelio.kava.Model.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Category implements Parcelable {

    public final int id;
    public final String title;
    private final List<Item> items;

    public Category(int id, String title, List<Item> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }

    //Parcelable implementation
    protected Category(Parcel in) {
        id = in.readInt();
        title = in.readString();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    @Override
    public String toString() {
        return title;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public List<Item> getItemList() {
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeTypedList(items);
    }
}
