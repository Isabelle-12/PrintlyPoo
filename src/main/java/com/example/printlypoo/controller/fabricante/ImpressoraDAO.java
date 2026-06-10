    package com.example.printlypoo.controller.fabricante;

    import com.example.printlypoo.model.fabricante.Impressora;

    import java.io.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.stream.Collectors;

    public class ImpressoraDAO {

        private static final String ARQUIVO = "data/impressoras.txt";

        private void salvarTodos(List<Impressora> lista) throws IOException {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(ARQUIVO))) {
                oos.writeObject(lista);
            }
        }

        @SuppressWarnings("unchecked")
        public List<Impressora> listarTodos() {
            File arquivo = new File(ARQUIVO);
            if (!arquivo.exists()) return new ArrayList<>();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                return (List<Impressora>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar impressoras: " + e.getMessage());
                return new ArrayList<>();
            }
        }

        private int proximoId() {
            return listarTodos().stream().mapToInt(Impressora::getId).max().orElse(0) + 1;
        }

        public void inserir(Impressora impressora) throws IOException {
            List<Impressora> lista = listarTodos();
            impressora.setId(proximoId());
            lista.add(impressora);
            salvarTodos(lista);
        }

        public boolean atualizar(Impressora atualizada) throws IOException {
            List<Impressora> lista = listarTodos();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == atualizada.getId()) {
                    lista.set(i, atualizada);
                    salvarTodos(lista);
                    return true;
                }
            }
            return false;
        }

        public boolean excluir(int id) throws IOException {
            List<Impressora> lista = listarTodos();
            boolean removido = lista.removeIf(imp -> imp.getId() == id);
            if (removido) salvarTodos(lista);
            return removido;
        }

        public void excluirPorFabricante(int idFabricante) throws IOException {
            List<Impressora> lista = listarTodos();
            lista.removeIf(imp -> imp.getIdFabricante() == idFabricante);
            salvarTodos(lista);
        }

        public List<Impressora> listarPorFabricante(int idFabricante) {
            return listarTodos().stream()
                    .filter(imp -> imp.getIdFabricante() == idFabricante)
                    .collect(Collectors.toList());
        }

        public Impressora buscarPorId(int id) {
            return listarTodos().stream()
                    .filter(imp -> imp.getId() == id)
                    .findFirst().orElse(null);
        }
    }