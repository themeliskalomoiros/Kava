package kalymnos.skemelio.kava.Model.pojos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Categories {
    private Categories() {
    }

    public static List<Category> from(JSONObject json) {
        List<Item> allItems = readItemsFrom(json.optJSONArray("items"));
        JSONArray categoryArray = json.optJSONArray("categories");
        List<Category> categories = createCategoriesFrom(allItems, categoryArray);
        return categories;
    }

    private static List<Item> readItemsFrom(JSONArray array) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.optJSONObject(i);
            Item item = getItemFrom(obj);
            items.add(item);
        }
        return items;
    }

    private static Item getItemFrom(JSONObject obj) {
        int id = obj.optInt("categoryId");
        String title = obj.optString("title");
        String picture = obj.optString("picturePath");
        String volume = obj.optString("volume");
        return new Item(id, picture, title, volume);
    }

    private static List<Category> createCategoriesFrom(List<Item> allItems, JSONArray array) {
        List<Category> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Category cat = inflateCategory(allItems, array, i);
            list.add(cat);
        }
        return list;
    }

    private static Category inflateCategory(List<Item> allItems, JSONArray array, int i) {
        JSONObject obj = array.optJSONObject(i);
        int id = obj.optInt("id");
        String title = obj.optString("title");
        List<Item> items = new ArrayList<>();
        for (Item item : allItems) {
            if (item.categoryId == id)
                items.add(item);
        }
        return new Category(id, title, items);
    }

    public static List<Item> allItemsOf(Category[] categories){
        List<Item> items = new ArrayList<>();
        for (Category c : categories){
            items.addAll(c.getItemList());
        }
        return items;
    }
}
