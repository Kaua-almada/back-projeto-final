package controller;

import DAL.CarrinhoDal;
import Domain.Carrinho;
import Services.RespostaEndPoint;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoController {
    private static List<Carrinho> carrinhoArray = new ArrayList<>();
    static RespostaEndPoint res = new RespostaEndPoint();

    public static class ProdutosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                doGet(exchange);
            } else if ("POST".equals(exchange.getRequestMethod())) {
                doPost(exchange);
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                // Handle PUT request
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                doDelete(exchange);
            } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
                return;
            } else {
                // Handle other methods
            }
        }

        public static void doGet(HttpExchange exchange) throws IOException {

            CarrinhoDal carrinhoDal = new CarrinhoDal();
            Carrinho carrinho = new Carrinho();
            List<Carrinho> carrinhoList  = new ArrayList<>();;
            JSONObject json;

            try {

                carrinhoList = carrinhoDal.listarProdutos();
                json = carrinho.arrayToJson(carrinhoList);

                System.out.println("resultado do carrinho list" + carrinhoList);
                System.out.println( "json resultado: "+ json);


                res.enviarResponseJson(exchange, json , 200);

            }catch (Exception e){
                System.out.println("erro no controller o erro foi" + e);

                String response = "ouve um problema ao conectar";
            }

//            if (!carrinhoArray.isEmpty()) {
//                for (Carrinho product : carrinhoArray) {
//                    System.out.println("title: " + product.getTitle());
//                    System.out.println("value: " + product.getValue());
//                    System.out.println("image: " + product.getImage());
//                    System.out.println("description: " + product.getDescription());
//                    System.out.println("-------------------------");
//                    System.out.println();
//                }
//            }
        }
        public static void doPost(HttpExchange exchange)throws IOException{
            int resp = 0;
            try (InputStream requestBody = exchange.getRequestBody()) {
                JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                CarrinhoDal carrinhoDal = new CarrinhoDal();

                Carrinho product = new Carrinho(
                        json.getString("title"),
                        json.getString("value"),
                        json.getString("image"),
                        json.getString("description")
                );
                carrinhoArray.add(product);
                res.enviarResponseJson(exchange, product.toJson(), 200);
                System.out.println("productsList: " + product.toJson());

                resp = carrinhoDal.inserirProduto(product.title, product.value, product.image, product.description);
            } catch (Exception e) {
                String responseJson = e.toString();
                // res.enviarResponseJson(exchange, responseJson, 200);
            }
        }


        public static void doDelete(HttpExchange exchange)throws IOException{
            int resp = 0;
            try (InputStream requestBody = exchange.getRequestBody()) {
                JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
                CarrinhoDal carrinhoDal = new CarrinhoDal();

                Carrinho product = new Carrinho(
                        json.getInt("id")
                );
                carrinhoArray.add(product);
                res.enviarResponseJson(exchange, product.toJson(), 200);
                System.out.println("productsList: " + product.toJson());

                resp = carrinhoDal.excluirProduto(product.id);
            } catch (Exception e) {
                String responseJson = e.toString();
                // res.enviarResponseJson(exchange, responseJson, 200);
            }
        }
    }
}

