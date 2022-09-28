package doacaorest

class DToken {
    static private SERVER_SEED = "9L0pLQsoOEOv2K52I1URy1Dbe5zP6JjZTpRs8edvGN2T6QLpaKBxvNpcfLBkCbf"

    static def generateTokenUsuario(idUsuario) {
        def usuario = Usuario.get(idUsuario)

        if(usuario == null) {
            return null
        }

        return ((usuario.cpf + usuario.senha).sha256() + SERVER_SEED).sha256()
    }

    static def validateTokenUsuario(idUsuario, token) {
        return idUsuario != null && token != null && generateTokenUsuario(idUsuario) == token
    }
}


