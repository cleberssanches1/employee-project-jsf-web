package br.com.viavarejo.constantes;

public class WebConstantes {

    public static final String CODIGO_SISTEMA = "P5-GestaoDeSaude";

    public static final String PAGINA_ERRO = "/error.jsp";
    public static final String PAGINA_TIMEOUT = "/timeout.jsp";
    public static final String PAGINA_AUTENTICACAO = "/AuthenticationServlet";
    public static final String PAGINA_INICIAL = "/faces/principal.jsf";
    public static final String PAGINA_CONTEXTO_ATIVIDADE = "/faces/contexto/contexto.jsf";

    public static final String MENU_REDIRECT_KEY = "page";

    public static final String OK = "ok";
    public static final String ERRO = "error";

    public static final String RPT_SESSION_KEY = "reportSessionKey";
    public static final String SECURITY_SESSION_KEY = "securitySessionKey";
    public static final String EMPRESA_SESSION_KEY = "empresaSessionKey";

    public static final String LABEL_PROPERTIES = "META-INF/label";

    public static final String SEC_ATRIBUTO_PERMISSAO = "permissao";

    public static final String regExpCNPJ = "^\\d{2}.?\\d{3}.?\\d{3}/?\\d{4}-?\\d{2}$";
    public static final String regExpCNPJOnlyNumber = "^\\d{14}$";
    public static final String regExpCPF = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$";
    public static final String regExpCPFOnlyNumber = "^\\d{11}$";
    public static final String regExpCEP = "^\\d{5}\\-?\\d{3}$";
    public static final String regExpCEPOnlyNumber = "^\\d{8}$";
    public static final String regExpDateFull = "^\\d{2}/\\d{2}/\\d{4}$";
    public static final String regExpDateCompetence = "^\\d{2}/\\d{4}$";
    public static final String regExpTelefone = "^[0-9]{2}-[0-9]{4}-[0-9]{4}$";
    public static final String regExpTelefoneTela = "^(([\\(]{1}[0-9]{2}[\\)]{1}){0,1}[0-9]{3,5}[\\-]{1}){0,1}[0-9]{4}$";
    public static final String regExpTelefoneOnlyNumber = "^\\d{10}$";
    public static final String regExpEmail = "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$";
    public static final String regExpURL = "^((http:\\/\\/www\\.)|(www\\.)|(http:\\/\\/))[a-zA-Z0-9._-]+\\.[a-zA-Z.]{2,5}$";
    public static final String regExpEmpty = ".*[^\\s].*";
    public static final String regExpDescritivo = "[a-zA-Z0-9@]+";
    public static final String regExpTAreaAspasSimples250 = "[^']{0,250}";
    public static final String regExpTextAspasSimples100 = "[^']{0,100}";
    public static final String regExpTextAspasSimplesPtoVirgula100 = "[^';]{0,100}";

    public static final int COMBO_ANO_MES_ANO_INICIO = 2013;
    public static final int COMBO_ANO_MES_MES_INICIO = 1;

    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_CSV = "text/txt";

    public static final String PARAMETRO_QTD_MESES_DIAGNOSTICO_FILIAL = "QTD_MESES_DIAGNOSTICO_FILIAL";
    public static final int QTD_MESES_DEFAULT_DIAGNOSTICO_FILIAL = 6;

    /**
     * Erro
     */
    public static final String ERROR = "ERRO";

    public static final String MSG_IMPORTACAO_ANALISAR_INCONSISTENCIAS = "msg_importacao_analisar_inconsistencias";
    public static final String ERRO_AO_TENTAR_CARREGAR_IMPORTACAO = "Erro ao tentar carregar importação.";
    public static final String MSG_IMPORTACAO_ARQUIVO_VAZIO = "msg_importacao_arquivo_vazio";
    public static final String TEMPO_DE_EXECUCAO_DA_VALIDACAO_DE_IMPORTACAO_DE_MANUTENCAO_FUNCIONARIOS =
                    "Tempo de execução da importação de manutenção funcionarios: ";
    public static final String HEADER_SUCESSO = "header_sucesso";
    public static final String AVISO = "aviso";
    public static final String MSG_IMPORTACAO_SUCESSO = "msg_importacao_sucesso";
    public static final String TAMANHO_DO_ARRAY = "Tamanho do Array: ";
    public static final String QUANTIDADE_SALVO_COM_SUCESSO = "Quantidade Salvo com sucesso: ";
    public static final String QUANTIDADE_INSUCESSO = "Quantidade Insucesso: ";
    public static final String PROGRESSO = "Progresso: ";
    public static final String SEGUNDOS_E_02D_MILISEGUNDOS = "%02d segundos  e %02d milisegundos";
    public static final String TEMPO_DE_EXECUCAO_DA_VALIDACAO_DE_IMPORTACAO_DE_FUNCIONARIOS =
                    "Tempo de execução da importação de funcionarios: ";

    public static final int CEM_PORCENTO = 100;

}

