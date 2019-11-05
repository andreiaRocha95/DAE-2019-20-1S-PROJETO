package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPartners",
                query = "SELECT p FROM Partner p ORDER BY p.name" // JPQL
        )
})

public class Partner extends User {

    public Partner() {
    }

    public Partner(int userId, String name, String password, String email) {
        super(userId, name, password, email);
    }
}
