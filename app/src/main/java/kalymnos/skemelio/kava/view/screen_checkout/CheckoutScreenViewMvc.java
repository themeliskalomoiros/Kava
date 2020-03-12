package kalymnos.skemelio.kava.view.screen_checkout;

import java.util.List;

import kalymnos.skemelio.kava.model.Category;
import kalymnos.skemelio.kava.view.ViewMvc;

public interface CheckoutScreenViewMvc extends ViewMvc {

    interface OnShareListener {
        void onShare();
    }

    void setOnShareListener(OnShareListener listener);

    void add(List<Category> categories);

    void setTitle(String title);

    String getTitle();

    void showTitle();

    void hideTitle();
}
