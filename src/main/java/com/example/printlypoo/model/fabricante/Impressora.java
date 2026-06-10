package com.example.printlypoo.model.fabricante;
import java.io.Serializable;

public class Impressora implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idFabricante;
    private String modelo;
    private double volumeMaximo; // cm³
    private String materiais;
    private int quantidade;

    public Impressora() {}

    public Impressora(int id, int idFabricante, String modelo,
                      double volumeMaximo, String materiais, int quantidade) {
        this.id = id;
        this.idFabricante = idFabricante;
        this.modelo = modelo;
        this.volumeMaximo = volumeMaximo;
        this.materiais = materiais;
        this.quantidade = quantidade;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdFabricante() { return idFabricante; }
    public void setIdFabricante(int idFabricante) { this.idFabricante = idFabricante; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getVolumeMaximo() { return volumeMaximo; }
    public void setVolumeMaximo(double volumeMaximo) { this.volumeMaximo = volumeMaximo; }

    public String getMateriais() { return materiais; }
    public void setMateriais(String materiais) { this.materiais = materiais; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return modelo;
    }
}
