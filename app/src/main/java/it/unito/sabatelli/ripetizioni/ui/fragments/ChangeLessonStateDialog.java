package it.unito.sabatelli.ripetizioni.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.ui.adapters.LessonListViewAdapter;

public class ChangeLessonStateDialog extends DialogFragment {
    Lesson lesson;


    public ChangeLessonStateDialog(Lesson l) {
        this.lesson = l;
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

                        switch (selectedPosition) {
                            case 0:
                                lesson.getState().setCode(2);
                                lesson.getState().setName("Effettuata");

                                break;
                            case 1:
                                lesson.getState().setCode(3);
                                lesson.getState().setName("Annullata");

                            default:
                                break;
                        }
                        ((BaseAdapter)((ListView)getActivity().findViewById(R.id.lessons_list_view)).getAdapter()).notifyDataSetChanged();

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
