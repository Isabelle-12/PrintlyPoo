package com.example.printlypoo.controller.fabricante;

import com.example.printlypoo.model.fabricante.MaterialMaker;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialMakerController {

    private static final String ARQUIVO = "Printlypoo/data" + File.separator + "materiais.txt";

    public void salvarMaterial(String id, String tipo, double preco) {

        List<MaterialMaker> lista = listarMateriais();


        lista.add(new MaterialMaker(id, tipo, preco));


        File arquivo = new File(ARQUIVO);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        //Grava a lista inteira atualizada usando ObjectOutputStream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(lista);
            System.out.println("Material salvo com sucesso usando ObjectOutputStream!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar material: " + e.getMessage());
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