package com.example.printlypoo.model.fabricante;

import java.io.Serializable;

public class MaterialMaker implements Serializable {
    private String idFabricante;
    private String tipoMaterial;
    private double precoPorGrama;

    public MaterialMaker(String idFabricante, String tipoMaterial, double precoPorGrama) {
        this.idFabricante = idFabricante;
        this.tipoMaterial = tipoMaterial;
        this.precoPorGrama = precoPorGrama;
    }

    public String getIdFabricante() { return idFabricante; }
    public void setIdFabricante(String idFabricante) { this.idFabricante = idFabricante; }

    public String getTipoMaterial() { return tipoMaterial; }
    public void setTipoMaterial(String tipoMaterial) { this.tipoMaterial = tipoMaterial; }

    public double getPrecoPorGrama() { return precoPorGrama; }
    public void setPrecoPorGrama(double precoPorGrama) { this.precoPorGrama = precoPorGrama; }

    @Override
    public String toString() {
        return tipoMaterial + " - R$ " + precoPorGrama + "/g";
    }
}