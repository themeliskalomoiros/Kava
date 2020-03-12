package kalymnos.skemelio.kava.view.screen_main;

import java.util.List;

import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.view.ViewMvc;

public interface MainScreenViewMvc extends ViewMvc {
    interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    void add(List<Category> categories);

    void setOnCategoryClickListener(OnCategoryClickListener listener);

    void showProgressBar();

    void hideProgressBar();
}
