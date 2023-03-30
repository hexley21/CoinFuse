package com.hxl.presentation.viewmodels;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.interactors.prefs.GetWelcome;
import com.hxl.domain.interactors.prefs.SaveWelcome;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WelcomeViewModel extends ViewModel {

    @NotNull public GetWelcome getWelcome;
    @NotNull public SaveWelcome saveWelcome;

    @Inject
    public WelcomeViewModel(@NotNull GetWelcome getWelcome, @NotNull SaveWelcome saveWelcome ) {
        this.getWelcome = getWelcome;
        this.saveWelcome = saveWelcome;
    }
}
