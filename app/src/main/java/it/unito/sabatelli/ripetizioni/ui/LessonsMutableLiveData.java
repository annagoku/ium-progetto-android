package it.unito.sabatelli.ripetizioni.ui;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.LessonState;

public class LessonsMutableLiveData extends MutableLiveData<List<Lesson>> {

    public void addItem(Lesson l) {
        if(this.getValue() != null) {
            this.getValue().add(l);
            this.postValue(getValue());
        }
    }

    public void removeItem(Lesson l) {
        if(this.getValue() != null) {
            this.getValue().remove(l);
            this.postValue(getValue());
        }
    }


    public void changeLessonState(int position, int newCode, String newName) {
        LessonState state = this.getValue().get(position).getState();
        state.setCode(newCode);
        state.setName(newName);
        this.postValue(getValue());
    }
}
