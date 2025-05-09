package org.example;

public class Emprestimo {

    private int codigo;
    private String tituloLivro;
    private String nomeAutor;
    private String dataEntrega;

    public Emprestimo(int codigo, String tituloLivro, String nomeAutor, String dataEntrega) {
        this.codigo = codigo;
        this.tituloLivro = tituloLivro;
        this.nomeAutor = nomeAutor;
        this.dataEntrega = dataEntrega;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "codigo=" + codigo +
                ", tituloLivro='" + tituloLivro + '\'' +
                ", nomeAutor='" + nomeAutor + '\'' +
                ", dataEntrega='" + dataEntrega + '\'' +
                '}';
    }
}