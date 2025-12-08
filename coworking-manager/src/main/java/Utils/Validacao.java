package Utils;

import Exceptions.CampoVazioException;

public class Validacao {

    public static void obrigatorio(String valor, String nomeCampo) throws CampoVazioException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new CampoVazioException(nomeCampo);
        }
    }
}