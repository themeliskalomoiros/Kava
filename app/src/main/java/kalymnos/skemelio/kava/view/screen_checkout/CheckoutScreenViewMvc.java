package kalymnos.skemelio.kava.view.screen_checkout;

import java.util.List;

import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.view.ViewMvc;

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
