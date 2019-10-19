package ejbs;

import entities.Administrador;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {

    @EJB
    private AdministradorBean adminBean;


    @PostConstruct
    public void populateDB(){
        System.out.println("Popula BD");

       try{
           adminBean.create(0101, "Andreia Rocha", "123456", "0101@gmail.com");
           adminBean.create(0102, "Jorge Manuel", "147258", "0102@gmail.com");
           adminBean.create(0103, "Maria Rita", "369258", "0103@gmail.com");
           adminBean.create(0104, "Carlos Sampaio", "123586", "0104@gmail.com");
           adminBean.create(0105, "Sara Jane", "987654", "0105@gmail.com");
           adminBean.create(0106, "Gustavo Teixeira", "159753", "01026@gmail.com");

       }  catch (EJBException e) {
           System.out.println("Erro: " + e.getMessage());
       }


    }

}
