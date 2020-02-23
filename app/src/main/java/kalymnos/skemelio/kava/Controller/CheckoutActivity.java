package kalymnos.skemelio.kava.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;

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
    private Category[] categories;
    private CheckoutFormatter formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategories();
        initFormatter();
        setupActivityLayout();
    }

    private void initCategories() {
        Parcelable[] parcels =
                getIntent().getParcelableArrayExtra(Category.class.getSimpleName());
        categories = new Category[parcels.length];
        for (int i = 0; i < parcels.length; i++) {
            categories[i] = (Category) parcels[i];
        }
    }

    private void initFormatter() {
        SharedPreferences categoryPrefs =
                getSharedPreferences(Category.class.getSimpleName(), MODE_PRIVATE);
        SharedPreferences itemPrefs =
                getSharedPreferences(Item.class.getSimpleName(), MODE_PRIVATE);
        QuantityRepo repo = QuantityRepoImpl.getInstance(categoryPrefs, itemPrefs);
        formatter = new CheckoutFormatter(
                repo,
                categories,
                getString(R.string.atoms),
                getString(R.string.containers));
    }

    private void setupActivityLayout() {
        initViewMvc();
        getSupportActionBar().setTitle(R.string.checkout_send);
        setContentView(viewMvc.getRootView());
    }

    private void initViewMvc() {
        viewMvc = new CheckoutScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnShareClickListener(this);
        viewMvc.bindData(formatter.formatKava());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_title) {
            // TODO: add title functionality
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
                viewMvc.getTitle(), // TODO: Add title here
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
        if (title==null || title.length()==0){
            viewMvc.hideTitle();
        }else{
            viewMvc.showTitle();
        }
    }
}
