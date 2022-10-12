package doacaorest

import grails.converters.JSON


class RequestInterceptor extends GenericController {

     RequestInterceptor() {
        match controller: 'usuario', action: 'get'
        match controller: 'usuario', action: 'update'
        match controller: 'doacao'
    }

    boolean before() {

        def idUsuario = getRequestJSON().id

        if (["POST", "GET", "DELETE"].contains(request.method)) {
            if(request.requestURI.contains("doacoes") || request.requestURI.contains("receber")){
                idUsuario = getRequestJSON().idDoador
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
