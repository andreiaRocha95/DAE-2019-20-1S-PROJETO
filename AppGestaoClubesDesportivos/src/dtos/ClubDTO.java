package dtos;

public class ClubDTO {
    private int clubCode;
    private String name;

    public ClubDTO() {
    }

    public ClubDTO(int clubCode, String name) {
        this.clubCode = clubCode;
        this.name = name;
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
}
