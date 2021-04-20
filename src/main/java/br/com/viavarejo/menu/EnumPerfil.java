package br.com.viavarejo.menu;

public enum EnumPerfil {
  P5BA("Usuarios do Modulo", EnumPermission.P5BA),
  P5BB("11  SC-RH Benefícios", EnumPermission.P5BB),
  P5BC("RH-Benefícios", EnumPermission.P5BC),
  P5BD("CSC-Contabilidade", EnumPermission.P5BD),
  P5BE("TI-RH", EnumPermission.P5BE),
  P5BF("Gestão de Acessos", EnumPermission.P5BF),
  P5BG("Operações de Loja", EnumPermission.P5BG),
  P5BH("SC-RH Folha de Pagamento", EnumPermission.P5BH),
  P5BI("Central de Atendimento", EnumPermission.P5BI),
  P5BJ("Colaboradores Jurídico", EnumPermission.P5BJ),
  P5BK("Documentos Jurídico", EnumPermission.P5BK) ,
  P5CB("Gestor responsavel pela revisao de acesso", EnumPermission.P5CB) ,
  P5CC("Gestao de Acessos", EnumPermission.P5CC) ,
  P5CD("Governança de TI", EnumPermission.P5CD) ,
  P5CF("Parametrização CIPA", EnumPermission.P5CF) ,
  P5S8("Revisão de Acessos Gestor",EnumPermission.P5S8),
  P5S9("Revisão de Acessos Gestão",EnumPermission.P5S9),
  P5S7("Auditoria",EnumPermission.P5S7),
  P5OF("Consulta Gestor Imediato", EnumPermission.P5OF),
  P5OB("Consulta Cargo para Cotas", EnumPermission.P5OB),
  P5OG("Registro de Presença", EnumPermission.P5OG),
  P5OC("Alteração Cargo para Cotas", EnumPermission.P5OC),
  P5OH("Marcaçãod e Ponto", EnumPermission.P5OH),
  P5OJ("Controle de Desligamento", EnumPermission.P5OJ),
  P5CG("Acompanhamento Menu Cipa", EnumPermission.P5CG),
  P5BN("Relatórios de colaboradores ADP", EnumPermission.P5BN);

  private String descricao;
  private EnumPermission permissao;

  private EnumPerfil(final String descricao, final EnumPermission permissao) {
    this.descricao = descricao;
    this.permissao = permissao;

  }

  public String getDescricao() {
    return this.descricao;
  }

  public EnumPermission getPermissao() {
    return this.permissao;
  }
}