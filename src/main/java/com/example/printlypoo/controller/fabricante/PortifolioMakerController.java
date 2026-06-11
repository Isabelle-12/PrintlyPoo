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

        File arquivo = new File(ARQUIVO);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar portifolio: " + e.getMessage());
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
            System.out.println("Erro ao ler portifolio: " + e.getMessage());
        }
        return lista;
    }
}