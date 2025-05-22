package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.io.*;

public class elefantecontroler {

        private elefante elefante1;

        @FXML
        private Button btelefante1;

        @FXML
        private Button btpergunta;

        @FXML
        private TextArea txtchat;

        @FXML
        private TextField txtidade;

        @FXML
        private TextField txtpeso;

        @FXML
        private TextArea txtresultado;

        @FXML
        private TextField txttamanho;

        @FXML
        void exibirdados(ActionEvent event) {
                try {
                        elefante1 = new elefante(
                                Integer.valueOf(txtidade.getText()),
                                Double.valueOf(txttamanho.getText()),
                                Integer.valueOf(txtpeso.getText())
                        );

                        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/thoma/IdeaProjects/banco_classes.db");

                        String createTableSQL = "CREATE TABLE IF NOT EXISTS elefante (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "idade INTEGER, " +
                                "tamanho REAL, " +
                                "peso INTEGER" +
                                ");";
                        conn.createStatement().execute(createTableSQL);

                        String insertSQL = "INSERT INTO elefante (idade, tamanho, peso) VALUES (?, ?, ?)";
                        PreparedStatement stmt = conn.prepareStatement(insertSQL);
                        stmt.setInt(1, elefante1.getIdade());
                        stmt.setDouble(2, elefante1.getTamanho());
                        stmt.setInt(3, elefante1.getPeso());

                        stmt.executeUpdate();

                        stmt.close();
                        conn.close();

                        txtresultado.setText(elefante1.exibirdados() + "\n\nElefante salvo no banco!");

                        txttamanho.setText("");
                        txtidade.setText("");
                        txtpeso.setText("");

                        new Thread(() -> {
                                String dadosBanco = obterDadosElefantesDoBanco();
                                String promptAtualizacao = "Atualize seu contexto com esses dados da tabela elefante:\n\n" + dadosBanco;
                                perguntarAoOllama(promptAtualizacao);
                        }).start();

                } catch (Exception e) {
                        e.printStackTrace();
                        txtresultado.setText("Erro ao inserir no banco: " + e.getMessage());
                }
        }

        private String obterDadosElefantesDoBanco() {
                StringBuilder sb = new StringBuilder();

                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/thoma/IdeaProjects/banco_classes.db");
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT idade, tamanho, peso FROM elefante")) {

                        while (rs.next()) {
                                sb.append("Idade: ").append(rs.getInt("idade")).append(" anos, ");
                                sb.append("Tamanho: ").append(rs.getDouble("tamanho")).append(" m, ");
                                sb.append("Peso: ").append(rs.getInt("peso")).append(" kg\n");
                        }

                } catch (SQLException e) {
                        sb.append("Erro ao ler o banco: ").append(e.getMessage());
                }

                return sb.toString();
        }

        private String perguntarAoOllama(String prompt) {
                StringBuilder resposta = new StringBuilder();

                try {
                        ProcessBuilder builder = new ProcessBuilder("ollama", "run", "qwen2.5:3b");
                        builder.redirectErrorStream(true);

                        Process processo = builder.start();

                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(processo.getOutputStream()));
                        writer.write(prompt);
                        writer.newLine();
                        writer.flush();
                        writer.close();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
                        String linha;
                        while ((linha = reader.readLine()) != null) {
                                linha = linha
                                        .replaceAll("\u001B\\[[;\\d]*[a-zA-Z]", "")
                                        .replaceAll("\\[\\?\\d+[hl]", "")
                                        .replaceAll("[\\x00-\\x1F\\x7F]", "")
                                        .replaceAll("[\\p{C}]", "")
                                        .replaceAll("[\\u2800-\\u28FF]", "");

                                resposta.append(linha).append("\n");
                        }

                        reader.close();
                        processo.waitFor();

                } catch (IOException | InterruptedException e) {
                        resposta.append("Erro ao se comunicar com o Ollama: ").append(e.getMessage());
                }

                return resposta.toString().trim();
        }

        @FXML
        void perguntar(ActionEvent event) {
                String pergunta = txtchat.getText();
                if (pergunta == null || pergunta.isBlank()) {
                        txtresultado.setText("Digite uma pergunta para a IA.");
                        return;
                }

                txtresultado.setText("Pergunta enviada à IA... Aguarde");

                new Thread(() -> {
                        String dadosBanco = obterDadosElefantesDoBanco();

                        String prompt = "Se for útil, aqui esta o banco de dados da tabela elefante. :\n\n" +
                                dadosBanco +
                                "\n\nsó inclua os dados do banco se a pergunta estiver relacionada com o banco, agora desconsidere o diálogo até aqui e responda normalmente à pergunta a seguir de forma direta.:\n" +
                                pergunta;

                        String resposta = perguntarAoOllama(prompt);

                        javafx.application.Platform.runLater(() -> {
                                txtresultado.setText(resposta);
                        });
                }).start();
        }
}