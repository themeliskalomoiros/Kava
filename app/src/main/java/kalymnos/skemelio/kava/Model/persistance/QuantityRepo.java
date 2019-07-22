package kalymnos.skemelio.kava.Model.persistance;

import kalymnos.skemelio.kava.Model.pojos.Quantity;

public interface QuantityRepo {
    boolean isCategorySet(int categoryId);

    void markCategoryAsSet(int categoryId);

    void unMarkCategoryAsSet(int categoryId);

    void save(int[] itemsHashes, Quantity[] quantities);

    void resetAll();

    void resetAllOf(int itemHashcode);
}
