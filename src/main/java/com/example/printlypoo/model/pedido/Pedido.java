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

    public Pedido() {}

    public Pedido(String id, String fabricante, String material, String quantidade, String valorTotal, String status, String motivoRecusa, String enderecoEntrega, String prazoPedido, String dataSolicitacao) {
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


    public String paraTexto() {
        return id + "|" + fabricante + "|" + material + "|" + quantidade + "|" + valorTotal + "|" + status + "|" + motivoRecusa + "|" + enderecoEntrega + "|" + prazoPedido + "|" + dataSolicitacao ;
    }


    public static Pedido deTexto(String linha) {
        String[] partes = linha.split("\\|");
        return new Pedido(
                partes[0], // id
                partes[1], // fabricante
                partes[2], // material
                partes[3], // quantidade
                partes[4], // valorTotal
                partes[5], // status
                partes[6], // motivoRecusa
                partes[7], // enderecoEntrega
                partes[8], // prazoPedido
                partes[9] // dataSolicitacao

        );
    }

    // Getters e Setters de todos os atributos
    public String getid() { return id; }
    public void setid(String id) { this.id = id; }

    public String getfabricante() { return fabricante; }
    public void setfabricante(String fabricante) { this.fabricante = fabricante; }

    public String getmaterial() { return material; }
    public void setmaterial(String material) { this.material = material; }

    public String getquantidade() { return quantidade; }
    public void setquantidade(String quantidade) { this.quantidade = quantidade; }

    public String getvalorTotal() { return valorTotal; }
    public void setvalorTotal(String valorTotal) { this.valorTotal = valorTotal; }

    public String getstatus() { return status; }
    public void setstatus(String status) { this.status = status; }

    public String getmotivoRecusa() { return motivoRecusa; }
    public void setmotivoRecusa(String motivoRecusa) { this.motivoRecusa = motivoRecusa; }

    public String getenderecoEntrega() { return enderecoEntrega; }
    public void setenderecoEntrega(String enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    public String getprazoPedido() { return prazoPedido; }
    public void setprazoPedido(String prazoPedido) { this.prazoPedido = prazoPedido; }

    public String getdataSolicitacao() { return dataSolicitacao; }
    public void setdataSolicitacao(String dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }



    @Override
    public String toString() {
        return id + " | " + status ;
    }
}