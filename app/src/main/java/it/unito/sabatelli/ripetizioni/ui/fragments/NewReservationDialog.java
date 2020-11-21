package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;

public class NewReservationDialog extends DialogFragment {
    Lesson lesson;


    public NewReservationDialog(Lesson l) {
        this.lesson = l;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {




        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_newreservation_title)
                .setMessage(R.string.dialog_newreservation_message)
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();



                        RipetizioniApiManager apiManager = ApiFactory.getRipetizioniApiManager(getActivity());

                       //TODO implementare post
                }

        })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
