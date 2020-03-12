package kalymnos.skemelio.kava.view.screen_checkout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;
import kalymnos.skemelio.kava.persistance.QuantityRepo;
import kalymnos.skemelio.kava.persistance.QuantityRepoImpl;

public class CheckoutItemAdapter extends RecyclerView.Adapter<CheckoutItemAdapter.ItemHolder> {

    private List<Category> categories;
    private QuantityRepo quantities;
    private Context context;

    public CheckoutItemAdapter(Context c) {
        this.context = c;
        quantities = QuantityRepoImpl.createFrom(c);
    }

    public void add(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutItemAdapter.ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Category cat = categories.get(position);
        if (quantities.isEmpty(cat))
            return;
        holder.bindCategory(cat.title);

        Resources res = context.getResources();
        StringBuilder builder = new StringBuilder();
        String quantitiesText = "";

        for (Item item : cat.getItems()) {
            if (quantities.isEmpty(item))
                continue;

            Quantity q = quantities.getQuantityOf(item);
            int atoms = q.getAtom();
            int containers = q.getContainer();

            if (atoms > 0 && containers > 0) {
                quantitiesText = String.format("%s, %s, %s: %d, %s: %d",
                        item.title,
                        item.volume,
                        res.getString(R.string.atoms),
                        atoms,
                        res.getString(R.string.containers),
                        containers);
            } else if (atoms > 0 && containers == 0) {
                quantitiesText = String.format("%s, %s, %s: %d",
                        item.title,
                        item.volume,
                        res.getString(R.string.atoms),
                        atoms);
            } else if (atoms == 0 && containers > 0) {
                quantitiesText = String.format("%s, %s, %s: %d",
                        item.title,
                        item.volume,
                        res.getString(R.string.containers),
                        containers);
            }
            builder.append(quantitiesText + "\n");
        }
        holder.bindItems(builder.toString());
    }

    @Override
    public int getItemCount() {
        if (categories != null) return categories.size();
        return 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private TextView category;
        private TextView items;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            items = itemView.findViewById(R.id.items);
        }

        public void bindCategory(String category) {
            this.category.setText(category);
        }

        public void bindItems(String items) {
            this.items.setText(items);
        }
    }
}
