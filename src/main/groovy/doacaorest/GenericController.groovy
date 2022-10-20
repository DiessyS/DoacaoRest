package doacaorest

import grails.converters.JSON

class GenericController {
     Map getRequestJSON(boolean url = false) {
        try {
            return url? params : request.JSON as Map
        } catch (Exception e) {
            render status:401, ["mensagem:" : DHelper.message('json.validation.erro')] as JSON
            return [error: true]
        }
    }
}
