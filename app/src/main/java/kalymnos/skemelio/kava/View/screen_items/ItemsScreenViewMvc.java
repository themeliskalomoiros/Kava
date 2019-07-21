package kalymnos.skemelio.kava.View.screen_items;

import java.util.List;

import kalymnos.skemelio.kava.Model.Item;
import kalymnos.skemelio.kava.View.ViewMvc;

public interface ItemsScreenViewMvc extends ViewMvc {
    interface OnItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener);

    interface OnSaveClickListener {
        void onSaveClick();
    }

    void setOnSaveClickListener(OnSaveClickListener listener);

    void bindItems(List<Item> items);
}
