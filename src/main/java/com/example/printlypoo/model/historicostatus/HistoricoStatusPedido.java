package com.example.printlypoo.model.historicostatus;

public class HistoricoStatusPedido {

    private String id;
    private String idPedido;
    private String statusAnterior;
    private String novoStatus;
    private String observacao;
    private String dataHoraMudanca;

    public HistoricoStatusPedido(String id, String idPedido, String statusAnterior,
                                 String novoStatus, String observacao, String dataHoraMudanca) {
        this.id = id;
        this.idPedido = idPedido;
        this.statusAnterior = statusAnterior;
        this.novoStatus = novoStatus;
        this.observacao = observacao;
        this.dataHoraMudanca = dataHoraMudanca;
    }

    // Serializa o objeto em uma única linha de texto para persistência em arquivo
    public String paraTexto() {
        return id + "|" + idPedido + "|" + statusAnterior + "|"
                + novoStatus + "|" + observacao + "|" + dataHoraMudanca;
    }

    // Desserializa uma linha de texto lida do arquivo para um objeto HistoricoStatusPedido
    public static HistoricoStatusPedido deTexto(String linha) {
        String[] partes = linha.split("\\|", -1);
        return new HistoricoStatusPedido(
                partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]
        );
    }

    // Getters
    public String getid()               { return id; }
    public String getidPedido()         { return idPedido; }
    public String getstatusAnterior()   { return statusAnterior; }
    public String getnovoStatus()       { return novoStatus; }
    public String getobservacao()       { return observacao; }
    public String getdataHoraMudanca()  { return dataHoraMudanca; }

    // Setters
    public void setid(String id)                         { this.id = id; }
    public void setidPedido(String idPedido)             { this.idPedido = idPedido; }
    public void setstatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    public void setnovoStatus(String novoStatus)         { this.novoStatus = novoStatus; }
    public void setobservacao(String observacao)         { this.observacao = observacao; }
    public void setdataHoraMudanca(String d)             { this.dataHoraMudanca = d; }
}
