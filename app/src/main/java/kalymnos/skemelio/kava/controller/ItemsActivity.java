package kalymnos.skemelio.kava.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kalymnos.skemelio.kava.persistance.QuantityRepo;
import kalymnos.skemelio.kava.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.view.screen_items.ItemsScreenViewMvc;
import kalymnos.skemelio.kava.view.screen_items.ItemsScreenViewMvcImpl;

public class ItemsActivity extends AppCompatActivity
        implements ItemsScreenViewMvc.OnItemQuantityChangeListener {

    private Category category;
    private QuantityRepo repo;
    private Quantity[] quantities;
    private ItemsScreenViewMvc viewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRepo();
        initCategory();
        initQuantities(savedInstanceState);
        initViewMvc();
        bindUi();
    }

    private void initRepo() {
        SharedPreferences categoryPrefs = getSharedPreferences(
                Category.class.getSimpleName(),
                MODE_PRIVATE);
        SharedPreferences itemPrefs = getSharedPreferences(
                Item.class.getSimpleName(),
                MODE_PRIVATE);
        repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
    }

    private void initCategory() {
        Bundle data = getIntent().getExtras();
        category = data.getParcelable(Category.class.getSimpleName());
    }

    private void initQuantities(Bundle b) {
        if (b != null && b.containsKey(Quantity.class.getSimpleName())) {
            quantities = (Quantity[]) b.getSerializable(Quantity.class.getSimpleName());
        } else {
            quantities = repo.getQuantitiesOf(category.getItems());
        }
    }

    private void initViewMvc() {
        viewMvc = new ItemsScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnItemQuantityChangeListener(this);
    }

    private void bindUi() {
        setContentView(viewMvc.getRootView());
        getSupportActionBar().setTitle(getString(R.string.note) + String.format(" \"%s\"", category.title));
        viewMvc.bindItems(category.getItems());
        viewMvc.bindQuantities(quantities);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (quantities != null && quantities.length > 0) {
            outState.putSerializable(Quantity.class.getSimpleName(), quantities);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            resetQuantities();
            viewMvc.bindQuantities(quantities);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetQuantities() {
        for (int i = 0; i < quantities.length; i++) {
            Quantity q = quantities[i];
            q.reset();
        }
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

    private String[] getKeys() {
        String[] keys = new String[category.getItems().size()];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = category.getItems().get(i).toString();
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
