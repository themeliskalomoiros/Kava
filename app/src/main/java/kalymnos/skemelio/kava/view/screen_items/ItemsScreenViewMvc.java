package kalymnos.skemelio.kava.view.screen_items;

import java.util.List;

import kalymnos.skemelio.kava.model.Item;
import kalymnos.skemelio.kava.model.Quantity;
import kalymnos.skemelio.kava.view.ViewMvc;

public interface ItemsScreenViewMvc extends ViewMvc {

    interface OnItemQuantityChangeListener {
        void onAtomAdded(int position);

        void onContainerAdded(int position);

        void onAtomRemoved(int position);

        void onContainerRemoved(int position);
    }

    void setOnItemQuantityChangeListener(OnItemQuantityChangeListener listener);

    void add(List<Item> items);

    void add(Quantity[] quantities);
}
