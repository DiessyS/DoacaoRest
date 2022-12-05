package doacaorest

import grails.converters.JSON


class RequestInterceptor extends GenericController {

     RequestInterceptor() {
        match controller: 'auth', action: 'logout'
        match controller: 'usuario', action: 'get'
        match controller: 'usuario', action: 'update'
        match controller: 'doacao'
    }

    boolean before() {
        def wrapperForId = [
            "usuarios": params.id,
            "logon": params.id,
            "doacoes": getRequestJSON().idDoador ?: params.idDoador,
            "receber" : getRequestJSON().idDoador ?: params.idDoador
        ]

        def idUsuario = 0

        wrapperForId.each { key, value ->
            if (request.requestURI.contains(key)) {
                idUsuario = value
            }
        }

        if(DToken.validateTokenUsuario(idUsuario, request.getHeader('token'))) {
            return true
        } else {
            render status:401, ["mensagem":DHelper.message('autorizacao.validation.naoAutorizado')] as JSON
            return false
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
