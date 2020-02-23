package kalymnos.skemelio.kava.View.screen_main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.R;

public class MainScreenViewMvcImpl implements MainScreenViewMvc {

    private View root;
    private RecyclerView categories;
    private ProgressBar progressBar;
    private CategoriesAdapter adapter;

    public MainScreenViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.screen_main, container, false);
        progressBar = root.findViewById(R.id.progressBar);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        categories = root.findViewById(R.id.categories);
        adapter = new CategoriesAdapter(root.getContext());
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
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
