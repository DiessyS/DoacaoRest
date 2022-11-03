package doacaorest
import grails.converters.*

class DoacaoController extends GenericController {
	static responseFormats = ['json']
	
    def index() { }

    def create(){
        def doacao = new Doacao(getRequestJSON())

        doacao.usuario = Usuario.get(getRequestJSON().idDoador)

        if(doacao.usuario == null) {
            render status:401, [
                    "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        doacao.setDoado(false)

        if(!doacao.save(flush:true)) {
            render status:401, ["mensagem" : DHelper.message(doacao.errors.getFieldError())] as JSON
        }
        render status:200, ["mensagem" : DHelper.message('default.sucesso.message')] as JSON
    }

    def delete(){
        def usuario = Usuario.get(getRequestJSON().idDoador)
        def doacao = Doacao.findById(getRequestJSON(true).id)

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
        render status:200, ["mensagem" : DHelper.message('default.sucesso.message')] as JSON
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

    def getAllDoacoesDoador() {
        def usuario = Usuario.get(getRequestJSON(true).idDoador)

        if (usuario == null) {
            render status:401, [
                    "mensagem":DHelper.message('usuario.validation.naoEncontrado')
            ] as JSON
            return
        }

        def doacoesDoador = []
        def doacoes = Doacao.all

        doacoes.every({ doacao ->
            if(doacao.usuario.getId() == usuario.getId()) {
                def doacaoJson = [
                    "codigo": doacao.getId(),
                    "descricao": doacao.getDescricao(),
                    "unidade": doacao.getUnidade(),
                    "doado": doacao.getDoado(),
                    "idDoador": doacao.usuario.getId(),
                ]
                doacoesDoador.add(doacaoJson)
            }
        })

        render status:200, ['lista': doacoesDoador] as JSON
    }

    def receiveDoacao() {
        def usuario = Usuario.get(getRequestJSON().idDoador)
        def doacao = Doacao.findById(getRequestJSON().idDoacao)

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
            render status:401, ["mensagem" : DHelper.message(doacao.errors.getFieldError())] as JSON
        } else {
            render status:200, ["mensagem" : DHelper.message('default.sucesso.message')] as JSON
        }
    }
}
