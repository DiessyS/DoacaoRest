package doacaorest

class DToken {
    static private SERVER_SEED = UUID.randomUUID().toString()

    static def generateTokenUsuario(idUsuario) {
        def usuario = Usuario.get(idUsuario)

        if(usuario == null) { return null }

        StringBuilder info = new StringBuilder()

        info.append(usuario.getId())
        info.append(usuario.getTelefone())
        info.append(usuario.getCpf())
        info.append(usuario.getNome())
        info.append(usuario.getSenha())
        info.append(SERVER_SEED)

        return (info.toString()).digest('SHA-1')
    }

    static def validateTokenUsuario(idUsuario, token) {
        return idUsuario != null && token != null && generateTokenUsuario(idUsuario) == token
    }
}


