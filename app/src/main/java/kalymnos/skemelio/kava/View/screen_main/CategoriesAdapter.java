package kalymnos.skemelio.kava.View.screen_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {
    private Context context;
    private List<Category> categories;
    private MainScreenViewMvc.OnCategoryClickListener itemClickListener;

    public CategoriesAdapter(Context context) {
        this.context = context;
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
        String categoryName = categories.get(i).title;
        holder.bind(categoryName);
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

        private TextView categoryName;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> itemClickListener.onCategoryClick(getAdapterPosition()));
            categoryName = itemView.findViewById(R.id.title);
        }

        void bind(String categoryName) {
            this.categoryName.setText(categoryName);
        }
    }
}
