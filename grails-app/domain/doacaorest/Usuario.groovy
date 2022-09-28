package doacaorest

class Usuario {

    String nome
    String cpf
    String telefone
    String senha

    static constraints = {
        nome blank: false, nullable: false, minSize: 1, maxSize: 255
        cpf blank: false, nullable: false, unique: true, minSize: 1, maxSize: 255
        telefone blank: false, nullable: false, minSize: 1, maxSize: 255
        senha blank: false, nullable: false, minSize: 1, maxSize: 255
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'sequence_usuario']
    }
}
