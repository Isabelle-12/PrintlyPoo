package com.example.printlypoo.model.anuncio;

public class AnuncioGlobal {

    private String titulo;
    private String mensagem;
    private String dataInicio;  // formato DD/MM/AAAA
    private String dataFim;     // formato DD/MM/AAAA
    private String status;      // "ATIVO" ou "INATIVO"

    public AnuncioGlobal() {}

    public AnuncioGlobal(String titulo, String mensagem,
                         String dataInicio, String dataFim,
                         String status) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    // Converte o objeto para uma linha de texto
    public String paraTexto() {
        return titulo + "|" + mensagem + "|" +
                dataInicio + "|" + dataFim + "|" + status;
    }

    // Cria um objeto a partir de uma linha de texto
    public static AnuncioGlobal deTexto(String linha) {
        String[] partes = linha.split("\\|");
        return new AnuncioGlobal(
                partes[0], // titulo
                partes[1], // mensagem
                partes[2], // dataInicio
                partes[3], // dataFim
                partes[4]  // status
        );
    }


    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return titulo + " | " + status + " | " + dataInicio + " até " + dataFim;
    }
}