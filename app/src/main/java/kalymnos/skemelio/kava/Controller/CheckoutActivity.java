package kalymnos.skemelio.kava.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import kalymnos.skemelio.kava.Dialogs.AddTitleDialog;
import kalymnos.skemelio.kava.Model.persistance.QuantityRepo;
import kalymnos.skemelio.kava.Model.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.View.screen_checkout.CheckoutScreenViewMvc;
import kalymnos.skemelio.kava.View.screen_checkout.CheckoutScreenViewMvcImpl;
import kalymnos.skemelio.kava.util.CheckoutFormatter;
import kalymnos.skemelio.kava.util.Time;

public class CheckoutActivity
        extends AppCompatActivity
        implements CheckoutScreenViewMvc.OnShareClickListener,
        AddTitleDialog.AddTitleDialogListener {

    private CheckoutScreenViewMvc viewMvc;
    private List<Category> categories;
    private CheckoutFormatter formatter;
    private QuantityRepo quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initQuantities();
        initCategories();
        initFormatter();
        setupLayout();
    }

    private void initQuantities() {
        SharedPreferences categoryPrefs =
                getSharedPreferences(Category.class.getSimpleName(), MODE_PRIVATE);
        SharedPreferences itemPrefs =
                getSharedPreferences(Item.class.getSimpleName(), MODE_PRIVATE);
        quantities = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
    }

    private void initCategories() {
        Parcelable[] parcels =
                getIntent().getParcelableArrayExtra(Category.class.getSimpleName());
        categories = new ArrayList<>();
        for (int i = 0; i < parcels.length; i++) {
            Category c = (Category) parcels[i];
            if (!quantities.isEmpty(c)) {
                categories.add(c);
            }
        }
    }

    private void initFormatter() {
        formatter = new CheckoutFormatter(
                quantities,
                categories.toArray(new Category[categories.size()]),
                getString(R.string.atoms),
                getString(R.string.containers));
    }

    private void setupLayout() {
        initViewMvc();
        getSupportActionBar().setTitle(R.string.checkout_send);
        setContentView(viewMvc.getRootView());
    }

    private void initViewMvc() {
        viewMvc = new CheckoutScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnShareClickListener(this);
        viewMvc.bind(categories);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_title) {
            AddTitleDialog d = new AddTitleDialog();
            d.setAddTitleDialogListener(this);
            String tag = "" + d.hashCode();
            d.show(getSupportFragmentManager(), tag);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShareClick() {
        Intent share = getShareIntent();
        startActivity(Intent.createChooser(share, getString(R.string.share_the_noted_items)));
    }

    private Intent getShareIntent() {
        String finalText = formatter.createTextToShare(
                viewMvc.getTitle(),
                Time.getCurrentTime(),
                formatter.formatKava());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, finalText);
        return intent;
    }

    @Override
    public void onDialogPositiveClick(String title) {
        viewMvc.bindTitle(title.trim());
        if (title == null || title.length() == 0) {
            viewMvc.hideTitle();
        } else {
            viewMvc.showTitle();
        }
    }
}
