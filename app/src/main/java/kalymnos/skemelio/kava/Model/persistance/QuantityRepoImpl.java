package kalymnos.skemelio.kava.Model.persistance;

import android.content.SharedPreferences;

import kalymnos.skemelio.kava.Model.pojos.Quantity;

public class QuantityRepoImpl implements QuantityRepo {
    private SharedPreferences categoryPrefs;
    private SharedPreferences itemsPrefs;

    public QuantityRepoImpl(SharedPreferences categoryPrefs, SharedPreferences itemsPrefs) {
        this.categoryPrefs = categoryPrefs;
        this.itemsPrefs = itemsPrefs;
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
    public void save(int[] itemsHashes, Quantity[] quantities) {
        SharedPreferences.Editor editor = itemsPrefs.edit();
        putValuesToPrefs(itemsHashes, quantities, editor);
        editor.apply();
    }

    private void putValuesToPrefs(int[] itemsHashes, Quantity[] quantities, SharedPreferences.Editor editor) {
        for (int i = 0; i < itemsHashes.length; i++) {
            int hash = itemsHashes[i];
            int atom = quantities[i].getAtom();
            int container = quantities[i].getContainer();
            editor.putString(String.valueOf(hash), String.format("%d,%d", atom, container));
        }
    }

    @Override
    public void clear() {
        categoryPrefs.edit().clear().apply();
        itemsPrefs.edit().clear().apply();
    }

    @Override
    public void clearQuantityOf(int itemHashCode) {
        itemsPrefs.edit().putString(String.valueOf(itemHashCode), "0,0").apply();
    }
}
