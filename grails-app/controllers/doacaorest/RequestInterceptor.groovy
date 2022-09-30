package doacaorest

import grails.converters.JSON


class RequestInterceptor {

     RequestInterceptor() {
        match controller: 'usuario', action: 'get'
        match controller: 'usuario', action: 'update'
        match controller: 'doacao'
    }

    boolean before() {

        def idUsuario = params.id

        if (["POST", "GET", "DELETE"].contains(request.method)) {
            if(request.requestURI.contains("doacoes") || request.requestURI.contains("receber")){
                idUsuario = params.idDoador
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
