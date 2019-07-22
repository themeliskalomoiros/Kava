package kalymnos.skemelio.kava.Model.persistance;

import kalymnos.skemelio.kava.Model.pojos.Quantity;

public interface QuantityRepo {
    String TAG = "QuantityRepo";

    boolean isCategorySet(int categoryId);

    void markCategoryAsSet(int categoryId);

    void unMarkCategoryAsSet(int categoryId);

    void save(int categoryId, int[] itemsHashes, Quantity[] quantities);

    void resetAll();

    void resetAllOf(int itemHashcode);
}
