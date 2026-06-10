package com.example.printlypoo.model.fabricante;
import java.io.Serializable;

public class Fabricante implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String cnpj;
    private String nome;
    private String telefone;
    private String endereco;

    public Fabricante() {}

    public Fabricante(int id, String cnpj, String nome, String telefone, String endereco) {
        this.id = id;
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return nome + " (" + cnpj + ")";
    }
}
