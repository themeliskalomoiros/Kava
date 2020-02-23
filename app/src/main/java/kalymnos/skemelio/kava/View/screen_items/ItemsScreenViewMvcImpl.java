package kalymnos.skemelio.kava.View.screen_items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;
import kalymnos.skemelio.kava.R;

public class ItemsScreenViewMvcImpl implements ItemsScreenViewMvc {
    private View root;
    private TextView title;
    private FloatingActionButton save;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;

    public ItemsScreenViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        root = inflater.inflate(R.layout.screen_items, parent, false);
        title = root.findViewById(R.id.title);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView = root.findViewById(R.id.items);
        adapter = new ItemsAdapter(root.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setOnItemQuantityChangeListener(OnItemQuantityChangeListener listener) {
        adapter.setOnItemQuantityChangeListener(listener);
    }

    @Override
    public void bindItems(List<Item> items) {
        adapter.addItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bindQuantities(Quantity[] quantities) {
        adapter.addQuantities(quantities);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View getRootView() {
        return root;
    }
}
