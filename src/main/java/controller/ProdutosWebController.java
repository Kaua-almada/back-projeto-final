package controller;

import DAL.ProductsWebDal;
import Domain.ProdutosWeb;
import Services.RespostaEndPoint;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProdutosWebController {

    private static List<ProdutosWeb> salesList = new ArrayList<>();
    private static RespostaEndPoint res = new RespostaEndPoint();

    static JSONObject responseJson = new JSONObject();

    public static class ProdutosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response;
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
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
                return;
            } else {
                response = "ERRO" + "o metodo utilizado foi " + exchange.getRequestMethod();
                RespostaEndPoint.enviarResponseJson(exchange, responseJson, 200);
            }
        }
    }

    public static void doGet(HttpExchange exchange) throws IOException {
        RespostaEndPoint res = new RespostaEndPoint();
        ProdutosWeb produtosWeb = new ProdutosWeb();
        ProductsWebDal productsWebDal = new ProductsWebDal();
        System.out.println("metodo get");
        List<ProdutosWeb> produtosWebsArray = new ArrayList<>();
        JSONObject json;

      try{
          produtosWebsArray = productsWebDal.listarProdutos();
          json = produtosWeb.arraytoJson(produtosWebsArray);
          res.enviarResponseJson(exchange, json, 200);
      }catch (Exception e){

      }


    }


    public static void doPost(HttpExchange exchange) throws IOException {
        System.out.println("cheguei no post");
        try (InputStream requestBody = exchange.getRequestBody()) {
            JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));
            ProdutosWeb sales = new ProdutosWeb(
                    json.getString("Title"),
                    json.getString("Value"),
                    json.getString("Image"),
                    json.getString("Description")
            );
            salesList.add(sales);
            res.enviarResponseJson(exchange, sales.arraytoJson(salesList), 200);
            System.out.println("salesList" + sales.arraytoJson(salesList));
        } catch (Exception e) {
            String response = "erro";
            String resposta = e.toString();
            res.enviarResponseJson(exchange, responseJson, 200);
        }
    }

    public static void doPut(HttpExchange exchange) throws IOException {
        RespostaEndPoint res = new RespostaEndPoint();
        String response = "";
        int linhasAfetadas = 0;

        try (InputStream requestBody = exchange.getRequestBody()) {
            JSONObject json = new JSONObject(new String(requestBody.readAllBytes()));

            ProdutosWeb produtos = new ProdutosWeb(
                    json.getString("Title"),
                    json.getString("Value"),
                    json.getString("Image"),
                    json.getString("Description")
            );

            // Considerando que você tenha um método atualizarProduto na classe ProductsWebDal
            // linhasAfetadas = userDal.atualizarProduto(produtos.title, produtos.value, produtos.image, produtos.description, produtos.id);

            if (linhasAfetadas > 0) {
                response = "Produto atualizado com sucesso";
                res.enviarResponse(exchange, response, 200);
            } else {
                response = "Falha ao atualizar o produto";
                res.enviarResponse(exchange, response, 400);
            }
        }
    }

    public static void doDelete(HttpExchange exchange) throws IOException {
        // Implemente a lógica para exclusão de produtos
    }
}
