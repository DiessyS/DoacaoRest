package doacaorest

import grails.converters.*

class UsuarioController {
	static responseFormats = ['json']

    def index() { }

    def create(){
        def usuario = new Usuario(params)

        if(!usuario.save(flush:true)) {
            render status:401, ["mensagem:" : DHelper.message(usuario.errors.getFieldError())] as JSON
        }
        render status:200, ["mensagem:" : DHelper.message('default.sucesso.message')] as JSON
    }

    def update(){
        def usuario = Usuario.get(params.id)

        if (usuario == null) {
            render status:401, [
                "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        usuario.nome = params.nome
        usuario.senha = params.senha
        usuario.telefone = params.telefone

        if(!usuario.save(flush:true)) {
            render status:401, ["mensagem:" : DHelper.message(usuario.errors.getFieldError())] as JSON
        } else {
            render status:200, ["mensagem:" : DHelper.message('default.sucesso.message')] as JSON
        }
    }

    def get(){
        def usuario = Usuario.get(params.id)

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
