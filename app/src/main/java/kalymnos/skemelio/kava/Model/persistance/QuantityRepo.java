package kalymnos.skemelio.kava.Model.persistance;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;

public interface QuantityRepo {
    boolean isCategorySet(int categoryId);

    void markCategoryAsSet(int categoryId);

    void markCategoryAsNonSet(int categoryId);

    void save(String[] items, Quantity[] quantities);

    void clear();

    boolean isEmpty(List<Category> categories);

    boolean isEmpty(Category category);

    boolean isEmpty(Item item);

    Quantity[] getQuantitiesOf(List<Item> items);

    Quantity getQuantityOf(Item item);
}
