package org.example;

public class App {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.registrarEmprestimo(new Emprestimo(1, "Dom Casmurro", "Machado de Assis", "10/05/2025"));
        biblioteca.registrarEmprestimo(new Emprestimo(2, "Dom Casmurro", "Machado de Assis", "15/05/2025"));
        biblioteca.registrarEmprestimo(new Emprestimo(3, "A Moreninha", "Joaquim Manuel de Macedo", "12/05/2025"));

        System.out.println("Todos os empréstimos:");
        System.out.println(biblioteca.listarEmprestimos());

        System.out.println("\nBusca por título 'Dom Casmurro':");
        System.out.println(biblioteca.buscarPorTitulo("Dom Casmurro"));
    }
}