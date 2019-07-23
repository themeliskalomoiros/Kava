package kalymnos.skemelio.kava.Model.persistance;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;

public interface QuantityRepo {
    boolean isCategorySet(int categoryId);

    void markCategoryAsSet(int categoryId);

    void markCategoryAsNonSet(int categoryId);

    void save(String[] items, Quantity[] quantities);

    void clear();

    Quantity[] getQuantitiesOf(List<Item> items);
}
