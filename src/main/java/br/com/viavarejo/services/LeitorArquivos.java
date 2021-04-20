package br.com.viavarejo.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 *
 * @author 7700364525
 *
 */
public class LeitorArquivos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BufferedReader retornarArquivo(final String arquivo) {

        InputStream in = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {

            in = this.getClass().getResourceAsStream("/arquivos/" + arquivo);

            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }

}
