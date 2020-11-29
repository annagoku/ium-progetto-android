package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import it.unito.sabatelli.ripetizioni.AbstractDialogFragment;
import it.unito.sabatelli.ripetizioni.AbstractFragment;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;

public class NewReservationDialog extends AbstractDialogFragment {
    Lesson lesson;
    CatalogFragment fragment;

    public NewReservationDialog(Lesson l, CatalogFragment fragment) {
        this.lesson = l;
        this.fragment = fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity act = getActivity();


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_newreservation_title)
                .setMessage(R.string.dialog_newreservation_message)
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                         apiManager.saveNewReservation(lesson,
                                (gr) -> {
                                     Toast.makeText(act, "Prenotazione salvata correttamente", Toast.LENGTH_SHORT).show();
                                     fragment.retrieveLessons();
                                },
                                (error) -> {
                                    Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
                                });


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
