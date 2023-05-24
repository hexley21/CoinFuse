package com.hxl.coinfuse.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.hxl.coinfuse.R;
import com.hxl.coinfuse.util.UiUtils;
import com.hxl.domain.model.PrefKeys;
import com.hxl.domain.repository.PreferenceRepository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    public PreferenceRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (repository != null) {
            UiUtils.setLanguage(this, repository.get(PrefKeys.LANGUAGE.key, PrefKeys.LANGUAGE.def));
            UiUtils.setTheme(repository.get(PrefKeys.THEME.key, PrefKeys.THEME.def));
        }

        SplashScreen.installSplashScreen(this).setKeepOnScreenCondition(() -> false );
        setContentView(R.layout.activity_main);
    }
}