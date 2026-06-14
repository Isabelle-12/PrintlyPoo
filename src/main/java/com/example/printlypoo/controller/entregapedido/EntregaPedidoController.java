package com.example.printlypoo.controller.entregapedido;

import com.example.printlypoo.model.entregapedido.EntregaPedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaPedidoController {

    private static final String ARQUIVO = "data/entregas.txt";

    public List<EntregaPedido> listar() {
        List<EntregaPedido> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(EntregaPedido.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void salvarTodos(List<EntregaPedido> lista) {
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (EntregaPedido ep : lista) {
                bw.write(ep.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(EntregaPedido ep) {
        List<EntregaPedido> lista = listar();
        lista.add(ep);
        salvarTodos(lista);
    }

    public void atualizar(int indice, EntregaPedido ep) {
        List<EntregaPedido> lista = listar();
        lista.set(indice, ep);
        salvarTodos(lista);
    }

    public void excluir(int indice) {
        List<EntregaPedido> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }
}
