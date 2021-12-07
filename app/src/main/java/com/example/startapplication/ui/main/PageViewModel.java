package com.example.startapplication.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String[]> mText = Transformations.map(mIndex, new Function<Integer, String[]>() {
        @Override
        public String[] apply(Integer input) {
            if(input==1)
            {
                String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand","Sce", "Sce", "australia", "Portugle", "America", "NewZealand"};
                return countryList;
            }
            String countryList[]={"Abedalla", "Nor", "Marwan", "Yosef","India", "China", "australia", "Portugle", "America", "NewZealand","Sce", "Sce", "australia", "Portugle", "America", "NewZealand"};
            return  countryList;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String[]> getText() {
        return mText;
    }
}