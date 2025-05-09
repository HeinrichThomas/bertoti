package org.example;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<Emprestimo> emprestimos = new ArrayList<>();

    public void registrarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public List<Emprestimo> buscarPorTitulo(String titulo) {
        List<Emprestimo> encontrados = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getTituloLivro().equalsIgnoreCase(titulo)) {
                encontrados.add(e);
            }
        }
        return encontrados;
    }
}