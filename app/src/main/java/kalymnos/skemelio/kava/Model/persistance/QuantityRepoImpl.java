package kalymnos.skemelio.kava.Model.persistance;

import android.content.SharedPreferences;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;

public class QuantityRepoImpl implements QuantityRepo {
    private static QuantityRepoImpl INSTANCE = null;
    private SharedPreferences categoryPrefs;
    private SharedPreferences itemsPrefs;

    private QuantityRepoImpl(SharedPreferences categoryPrefs, SharedPreferences itemsPrefs) {
        this.categoryPrefs = categoryPrefs;
        this.itemsPrefs = itemsPrefs;
    }

    public static QuantityRepo getInstance(SharedPreferences categoryPrefs, SharedPreferences itemsPrefs) {
        if (INSTANCE == null) {
            INSTANCE = new QuantityRepoImpl(categoryPrefs, itemsPrefs);
        }
        return INSTANCE;
    }

    @Override
    public boolean isCategorySet(int categoryId) {
        return categoryPrefs.getBoolean(String.valueOf(categoryId), false);
    }

    @Override
    public void markCategoryAsSet(int categoryId) {
        categoryPrefs.edit().putBoolean(String.valueOf(categoryId), true).apply();
    }

    @Override
    public void markCategoryAsNonSet(int categoryId) {
        categoryPrefs.edit().putBoolean(String.valueOf(categoryId), false).apply();
    }

    @Override
    public void save(String[] items, Quantity[] quantities) {
        SharedPreferences.Editor editor = itemsPrefs.edit();
        putValuesToPrefs(items, quantities, editor);
        editor.apply();
    }

    private void putValuesToPrefs(String[] items, Quantity[] quantities, SharedPreferences.Editor editor) {
        for (int i = 0; i < items.length; i++) {
            int atom = quantities[i].getAtom();
            int container = quantities[i].getContainer();
            editor.putString(items[i], String.format("%d,%d", atom, container));
        }
    }

    @Override
    public void clear() {
        categoryPrefs.edit().clear().apply();
        itemsPrefs.edit().clear().apply();
    }

    @Override
    public Quantity[] getQuantitiesOf(List<Item> items) {
        Quantity[] quantities = new Quantity[items.size()];
        for (int i = 0; i < items.size(); i++) {
            String key = items.get(i).toString();
            String values[] = itemsPrefs.getString(key, "0,0").split(",");
            quantities[i] = new Quantity(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        }
        return quantities;
    }
}
