package com.example.printlypoo.controller.usuario;
import com.example.printlypoo.model.usuario.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    private static final String ARQUIVO = "data/usuarios.txt";


    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    lista.add(Usuario.deTexto(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }


    private void salvarTodos(List<Usuario> lista) {
        // Cria a pasta data se não existir
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario u : lista) {
                bw.write(u.paraTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void inserir(Usuario u) {
        List<Usuario> lista = listar();
        lista.add(u);
        salvarTodos(lista);
    }


    public void atualizar(int indice, Usuario u) {
        List<Usuario> lista = listar();
        lista.set(indice, u);
        salvarTodos(lista);
    }


    public void excluir(int indice) {
        List<Usuario> lista = listar();
        lista.remove(indice);
        salvarTodos(lista);
    }
}