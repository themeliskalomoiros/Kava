package kalymnos.skemelio.kava.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;
import kalymnos.skemelio.kava.persistance.QuantityRepo;
import kalymnos.skemelio.kava.persistance.QuantityRepoImpl;
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
        setupLayout();
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
        Bundle b = getIntent().getExtras();
        category = b.getParcelable(Category.class.getSimpleName());
    }

    private void initQuantities(Bundle b) {
        boolean quantitiesExist = b != null && b.containsKey(Quantity.class.getSimpleName());
        if (quantitiesExist) {
            quantities = (Quantity[]) b.getSerializable(Quantity.class.getSimpleName());
        } else {
            quantities = repo.getQuantitiesOf(category.getItems());
        }
    }

    private void initViewMvc() {
        viewMvc = new ItemsScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnItemQuantityChangeListener(this);
    }

    private void setupLayout() {
        setContentView(viewMvc.getRootView());
        String title = getString(R.string.note) + String.format(" \"%s\"", category.title);
        getSupportActionBar().setTitle(title);
        viewMvc.bindItems(category.getItems());
        viewMvc.bindQuantities(quantities);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean quantitiesExist = quantities != null && quantities.length > 0;
        if (quantitiesExist)
            outState.putSerializable(Quantity.class.getSimpleName(), quantities);
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
        quantities[position].addAtom();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onContainerAdded(int position) {
        quantities[position].addContainer();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onAtomRemoved(int position) {
        quantities[position].removeAtom();
        viewMvc.bindQuantities(quantities);
    }

    @Override
    public void onContainerRemoved(int position) {
        quantities[position].removeContainer();
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
        for (int i = 0; i < keys.length; i++)
            keys[i] = category.getItems().get(i).toString();

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
            if (quantities[i].isEmpty()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
