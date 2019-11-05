package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity

@NamedQueries({
        @NamedQuery(
                name = "getAllCoaches",
                query = "SELECT c FROM Coach c ORDER BY c.name" // JPQL
        )
})


public class Coach extends User {

    @ManyToOne
    @JoinColumn(name = "CLUB_CODE")
    @NotNull
    private Club club;

    @ManyToMany(mappedBy = "coaches")
    private List<Modality> modalities;

    public Coach() {
        modalities = new LinkedList<Modality>();
    }

    public Coach(int userId, String password, String name, String email, Club club) {
        super(userId, password, name, email);
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


}
