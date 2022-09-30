package doacaorest

import java.text.SimpleDateFormat

class DToken {
    static private SERVER_SEED = UUID.randomUUID().toString()

    static def generateTokenUsuario(idUsuario) {
        def usuario = Usuario.get(idUsuario)

        if(usuario == null) { return null }

        def sdf = new SimpleDateFormat("MM/dd/yyyy")

        return (
            usuario.cpf + sdf.format(new Date()) + usuario.senha + SERVER_SEED
        ).digest('SHA-1')
    }

    static def validateTokenUsuario(idUsuario, token) {
        return idUsuario != null && token != null && generateTokenUsuario(idUsuario) == token
    }
}


