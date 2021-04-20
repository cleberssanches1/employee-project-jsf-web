package br.com.viavarejo.p5.bean;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import br.com.viavarejo.services.EmployeeServices;
import br.com.viavarejo.services.EmployeeServicesImpl;
import br.com.viavarejo.vo.Employee;

@ManagedBean
@ViewScoped
public class EmployeeDetalheMBean extends BeanUtils {

	private final static String DATE_FORMAT = "yyyy-MM-dd";
	private final static Locale localeDefault = new Locale("en", "US", "WIN");
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

	//EmployeeAPI employeeAPI  = new EmployeeAPI("a1f0f5e01e50987039884eadcbe36265", "https://www.pulses.com.br/api/engage_sandbox/v1/");
	
	private final boolean habilitacaoEdicao = Boolean.FALSE;

	@PostConstruct
	public void init() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession();
		this.employeeItemListagem = (Employee) session.getAttribute("Employee");
	}

	/**
	 * Metodo para salvar dados da agencia
	 *
	 * @return String
	 */
	public String salvar() {

		String telaRetorno = EMPLOYEE_DETALHE;
		try {

			if (this.employeeItemListagem.getIdPerson() == null) {
				this.employeeServices.incluirEmployee(this.employeeItemListagem);
			} else {
				this.employeeServices.updateEmployee(this.employeeItemListagem);
			}

			this.adicionarMensagemInfo("header_sucesso", "salvar_sucesso", null);
			telaRetorno = EMPLOYEE_CONSULTA;

		} catch (Exception e) {
			this.adicionarMensagemErro("Erro", e.getMessage(), null);
		}
		return telaRetorno;
	}

	/**
	 * M�todo para retornar a tela de consulta de ag�ncias.
	 *
	 * @return String.
	 */
	public String redirectConsulta() {
		return EMPLOYEE_CONSULTA;
	}

	public Employee getEmployeeItemListagem() {
		return this.employeeItemListagem;
	}

	public void setEmployeeItemListagem(Employee employeeItemListagem) {
		this.employeeItemListagem = employeeItemListagem;
	}

	public boolean isHabilitacaoEdicao() {
		return this.habilitacaoEdicao;
	}

}
