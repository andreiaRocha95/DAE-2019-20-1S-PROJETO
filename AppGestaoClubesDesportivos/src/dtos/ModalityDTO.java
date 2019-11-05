package dtos;

public class ModalityDTO {
    private int modalityCode;
    private String name;
    private int clubCode;

    public ModalityDTO() {
    }

    public ModalityDTO(int modalityCode, String name, int clubCode) {
        this.modalityCode = modalityCode;
        this.name = name;
        this.clubCode = clubCode;
    }

    public int getModalityCode() {
        return modalityCode;
    }

    public void setModalityCode(int modalityCode) {
        this.modalityCode = modalityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClubCode() {
        return clubCode;
    }

    public void setClubCode(int clubCode) {
        this.clubCode = clubCode;
    }
}
