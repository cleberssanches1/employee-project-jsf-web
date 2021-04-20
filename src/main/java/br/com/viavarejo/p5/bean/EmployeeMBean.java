package br.com.viavarejo.p5.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.viavarejo.services.EmployeeServices;
import br.com.viavarejo.services.EmployeeServicesImpl;
import br.com.viavarejo.vo.Employee;
import br.com.viavarejo.vo.EmployeeFiltro;
import br.com.viavarejo.vo.Groups;
import br.com.viavarejo.vo.Leaders;


/**
 *
 * @author 7700364525
 *
 */
@ManagedBean
@ViewScoped
public class EmployeeMBean extends BeanUtils {

  private static final String LBL_DEFAULT_CAMPO_OBRIGATORIO = "lbl_default_campoObrigatorio";
  /**
   * Campo serial
   */
  private static final long serialVersionUID = 5559545305554612209L;

  private final static String DATE_FORMAT = "yyyy-MM-dd";
  private final static Locale localeDefault = new Locale("en", "US", "WIN");

  /**
   * Controle do filtro.
   */
  private boolean verFiltro;
  /**
   * Controle para executar o filtro.
   */
  private boolean executaFiltro;

  private EmployeeFiltro filtro;
  /**
   * Refer�ncia a tela de detalhe
   */
  private static final String EMPLOYEE_DETALHE = "employeeDetalhe.xhtml";
  /**
   * Referencia a tela de consulta
   */
  private static final String EMPLOYEE_CONSULTA = "employee.xhtml";

  private List<Employee> employeeListagem;

  private Employee employeeItemListagem;

  private Employee employeeDetalhe;

  // @EJB
  private final EmployeeServices employeeServices = new EmployeeServicesImpl("https://www.pulses.com.br/api/engage_sandbox/v1/", "a1f0f5e01e50987039884eadcbe36265");
  //EmployeeAPI employeeAPI = new EmployeeAPI("a1f0f5e01e50987039884eadcbe36265", "https://www.pulses.com.br/api/engage_sandbox/v1/");
	
  private boolean habilitacaoEdicao = Boolean.FALSE;

  /**
   * M�todo inicial.
   */
  @PostConstruct
  public void init() {
	this.setEmployeeListagem(new ArrayList<Employee>());
    this.limpar();
  }

  /**
   * M�todo que retorna dados para tabela atrav�s do filtro.
   */
  public void buscar() {
    try {

      // this.employeeListagem = this.employFake();

      // EmployeeFiltro filtro = new EmployeeFiltro();
      // filtro.setCpf("24836399840");

      this.filtro.setCpf(this.filtro.getCpf().replace(".", "").replace("-", ""));

      this.employeeListagem = employeeServices.buscarDadosEmployees(this.filtro);

      this.verFiltro = true;

    } catch (final Exception e) {
      this.adicionarMensagemEmployeeException(e);
    }
  }

  public void importarEmployees(final FileUploadEvent event) {

    event.getComponent().setTransient(false);
    InputStreamReader entrada = null;

    try {
      InputStream arquivo = event.getFile().getInputstream();
      entrada = new InputStreamReader(arquivo);
      List<Employee> listaEmployee = this.carregarFuncionarios(entrada);

      this.employeeServices.incluirListaEmployee(listaEmployee);
      this.adicionarMensagemInfo("header_sucesso", "salvar_sucesso", null);
    } catch (final Exception e) {
      this.adicionarMensagemException(e);
    }
  }

