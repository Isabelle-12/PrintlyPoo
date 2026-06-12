package com.example.printlypoo.controller.pedido;
import com.example.printlypoo.model.pedido.Pedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoController {

    private static final String ARQUIVO = "data/pedido.txt";


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


    private void salvarTodos(List<Pedido> lista) {
        // Cria a pasta data se não existir
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


    public void inserir(Pedido u) {
        List<Pedido> lista = listar();
        lista.add(u);
        salvarTodos(lista);
    }


    public void atualizar(int indice, Pedido u) {
        List<Pedido> lista = listar();
        lista.set(indice, u);
        salvarTodos(lista);
    }


    public void excluir(int indice) {
        List<Pedido> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }
}