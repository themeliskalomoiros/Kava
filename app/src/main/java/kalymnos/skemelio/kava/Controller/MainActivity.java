package kalymnos.skemelio.kava.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kalymnos.skemelio.kava.Model.Category;
import kalymnos.skemelio.kava.R;
import kalymnos.skemelio.kava.View.MainScreenViewMvc;
import kalymnos.skemelio.kava.View.MainScreenViewMvcImpl;

public class MainActivity extends AppCompatActivity implements MainScreenViewMvc.OnCategoryClickListener {

    private MainScreenViewMvc viewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = new MainScreenViewMvcImpl(LayoutInflater.from(this), null);
        viewMvc.setOnCategoryClickListener(this);
        setContentView(viewMvc.getRootView());
        //TODO: fetch real data
        Category category1 = new Category(1,"ela",null);
        Category category2 = new Category(2,"pame",null);
        Category category3 = new Category(3,"dwse",null);
        Category category4 = new Category(3,"rixe",null);
        List<Category> list = new ArrayList<>();
        list.add(category1);
        list.add(category2);
        list.add(category3);
        list.add(category4);
        viewMvc.bind(list);
    }

    @Override
    public void onCategoryClick(int position) {
        //TODO: Remove toast after implementing the method
        Toast.makeText(this, "Pressed " + (position + 1), Toast.LENGTH_SHORT).show();
    }
}
