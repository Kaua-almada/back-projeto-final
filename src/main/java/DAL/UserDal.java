package DAL;
import Domain.Usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDal {
    public static Connection conectar(){
        Connection conexao = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";
            String usuario = "user";
            String senha = "123456";

            conexao = DriverManager.getConnection(url,usuario,senha);

            if(conexao != null){
                return conexao;
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("o erro foi "+ e);
        }
//        finally {
//            try {
//                if(conexao != null && !conexao.isClosed()){
//                    conexao.close();
//                }
//            }catch(SQLException e){
//                System.out.println("o erro no finally foi " + e);
//            }
            return conexao;
        }


    // Inserir - create
    public int inserirUsuarios(String name, String lastName, String email, String passeword, String cpf) throws SQLException {
        String sql = "INSERT INTO usuarios (name,lastName,email,passeword,cpf) VALUES(?,?,?,?,?)";
        int linhasAfetadas = 0;
        Connection conexao = conectar();

        System.out.println("o banco esta "+ conexao);
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            System.out.println("O ERRO NO TRY FOI " + statement);
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setString(3,email);
            statement.setString(4,passeword);
            statement.setString(5,cpf);

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

    public List listarUsuario()throws SQLException{
        String sql = "SELECT * FROM usuarios";
        ResultSet result = null;

        List<Usuarios> userArray = new ArrayList<>();

        try(PreparedStatement statement = conectar().prepareStatement(sql)){


            result = statement.executeQuery();
            System.out.println("Listagem dos Usuarios:");

            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                String email = result.getString("email");
                String passeword = result.getString("passeword");
                String cpf = result.getString("cpf");


                Usuarios currentUser = new Usuarios(id,name, lastName,email,passeword,cpf);

                userArray.add(currentUser);


            }
            result.close();
            return userArray;

        }catch (SQLException e){
            System.out.println("O erro na listagem de dados foi: " + e);
        }
                return userArray;
    }

   // Connection conexao,int id, String name, String lastName, String email, String passeword, String cpf
    public int atualizarUsuario(String name, String lastName, String email, String passeword, String cpf, int id) throws SQLException{
        String sql = "UPDATE usuarios SET name = ?,lastName = ?, email = ?, passeword = ?, cpf ? WHERE id = ?";
        int linahsAfetadas = 0;

        try (PreparedStatement statement = conectar().prepareStatement(sql)){
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setString(3,email);
            statement.setString(4,passeword);
            statement.setString(5,cpf);
            statement.setInt(6,id);

            int linhasAfetadaas = statement.executeUpdate();
            System.out.println("Foram modificadas "+ linhasAfetadaas + " no banco de dados");
            return linhasAfetadaas;
        }
        catch (SQLException e){
            System.out.println("O erro na listagem de dados foi: " + e);
        }
        return linahsAfetadas;
    }
    public int excluirUsuario(int id) throws  SQLException{
        String sql = "DELETE FROM usuarios WHERE id = ?";
        int linhasAfetadas = 0 ;

        try (PreparedStatement statement = conectar().prepareStatement(sql)){
            statement.setInt(1,id);

             linhasAfetadas = statement.executeUpdate();

            System.out.println("Foram modificadas "+ linhasAfetadas + " no banco de dados");;
        }catch (SQLException e){
            System.out.println("O erro na exclusao de dados foi: " + e);
        }

        return linhasAfetadas;
    }
}
