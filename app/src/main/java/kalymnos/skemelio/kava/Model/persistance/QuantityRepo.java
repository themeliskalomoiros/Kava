package kalymnos.skemelio.kava.Model.persistance;

import kalymnos.skemelio.kava.Model.pojos.Quantity;

public interface QuantityRepo {
    boolean isCategorySet(int categoryId);

    void markCategoryAsSet(int categoryId);

    void markCategoryAsNonSet(int categoryId);

    void save(int[] itemsHashes, Quantity[] quantities);

    void clear();

    void clearQuantityOf(int itemHashcode);
}
