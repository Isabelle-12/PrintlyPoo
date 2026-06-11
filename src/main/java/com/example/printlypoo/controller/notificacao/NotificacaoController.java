package com.example.printlypoo.controller;

import com.example.printlypoo.model.Notificacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoController {

    private List<Notificacao> notificacoes = new ArrayList<>();
    private int proximoId = 1;

    private final String ARQUIVO = "data" + File.separator + "notificacoes.dat";

    public NotificacaoController() {
        this.notificacoes = carregar();
        if (!notificacoes.isEmpty()) {
            this.proximoId = notificacoes.stream()
                    .mapToInt(Notificacao::getId).max().orElse(0) + 1;
        }
    }
    // CREATE
    public Notificacao criarNotificacao(String tipo, String titulo, String mensagem, String email) {
        try {
            Notificacao n = new Notificacao(proximoId, tipo, titulo, mensagem, email);
            notificacoes.add(n);
            proximoId++;

            salvar(); // ADICIONADO

            return n;

        } catch (Exception e) {
            System.out.println("Erro ao criar notificação: " + e.getMessage());
            return null;
        }
    }

    // READ (todas)
    public List<Notificacao> listarNotificacoes() {
            return notificacoes;
    }

    // READ (por id)
    public Notificacao buscarPorId(int id) {
        for (Notificacao n : notificacoes) {
            if (n.getId() == id) return n;
        }
        return null;
    }

    // UPDATE
    public void atualizarNotificacao(int id, String tipo, String titulo, String mensagem, String email) {
        try {
            Notificacao n = buscarPorId(id);

            if (n == null) {
                throw new IllegalArgumentException("Notificação inexistente");
            }

            n.setTipo(tipo);
            n.setTitulo(titulo);
            n.setMensagem(mensagem);
            n.setEmailDestino(email);

            salvar(); // ADICIONADO

        } catch (Exception e) {
            System.out.println("Erro ao atualizar notificação: " + e.getMessage());
        }
    }

    // MARCAR COMO LIDA
    public void marcarComoLida(int id) {
        try {
            Notificacao n = buscarPorId(id);
            if (n == null) throw new IllegalArgumentException("Notificação não encontrada");
            n.marcarComoLida();
            salvar();
        } catch (Exception e) {
            System.out.println("Erro ao marcar como lida: " + e.getMessage());
        }
    }

    // DELETE
    public void removerNotificacao(int id) {
        try {
            Notificacao n = buscarPorId(id);

            if (n == null) {
                throw new IllegalArgumentException("Notificação não encontrada");
            }

            notificacoes.remove(n);

            salvar(); // ADICIONADO

        } catch (Exception e) {
            System.out.println("Erro ao remover notificação: " + e.getMessage());
        }
    }

    // =========================
    // PERSISTÊNCIA (ADICIONADO)
    // =========================

    private void salvar() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {

            oos.writeObject(notificacoes);

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        } finally {
            System.out.println("Operação de escrita finalizada.");
        }
    }

    private List<Notificacao> carregar() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(ARQUIVO))) {

            return (List<Notificacao>) ois.readObject();

        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Versão incompatível do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}