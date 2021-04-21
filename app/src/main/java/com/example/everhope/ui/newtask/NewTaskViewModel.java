package com.example.everhope.ui.newtask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewTaskViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public NewTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is New Task fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
