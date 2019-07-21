package kalymnos.skemelio.kava.View.screen_items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import kalymnos.skemelio.kava.Model.Item;
import kalymnos.skemelio.kava.R;

public class ItemsScreenViewMvcImpl implements ItemsScreenViewMvc {
    private View root;
    private FloatingActionButton save;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;

    public ItemsScreenViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        root = inflater.inflate(R.layout.screen_items,parent,false);
        save = root.findViewById(R.id.save);
        recyclerView = root.findViewById(R.id.items);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {

    }

    @Override
    public void setOnSaveClickListener(OnSaveClickListener listener) {

    }

    @Override
    public void bindItems(List<Item> items) {

    }

    @Override
    public View getRootView() {
        return null;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
