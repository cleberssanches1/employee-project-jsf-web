package br.com.viavarejo.menu;

import java.util.Collection;

/**
 * Classe que contm um a representao do menu para o grupo especifco
 * 
 */
public class Menu implements Comparable<Menu> {

  private EnumFuncionalidade item;
  private Collection<Menu> collFilhos;

  public Menu(EnumFuncionalidade item) {
    setItem(item);
  }

  public void setItem(EnumFuncionalidade item) {
    this.item = item;
  }

  public EnumFuncionalidade getItem() {
    return item;
  }

  public void setCollFilhos(Collection<Menu> collFilhos) {
    this.collFilhos = collFilhos;
  }

  public Collection<Menu> getCollFilhos() {
    return collFilhos;
  }

  public int compareTo(Menu o) {
    return (new Integer(this.getItem().getOrdem())).compareTo(new Integer(o.getItem().getOrdem()));
  }

}
