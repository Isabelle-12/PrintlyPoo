package com.example.printlypoo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Notificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String tipo;
    private String titulo;
    private String mensagem;
    private String emailDestino;
    private LocalDateTime dataEnvio;
    private boolean lida;

    public Notificacao(int id, String tipo, String titulo, String mensagem, String emailDestino) {
            setId(id);
            setTipo(tipo);
            setTitulo(titulo);
            setMensagem(mensagem);
            setEmailDestino(emailDestino);

            this.dataEnvio = LocalDateTime.now();
            this.lida = false;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public boolean isLida() {
        return lida;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        this.id = id;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Tipo inválido");
        }
        this.tipo = tipo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido");
        }
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("Mensagem inválida");
        }
        this.mensagem = mensagem;
    }

    public void setEmailDestino(String emailDestino) {
        if (emailDestino == null || !emailDestino.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.emailDestino = emailDestino;
    }

    public void marcarComoLida() {
            this.lida = true;
    }
}