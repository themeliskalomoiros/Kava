package kalymnos.skemelio.kava.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Category implements Parcelable {

    public final int id;
    public final String title;
    public final HashMap<Item, Quantity> items;

    public Category(int id, String title, HashMap<Item, Quantity> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        title = in.readString();
        items = new HashMap<>();
        in.readHashMap(getClass().getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
    }
}
