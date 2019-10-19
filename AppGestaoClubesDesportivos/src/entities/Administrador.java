package entities;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a ORDER BY a.nome"
        )
})
public class Administrador implements Serializable {

    @Id
    private int userId;

    @NotNull
    private String nome;

    @NotNull
    private String password;

    @NotNull
    private String email;

    public Administrador() {
    }

    public Administrador(int userId, String nome, String password, String email) {
        this.userId = userId;
        this.nome = nome;
        this.password = password;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
