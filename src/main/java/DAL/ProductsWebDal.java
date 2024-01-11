package DAL;
import Domain.ProdutosWeb;
import Domain.Usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsWebDal {
    public static Connection conectar() {
        Connection conexao = null;
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
        String sql = "INSERT INTO produtos (title, value, image, description) VALUES (?, ?, ?, ?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        System.out.println("o banco esta "+ conexao);
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            System.out.println("O ERRO NO TRY FOI " + statement);
            statement.setString(1,title);
            statement.setString(2,value);
            statement.setString(3,image);
            statement.setString(4,description);


            linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram modificadas "+ linhasAfetadas + " no banco de dados");

            return linhasAfetadas;
        }
        catch(SQLException e){
            System.out.println("o banco esta (NO CATCH) "+ conexao);
            System.out.println("O erro na inserçao de dados NO CATCH FOI: " + e);
            conexao.close();
        }
        conexao.close();
        return linhasAfetadas;
    }

    public List<ProdutosWeb> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos";
        List<ProdutosWeb> productList = new ArrayList<>();

        try (PreparedStatement statement = conectar().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String value = result.getString("value");
                String image = result.getString("image");
                String description = result.getString("description");

                ProdutosWeb product = new ProdutosWeb(id, title, value, image, description);
                productList.add(product);

                System.out.println("id:" + id);
                System.out.println("title:" + title);
                System.out.println("value:" + value);
                System.out.println("image:" + image);
                System.out.println("description:" + description);
                System.out.println(" ");

                ProdutosWeb currentUser = new ProdutosWeb(id,title, value,image,description);

                productList.add(currentUser);
            }

            return productList;
        } catch (SQLException e){
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return productList;
    }

    public int atualizarProduto(String title, String value, String image, String description, int id) throws SQLException {
        String sql = "UPDATE produtos SET title = ?, value = ?, image = ?, description = ? WHERE id = ?";
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
        String sql = "DELETE FROM produtos WHERE id = ?";
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
