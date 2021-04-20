package br.com.viavarejo.menu;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class MenuBean {


  private String includedPage = "principal.jsf";

  public MenuBean() {}

  public String getPage() {
    return this.includedPage;
  }

  public void setPageName() {
    this.includedPage = this.includedPage;
  }

  public void goNav() {
//    FacesContext context = FacesContext.getCurrentInstance();
//    String selectedPageViewId =
//        context.getExternalContext().getRequestParameterMap().get("pageViewId");
//    if (selectedPageViewId.equalsIgnoreCase("page1")) {
     // this.includedPage = "employee.jsf";
//    }

    try {
      FacesContext.getCurrentInstance().getExternalContext().redirect("employee.jsf");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  //  return "employee";
  }

  public String getIncludedPage() {
    return this.includedPage;
  }

  public void setIncludedPage(String includedPage) {
    this.includedPage = includedPage;
  }


}
