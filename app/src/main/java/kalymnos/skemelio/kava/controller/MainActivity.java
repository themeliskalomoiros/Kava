package kalymnos.skemelio.kava.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.model.Categories;
import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.persistance.QuantityRepo;
import kalymnos.skemelio.kava.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.util.JsonDataRetriever;
import kalymnos.skemelio.kava.view.screen_main.MainScreenViewMvc;
import kalymnos.skemelio.kava.view.screen_main.MainScreenViewMvcImpl;

public class MainActivity extends AppCompatActivity
        implements MainScreenViewMvc.OnCategoryClickListener,
        LoaderManager.LoaderCallbacks<List<Category>> {

    private static final int LOADER_ID = 1212;

    private QuantityRepo repo;
    private MainScreenViewMvc viewMvc;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRepo();
        setupLayout();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    private void initRepo() {
        SharedPreferences categoryPrefs =
                getSharedPreferences(Category.class.getSimpleName(), MODE_PRIVATE);
        SharedPreferences itemPrefs =
                getSharedPreferences(Item.class.getSimpleName(), MODE_PRIVATE);
        repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
    }

    private void setupLayout() {
        viewMvc = new MainScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnCategoryClickListener(this);
        setContentView(viewMvc.getRootView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_checkout:
                handleCheckout();
                return true;
            case R.id.action_clear:
                resetAllQuantities();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleCheckout() {
        if (repo.isEmpty(categories)) {
            Snackbar.make(
                    viewMvc.getRootView(),
                    getString(R.string.kava_not_ready),
                    Snackbar.LENGTH_SHORT).show();
        } else {
            startActivity(getCategoriesIntent());
        }
    }

    private Intent getCategoriesIntent() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        Parcelable[] parcelables = new Parcelable[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            parcelables[i] = c;
        }
        intent.putExtra(Category.class.getSimpleName(), parcelables);
        return intent;
    }

    private void resetAllQuantities() {
        repo.clear();
        viewMvc.bind(categories);
    }

    @Override
    public void onCategoryClick(int position) {
        Category category = categories.get(position);
        startActivity(getIntentOf(category));
    }

    private Intent getIntentOf(Category c) {
        Bundle extras = new Bundle();
        extras.putParcelable(Category.class.getSimpleName(), c);
        Intent intent = new Intent(this, ItemsActivity.class);
        intent.putExtras(extras);
        return intent;
    }

    @NonNull
    @Override
    public Loader<List<Category>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Category>>(this) {

            @Override
            protected void onStartLoading() {
                viewMvc.showProgressBar();
                forceLoad();
            }

            @Nullable
            @Override
            public List<Category> loadInBackground() {
                return getCategoriesOf("data.json");
            }

            private List<Category> getCategoriesOf(String file) {
                try (InputStream in = getContext().getAssets().open(file)) {
                    String json = JsonDataRetriever.rawJson(in);
                    return Categories.from(new JSONObject(json));
                } catch (JSONException | IOException e) {
                    return new ArrayList<>(0);
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Category>> loader, List<Category> data) {
        categories = data;
        viewMvc.hideProgressBar();
        viewMvc.bind(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Category>> loader) {
    }
}
