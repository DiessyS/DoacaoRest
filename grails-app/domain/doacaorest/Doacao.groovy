package doacaorest

class Doacao {

    String descricao
    String unidade
    Boolean doado

    static belongsTo = [usuario: Usuario]

    static constraints = {
        descricao blank: false, nullable: false, minSize: 1, maxSize: 255
        unidade blank: false, nullable: false, minSize: 1, maxSize: 255
        usuario nullable: false
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'sequence_doacao']
        doado defaultValue: false
    }
}
