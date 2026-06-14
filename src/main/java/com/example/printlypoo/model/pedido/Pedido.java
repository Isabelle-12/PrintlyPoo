package com.example.printlypoo.model.pedido;

public class Pedido {

    private String id;
    private String fabricante;
    private String material;
    private String quantidade;
    private String valorTotal;
    private String status;
    private String motivoRecusa;
    private String enderecoEntrega;
    private String prazoPedido;
    private String dataSolicitacao;

    public Pedido(String id, String fabricante, String material, String quantidade,
                  String valorTotal, String status, String motivoRecusa,
                  String enderecoEntrega, String prazoPedido, String dataSolicitacao) {
        this.id = id;
        this.fabricante = fabricante;
        this.material = material;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.status = status;
        this.motivoRecusa = motivoRecusa;
        this.enderecoEntrega = enderecoEntrega;
        this.prazoPedido = prazoPedido;
        this.dataSolicitacao = dataSolicitacao;
    }

    // Serializa o objeto em uma única linha de texto para persistência em arquivo
    public String paraTexto() {
        return id + "|" + fabricante + "|" + material + "|" + quantidade + "|"
                + valorTotal + "|" + status + "|" + motivoRecusa + "|"
                + enderecoEntrega + "|" + prazoPedido + "|" + dataSolicitacao;
    }

    // Desserializa uma linha de texto lida do arquivo para um objeto Pedido
    public static Pedido deTexto(String linha) {
        String[] partes = linha.split("\\|", -1);
        return new Pedido(
                partes[0], partes[1], partes[2], partes[3], partes[4],
                partes[5], partes[6], partes[7], partes[8], partes[9]
        );
    }

    // Getters
    public String getid()              { return id; }
    public String getfabricante()      { return fabricante; }
    public String getmaterial()        { return material; }
    public String getquantidade()      { return quantidade; }
    public String getvalorTotal()      { return valorTotal; }
    public String getstatus()          { return status; }
    public String getmotivoRecusa()    { return motivoRecusa; }
    public String getenderecoEntrega() { return enderecoEntrega; }
    public String getprazoPedido()     { return prazoPedido; }
    public String getdataSolicitacao() { return dataSolicitacao; }

    // Setters
    public void setid(String id)                       { this.id = id; }
    public void setfabricante(String fabricante)       { this.fabricante = fabricante; }
    public void setmaterial(String material)           { this.material = material; }
    public void setquantidade(String quantidade)       { this.quantidade = quantidade; }
    public void setvalorTotal(String valorTotal)       { this.valorTotal = valorTotal; }
    public void setstatus(String status)               { this.status = status; }
    public void setmotivoRecusa(String motivoRecusa)   { this.motivoRecusa = motivoRecusa; }
    public void setenderecoEntrega(String e)           { this.enderecoEntrega = e; }
    public void setprazoPedido(String prazoPedido)     { this.prazoPedido = prazoPedido; }
    public void setdataSolicitacao(String d)           { this.dataSolicitacao = d; }
}
