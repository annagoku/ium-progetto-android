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
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;

public class ChangeLessonStateDialog extends AbstractDialogFragment {
    final Lesson lesson;
    final int position;


    public ChangeLessonStateDialog(Lesson l, int position) {
        this.lesson = l;
        this.position = position;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String [] items = new String[] {
                getString(R.string.dialog_statelesson_done),
                getString(R.string.dialog_statelesson_delete)};

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_statelesson_title)
                .setSingleChoiceItems(items, -1, null)
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        System.out.println("ChangeLessonState -> Ho cliccato "+items[selectedPosition]);

                        Activity act = getActivity();
                        if(selectedPosition == -1) {
                            Toast.makeText(getActivity(), "Nessuna azione selezionata",Toast.LENGTH_LONG ).show();
                        }
                        else {
                            int  newStateCode = lesson.getState().getCode();
                            String newStateDesc = lesson.getState().getName();
                            switch (selectedPosition) {
                                case 0:
                                    newStateCode = 2;
                                    newStateDesc= "Effettuata";

                                    break;
                                case 1:
                                    newStateCode = 3;
                                    newStateDesc= "Annullata";

                                default:
                                    break;
                            }


                            final String finalNewStateDesc = newStateDesc;
                            final int finalNewStateCode = newStateCode;
                            String action ="modificastato";
                            //comunicazione al server
                            apiManager.changeLessonState(lesson, action, newStateCode,
                                (v)-> {
                                    vModel.reservations.changeLessonState(position, finalNewStateCode, finalNewStateDesc);

                                    Toast.makeText(act, "Modifica effettuata con successo ", Toast.LENGTH_SHORT).show();
                                },
                                (error) -> {
                                    if(!act.isFinishing()) {
                                        Toast.makeText(act, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
