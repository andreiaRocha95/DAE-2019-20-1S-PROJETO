package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllModalities",
                query = "SELECT m FROM Modality m ORDER BY m.club.name DESC, m.name"
        )
})

public class Modality implements Serializable {
    @Id
    private int modalityCode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "CLUB_CODE")
    private Club club;

    @ManyToMany
    @JoinTable(name = "MODALITIES_ATHLETES",
            joinColumns = @JoinColumn(name = "MODALITY_CODE", referencedColumnName = "MODALITYCODE"),
            inverseJoinColumns = @JoinColumn(name = "ATHLETE_USERID", referencedColumnName =
                    "USERID"))
    private Set<Athlete> athletes;

    @ManyToMany
    @JoinTable(name = "MODALITIES_COACHES",
            joinColumns = @JoinColumn(name = "MODALITY_CODE", referencedColumnName = "MODALITYCODE"),
            inverseJoinColumns = @JoinColumn(name = "COACH_USERID", referencedColumnName =
                    "USERID"))
    private Set<Coach> coaches;

    @NotNull
    private String name;

    public Modality() {
        athletes = new LinkedHashSet<Athlete>();
        coaches = new LinkedHashSet<Coach>();
    }

    public Modality(int modalityCode, String name, Club club) {
        this.modalityCode = modalityCode;
        this.club = club;
        this.name = name;
        this.athletes = new LinkedHashSet<Athlete>();
        this.coaches = new LinkedHashSet<Coach>();
    }

    public int getModalityCode() {
        return modalityCode;
    }

    public void setModalityCode(int modalityCode) {
        this.modalityCode = modalityCode;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public void addAthlete(Athlete athlete) {
        if (athlete == null) {
            return;
        }
        if (athletes.contains(athlete)) {
            System.out.println("Impossivel adicionar. Estudante já se encontra adicionado.");
        } else {
            athletes.add(athlete);
        }

    }

    public void removeAthlete(Athlete athlete) {
        if (athlete == null) {
            return;
        }
        if (!(athletes.contains(athlete))) {
            System.out.println("Impossivel remover. Estudante não existe.");
        } else {
            athletes.remove(athlete);
        }

    }

    public void addCoach(Coach coach) {
        if (coach == null) {
            return;
        }
        if (coaches.contains(coach)) {
            System.out.println("Impossivel adicionar. Estudante já se encontra adicionado.");
        } else {
            coaches.add(coach);
        }

    }

    public void removeCoach(Coach coach) {
        if (coach == null) {
            return;
        }
        if (!(coaches.contains(coach))) {
            System.out.println("Impossivel remover. Estudante não existe.");
        } else {
            coaches.remove(coach);
        }

    }

}
