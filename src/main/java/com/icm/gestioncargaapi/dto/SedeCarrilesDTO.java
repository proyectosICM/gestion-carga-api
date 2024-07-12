package com.icm.gestioncargaapi.dto;

public class SedeCarrilesDTO {
    private Long sedeId;
    private Long carriles;

    public SedeCarrilesDTO(Long sedeId, Long carriles) {
        this.sedeId = sedeId;
        this.carriles = carriles;
    }

    public Long getSedeId() {
        return sedeId;
    }

    public void setSedeId(Long sedeId) {
        this.sedeId = sedeId;
    }

    public Long getCarriles() {
        return carriles;
    }

    public void setCarriles(Long carriles) {
        this.carriles = carriles;
    }
}
