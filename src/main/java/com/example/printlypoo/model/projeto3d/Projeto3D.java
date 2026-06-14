package com.example.printlypoo.model.projeto3d;

public class Projeto3D {

    private String usuario;
    private String nome;
    private String descricao;
    private String caminhoArquivo;
    private String formato;
    private double volume;
    private double peso;
    private String status;

    public Projeto3D() {}

    public Projeto3D(String usuario, String nome, String descricao,
                     String caminhoArquivo, String formato,
                     double volume, double peso, String status) {
        this.usuario = usuario;
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoArquivo = caminhoArquivo;
        this.formato = formato;
        this.volume = volume;
        this.peso = peso;
        this.status = status;
    }

    public String paraTexto() {
        return usuario + "|" + nome + "|" + descricao + "|" + caminhoArquivo + "|" +
                formato + "|" + volume + "|" + peso + "|" + status;
    }

    public static Projeto3D deTexto(String linha) {
        String[] partes = linha.split("\\|");
        return new Projeto3D(
                partes[0],                     // usuario
                partes[1],                     // nome
                partes[2],                     // descricao
                partes[3],                     // caminhoArquivo
                partes[4],                     // formato
                Double.parseDouble(partes[5]), // volume
                Double.parseDouble(partes[6]), // peso
                partes[7]                      // status
        );
    }

    // Getters e Setters
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCaminhoArquivo() { return caminhoArquivo; }
    public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return nome + " | " + formato + " | " + status;
    }
}
