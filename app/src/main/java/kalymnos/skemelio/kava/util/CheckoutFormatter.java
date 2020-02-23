package kalymnos.skemelio.kava.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kalymnos.skemelio.kava.Model.persistance.QuantityRepo;
import kalymnos.skemelio.kava.Model.pojos.Categories;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;

public class CheckoutFormatter {
    private final String atomLabel;
    private final String containerLabel;
    private final QuantityRepo repo;
    private final Category[] categories;

    public CheckoutFormatter(
            QuantityRepo repo,
            Category[] categories,
            String atomLabel,
            String containerLabel) {
        this.repo = repo;
        this.categories = categories;
        this.atomLabel = atomLabel;
        this.containerLabel = containerLabel;
    }

    public String formatKava() {
        StringBuilder builder = new StringBuilder();
        List<Item> allItems = Categories.allItemsOf(categories);
        List<Item> itemsToPrint = new ArrayList<>();
        List<Quantity> quantitiesToPrint = new ArrayList<>();
        for (Item i : allItems) {
            Quantity q = repo.getQuantityOf(i);
            boolean isValidQuantity = q != null && (q.getAtom() > 0 || q.getContainer() > 0);
            if (isValidQuantity) {
                itemsToPrint.add(i);
                quantitiesToPrint.add(q);
            }
        }

        Collections.sort(itemsToPrint);

        String firstCategory = getCategoryTitleFrom(itemsToPrint.get(0).categoryId);
        String firstItem = String.format(" *%s, %s\n", itemsToPrint.get(0).toString(), quantitiesToPrint.get(0).getTextWithoutEmptyValues(containerLabel, atomLabel));
        builder.append(firstCategory + "\n" + firstItem);
        for (int i = 1; i < itemsToPrint.size(); i++) {
            Item current = itemsToPrint.get(i);
            Item previous = itemsToPrint.get(i - 1);
            if (current.categoryId != previous.categoryId) {
                builder.append("\n" + getCategoryTitleFrom(current.categoryId) + "\n");
            }
            builder.append(String.format(" *%s, %s\n", itemsToPrint.get(i).toString(), quantitiesToPrint.get(i).getTextWithoutEmptyValues(containerLabel, atomLabel)));
        }


        return builder.toString();
    }

    private String getCategoryTitleFrom(int categoryId) {
        for (Category c : categories) {
            if (c.id == categoryId) {
                return c.title;
            }
        }
        return null;
    }

    public String createTextToShare(String title, String date, String kava) {
        StringBuilder builder = new StringBuilder();
        builder.append(title + "\n");
        builder.append("(" + date + ")" + "\n\n");
        builder.append(kava);
        return builder.toString();
    }
}
