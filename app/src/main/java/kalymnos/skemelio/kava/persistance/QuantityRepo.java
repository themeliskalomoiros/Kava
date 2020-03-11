package kalymnos.skemelio.kava.persistance;

import java.util.List;

import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;

// TODO: needs refactoring
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
