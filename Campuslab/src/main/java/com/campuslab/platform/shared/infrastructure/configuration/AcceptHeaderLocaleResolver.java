package com.campuslab.platform.shared.infrastructure.configuration;

import java.util.List;
import java.util.Locale;

public interface AcceptHeaderLocaleResolver {
    void setDefaultLocale(Locale english);

    void setSupportedLocales(List<Locale> es);
}
