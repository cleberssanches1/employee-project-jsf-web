package br.com.viavarejo.menu;

public enum EnumPermission implements EnumPermissionI {

     P5BA("P5BA"),// Acesso ao sistema
     P5BB("P5BB"),
     P5BC("P5BC"),
     P5BD("P5BD"),
     P5BE("P5BE"),
     P5BF("P5BF"),
     P5BG("P5BG"),
     P5BH("P5BH"),
     P5BI("P5BI"),
     P5BJ("P5BJ"),
     P5BK("P5BK"),
     P5CB("P5CB"),
     P5CC("P5CC"),
     P5CD("P5CD"),
     P5CF("P5CF"),
     P5S8("P5S8"),
     P5S9("P5S9"),
     P5S7("P5S7"),
     P5OF("P5OF"),
     P5OB("P5OB"),
     P5OC("P5OC"),
     P5OH("P5OH"),
     P5OG("P5OG"),
     P5OJ("P5OJ"),
     P5CG("P5CG"),
     P5BN("P5BN");

    private String transacao;

    private EnumPermission(final String transacao) {
        this.transacao = transacao;
    }

    /**
     * @return o transacao
     */
    @Override
    public String getTransacao() {
        return this.transacao;
    }

    /**
     * @param transacao o transacao a ser configurado
     */
    @Override
    public void setTransacao(final String transacao) {
        this.transacao = transacao;
    }

    public static EnumPermission getPermissao(final String transacao) {
        for (final EnumPermission v : values()) {
            if (v.getTransacao().equals(transacao)) {
                return v;
            }
        }
        return null;
    }
}
