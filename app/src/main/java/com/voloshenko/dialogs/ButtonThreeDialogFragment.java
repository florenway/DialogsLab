package com.voloshenko.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Диалог третьей кнопки: заголовок, иконка test_icon и два выбора.
 */
public class ButtonThreeDialogFragment extends DialogFragment {

    public interface Listener {
        void onPaintButtonsRed();

        void onButtonThreeCancelled();
    }

    private Listener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Listener)) {
            throw new IllegalStateException(
                    context.getClass().getName() + " must implement ButtonThreeDialogFragment.Listener");
        }
        listener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.dialog3_title)
                .setIcon(R.drawable.test_icon)
                .setMessage(R.string.dialog3_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> notifyYes())
                .setNegativeButton(R.string.cancel, (dialog, which) -> notifyCancel());
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void notifyYes() {
        if (listener != null) {
            listener.onPaintButtonsRed();
        }
    }

    private void notifyCancel() {
        if (listener != null) {
            listener.onButtonThreeCancelled();
        }
    }
}
