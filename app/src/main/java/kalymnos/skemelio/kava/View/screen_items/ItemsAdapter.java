package kalymnos.skemelio.kava.View.screen_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kalymnos.skemelio.kava.Model.Item;
import kalymnos.skemelio.kava.R;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private List<Item> items;
    private Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    public void addItems(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_preview, parent, false);
        return new ItemsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        Item item = items.get(position);
        String title = item.title;
        String subtitle = item.getQuantityText(context.getString(R.string.containers), context.getString(R.string.atoms));
        holder.bind(title, subtitle);
    }

    @Override
    public int getItemCount() {
        if (items != null) return items.size();
        return 0;
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle;
        private ImageButton plus, minus;

        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(@NonNull View itemView) {
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
            plus = itemView.findViewById(R.id.add_button);
            minus = itemView.findViewById(R.id.remove_button);
        }

        void bind(String title, String subtitle) {
            this.title.setText(title);
            this.subtitle.setText(subtitle);
        }
    }
}
