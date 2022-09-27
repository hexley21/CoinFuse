package com.hxl.domain.repository;

public interface IPreferenceRepository {

    default boolean getWelcome() {
        return getWelcome(false);
    }

    boolean getWelcome(Boolean def);

    void saveWelcome(boolean value);

    default String getLanguage() {
        return getLanguage("en");
    }

    String getLanguage(String def);

    void saveLanguage(String value);

    default int getTheme() {
        return getTheme(-1);
    }

    int getTheme(int def);

    void saveTheme(int value);

    default String getCurrency() {
        return getCurrency("USD");
    }

    String getCurrency(String def);

    void saveCurrency(String value);
}
