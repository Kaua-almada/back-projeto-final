package Services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoSqlServer{
    public static Connection conectar(){
        Connection conexao = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://localhost:1433;databaseName=pi;trustServerCertificate=true";

            String usuario = "user";
            String senha = "123456";

            conexao = DriverManager.getConnection(url,  usuario, senha);

            if(conexao != null){
                System.out.println("Conecao com o banco feita com sucesso");
            }
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("o erro foi "+ e);
        }finally {
            try {
                if(conexao != null && !conexao.isClosed()){
                    conexao.close();
                }
            }catch(SQLException e){
                 System.out.println("o erro no finally foi " + e);
            }
        }
        return conexao;
    }
}
