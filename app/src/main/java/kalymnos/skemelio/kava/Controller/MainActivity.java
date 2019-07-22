package kalymnos.skemelio.kava.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Categories;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.View.screen_main.MainScreenViewMvc;
import kalymnos.skemelio.kava.View.screen_main.MainScreenViewMvcImpl;

public class MainActivity extends AppCompatActivity
        implements MainScreenViewMvc.OnCategoryClickListener,
        LoaderManager.LoaderCallbacks<List<Category>> {

    private static final int LOADER_ID = 1212;

    private MainScreenViewMvc viewMvc;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = new MainScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnCategoryClickListener(this);
        setContentView(viewMvc.getRootView());
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_note) {
            resetAllQuantities();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetAllQuantities() {
        //TODO implement
    }

    @Override
    public void onCategoryClick(int position) {
        Category category = categories.get(position);
        Intent intent = getIntentWith(category);
        startActivity(intent);
    }

    private Intent getIntentWith(Category c) {
        Bundle extras = new Bundle();
        extras.putSerializable(Category.TAG, c);
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
                String rawJson = getDataFromAssets("data.json");
                try {
                    return Categories.from(new JSONObject(rawJson));
                } catch (JSONException e) {
                    return null;
                }
            }

            private String getDataFromAssets(String fileName) {
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(getContext().getAssets().open(fileName)));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                } catch (IOException e) {
                    Log.d(MainActivity.class.getName(), e.getMessage());
                    return null;
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            Log.d(MainActivity.class.getName(), e.getMessage());
                        }
                    }
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
