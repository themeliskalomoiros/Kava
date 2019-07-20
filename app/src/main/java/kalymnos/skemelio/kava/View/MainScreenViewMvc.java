package kalymnos.skemelio.kava.View;

import java.util.List;

import kalymnos.skemelio.kava.Model.Category;

public interface MainScreenViewMvc extends ViewMvc {
    void bind(List<Category> categories);

    interface OnCategoryClickListener{
        void onCategoryClick(int position);
    }

    void setOnCategoryClickListener(OnCategoryClickListener listener);
}
