package doacaorest
import grails.converters.*

class DoacaoController {
	static responseFormats = ['json']
	
    def index() { }

    def create(){
        def doacao = new Doacao(params)

        doacao.usuario = Usuario.get(params.idDoador)

        if(doacao.usuario == null) {
            render status:401, [
                    "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        doacao.setDoado(false)

        if(!doacao.save(flush:true)) {
            render status:401, ["mensagem:" : DHelper.message(doacao.errors.getFieldError())] as JSON
        }
        render status:200, ["mensagem:" : DHelper.message('default.sucesso.message')] as JSON
    }

    def delete(){
        def usuario = Usuario.get(params.idDoador)
        def doacao = Doacao.findById(params.id)

        if (doacao == null) {
            render status:401, [
                "mensagem":DHelper.message('doacao.validation.naoEncontrado')
            ] as JSON
            return
        }

        if(doacao.usuario.getId() != usuario.getId()) {
            render status:401, [
                "mensagem":DHelper.message('doacao.validation.removerNaoAutorizado')
            ] as JSON
            return
        }

        doacao.delete(flush:true)
        render status:200, ["mensagem:" : DHelper.message('default.sucesso.message')] as JSON
    }

    def getAllDoacoes(){
        def doacoes = Doacao.all
        def doacaoRetorno = []

        doacoes.every({ doacao ->
            def doacaoJson = [
                "codigo": doacao.getId(),
                "descricao": doacao.getDescricao(),
                "unidade": doacao.getUnidade(),
                "doado": doacao.getDoado(),
                "idDoador": doacao.usuario.getId(),
            ]
            doacaoRetorno.add(doacaoJson)
        })

        render status:200, ['lista': doacaoRetorno] as JSON
    }

    def receiveDoacao() {
        def usuario = Usuario.get(params.idDoador)
        def doacao = Doacao.findById(params.idDoacao)

        if (doacao == null) {
            render status:401, [
                    "mensagem":DHelper.message('doacao.validation.naoEncontrado')
            ] as JSON
            return
        }

        if(doacao.usuario.getId() == usuario.getId()) {
            render status:401, [
                    "mensagem":DHelper.message('doacao.validation.naoPodeReceber')
            ] as JSON
            return
        }

        if (doacao.getDoado()) {
            render status:401, [
                    "mensagem":DHelper.message('doacao.validation.jaDoado')
            ] as JSON
            return
        }

        doacao.doado = true

        if(!doacao.save(flush:true)) {
            render status:401, ["mensagem:" : DHelper.message(doacao.errors.getFieldError())] as JSON
        } else {
            render status:200, ["mensagem:" : DHelper.message('default.sucesso.message')] as JSON
        }
    }
}
