package kalymnos.skemelio.kava.view.screen_checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.model.Category;

public class CheckoutScreenViewMvcImpl implements CheckoutScreenViewMvc {
    private View root, titleLine;
    private FloatingActionButton share;
    private TextView title;
    private RecyclerView items;
    private CheckoutItemAdapter adapter;

    public CheckoutScreenViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        root = inflater.inflate(R.layout.screen_checkout, parent, false);
        share = root.findViewById(R.id.share);
        title = root.findViewById(R.id.title);
        titleLine = root.findViewById(R.id.title_line);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        items = root.findViewById(R.id.items);
        adapter = new CheckoutItemAdapter(root.getContext());
        items.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(root.getContext());
        items.setLayoutManager(lm);
        items.setHasFixedSize(true);
    }

    @Override
    public void setOnShareClickListener(OnShareClickListener listener) {
        if (listener != null)
            share.setOnClickListener(v -> listener.onShareClick());
    }

    @Override
    public void bind(List<Category> categories) {
        items.setItemViewCacheSize(categories.size());
        adapter.add(categories);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bindTitle(String text) {
        title.setText(text);
    }

    @Override
    public String getTitle() {
        return title.getText().toString();
    }

    @Override
    public void showTitle() {
        title.setVisibility(View.VISIBLE);
        titleLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTitle() {
        title.setVisibility(View.GONE);
        titleLine.setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return root;
    }
}
