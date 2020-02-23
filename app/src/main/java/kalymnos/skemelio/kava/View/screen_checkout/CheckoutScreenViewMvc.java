package kalymnos.skemelio.kava.View.screen_checkout;

import kalymnos.skemelio.kava.View.ViewMvc;

public interface CheckoutScreenViewMvc extends ViewMvc {

    interface OnShareClickListener {
        void onShareClick();
    }

    void setOnShareClickListener(OnShareClickListener listener);

    String getTitle();

    void bindData(String data);
}
