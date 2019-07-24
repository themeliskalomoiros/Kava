package kalymnos.skemelio.kava.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import kalymnos.skemelio.kava.Model.persistance.QuantityRepo;
import kalymnos.skemelio.kava.Model.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.Model.pojos.Quantity;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.View.screen_items.ItemsScreenViewMvc;
import kalymnos.skemelio.kava.View.screen_items.ItemsScreenViewMvcImpl;

public class ItemsActivity extends AppCompatActivity
        implements ItemsScreenViewMvc.OnSaveClickListener,
        ItemsScreenViewMvc.OnItemQuantityChangeListener {

    private ItemsScreenViewMvc viewMvc;
    private Category category;
    private Quantity[] quantities;
    private QuantityRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategory();
        initQuantities();
        initViewMvc();
        bindUi();
    }

    private void initCategory() {
        Bundle data = getIntent().getExtras();
        category = (Category) data.getParcelable(Category.class.getSimpleName());
    }

    private void initQuantities() {
        SharedPreferences categoryPrefs = getSharedPreferences(Category.class.getSimpleName(), MODE_PRIVATE);
        SharedPreferences itemPrefs = getSharedPreferences(Item.class.getSimpleName(), MODE_PRIVATE);
        repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
        quantities = repo.getQuantitiesOf(category.getItemList());
    }

    private void initViewMvc() {
        viewMvc = new ItemsScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnItemQuantityChangeListener(this);
        viewMvc.setOnSaveClickListener(this);
    }

    private void bindUi() {
        setContentView(viewMvc.getRootView());
        getSupportActionBar().setTitle(category.title + " (" + category.getItemList().size() + ")");
        viewMvc.bindTitle(getString(R.string.take_notes_on_items) + String.format(" \"%s\"", category.title));
        viewMvc.bindItems(category.getItemList());
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            resetAllQuantitiesOf(category.id);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetAllQuantitiesOf(int categoryId) {
        for (int i = 0; i < quantities.length; i++) {
            Quantity q = quantities[i];
            q.reset();
        }
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onAtomAdded(int position) {
        Quantity q = quantities[position];
        q.addAtom();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onContainerAdded(int position) {
        Quantity q = quantities[position];
        q.addContainer();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onAtomRemoved(int position) {
        Quantity q = quantities[position];
        q.removeAtom();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onContainerRemoved(int position) {
        Quantity q = quantities[position];
        q.removeContainer();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onBackPressed() {
        repo.save(getKeys(), quantities);
        setCategoryItemCheckVisibility();
        finish();
    }

    @Override
    public void onSaveClick() {
        repo.save(getKeys(), quantities);
        setCategoryItemCheckVisibility();
        if (allQuantitiesAreEmpty()) {
            Snackbar.make(viewMvc.getRootView(), R.string.nothing_to_save, Snackbar.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    private String[] getKeys() {
        String[] keys = new String[category.getItemList().size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = category.getItemList().get(i).toString();
        }
        return keys;
    }

    private void setCategoryItemCheckVisibility() {
        if (allQuantitiesAreEmpty()) {
            repo.markCategoryAsNonSet(category.id);

        } else {
            repo.markCategoryAsSet(category.id);
        }
    }

    private boolean allQuantitiesAreEmpty() {
        for (int i = 0; i < quantities.length; i++) {
            Quantity q = quantities[i];
            if (q.isEmpty()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
