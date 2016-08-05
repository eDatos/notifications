package org.siemac.metamac.notices.core.error;

import java.util.Locale;

import org.siemac.metamac.common.test.translations.CheckTranslationsTestBase;

public class NoticesCheckTranslationsTest extends CheckTranslationsTestBase {

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionTypeClasses() {
        return new Class[]{ServiceExceptionType.class};
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionParameterClasses() {
        return new Class[]{ServiceExceptionParameters.class};
    }

    @Override
    public Locale[] getLocalesToTranslate() {
        return LOCALES_TO_TRANSLATE;
    }

}