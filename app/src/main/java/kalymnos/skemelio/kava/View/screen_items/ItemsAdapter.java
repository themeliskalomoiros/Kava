package kalymnos.skemelio.kava.View.screen_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;
import kalymnos.skemelio.kava.R;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private List<Item> items;
    private Quantity[] quantities;
    private Context context;
    private ItemsScreenViewMvc.OnItemQuantityChangeListener onItemQuantityChangeListener;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    public void addItems(List<Item> items) {
        this.items = items;
    }

    public void addQuantities(Quantity[] quantities) {
        this.quantities = quantities;
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
        Quantity quantity = quantities[position];
        holder.bind(item.toString(), quantity.getQuantityText(context.getString(R.string.containers), context.getString(R.string.atoms)));
    }

    @Override
    public int getItemCount() {
        if (items != null) return items.size();
        return 0;
    }

    public void setOnItemQuantityChangeListener(ItemsScreenViewMvc.OnItemQuantityChangeListener listener) {
        onItemQuantityChangeListener = listener;
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
            initMinusButton(itemView);
            initPlusButton(itemView);
        }

        private void initMinusButton(@NonNull View itemView) {
            minus = itemView.findViewById(R.id.remove_button);
            minus.setOnClickListener(view -> {
                if (onItemQuantityChangeListener != null)
                    onItemQuantityChangeListener.onAtomRemoved(getAdapterPosition());
            });
            minus.setOnLongClickListener(view -> {
                if (onItemQuantityChangeListener != null)
                    onItemQuantityChangeListener.onContainerRemoved(getAdapterPosition());
                return true;
            });
        }

        private void initPlusButton(@NonNull View itemView) {
            plus = itemView.findViewById(R.id.add_button);
            plus.setOnClickListener(view -> {
                if (onItemQuantityChangeListener != null)
                    onItemQuantityChangeListener.onAtomAdded(getAdapterPosition());
            });
            plus.setOnLongClickListener(view -> {
                if (onItemQuantityChangeListener != null)
                    onItemQuantityChangeListener.onContainerAdded(getAdapterPosition());
                return true;
            });
        }

        void bind(String title, String subtitle) {
            this.title.setText(title);
            this.subtitle.setText(subtitle);
        }
    }
}
