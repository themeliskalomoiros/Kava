package kalymnos.skemelio.kava.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import kalymnos.skemelio.kava.R;

public class AddTitleDialog extends DialogFragment {

    public interface AddTitleDialogListener {
        void onDialogPositiveClick(String title);
    }

    private AddTitleDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddTitleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public void setAddTitleDialogListener(AddTitleDialogListener listener) {
        if (listener != null)
            this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.add_title_dialog, null);
        builder.setView(layout);

        builder.setMessage(R.string.add_title);

        builder.setPositiveButton(R.string.add,
                (dialog, id) -> {
                    //TODO: implement add button
                    EditText et = layout.findViewById(R.id.title);
                    listener.onDialogPositiveClick(et.getText().toString());
                });

        builder.setNegativeButton(R.string.cancel, null);


        return builder.create();
    }
}
