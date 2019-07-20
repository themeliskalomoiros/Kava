package kalymnos.skemelio.kava.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.Model.Category;
import kalymnos.skemelio.kava.R;

public class MainScreenViewMvcImpl implements MainScreenViewMvc {

    private View root;
    private RecyclerView categories;
    private CategoriesAdapter adapter;

    public MainScreenViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_main, container, false);
        adapter = new CategoriesAdapter(root.getContext());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        categories = root.findViewById(R.id.categories);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        categories.setAdapter(adapter);
        categories.setLayoutManager(layoutManager);
        categories.setHasFixedSize(true);
    }

    @Override
    public void bind(List<Category> categories) {
        adapter.add(categories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        if (listener != null)
            adapter.setOnCategoryClickListener(listener);
    }

    @Override
    public View getRootView() {
        return root;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
