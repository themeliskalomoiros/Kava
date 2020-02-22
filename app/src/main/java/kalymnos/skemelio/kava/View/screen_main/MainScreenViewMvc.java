package kalymnos.skemelio.kava.View.screen_main;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.View.ViewMvc;

public interface MainScreenViewMvc extends ViewMvc {
    void bind(List<Category> categories);

    interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    void setOnCategoryClickListener(OnCategoryClickListener listener);

    void showProgressBar();

    void hideProgressBar();
}
