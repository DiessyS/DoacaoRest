package doacaorest

class Usuario {

    String nome
    String cpf
    String telefone
    String senha

    static constraints = {
        nome blank: false, nullable: false
        cpf blank: false, nullable: false, unique: true
        telefone blank: false, nullable: false
        senha blank: false, nullable: false
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'sequence_usuario']
    }
}
