package doacaorest

class UrlMappings {

    static mappings = {

        /* Usuario */
        post "/usuarios"(controller: 'usuario', action: 'create')
        put "/usuarios/$id?"(controller: 'usuario', action: 'update')
        get "/usuarios/$id?"(controller: 'usuario', action: 'get')

        /* Autenticação */
        post "/login"(controller: 'auth', action: 'login')
        get "/recupera_senha"(controller: 'auth', action: 'recuperarSenha')
        get "/logon/$id?"(controller: 'auth', action: 'logout')

        /* Doação */
        post "/doacoes"(controller: 'doacao', action: 'create')
        get "/doacoes"(controller: 'doacao', action: 'getAllDoacoes')
        get "/doacoes/$idDoador?"(controller: 'doacao', action: 'getAllDoacoesDoador')
        delete "/doacoes/$id?"(controller: 'doacao', action: 'delete')

        /* Receber doação */
        post "/receber"(controller: 'doacao', action: 'receiveDoacao')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
