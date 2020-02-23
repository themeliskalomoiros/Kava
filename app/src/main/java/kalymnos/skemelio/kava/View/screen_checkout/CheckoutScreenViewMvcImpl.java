package kalymnos.skemelio.kava.View.screen_checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kalymnos.skemelio.kava.R;

public class CheckoutScreenViewMvcImpl implements CheckoutScreenViewMvc {
    private View root;
    private FloatingActionButton share;
    private TextView data;

    public CheckoutScreenViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        root = inflater.inflate(R.layout.screen_checkout, parent, false);
        share = root.findViewById(R.id.share);
        data = root.findViewById(R.id.items);
    }

    @Override
    public void setOnShareClickListener(OnShareClickListener listener) {
        if (listener != null)
            share.setOnClickListener(v -> listener.onShareClick());
    }

    @Override
    public void bindData(String data) {
        this.data.setText(data);
    }

    @Override
    public View getRootView() {
        return root;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }
}
