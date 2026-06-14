package com.example.printlypoo.controller.projeto3d;

import com.example.printlypoo.model.projeto3d.Projeto3D;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Projeto3DController {

    private static final String ARQUIVO = "data/projetos3d.txt";

    public List<Projeto3D> listar() {
        List<Projeto3D> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(Projeto3D.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Projeto3D> listarPorUsuario(String usuario) {
        List<Projeto3D> doUsuario = new ArrayList<>();
        for (Projeto3D p : listar()) {
            if (p.getUsuario().equalsIgnoreCase(usuario)) {
                doUsuario.add(p);
            }
        }
        return doUsuario;
    }

    private void salvarTodos(List<Projeto3D> lista) {
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Projeto3D p : lista) {
                bw.write(p.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Projeto3D p) {
        List<Projeto3D> lista = listar();
        lista.add(p);
        salvarTodos(lista);
    }

    public void atualizar(int indice, Projeto3D p) {
        List<Projeto3D> lista = listar();
        lista.set(indice, p);
        salvarTodos(lista);
    }

    public void excluir(int indice) {
        List<Projeto3D> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }
}
