package com.example.printlypoo.controller.fabricante;

import com.example.printlypoo.model.fabricante.MaterialMaker;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialMakerController {

    private static final String ARQUIVO = "data" + File.separator + "materiais.txt";

    public void salvarMaterial(String id, String tipo, double preco) {
        List<MaterialMaker> lista = listarMateriais();
        lista.add(new MaterialMaker(id, tipo, preco));
        salvarLista(lista);
    }


    public void atualizarMaterial(String id, String novoTipo, double novoPreco) {
        List<MaterialMaker> lista = listarMateriais();
        for (MaterialMaker m : lista) {
            if (m.getIdFabricante().equals(id)) {
                m.setTipoMaterial(novoTipo);
                m.setPrecoPorGrama(novoPreco);
                break;
            }
        }
        salvarLista(lista);
    }


    public void excluirMaterial(String id) {
        List<MaterialMaker> lista = listarMateriais();
        lista.removeIf(m -> m.getIdFabricante().equals(id));
        salvarLista(lista);
    }

    private void salvarLista(List<MaterialMaker> lista) {
        File arquivo = new File(ARQUIVO);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao salvar materiais: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<MaterialMaker> listarMateriais() {
        List<MaterialMaker> lista = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (!file.exists()) return lista;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            lista = (List<MaterialMaker>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler materiais: " + e.getMessage());
        }
        return lista;
    }
}