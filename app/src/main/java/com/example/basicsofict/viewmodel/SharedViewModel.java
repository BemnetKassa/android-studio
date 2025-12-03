package com.example.basicsofict.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.basicsofict.utils.Event; // <<< IMPORT THE NEW EVENT CLASS

public class SharedViewModel extends ViewModel {

    // *** FIX: Change from a simple Boolean to an Event-wrapped Boolean ***
    private final MutableLiveData<Event<Boolean>> progressUpdateTrigger = new MutableLiveData<>();

    public void triggerProgressUpdate() {
        // Wrap the trigger in an Event object
        progressUpdateTrigger.setValue(new Event<>(true));
    }

    public LiveData<Event<Boolean>> getProgressUpdateTrigger() {
        return progressUpdateTrigger;
    }
}
