package doacaorest

import grails.converters.JSON
import grails.util.Holders
import org.springframework.context.i18n.LocaleContextHolder


class DHelper {

    static def message(msg) {

        if(msg == null) {
            return Holders.applicationContext.getBean("messageSource").
                    getMessage('sistema.erro', null, 'Default Message', LocaleContextHolder.locale)
        }

        Holders.applicationContext.getBean("messageSource").
                getMessage(msg, LocaleContextHolder.getLocale())
    }

    static def message(String msg) {
        Holders.applicationContext.getBean("messageSource").
                getMessage(msg, null, 'Default Message', LocaleContextHolder.locale)
    }

    static def message(String msg, Object[] args) {
        Holders.applicationContext.getBean("messageSource").
                getMessage(msg, args, 'Default Message', LocaleContextHolder.locale)
    }

}
