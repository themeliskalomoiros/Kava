package kalymnos.skemelio.kava.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import kalymnos.skemelio.kava.Model.persistance.QuantityRepo;
import kalymnos.skemelio.kava.Model.persistance.QuantityRepoImpl;
import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.View.screen_checkout.CheckoutScreenViewMvc;
import kalymnos.skemelio.kava.View.screen_checkout.CheckoutScreenViewMvcImpl;
import kalymnos.skemelio.kava.util.CheckoutFormatter;
import kalymnos.skemelio.kava.util.Time;

public class CheckoutActivity extends AppCompatActivity implements CheckoutScreenViewMvc.OnShareClickListener {

    private CheckoutScreenViewMvc viewMvc;
    private Category[] categories;
    private QuantityRepo repo;
    private CheckoutFormatter formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewMvc();
        initRepo();
        initCategories();
        formatter = new CheckoutFormatter(repo, categories, getString(R.string.atoms), getString(R.string.containers));
        setContentView(viewMvc.getRootView());
        viewMvc.bindData(formatter.formatKava());
    }

    private void initViewMvc() {
        viewMvc = new CheckoutScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnShareClickListener(this);
    }

    private void initRepo() {
        SharedPreferences categoryPrefs = getSharedPreferences(Category.class.getSimpleName(), MODE_PRIVATE);
        SharedPreferences itemPrefs = getSharedPreferences(Item.class.getSimpleName(), MODE_PRIVATE);
        repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
    }

    private void initCategories() {
        Parcelable[] parcels = getIntent().getParcelableArrayExtra(Category.class.getSimpleName());
        categories = new Category[parcels.length];
        for (int i = 0; i < parcels.length; i++) {
            categories[i] = (Category) parcels[i];
        }
    }

    @Override
    public void onShareClick() {
        Intent share = getShareIntent();
        startActivity(Intent.createChooser(share, getString(R.string.share_the_noted_items)));
    }

    private Intent getShareIntent() {
        String finalText = formatter.createTextToShare(viewMvc.getTitle(), Time.getCurrentTime(), viewMvc.getData());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, finalText);
        return intent;
    }
}
