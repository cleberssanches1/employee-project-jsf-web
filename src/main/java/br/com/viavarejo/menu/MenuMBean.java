package br.com.viavarejo.menu;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import br.com.viavarejo.constantes.WebConstantes;
import br.com.viavarejo.p5.bean.BeanUtils;



@Named
@SessionScoped
public class MenuMBean extends BeanUtils {

    private static final long serialVersionUID = 2924928597379455948L;


    private final String urlRedirectPattern = "/RedirectServlet?" + WebConstantes.MENU_REDIRECT_KEY + "={0}";

    private MenuModel menu;


    private String includedPage = "principal.xhtml";

    public MenuMBean() {
    }

    public String getPage(){
        return this.includedPage;
    }
    public void setPageName(){
        this.includedPage = this.includedPage;
    }

    public String goNav() {
       FacesContext context = FacesContext.getCurrentInstance();
       String selectedPageViewId = context.getExternalContext().getRequestParameterMap().get("pageViewId");
       if (selectedPageViewId.equalsIgnoreCase("page1")){
            this.includedPage = "employee.xhtml";
        }
       return this.includedPage;
    }


    public MenuModel getMenu() throws SecurityException {
        if (this.menu == null) {
        	final Menu root = EnumFuncionalidade.construirMenu(new Menu(EnumFuncionalidade.P5));
            try {
                this.menu = this.carregarMenu(root);
            } catch (final SecurityException e) {
                this.adicionarMensagemErro("erro", "sis.erroUsuarioNaoLogado", null);
            }
        }
        return this.menu;
    }

    public String getUsuario() {
        String usuarioNome = null;

        try {
            usuarioNome = "Usuario";
        } catch (final Exception e) {
            this.adicionarMensagemErro("erro", "sis.erroUsuarioNaoLogado", null);

        }

        return usuarioNome;
    }

    public String getMatricula() {
        String usuarioMatricula = null;

        try {
            usuarioMatricula = "1";
        } catch (final Exception e) {
            this.adicionarMensagemException(e);
        }

        return usuarioMatricula;
    }

    public String getEmpresa() {
        return "VIA";
    }

    public String changeSecurityContext() {
        return WebConstantes.OK;
    }

    public DefaultMenuModel carregarMenu(Menu root) {
        DefaultMenuModel menuModel = new DefaultMenuModel();

        DefaultMenuItem menuItemLogo = new DefaultMenuItem();
        menuItemLogo.setUrl("/faces/principal.jsf");
        menuItemLogo.setValue(recuperarLabel("sigla_projeto"));
        menuItemLogo.setTitle(recuperarLabel("tooltip_navegacao_pagina_inicial"));
        menuItemLogo.setStyleClass("logoViavarejo");
        menuModel.addElement(menuItemLogo);

        for (Menu item : root.getCollFilhos()) {
            MenuElement menuComponente = this.criarItem(item);
            if (menuComponente instanceof Submenu) {
                menuModel.addElement(menuComponente);
            } else if (menuComponente instanceof MenuItem) {
                menuModel.addElement(menuComponente);
            }
        }
        return menuModel;
    }

    public static String recuperarLabel(final String label) {
      return getMessageResourceString(WebConstantes.LABEL_PROPERTIES, String.valueOf(label), null, getLocale());
    }

    public static String getMessageResourceString(final String bundleName, final String key, final Object params[], final Locale locale) {
      String text = null;
      final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

      try {
          text = bundle.getString(key);
      } catch (final Exception e) {
          text = getSummaryExceptionSpecific(bundle, key);

          if (text == null) {
              text = "<Erro> " + key + " <Erro>";
          }
      }
      if (params != null) {
          final MessageFormat mf = new MessageFormat(text, locale);
          text = mf.format(params, new StringBuffer(), null).toString();
      }

      return text;
  }

    private static String getSummaryExceptionSpecific(final ResourceBundle bundle, final String messageId) {
      String summary = null;

      if (messageId.toUpperCase().contains("SQLSTATE")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("SQLSTATE")));
      } else if (messageId.toUpperCase().contains("SQL_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("SQL_")));
      } else if (messageId.toUpperCase().contains("RPT_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("RPT_")));
      } else if (messageId.toUpperCase().contains("CIC_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("CIC_")));
      } else if (messageId.toUpperCase().contains("SEC_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("SEC_")));
      } else if (messageId.toUpperCase().contains("CBS_")) {
          summary = messageId.substring(messageId.toUpperCase().indexOf("CBS_")).substring(4);
      } else if (messageId.toUpperCase().contains("BUS_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("BUS_")));
      } else if (messageId.toUpperCase().contains("WEB_")) {
          summary = bundle.getString(messageId.substring(messageId.toUpperCase().indexOf("WEB_")));
      }

      return summary;
  }

    public static Locale getLocale() {
      final FacesContext facesContext = FacesContext.getCurrentInstance();
      return facesContext.getViewRoot().getLocale();
  }

    private MenuElement criarItem(final Menu pai) {
        MenuElement menuComponente;

        if (pai.getCollFilhos().isEmpty()) {
            final DefaultMenuItem menuItem = new DefaultMenuItem();
            menuItem.setValue(recuperarLabel(pai.getItem().getDescricao()));
            menuItem.setId(pai.getItem().name());
            menuItem.setUrl(this.getUrl(pai));
            menuItem.setAjax(false);
            menuItem.setOnclick("PF('statusDialog').show();");
            menuItem.setOncomplete("PF('statusDialog').hide();");
            menuItem.setOnsuccess("PF('statusDialog').hide();");
            menuComponente = menuItem;
        } else {
            // Se pai tem filhos, ele vira um submenu e solicita a criacao
            // dos filhos

            final DefaultSubMenu subMenu = new DefaultSubMenu();
            subMenu.setLabel(recuperarLabel(pai.getItem().getDescricao()));
            subMenu.setId(pai.getItem().name());
            for (final Menu filho : pai.getCollFilhos()) {
                subMenu.addElement(this.criarItem(filho));
            }
            menuComponente = subMenu;
        }

        return menuComponente;
    }

    private String getUrl(final Menu item) {
        String url;
        if (item.getItem().isUrlDirect()) {
            url = item.getItem().getUrl();
        } else {
            url = MessageFormat.format(this.urlRedirectPattern, item.getItem().getUrl());
        }
        return url;
    }

}
