package kalymnos.skemelio.kava.View.screen_checkout;

import kalymnos.skemelio.kava.View.ViewMvc;

public interface CheckoutScreenViewMvc extends ViewMvc {

    interface OnShareClickListener {
        void onShareClick();
    }

    void setOnShareClickListener(OnShareClickListener listener);

    void bindData(String data);

    void bindTitle(String title);

    void showTitle();

    void hideTitle();
}
