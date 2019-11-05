package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "getAllAthletes",
                query = "SELECT a FROM Athlete a ORDER BY a.name" // JPQL
        )
})

@Entity
public class Athlete extends Partner {

    @ManyToOne
    @JoinColumn(name = "CLUB_CODE")
    @NotNull
    private Club club;

    @ManyToMany(mappedBy = "athletes")
    private List<Modality> modalities;

    public Athlete() {
        modalities = new LinkedList<Modality>();
    }

    public Athlete(int userId, String name, String password, String email, Club club) {
        super(userId, name, password, email);
        this.club = club;
        this.modalities = new LinkedList<Modality>();

    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Modality> getModalities() {
        return modalities;
    }

    public void setModalities(List<Modality> modalities) {
        this.modalities = modalities;
    }

    public void addModality(Modality modality) {
        if (modality == null) {
            return;
        }
        if (modalities.contains(modality)) {
            System.out.println("Impossivel adicionar. Subject já se encontra adicionado.");
        } else {
            modalities.add(modality);
        }

    }

    public void removeModality(Modality modality) {
        if (modality == null) {
            return;
        }
        if (!(modalities.contains(modality))) {
            System.out.println("Impossivel remover. Subject não existe.");
        } else {
            modalities.remove(modality);
        }

    }

}
