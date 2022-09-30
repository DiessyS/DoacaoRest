package doacaorest

class DToken {
    static private SERVER_SEED = UUID.randomUUID().toString()

    static def generateTokenUsuario(idUsuario) {
        def usuario = Usuario.get(idUsuario)

        if(usuario == null) { return null }

        return (usuario.cpf + usuario.senha + SERVER_SEED).digest('SHA-1')
    }

    static def validateTokenUsuario(idUsuario, token) {
        return idUsuario != null && token != null && generateTokenUsuario(idUsuario) == token
    }
}


