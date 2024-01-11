package controller;
import DAL.UserDal;
import Domain.Usuarios;
import Services.RespostaEndPoint;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersController {

    public static class user implements HttpHandler {
        public static String response = "";

        RespostaEndPoint res = new RespostaEndPoint();
        List<Domain.Usuarios> usersList = new ArrayList<>();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "";
            JSONObject responseJson = new JSONObject();
            if ("GET".equals(exchange.getRequestMethod())) {
                doGet(exchange);

            } else if ("POST".equals(exchange.getRequestMethod())) {
                doPost(exchange);

            } else if ("PUT".equals(exchange.getRequestMethod())) {
                doPut(exchange);

            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                doDelete(exchange);

            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                doOptions(exchange);

            } else {
                response = "ERRO" + "o metodo utilizado foi " + exchange.getRequestMethod();
                res.enviarResponse(exchange, response, 405);
            }
        }

        public static void doGet(HttpExchange exchange)throws IOException{
            RespostaEndPoint res = new RespostaEndPoint();
            List<Domain.Usuarios> usersList = new ArrayList<>();
            UserDal userDal = new UserDal();
            JSONObject json;
            Usuarios usuarios = new Usuarios();

            List<Domain.Usuarios> getAllFromArray = Domain.Usuarios.getAllUser(usersList);
                try{
                    usersList = userDal.listarUsuario();
                    json = usuarios.arraytoJson(usersList);

                    res.enviarResponseJson(exchange,json,200);
                }catch (Exception e){
                    System.out.println("o erro foi" +e);
                    response = "ouve um problema ao conectar";
                }

                for(Domain.Usuarios users : getAllFromArray){
                    System.out.println("name: " + users.getName());
                    System.out.println("last name: " + users.getLastname());
                    System.out.println("email: " + users.getemail());
                    System.out.println("passeword: " + users.getpasseword());
                    System.out.println("cpf: " + users.getcpf());
                    System.out.println();
                    System.out.println("-------------------------");
                    System.out.println();
                }
//                res.enviarResponseJson(exchange, usersJson.arraytoJson(getAllFromArray), 200);
            }
        }
        public static void doPost(HttpExchange exchange) throws IOException {
            String response = "";
            RespostaEndPoint res = new RespostaEndPoint();
            List<Usuarios> usersList = new ArrayList<>();

            UserDal userDal = new UserDal();

            try (InputStream resquestBody = exchange.getRequestBody()) {

                JSONObject json = new JSONObject(new String(resquestBody.readAllBytes()));
                int resp = 0;

               Usuarios user = new Usuarios(
                        json.getString("name"),
                        json.getString("lastName"),
                        json.getString("email"),
                        json.getString("passeword"),
                        json.getString("cpf")
                );

                usersList.add(user);

                System.out.println("usersList controller" + user.toJson());

                resp = userDal.inserirUsuarios(user.name, user.lastName, user.email, user.passeword, user.cpf);



                if (resp == 0) {
                    response = "houve um problema ao criar o usuarios";
                } else {
                    response = "Usuario criado com sucesso";
                }

                res.enviarResponseJson(exchange, user.toJson(), 200);
            } catch (IOException | SQLException e) {
                System.out.println("erro no controler " + e );
            }
        }
        public static void doPut(HttpExchange exchange) throws IOException {
            RespostaEndPoint res = new RespostaEndPoint();
            UserDal userDal = new UserDal();
            String response = "";
            int linhasAfetadas = 0;
            int resp = 0;
            int id = 1;

            try(InputStream resquestBody = exchange.getRequestBody()){
                JSONObject json = new JSONObject(new String(resquestBody.readAllBytes()));

                Usuarios usuarios = new Usuarios(
                        json.getInt("id"),
                        json.getString("name"),
                        json.getString("lastName"),
                        json.getString("email"),
                        json.getString("passeword"),
                        json.getString("cpf")
                );
                resp = userDal.atualizarUsuario(usuarios.name, usuarios.lastName, usuarios.email, usuarios.passeword, usuarios.cpf,id);

                if (linhasAfetadas > 0) {
                    response = "Usuário atualizado com sucesso";
                    res.enviarResponse(exchange, response, 200);
                } else {
                    response = "Falha ao atualizar o usuário";
                    res.enviarResponse(exchange, response, 400);
                }
            } catch (SQLException | NumberFormatException e) {
                System.out.println("O erro foi:" + e);
                response = "Erro durante a atualização do usuário";
                res.enviarResponse(exchange, response, 500);
            }
        }
        public static void doDelete(HttpExchange exchange) throws IOException{
            RespostaEndPoint res = new RespostaEndPoint();
            UserDal userDal = new UserDal();
            int id = 1;

            String response = "";

            try(InputStream resquestBody = exchange.getRequestBody()){

                JSONObject json  = new JSONObject(new String(resquestBody.readAllBytes()));

                int resp = userDal.excluirUsuario(id);


            }catch (Exception e){
                System.out.println("o erro foi:" + e);
            }

            response = "essa e a rota de Poducts Delete";
            res.enviarResponse(exchange, response, 200);
        }
        public static void doOptions(HttpExchange exchange) throws IOException {
            String response ="";

            exchange.sendResponseHeaders(200,-1);
            exchange.close();
            return;
        }
    }

