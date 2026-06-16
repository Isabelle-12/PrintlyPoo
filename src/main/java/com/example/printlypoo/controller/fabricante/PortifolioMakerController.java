package com.example.printlypoo.controller.fabricante;

import com.example.printlypoo.model.fabricante.PortifolioMaker;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PortifolioMakerController {
    private static final String ARQUIVO = "data" + File.separator + "portifolio.txt";

    public void salvarPortifolio(String titulo, String descricao, String caminhoImagem) {
        List<PortifolioMaker> lista = listarPortifolios();
        lista.add(new PortifolioMaker(titulo, descricao, caminhoImagem));
        salvarLista(lista);
    }

    public void atualizarPortifolio(String titulo, String novaDescricao, String novoCaminho) {
        List<PortifolioMaker> lista = listarPortifolios();
        for (PortifolioMaker p : lista) {
            if (p.getTitulo().equals(titulo)) {
                p.setDescricaoTecnica(novaDescricao);
                p.setCaminhoImagem(novoCaminho);
                break;
            }
        }
        salvarLista(lista);
    }

    public void excluirPortifolio(String titulo) {
        List<PortifolioMaker> lista = listarPortifolios();
        lista.removeIf(p -> p.getTitulo().equals(titulo));
        salvarLista(lista);
    }

    private void salvarLista(List<PortifolioMaker> lista) {
        File arquivo = new File(ARQUIVO);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar portifólio: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<PortifolioMaker> listarPortifolios() {
        List<PortifolioMaker> lista = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (!file.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<PortifolioMaker>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler portifólio: " + e.getMessage());
        }
        return lista;
    }
}