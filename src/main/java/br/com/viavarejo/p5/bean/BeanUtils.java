package br.com.viavarejo.p5.bean;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

/**
 *
 * @author cleber
 *
 */
public class BeanUtils {

  private String headerModalMsg;
  private String modalMsg;
  private String nextModalAction;
  private static String LABEL_PROPERTIES = "META-INF/label";

  public void addModalMessage(String headerModalMessage, String modalMessage,
      Object[] modalMessageParams, String nextAction) {
    this.headerModalMsg = getMessage(headerModalMessage, null);
    this.modalMsg = getMessage(modalMessage, modalMessageParams);
    this.nextModalAction = nextAction;
    RequestContext.getCurrentInstance().execute("PF('modalDialog').show();");
  }

  public static String getMessage(final String messageId, final Object params[]) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final String bundleName = facesContext.getApplication().getMessageBundle();

    return BeanUtils.getMessage(messageId, params, facesContext, bundleName);
  }

  public static String getLabel(final String labelId, final Object params[]) {
    return getMessage(labelId, params, FacesContext.getCurrentInstance(), LABEL_PROPERTIES);
  }

  private static String getMessage(final String messageId, final Object[] params,
      final FacesContext facesContext, final String bundleName) {
    if ((bundleName != null) && (messageId != null)) {
      String summary = null;
      String detail = null;
      final Locale locale = facesContext.getViewRoot().getLocale();
      final ResourceBundle bundle =
          ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

      try {
        summary = bundle.getString(messageId);
      } catch (final MissingResourceException e) {

        try {
          summary = getSummaryExceptionSpecific(bundle, messageId);
        } catch (final MissingResourceException ex) {

          summary =
              "No foi possvel identificar o erro ocorrido. Favor contactar os Administradores!";
        }
      }

      try {
        detail = bundle.getString(messageId + "_detail");
      } catch (final MissingResourceException e) {

      }

      if (summary != null) {
        MessageFormat mf = null;
        if (params != null) {
          mf = new MessageFormat(summary, locale);
          summary = mf.format(params, new StringBuffer(), null).toString();
        }
        if ((detail != null) && (params != null)) {
          mf.applyPattern(detail);
          detail = mf.format(params, new StringBuffer(), null).toString();
        }
        // Caso no encontre a mensagem, retorna o nome dela
        if (summary != null) {
          return summary;
        }
      }
    }

    // Caso no encontre a mensagem, retorna aviso
    if (ProjectStage.Development
        .equals(FacesContext.getCurrentInstance().getApplication().getProjectStage())) {
      // Mensagem "suja" exibida apenas caso o sistema esteja em status de
      // desenvolvimento.
      return "Erro no Sistema, a operao no pode ser realizada. Message key not registered: "
          + messageId;
    } else {
      // Caso esteja em produo, retorna mensagem "limpa":
      return "Erro no Sistema, a operao no pode ser realizada.";
    }
  }

  public static FacesMessage getMessage(final String messageId, final Object params[],
      final FacesMessage.Severity severity) {
    if (messageId == null) {
      // FIXME - REMOVER APOS ARRUMAR PROBLEMA DE key null not found !
      // System.out.println("getMessage() recebeu messageId nulo !!!");

    }

    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final String bundleName = facesContext.getApplication().getMessageBundle();

    if ((bundleName != null) && (messageId != null)) {
      String summary = null;
      String detail = null;
      final Locale locale = facesContext.getViewRoot().getLocale();
      final ResourceBundle bundle =
          ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

      try {
        summary = bundle.getString(messageId);
      } catch (final MissingResourceException e) {

        summary = getSummaryExceptionSpecific(bundle, messageId);
      }

      try {
        detail = bundle.getString(messageId + "_detail");
      } catch (final MissingResourceException e) {

      }

      if (summary != null) {
        MessageFormat mf = null;
        if (params != null) {
          mf = new MessageFormat(summary, locale);
          summary = mf.format(params, new StringBuffer(), null).toString();
        }
        if ((detail != null) && (params != null)) {
          mf.applyPattern(detail);
          detail = mf.format(params, new StringBuffer(), null).toString();
        }
        return new FacesMessage(severity, summary, detail);
      }
    }
    return new FacesMessage(severity, "<Erro> " + messageId + " <Erro>", null);
  }

  public void adicionarMensagemInfo(final String titulo, final String mensagem,
      final Object[] parametros) {
    this.adicionarMensagem(FacesMessage.SEVERITY_INFO, this.getMensagemSemTratamento(titulo, null),
        this.getMensagemSemTratamento(mensagem, parametros));
  }

  public void adicionarMensagemException(final Exception exception) {
    this.adicionarMensagemErro("erro", "sis.erroDesconhecido", null);
    exception.printStackTrace();
  }

  public void adicionarMensagemEmployeeException(final Exception exception) {
    this.adicionarMensagemAviso("aviso", "employee.nao_encontrado", null);
    exception.printStackTrace();
  }

  private void adicionarMensagem(final FacesMessage.Severity severity, final String titulo,
      final String mensagem) {
    FacesContext.getCurrentInstance().addMessage("MensagensGerais",
        new FacesMessage(severity, titulo, mensagem));
  }

  public void adicionarMensagemAviso(final String titulo, final String mensagem,
      final Object[] parametros) {
    this.adicionarMensagem(FacesMessage.SEVERITY_WARN, this.getMensagemSemTratamento(titulo, null),
        this.getMensagemSemTratamento(mensagem, parametros));
  }

  public void adicionarMensagemErro(final String titulo, final String mensagem,
      final Object[] parametros) {
    this.adicionarMensagem(FacesMessage.SEVERITY_ERROR, this.getMensagemSemTratamento(titulo, null),
        this.getMensagemSemTratamento(mensagem, parametros));
  }

  public String getMensagemSemTratamento(final String messageId, final Object params[]) {

    String resultado;
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final String bundleName = facesContext.getApplication().getMessageBundle();
    final Locale locale = facesContext.getViewRoot().getLocale();
    final ResourceBundle bundle =
        ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

    try {
      resultado = bundle.getString(messageId);
    } catch (final MissingResourceException e) {
      // LOGGER.error(e.getMessage(), e);
      try {
        resultado = getSummaryExceptionSpecific(bundle, messageId);
        resultado = resultado == null ? messageId : resultado;
      } catch (final MissingResourceException ex) {
        // LOGGER.error(ex.getMessage(), ex);
        resultado = messageId;
      }
    }

    if ((resultado != null) && (params != null)) {
      MessageFormat mf = null;
      mf = new MessageFormat(resultado, locale);
      resultado = mf.format(params, new StringBuffer(), null).toString();
    }

    return resultado;
  }

  private static String getSummaryExceptionSpecific(final ResourceBundle bundle,
      final String messageId) {
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

  protected static ClassLoader getCurrentClassLoader(final Object defaultObject) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) {
      loader = defaultObject.getClass().getClassLoader();
    }
    return loader;
  }

  public String getHeaderModalMsg() {
    return this.headerModalMsg;
  }

  public void setHeaderModalMsg(String headerModalMsg) {
    this.headerModalMsg = headerModalMsg;
  }

  public String getModalMsg() {
    return this.modalMsg;
  }

  public void setModalMsg(String modalMsg) {
    this.modalMsg = modalMsg;
  }

  public String getNextModalAction() {
    return this.nextModalAction;
  }

  public void setNextModalAction(String nextModalAction) {
    this.nextModalAction = nextModalAction;
  }


}
