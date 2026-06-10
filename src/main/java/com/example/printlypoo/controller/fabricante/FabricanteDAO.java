package com.example.printlypoo.controller.fabricante;

import com.example.printlypoo.model.fabricante.Fabricante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FabricanteDAO {

    private static final String ARQUIVO = "data/fabricantes.dat";

    private void salvarTodos(List<Fabricante> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Fabricante> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Fabricante>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar fabricantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private int proximoId() {
        return listarTodos().stream().mapToInt(Fabricante::getId).max().orElse(0) + 1;
    }

    public void inserir(Fabricante fabricante) throws IOException {
        List<Fabricante> lista = listarTodos();
        fabricante.setId(proximoId());
        lista.add(fabricante);
        salvarTodos(lista);
    }

    public boolean atualizar(Fabricante atualizado) throws IOException {
        List<Fabricante> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                lista.set(i, atualizado);
                salvarTodos(lista);
                return true;
            }
        }
        return false;
    }

    public boolean excluir(int id) throws IOException {
        List<Fabricante> lista = listarTodos();
        boolean removido = lista.removeIf(f -> f.getId() == id);
        if (removido) salvarTodos(lista);
        return removido;
    }

    public Fabricante buscarPorId(int id) {
        return listarTodos().stream()
                .filter(f -> f.getId() == id)
                .findFirst().orElse(null);
    }

    public Fabricante buscarPorCnpj(String cnpj) {
        return listarTodos().stream()
                .filter(f -> f.getCnpj().equals(cnpj))
                .findFirst().orElse(null);
    }
}
