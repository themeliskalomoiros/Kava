package kalymnos.skemelio.kava.View.screen_checkout;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Category;
import kalymnos.skemelio.kava.View.ViewMvc;

public interface CheckoutScreenViewMvc extends ViewMvc {

    interface OnShareClickListener {
        void onShareClick();
    }

    void setOnShareClickListener(OnShareClickListener listener);

    void bind(List<Category> categories);

    void bindTitle(String title);

    String getTitle();

    void showTitle();

    void hideTitle();
}
