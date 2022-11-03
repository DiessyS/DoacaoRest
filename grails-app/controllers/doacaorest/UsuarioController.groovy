package doacaorest

import grails.converters.*

class UsuarioController extends GenericController {
	static responseFormats = ['json']

    def index() { }

    def create(){
        def usuario = new Usuario(getRequestJSON())

        if(!usuario.save(flush:true)) {
            render status:401, ["mensagem" : DHelper.message(usuario.errors.getFieldError())] as JSON
        }
        render status:200, ["mensagem" : DHelper.message('default.sucesso.message')] as JSON
    }

    def update(){
        def usuario = Usuario.get(getRequestJSON(true).id)

        if (usuario == null) {
            render status:401, [
                "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        usuario.nome = getRequestJSON().nome
        usuario.senha = getRequestJSON().senha
        usuario.telefone = getRequestJSON().telefone

        if(!usuario.save(flush:true)) {
            render status:401, ["mensagem" : DHelper.message(usuario.errors.getFieldError())] as JSON
        } else {
            render status:200, [
                "mensagem" : DHelper.message('default.sucesso.message'),
                "token": DToken.generateTokenUsuario(usuario.getId())
            ] as JSON
        }
    }

    def get(){
        def usuario = Usuario.get(getRequestJSON(true).id)

        if (usuario == null) {
            render status:401, [
                    "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        render status:200, [
                "nome": usuario.getNome(),
                "cpf": usuario.getCpf(),
                "telefone": usuario.getTelefone(),
        ] as JSON
    }
}
