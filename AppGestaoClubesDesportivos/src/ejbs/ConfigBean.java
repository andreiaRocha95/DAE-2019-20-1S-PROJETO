package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {

    @EJB
    private AdministradorBean adminBean;

    @EJB
    ModalityBean modalityBean;
    @EJB
    private CoachBean coachBean;
    @EJB
    private AthleteBean athleteBean;
    @EJB
    private PartnerBean partnerBean;
    @EJB
    private ClubBean clubBean;

    @PostConstruct
    public void populateDB(){
        System.out.println("Popula BD");

       try{

           System.out.println("Creating Clubs......");
           clubBean.create(1, "Benfica");
           clubBean.create(2, "Sporting");

           System.out.println("Creating Admins......");
           adminBean.create(111111, "Andreia Rocha", "123456", "0101@gmail.com");
           adminBean.create(222222, "Jorge Manuel", "147258", "0102@gmail.com");
           adminBean.create(333333, "Maria Rita", "369258", "0103@gmail.com");

           System.out.println("Creating Coaches......");
           coachBean.create(444444, "Carlos Sampaio", "123586", "0104@gmail.com", 1);
           coachBean.create(555555, "Sara Jane", "987654", "0105@gmail.com", 1);
           coachBean.create(666666, "Gustavo Teixeira", "159753", "01026@gmail.com", 2);

           System.out.println("Creating Partners......");
           partnerBean.create(777777, "Jorge Ferreira", "123586", "0107@gmail.com");
           partnerBean.create(888888, "Tiago Neves", "987654", "0108@gmail.com");
           partnerBean.create(999999, "Rute Ribeiro", "159753", "01029@gmail.com");

           System.out.println("Creating Athletes......");
           athleteBean.create(121212, "Tatiana Pontes", "123586", "0100@gmail.com", 1);
           athleteBean.create(131313, "Ricardo Lopes", "987654", "01051@gmail.com", 1);
           athleteBean.create(141414, "Rayssa Silva", "159753", "01022@gmail.com", 2);

           System.out.println("Creating Modalities......");
           modalityBean.create(01, "Futebol", 1);
           modalityBean.create(02, "Andebol", 1);
           modalityBean.create(03, "Ginastica", 2);
           modalityBean.create(04, "Voleibol", 2);


           System.out.println("Enroling Athletes in Modalities......");
           athleteBean.enrollAthleteInModality(121212, 01);
           athleteBean.enrollAthleteInModality(121212, 02);
           athleteBean.enrollAthleteInModality(131313, 01);
           athleteBean.enrollAthleteInModality(131313, 02);

           athleteBean.enrollAthleteInModality(141414, 03);
           athleteBean.enrollAthleteInModality(141414, 04);

           System.out.println("Enroling Coaches in Modalities......");
           coachBean.enrollCoachInModality(444444, 01);
           coachBean.enrollCoachInModality(555555, 01);
           coachBean.enrollCoachInModality(666666, 03);

       } catch (Exception e) {
           System.out.println("Erro: " + e.getMessage());
       }


    }

}
