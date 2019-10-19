package dtos;

public class AdministradorDTO {
    private int userId;
    private String nome;
    private String password;
    private String email;

    public AdministradorDTO() {
    }

    public AdministradorDTO(int userId, String nome, String password, String email) {
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
