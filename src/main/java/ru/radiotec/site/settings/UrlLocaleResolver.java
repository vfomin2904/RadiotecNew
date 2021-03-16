package ru.radiotec.site.settings;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class UrlLocaleResolver implements LocaleResolver {

    private static final String URL_LOCALE_ATTRIBUTE_NAME = "URL_LOCALE_ATTRIBUTE_NAME";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String uri = request.getRequestURI();

        System.out.println("URI=" + uri);

        String prefixEn = "/en/";
        String prefixRu = "/ru/";

        Locale locale = null;

        if (uri.contains(prefixEn)) {
            locale = Locale.ENGLISH;
        }
        else if (uri.contains(prefixRu)) {
            locale = new Locale("ru","RUSSIA");
        }

        if (locale != null) {
            request.getSession().setAttribute(URL_LOCALE_ATTRIBUTE_NAME, locale);
        }
        if (locale == null) {
            locale = (Locale) request.getSession().getAttribute(URL_LOCALE_ATTRIBUTE_NAME);
            if (locale == null) {
                locale = new Locale("ru","RUSSIA");
            }
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // Nothing
    }

}