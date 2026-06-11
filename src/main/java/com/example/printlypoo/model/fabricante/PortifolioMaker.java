package com.example.printlypoo.model.fabricante;

import java.io.Serializable;

public class PortifolioMaker implements Serializable {
    private String titulo;
    private String descricaoTecnica;
    private String caminhoImagem;

    public PortifolioMaker(String titulo, String descricaoTecnica, String caminhoImagem) {
        this.titulo = titulo;
        this.descricaoTecnica = descricaoTecnica;
        this.caminhoImagem = caminhoImagem;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricaoTecnica() { return descricaoTecnica; }
    public void setDescricaoTecnica(String descricaoTecnica) { this.descricaoTecnica = descricaoTecnica; }

    public String getCaminhoImagem() { return caminhoImagem; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }
}