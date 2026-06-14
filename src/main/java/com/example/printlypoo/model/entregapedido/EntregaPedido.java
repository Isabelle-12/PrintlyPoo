package com.example.printlypoo.model.entregapedido;

public class EntregaPedido {

    private String idPedido;
    private String caminhoArquivo;
    private String tipoEntrega;
    private String dataEnvio;

    public EntregaPedido() {}

    public EntregaPedido(String idPedido, String caminhoArquivo,
                         String tipoEntrega, String dataEnvio) {
        this.idPedido = idPedido;
        this.caminhoArquivo = caminhoArquivo;
        this.tipoEntrega = tipoEntrega;
        this.dataEnvio = dataEnvio;
    }

    public String paraTexto() {
        return idPedido + "|" + caminhoArquivo + "|" + tipoEntrega + "|" + dataEnvio;
    }

    public static EntregaPedido deTexto(String linha) {
        String[] partes = linha.split("\\|");
        return new EntregaPedido(
                partes[0], // idPedido
                partes[1], // caminhoArquivo
                partes[2], // tipoEntrega
                partes[3]  // dataEnvio
        );
    }

    // Getters e Setters
    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }

    public String getCaminhoArquivo() { return caminhoArquivo; }
    public void setCaminhoArquivo(String caminhoArquivo) { this.caminhoArquivo = caminhoArquivo; }

    public String getTipoEntrega() { return tipoEntrega; }
    public void setTipoEntrega(String tipoEntrega) { this.tipoEntrega = tipoEntrega; }

    public String getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(String dataEnvio) { this.dataEnvio = dataEnvio; }

    @Override
    public String toString() {
        return "Pedido " + idPedido + " | " + tipoEntrega + " | " + dataEnvio;
    }
}
