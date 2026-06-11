package com.example.printlypoo.model;

import java.io.Serializable;

public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int nota; // 1 a 5
    private String comentarioCliente;
    private String respostaFabricante;

    public Avaliacao(int id, int nota, String comentarioCliente) throws ValidacaoException {
            setId(id);
            setNota(nota);
            setComentarioCliente(comentarioCliente);
            this.respostaFabricante = null;
    }

    public int getId() {
        return id;
    }

    public int getNota() {
        return nota;
    }

    public String getComentarioCliente() {
        return comentarioCliente;
    }

    public String getRespostaFabricante() {
        return respostaFabricante;
    }

    public void setNota(int nota) throws ValidacaoException {
        if (nota < 1 || nota > 5) {
            throw new ValidacaoException("Nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public void setComentarioCliente(String comentarioCliente) {
        if (comentarioCliente == null || comentarioCliente.isBlank()) {
            throw new IllegalArgumentException("Comentário inválido");
        }
        this.comentarioCliente = comentarioCliente;
    }

    public void responderFabricante(String resposta) {
        if (resposta == null || resposta.isBlank()) {
            throw new IllegalArgumentException("Resposta inválida");
        }
        this.respostaFabricante = resposta;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        this.id = id;
    }

}
