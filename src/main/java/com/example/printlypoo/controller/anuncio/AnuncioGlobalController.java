package com.example.printlypoo.controller.anuncio;

import com.example.printlypoo.model.anuncio.AnuncioGlobal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnuncioGlobalController {

    private static final String ARQUIVO = "data/anuncios.txt";

    public List<AnuncioGlobal> listar() {
        List<AnuncioGlobal> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(AnuncioGlobal.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void salvarTodos(List<AnuncioGlobal> lista) {

        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (AnuncioGlobal a : lista) {
                bw.write(a.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(AnuncioGlobal a) {
        List<AnuncioGlobal> lista = listar();
        lista.add(a);
        salvarTodos(lista);
    }

    public void atualizar(int indice, AnuncioGlobal a) {
        List<AnuncioGlobal> lista = listar();
        lista.set(indice, a);
        salvarTodos(lista);
    }

    public void excluir(int indice) {
        List<AnuncioGlobal> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }
}