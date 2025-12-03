package com.example.basicsofict.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    // This LiveData will hold a simple trigger event. Using a Boolean is common and effective.
    private final MutableLiveData<Boolean> progressUpdateTrigger = new MutableLiveData<>();

    /**
     * Call this method from anywhere (like LessonDetailFragment) when a lesson is completed.
     * It posts a new value to the LiveData, which will notify any active observers.
     */
    public void triggerProgressUpdate() {
        progressUpdateTrigger.setValue(true);
    }

    /**
     * Fragments that need to know about the update (like ProgressFragment) will observe this LiveData.
     * When the value changes, they know it's time to refresh their UI.
     * @return The LiveData object for observers to subscribe to.
     */
    public LiveData<Boolean> getProgressUpdateTrigger() {
        return progressUpdateTrigger;
    }
}
