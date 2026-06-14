package com.example.printlypoo.controller.historicostatus;

import com.example.printlypoo.model.historicostatus.HistoricoStatusPedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoStatusPedidoController {

    private static final String ARQUIVO = "data/historicostatus.txt";

    // Lê todos os registros de histórico do arquivo e retorna como lista
    public List<HistoricoStatusPedido> listar() {
        List<HistoricoStatusPedido> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(HistoricoStatusPedido.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Sobrescreve o arquivo com a lista completa de históricos
    private void salvarTodos(List<HistoricoStatusPedido> lista) {
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (HistoricoStatusPedido h : lista) {
                bw.write(h.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adiciona um novo registro de histórico ao arquivo
    public void inserir(HistoricoStatusPedido h) {
        List<HistoricoStatusPedido> lista = listar();
        lista.add(h);
        salvarTodos(lista);
    }

    // Atualiza o registro de histórico na posição indicada
    public void atualizar(int indice, HistoricoStatusPedido h) {
        List<HistoricoStatusPedido> lista = listar();
        lista.set(indice, h);
        salvarTodos(lista);
    }

    // Remove o registro de histórico na posição indicada
    public void excluir(int indice) {
        List<HistoricoStatusPedido> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }

    // Retorna apenas os registros de histórico de um pedido específico
    public List<HistoricoStatusPedido> listarPorPedido(String idPedido) {
        List<HistoricoStatusPedido> todos = listar();
        List<HistoricoStatusPedido> filtrados = new ArrayList<>();
        for (HistoricoStatusPedido h : todos) {
            if (h.getidPedido().equals(idPedido)) {
                filtrados.add(h);
            }
        }
        return filtrados;
    }
}
;;