  private List<Employee> carregarFuncionarios(final InputStreamReader entrada) throws Exception {
    BufferedReader br = new BufferedReader(entrada);
    String registro;
    List<Employee> importacoes = new ArrayList<Employee>();

    boolean registroPrimeiro = true;
    String delimitador = ";";
    try {

      while ((registro = br.readLine()) != null) {

        if (registroPrimeiro) {
          registroPrimeiro = false;
          continue;
        }

        Employee importacao = new Employee();

        String[] valores = registro.split(delimitador);

        if (valores.length == 0) {
          continue;
        }

        importacao.setName(valores[0].trim());
        importacao.setEmail(valores[1].trim());
        importacao.setInternalNumber(valores[2]);
        importacao.setCpf(valores[3]);
        importacao.setSex(valores[4]);
        importacao.setCelphone(valores[5]);
        importacao.setBirthday(stringToDate(valores[6]));
        importacao.setHiringDate(stringToDate(valores[7]));
        importacao.setScholarity(valores[8].trim());
        importacao.setPosition(valores[9].trim());
        importacao.setUnitGeography(valores[10].trim());
        importacao.setUnitBusiness(valores[11].trim());
        importacao.setLanguage(valores[12].trim());

        Groups grupo = new Groups();
        grupo.setName(valores[13].trim());
        Groups[] arr = {grupo};
        importacao.setGroups(arr);

        Leaders leaders = new Leaders();
        leaders.setName(valores[14].trim());
        leaders.setCpf("");
        leaders.setInternalNumber(valores[15].trim());

        Leaders[] led = {leaders};
        importacao.setLeaders(led);

        importacao.setBlocked("0");

        importacoes.add(importacao);
      }
    } catch (Exception e) {
      throw e;
    }

    return importacoes;
  }

  public String delete(){
    String telaRetorno = "";
    try{
    	this.employeeServices.deleteEmployee(this.employeeItemListagem);
       this.adicionarMensagemInfo("header_sucesso", "deletado_sucesso", null);
       this.employeeListagem.clear();
       RequestContext.getCurrentInstance().update("form1");
       telaRetorno = EMPLOYEE_CONSULTA;
    }catch (Exception e) {
      this.adicionarMensagemException(e);
    }
    return telaRetorno;
  }

