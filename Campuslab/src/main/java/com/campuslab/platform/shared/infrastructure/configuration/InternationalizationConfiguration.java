package com.campuslab.platform.shared.infrastructure.configuration;

import java.util.List;
import java.util.Locale;

@com.campuslab.platform.shared.infrastructure.configuration.Configuration
public class InternationalizationConfiguration {

    @com.campuslab.platform.shared.infrastructure.configuration.Bean
    public com.campuslab.platform.shared.infrastructure.configuration.LocaleResolver localeResolver() {
        com.campuslab.platform.shared.infrastructure.configuration.AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver() {
            @Override
            public void setDefaultLocale(Locale english) {

            }

            @Override
            public void setSupportedLocales(List<Locale> es) {

            }
        };
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setSupportedLocales(List.of(
                Locale.ENGLISH,
                Locale.forLanguageTag("en-US"),
                Locale.forLanguageTag("es"),
                Locale.forLanguageTag("es-PE")
        ));
        return (com.campuslab.platform.shared.infrastructure.configuration.LocaleResolver) resolver;
    }
}
