package kalymnos.skemelio.kava.View.screen_items;

import java.util.List;

import kalymnos.skemelio.kava.Model.pojos.Item;
import kalymnos.skemelio.kava.View.ViewMvc;

public interface ItemsScreenViewMvc extends ViewMvc {

    interface OnItemQuantityChangeListener {
        void onAtomAdded(int position);

        void onContainerAdded(int position);

        void onAtomRemoved(int position);

        void onContainerRemoved(int position);
    }

    void setOnItemQuantityChangeListener(OnItemQuantityChangeListener listener);

    interface OnSaveClickListener {
        void onSaveClick();
    }

    void setOnSaveClickListener(OnSaveClickListener listener);

    void bindItems(List<Item> items);

    void bindTitle(String title);
}
