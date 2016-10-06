package com.patrick.developer.nybaiboliko.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import com.patrick.developer.nybaiboliko.R;

/**
 * Created by developer on 10/6/16.
 */

public class DialogBox {

    Context context;

    public DialogBox(Context context) {
        this.context = context;
    }

    public void leave() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set title
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        // set dialog message
        alertDialogBuilder
                .setMessage(Html.fromHtml(context.getResources().getString(R.string.message_quitte)))
                .setCancelable(false)
                .setPositiveButton("Akatona", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity) context).finish();
                    }
                }).setNegativeButton("Tsy akatona", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
