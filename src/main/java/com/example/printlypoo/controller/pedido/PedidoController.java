package com.example.printlypoo.controller.pedido;

import com.example.printlypoo.model.pedido.Pedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    private static final String ARQUIVO = "data/pedido.txt";

    // Lê todos os pedidos do arquivo e retorna como lista
    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(Pedido.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Sobrescreve o arquivo com a lista completa de pedidos
    private void salvarTodos(List<Pedido> lista) {
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Pedido u : lista) {
                bw.write(u.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adiciona um novo pedido ao arquivo
    public void inserir(Pedido u) {
        List<Pedido> lista = listar();
        lista.add(u);
        salvarTodos(lista);
    }

    // Atualiza o pedido na posição indicada
    public void atualizar(int indice, Pedido u) {
        List<Pedido> lista = listar();
        lista.set(indice, u);
        salvarTodos(lista);
    }

    // Remove o pedido na posição indicada
    public void excluir(int indice) {
        List<Pedido> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }

    // Atualiza apenas o campo status de um pedido identificado pelo ID
    public void atualizarStatus(String idPedido, String novoStatus) {
        List<Pedido> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getid().equals(idPedido)) {
                lista.get(i).setstatus(novoStatus);
                salvarTodos(lista);
                return;
            }
        }
    }
}
