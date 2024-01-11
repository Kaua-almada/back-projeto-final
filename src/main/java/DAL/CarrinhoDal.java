package DAL;

import Domain.Carrinho;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDal {
    public static Connection conectar() {
        Connection conexao = null;

        System.out.println();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";
            String usuario = "user";
            String senha = "123456";

            conexao = DriverManager.getConnection(url, usuario, senha);

            if (conexao != null) {
                return conexao;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("O erro foi " + e);
        }
        return conexao;
    }

    // Inserir - create
    public int inserirProduto(String title, String value, String image, String description) throws SQLException {
        String sql = "INSERT INTO carrinho (title, value, image, description) VALUES (?, ?, ?, ?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, value);
            statement.setString(3, image);
            statement.setString(4, description);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram modificadas " + linhasAfetadas + " no banco de dados");

            return linhasAfetadas;
        } catch (SQLException e) {
            System.out.println("O erro na inserção de dados foi: " + e);
            conexao.close();
        }
        conexao.close();
        return linhasAfetadas;
    }

    public List<Carrinho> listarProdutos() throws SQLException{
        String sql = "SELECT * FROM carrinho";
        ResultSet result = null;

        List<Carrinho> carrinhoArray = new ArrayList<>();

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            result = statement.executeQuery();
            System.out.println("Listagem dos produtos:");

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String value = result.getString("value");
                String image = result.getString("image");
                String description = result.getString("description");

                // Use o construtor correto com o id
                Carrinho product = new Carrinho(id, title, value, image, description);
                carrinhoArray.add(product);

                System.out.println("id:" + id);
                System.out.println("title:" + title);
                System.out.println("value:" + value);
                System.out.println("image:" + image);
                System.out.println("description:" + description);
                System.out.println(" ");
            }

            result.close();
            System.out.println("Aqui está o que está vindo do meu banco pelo Dal " + carrinhoArray);
            return carrinhoArray;
        } catch (SQLException e) {
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return carrinhoArray;
    }

    public int atualizarProduto(String title, String value, String image, String description, int id) throws SQLException {
        String sql = "UPDATE carrinho SET title = ?, value = ?, image = ?, description = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2, value);
            statement.setString(3, image);
            statement.setString(4, description);
            statement.setInt(5, id);

            linhasAfetadas = statement.executeUpdate();
            System.out.println("Foram modificadas " + linhasAfetadas + " no banco de dados");
            return linhasAfetadas;
        } catch (SQLException e) {
            System.out.println("O erro na atualização de dados foi: " + e);
        }
        return linhasAfetadas;
    }

    public int excluirProduto(int id) throws SQLException {
        String sql = "DELETE FROM carrinho WHERE id = ?";
        int linhasAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            statement.setInt(1, id);

            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram modificadas " + linhasAfetadas + " no banco de dados");
        } catch (SQLException e) {
            System.out.println("O erro na exclusão de dados foi: " + e);
        }

        return linhasAfetadas;
    }
}
