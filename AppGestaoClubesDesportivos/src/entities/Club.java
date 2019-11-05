package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllClubs",
                query = "SELECT c FROM Club c ORDER BY c.name" // JPQL
        )
})
public class Club implements Serializable {
    @Id
    private int clubCode;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Athlete> athletes;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Coach> coachs;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private Set<Modality> modalities;

    public Club() {
        athletes = new LinkedList<Athlete>();
        modalities = new LinkedHashSet<Modality>();
        coachs = new LinkedList<Coach>();
    }

    public Club(int clubCode, String name) {
        this.clubCode = clubCode;
        this.name = name;
        this.modalities = new LinkedHashSet<Modality>();
        this.athletes = new LinkedList<Athlete>();
        this.coachs = new LinkedList<Coach>();
    }

    public int getClubCode() {
        return clubCode;
    }

    public void setClubCode(int clubCode) {
        this.clubCode = clubCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(Set<Modality> modalities) {
        this.modalities = modalities;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public List<Coach> getCoachs() {
        return coachs;
    }

    public void setCoachs(List<Coach> coachs) {
        this.coachs = coachs;
    }

    public void addModality(Modality modality) {
        if (modality == null) {
            return;
        }
        if (modalities.contains(modality)) {
            System.out.println("Modality already exists!");
        } else {
            modalities.add(modality);
        }

    }

    public void removeModality(Modality modality) {
        if (modality == null) {
            return;
        }
        if (!(modalities.contains(modality))) {
            System.out.println("Modality dos not  exist!");
        } else {
            modalities.remove(modality);
        }

    }

    public void addAthlete(Athlete athlete) {
        if (athlete == null) {
            return;
        }
        if (athletes.contains(athlete)) {
            System.out.println("Athlete already exists!");
        } else {
            athletes.add(athlete);
        }
    }

    public void removeAthlete(Athlete athlete) {
        if (athlete == null) {
            return;
        }
        if (!(athletes.contains(athlete))) {
            System.out.println("Athlete dos not  exist!");
        } else {
            athletes.remove(athlete);
        }

    }

    public void addCoach(Coach coach) {
        if (coach == null) {
            return;
        }
        if (coachs.contains(coach)) {
            System.out.println("Coach already exists!");
        } else {
            coachs.add(coach);
        }
    }

    public void removeCoach(Coach coach) {
        if (coach == null) {
            return;
        }
        if (!(coachs.contains(coach))) {
            System.out.println("Coach dos not  exist!");
        } else {
            coachs.remove(coach);
        }

    }

}
