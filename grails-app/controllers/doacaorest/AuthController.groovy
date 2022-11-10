package doacaorest


import grails.rest.*
import grails.converters.*

class AuthController extends GenericController {
	static responseFormats = ['json']
	
    def index() { }

    def login(){
        def usuario = Usuario.findByCpf(getRequestJSON().cpf)

        if (usuario == null) {
            render status:401, [
                "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        if (usuario.getSenha() != getRequestJSON().senha) {
            render status:401, [
                "mensagem":DHelper.message('usuario.validation.credenciasInvalidas')
            ] as JSON
            return
        }

        usuario.save(flush:true)

        render status:200, [
            "id": usuario.getId(),
            "token": DToken.generateTokenUsuario(usuario.getId())
        ] as JSON
    }

    def logout(){
        def usuario = Usuario.get(getRequestJSON(true).id)

        if (usuario == null) {
            render status:401, [
                "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        render status:200
    }

    def recuperarSenha() {
        def usuario = Usuario.findByCpf(getRequestJSON().cpf)

        if (usuario == null) {
            render status: 401, [
                    "mensagem": DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        render status: 200, [
                "senha": usuario.getSenha()
        ] as JSON
    }

}
