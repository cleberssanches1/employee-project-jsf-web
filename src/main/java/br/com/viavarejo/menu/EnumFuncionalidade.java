package br.com.viavarejo.menu;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public enum EnumFuncionalidade {


	P5(1, "lbl_menu_root", null, null, EnumPerfil.P5BA),

	P5_Employee(10, "Employees", P5, null, null),

    P5_MENU_P5_Employee(1,
			"Employees", P5_Employee, null,
			EnumPerfil.P5BB, EnumPerfil.P5BC, EnumPerfil.P5BD, EnumPerfil.P5BE,
			EnumPerfil.P5BI);

    private EnumFuncionalidade(final boolean ativo, final int ordem, final String descricao,

            final EnumFuncionalidade pai, final String url, final boolean urlDirect, final EnumPerfil... perfis) {
        this.ativo = ativo;
        this.ordem = ordem;
        this.descricao = descricao;
        this.pai = pai;
        this.url = url;
        this.urlDirect = urlDirect;
        this.perfis = perfis;
    }

    private EnumFuncionalidade(final int ordem, final String descricao, final EnumFuncionalidade pai, final String url,
            final boolean urlDirect, final EnumPerfil... perfis) {
        this(true, ordem, descricao, pai, url, urlDirect, perfis);
    }

    private EnumFuncionalidade(final boolean ativo, final int ordem, final String descricao,
            final EnumFuncionalidade pai, final String url, final EnumPerfil... perfis) {
        this(ativo, ordem, descricao, pai, url, false, perfis);
    }

    private EnumFuncionalidade(final int ordem, final String descricao, final EnumFuncionalidade pai, final String url,
            final EnumPerfil... perfis) {
        this(true, ordem, descricao, pai, url, false, perfis);
    }

    private boolean ativo;
    private int ordem;
    private String descricao;
    private EnumFuncionalidade pai;
    private String url;
    private boolean urlDirect;
    private EnumPerfil[] perfis;

    public int getOrdem() {
        return this.ordem;
    }

    public void setOrdem(final int ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public EnumFuncionalidade getPai() {
        return this.pai;
    }

    public void setPai(final EnumFuncionalidade pai) {
        this.pai = pai;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public void setAtivo(final boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isUrlDirect() {
        return this.urlDirect;
    }

    public void setUrlDirect(final boolean urlDirect) {
        this.urlDirect = urlDirect;
    }

    public EnumPerfil[] getPerfis() {
        return this.perfis;
    }

    public void setPerfis(final EnumPerfil[] perfis) {
        this.perfis = perfis;
    }

    /**
     * Recuperar os itens de menu conforme as permissoes do usuario logado
     *
     * @return
     * @throws SecurityException
     */
    private static Collection<EnumFuncionalidade> getMenuItens() throws SecurityException {
        Collection<EnumFuncionalidade> itemColl = new ArrayList<EnumFuncionalidade>();
        try {
            for (EnumFuncionalidade element : EnumFuncionalidade.values()) {
               // boolean blAutorizado = true;

                itemColl.add(element);

               // SecurityWeb securityWeb = SecurityWeb.getInstance();

//                if (element.isAtivo()) {
//                    for (EnumPerfil perfil : element.getPerfis()) {
//                        //if (securityWeb.verificaTransacao(perfil.getPermissao())) {
//                            blAutorizado = true;
//                            break;
//                        //}
//                    }
//                    if (blAutorizado) {
//                        itemColl.add(element);
//                    }
//                }
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }

        return itemColl;
    }

    /**
     * Recupera os filhos de um item pai de menu
     *
     * @param pai
     * @param menuItens
     * @return
     */
    private static Collection<Menu> getFilhos(final Menu pai, final Collection<EnumFuncionalidade> menuItens) {
        final Collection<Menu> filhos = new ArrayList<Menu>();
        for (final EnumFuncionalidade element : menuItens) {
            if (!element.getDescricao().equals(EnumFuncionalidade.P5.getDescricao())) {
                if (element.getPai().compareTo(pai.getItem()) == 0) {
                    filhos.add(new Menu(element));
                }
            }
        }
        Collections.sort((List<Menu>) filhos);

        return filhos;
    }

    /**
     * Constroi o objeto menu a partir da raiz
     *
     * @param root
     * @return
     * @throws SecurityException
     */
    public static Menu construirMenu(final Menu root) throws SecurityException {
        root.setCollFilhos(construirMenuFilhos(root, getMenuItens()));
        return root;
    }

    /**
     * Retorna os submenus do menu passado como parametro
     *
     * @param menu
     * @return Collection<Menu>
     */
    private static Collection<Menu> construirMenuFilhos(final Menu pai, final Collection<EnumFuncionalidade> menuItens) {
        final Collection<Menu> filhos = getFilhos(pai, menuItens);
        for (final Menu filho : filhos) {
            filho.setCollFilhos(construirMenuFilhos(filho, menuItens));
        }
        return filhos;
    }

}