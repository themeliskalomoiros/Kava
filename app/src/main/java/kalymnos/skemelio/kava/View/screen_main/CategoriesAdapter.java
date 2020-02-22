package kalymnos.skemelio.kava.View.screen_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.Model.persistance.QuantityRepo;
import kalymnos.skemelio.kava.Model.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {
    private Context context;
    private List<Category> categories;
    private MainScreenViewMvc.OnCategoryClickListener itemClickListener;
    private QuantityRepo repo;

    public CategoriesAdapter(Context context) {
        this.context = context;
        SharedPreferences categoryPrefs = context.getSharedPreferences(Category.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences itemPrefs = context.getSharedPreferences(Item.class.getSimpleName(), Context.MODE_PRIVATE);
        repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
    }

    public void add(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int i) {
        String name = categories.get(i).title;
        int id = categories.get(i).id;
        holder.bind(name, id);
    }

    @Override
    public int getItemCount() {
        if (categories != null) return categories.size();
        return 0;
    }

    public void setOnCategoryClickListener(MainScreenViewMvc.OnCategoryClickListener listener) {
        itemClickListener = listener;
    }

    class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView checked;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> itemClickListener.onCategoryClick(getAdapterPosition()));
            name = itemView.findViewById(R.id.title);
            checked = itemView.findViewById(R.id.checked);
        }

        void bind(String name, int id) {
            this.name.setText(name);
            if (repo.isCategorySet(id)) {
                checked.setVisibility(View.VISIBLE);
            } else {
                checked.setVisibility(View.INVISIBLE);
            }
        }
    }
}
