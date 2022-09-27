package com.hxl.cryptonumismatist.presentation.fragments.welcome;

import androidx.lifecycle.ViewModel;

import com.hxl.domain.usecase.prefs.GetWelcome;
import com.hxl.domain.usecase.prefs.SaveWelcome;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WelcomeFragmentViewModel extends ViewModel {

    @NotNull public GetWelcome getWelcome;

    @NotNull public SaveWelcome saveWelcome;

    @Inject
    public WelcomeFragmentViewModel(@NotNull GetWelcome getWelcome, @NotNull SaveWelcome saveWelcome ) {
        this.getWelcome = getWelcome;
        this.saveWelcome = saveWelcome;
    }
}