  public static Date stringToDate(final String stringDate) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
      date = sdf.parse(stringDate);
    } catch (ParseException e) {
      System.out.println(e.toString());
    }
    return date;
  }

  /**
   *
   * @return
   */
  private List<Employee> employFake() {

    List<Employee> listaEmployee = new ArrayList<Employee>();

    Employee em = new Employee();
    em.setIdPerson(1L);
    em.setName("Cleber Santos Sanches");
    em.setEmail("cleberssanches1@gmail.com");
    em.setCpf("30039779890");
    em.setInternalNumber("1");
    em.setCelphone("13981587989");
    // em.setListaGroups(new ArrayList<Groups>());
    // em.setListaLeaders(new ArrayList<Leaders>());

    Groups grupo = new Groups();

    grupo.setAncestors("");
    grupo.setIdGroup("1");
    grupo.setName("name");

    Groups[] arr = {grupo};
    em.setGroups(arr);


    // em.setGroups(grupo);

    Leaders leaders = new Leaders();
    leaders.setName("Nome");
    leaders.setCpf("30039779890");
    leaders.setInternalNumber("1");

    Leaders[] led = {leaders};
    em.setLeaders(led);

    em.setLanguage("language");
    em.setBlocked("blocked");

    em.setSex("sex");

    Locale locale2 = new Locale("en", "US");
    Calendar birthday = Calendar.getInstance(locale2);
    birthday.set(Calendar.YEAR, 1983);
    birthday.set(Calendar.MONTH, 9);
    birthday.set(Calendar.DAY_OF_MONTH, 4);
    birthday.set(Calendar.MINUTE, 0);
    birthday.set(Calendar.HOUR_OF_DAY, 0);
    birthday.set(Calendar.SECOND, 0);

    em.setBirthday(birthday.getTime());

    Calendar hiringDate = Calendar.getInstance(locale2);
    em.setHiringDate(hiringDate.getTime());

    Calendar resignationDate = Calendar.getInstance(locale2);
    em.setResignationdate(resignationDate.getTime());

    em.setPosition("");
    em.setScholarity("");

    em.setUnitBusiness("");

    em.setUnitGeography("");

    listaEmployee.add(em);

    return listaEmployee;

  }

  public static String format(Date dtRef, String pattern) {
    SimpleDateFormat sdt = new SimpleDateFormat(pattern, localeDefault);
    return sdt.format(dtRef);
  }

  /**
   * M�todo para abrir a tela de inclus�o de ag�ncias
   *
   * @return String
   */
  public String novo() {
    try {

      this.employeeItemListagem = new Employee();

      FacesContext fc = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
      session.setAttribute("Employee", this.employeeItemListagem);

      return EMPLOYEE_DETALHE;

    } catch (final Exception e) {
      this.adicionarMensagemException(e);
    }
    return null;
  }

  /**
   * M�todo Limpar Campos.
   *
   * @return String
   */
  public String limparCampos() {
    this.limpar();
    return EMPLOYEE_CONSULTA;
  }

  /**
   * M�todo para salvar dados da ag�ncia
   *
   * @return String
   */
  public String salvar() {
    this.habilitarFiltro();
    String telaRetorno = EMPLOYEE_DETALHE;
    try {
      this.adicionarMensagemInfo("header_sucesso", "salvar_sucesso", null);
      telaRetorno = EMPLOYEE_CONSULTA;

    } catch (Exception e) {
      this.adicionarMensagemErro("Erro", e.getMessage(), null);
    }
    return telaRetorno;
  }

  /**
   * M�todo para habilitar filtros.
   */
  public void habilitarFiltro() {
    this.verFiltro = Boolean.TRUE;
    this.executaFiltro = Boolean.TRUE;
  }

  private void limpar() {
    this.filtro = new EmployeeFiltro();

    this.employeeListagem.clear();
    this.verFiltro = false;
    this.executaFiltro = false;
  }

  /**
   * M�todo para retornar a tela de consulta de ag�ncias.
   *
   * @return String.
   */
  public String redirectConsulta() {

    if (this.employeeListagem.isEmpty()) {
      this.verFiltro = Boolean.FALSE;
      this.executaFiltro = Boolean.FALSE;
    } else {
      this.habilitarFiltro();
    }
    return EMPLOYEE_CONSULTA;
  }

  /**
   * M�todo que direciona o usu�rio para tela de detalhe.
   *
   * @return String
   */
  public String detalhe() {
    try {
      this.verFiltro = false;

      FacesContext fc = FacesContext.getCurrentInstance();
      HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
      session.setAttribute("Employee", this.employeeItemListagem);

    } catch (final Exception e) {
      this.adicionarMensagemException(e);
      return null;
    }
    return EMPLOYEE_DETALHE;
  }



  /**
   * @return the verFiltro
   */
  public boolean isVerFiltro() {
    return this.verFiltro;
  }

  /**
   * @param verFiltro the verFiltro to set
   */
  public void setVerFiltro(final boolean verFiltro) {
    this.verFiltro = verFiltro;
  }

  /**
   * @return the executaFiltro
   */
  public boolean isExecutaFiltro() {
    return this.executaFiltro;
  }

  /**
   * @param executaFiltro the executaFiltro to set
   */
  public void setExecutaFiltro(final boolean executaFiltro) {
    this.executaFiltro = executaFiltro;
  }

  public EmployeeFiltro getFiltro() {
    return this.filtro;
  }

  public void setFiltro(final EmployeeFiltro filtro) {
    this.filtro = filtro;
  }

  /**
   * @return the employeeListagem
   */
  public List<Employee> getEmployeeListagem() {
    return this.employeeListagem;
  }

  /**
   * @param employeeListagem the employeeListagem to set
   */
  public void setEmployeeListagem(final List<Employee> employeeListagem) {
    this.employeeListagem = employeeListagem;
  }

  /**
   * @return the employeeItemListagem
   */
  public Employee getEmployeeItemListagem() {
    return this.employeeItemListagem;
  }

  /**
   * @param employeeItemListagem the employeeItemListagem to set
   */
  public void setEmployeeItemListagem(final Employee employeeItemListagem) {
    this.employeeItemListagem = employeeItemListagem;
  }

  /**
   * @return the employeeDetalhe
   */
  public Employee getEmployeeDetalhe() {
    return this.employeeDetalhe;
  }

  /**
   * @param employeeDetalhe the employeeDetalhe to set
   */
  public void setEmployeeDetalhe(final Employee employeeDetalhe) {
    this.employeeDetalhe = employeeDetalhe;
  }

  /**
   * @return the habilitacaoEdicao
   */
  public boolean isHabilitacaoEdicao() {
    return this.habilitacaoEdicao;
  }

  /**
   * @param habilitacaoEdicao the habilitacaoEdicao to set
   */
  public void setHabilitacaoEdicao(final boolean habilitacaoEdicao) {
    this.habilitacaoEdicao = habilitacaoEdicao;
  }

}
