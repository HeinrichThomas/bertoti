+-------------------+
|      Main         |
+-------------------+
| + main(args): void|
+-------------------+
         |
         | usa
         v
+-------------------------------------------------------+
|                     Biblioteca                        |
+-------------------------------------------------------+
| - emprestimos: List<Emprestimo>                       |
+-------------------------------------------------------+
| + adicionarEmprestimo(e: Emprestimo): void            |
| + listarEmprestimos(): List<Emprestimo>               |
| + buscarEmprestimos(titulo: String): List<Emprestimo> |
+-------------------------------------------------------+
         ^
         | contem (0..*)
+--------------------------+
|       Emprestimo         |
+--------------------------+
| - codigo: int            |
| - tituloLivro: String    |
| - nomeAutor: String      |
| - dataEntrega: String    |
+--------------------------+
| + Emprestimo(c, t, a, d) |
| + getters/setters        |
+--------------------------+