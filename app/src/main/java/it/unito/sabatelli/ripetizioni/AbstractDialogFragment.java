package it.unito.sabatelli.ripetizioni;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public abstract class AbstractDialogFragment extends DialogFragment {
    protected MainViewModel vModel;
    protected RipetizioniApiManager apiManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);
        apiManager = ApiFactory.getRipetizioniApiManager((AbstractActivity) getActivity());
    }

}
