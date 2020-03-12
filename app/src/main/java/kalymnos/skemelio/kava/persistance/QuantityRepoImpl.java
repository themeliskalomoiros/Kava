package kalymnos.skemelio.kava.persistance;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;

// TODO: needs refactoring
public class QuantityRepoImpl implements QuantityRepo {
    private static QuantityRepoImpl INSTANCE = null;
    private SharedPreferences categoryPrefs;
    private SharedPreferences itemPrefs;

    private QuantityRepoImpl(SharedPreferences categories, SharedPreferences items) {
        categoryPrefs = categories;
        itemPrefs = items;
    }

    public static QuantityRepo createFrom(Context c) {
        if (INSTANCE == null) {
            SharedPreferences cp =
                    c.getSharedPreferences(Category.class.getSimpleName(), Context.MODE_PRIVATE);
            SharedPreferences ip =
                    c.getSharedPreferences(Item.class.getSimpleName(), Context.MODE_PRIVATE);
            INSTANCE = new QuantityRepoImpl(cp, ip);
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
        SharedPreferences.Editor editor = itemPrefs.edit();
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
        itemPrefs.edit().clear().apply();
    }

    @Override
    public boolean isEmpty(List<Category> categories) {
        List<Boolean> categoriesChecked = new ArrayList<>(categories.size());
        for (Category c : categories) {
            categoriesChecked.add(this.categoryPrefs.getBoolean(String.valueOf(c.id), false));
        }
        for (boolean checked : categoriesChecked) {
            if (checked) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty(Category category) {
        Quantity[] quantities = getQuantitiesOf(category.getItems());
        for (Quantity q : quantities) {
            if (!q.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty(Item item) {
        Quantity q = getQuantityOf(item);
        return q.isEmpty();
    }

    @Override
    public Quantity[] getQuantitiesOf(List<Item> items) {
        Quantity[] quantities = new Quantity[items.size()];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            quantities[i] = getQuantityOf(item);
        }
        return quantities;
    }

    @Override
    public Quantity getQuantityOf(Item item) {
        String atomContainer = itemPrefs.getString(item.toString(), "0,0");
        String[] values = atomContainer.split(",");
        int atom = Integer.parseInt(values[0]);
        int container = Integer.parseInt(values[1]);
        return new Quantity(atom, container);
    }
}
