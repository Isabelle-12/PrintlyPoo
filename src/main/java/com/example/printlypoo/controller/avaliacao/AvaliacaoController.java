package com.example.printlypoo.controller;

import com.example.printlypoo.model.Avaliacao;
import com.example.printlypoo.model.ValidacaoException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoController {

    private List<Avaliacao> avaliacoes = new ArrayList<>();
    private int proximoId = 1;

    private final String ARQUIVO = "data" + File.separator + "avaliacoes.dat";

    public AvaliacaoController() {
        this.avaliacoes = carregar();
        this.proximoId = avaliacoes.stream()
                .mapToInt(Avaliacao::getId).max().orElse(0) + 1;
    }
    // CREATE
    public Avaliacao criarAvaliacao(int nota, String comentario) {
        try {
            Avaliacao a = new Avaliacao(proximoId, nota, comentario);
            avaliacoes.add(a);
            proximoId++;
            salvar(); // ADICIONADO

            return a;

        } catch (Exception e) {
            System.out.println("Erro ao criar avaliação: " + e.getMessage());
            return null;
        }
    }

    // READ (todas)
    public List<Avaliacao> listarAvaliacoes() {
            return avaliacoes;
    }

    // READ (por id)
    public Avaliacao buscarPorId(int id) {
        for (Avaliacao a : avaliacoes) {
            if (a.getId() == id) return a;
        }
        return null;
    }


    // UPDATE
    public void atualizarAvaliacao(int id, int novaNota, String novoComentario) {
        try {
            Avaliacao a = buscarPorId(id);

            if (a == null) {
                throw new IllegalArgumentException("Avaliação inexistente");
            }

            a.setNota(novaNota);
            a.setComentarioCliente(novoComentario);

            salvar(); // ADICIONADO

        } catch (ValidacaoException e) {
            System.out.println("Nota inválida: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao atualizar avaliação: " + e.getMessage());
        }
    }

    // DELETE
    public void removerAvaliacao(int id) {
        try {
            Avaliacao a = buscarPorId(id);

            if (a == null) {
                throw new IllegalArgumentException("Avaliação não encontrada");
            }

            avaliacoes.remove(a);

            salvar(); // ADICIONADO

        } catch (Exception e) {
            System.out.println("Erro ao remover avaliação: " + e.getMessage());
        }
    }

    // =========================
    // PERSISTÊNCIA (ADICIONADO)
    // =========================

    private void salvar() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {

            oos.writeObject(avaliacoes);

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        } finally {
            System.out.println("Operação de escrita finalizada.");
        }
    }

    private List<Avaliacao> carregar() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(ARQUIVO))) {

            return (List<Avaliacao>) ois.readObject();

        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Versão incompatível do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}