package senac.java;

import Services.Servidor;
import Services.ConexaoSqlServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor();
//        ConexaoSqlServer conexaoSqlServer = new ConexaoSqlServer();
        servidor.piServer();
        }
}
