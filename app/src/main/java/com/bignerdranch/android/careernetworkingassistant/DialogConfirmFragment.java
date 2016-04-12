package com.bignerdranch.android.careernetworkingassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by EdwardLichtman on 4/7/16.
 */
public class DialogConfirmFragment extends DialogFragment{

    public static final String EXTRA_CONFIRM = "confirm";

    public static DialogConfirmFragment newInstance(int confirm) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONFIRM, confirm);

        DialogConfirmFragment fragment = new DialogConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int title = (int) getArguments().getSerializable(EXTRA_CONFIRM);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_CANCELED);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, new Intent());
    }
}
