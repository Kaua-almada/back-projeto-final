package Domain;

import org.json.JSONObject;

import java.util.List;

public class ProdutosWeb {
    public int id = 0;
    public String title = " ";
    public String value = " ";
    public String image =  " ";
    public String description = "";


    public ProdutosWeb(int id, String title, String value, String image, String description){

    }
    public ProdutosWeb(String title, String value, String image, String description){

        this.title = title;
        this.value = value;
        this.image = image;
        this.description = description;
    }

    public ProdutosWeb() {

    }

    //getters and setters
    public String getName(){
        return title;
    }
    public void setName(String user){
        this.title = user;
    }
    public String getProduts(){
        return value;
    }
    public void setproduts(String produts){
        this.value = produts;
    }
    public String getvalor(){
        return image;
    }
    public void setValor(String valor){
        this.image = valor;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String finishedSale){
        this.description = finishedSale;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", title);
        json.put("produts", value);
        json.put("valor", image);
        json.put("finishedSale", description);
        return json;
    }

    public static List<ProdutosWeb> getAllSales(List<ProdutosWeb> salesList){return salesList;}
    public JSONObject arraytoJson(List<ProdutosWeb> salesListArray) {
            JSONObject json = new JSONObject();

            if (!salesListArray.isEmpty()) {
                var keyJson = 0;
                for (ProdutosWeb sales : salesListArray) {
                    JSONObject valorJson = new JSONObject();
                    valorJson.put("Title", sales.getName());
                    valorJson.put("Value", sales.getProduts());
                    valorJson.put("Image", sales.getvalor());
                    valorJson.put("Description", sales.getDescription());
                    json.put(String.valueOf(keyJson), valorJson);

                    keyJson++;
                }

                return json;
            }else {
                return null;
            }
    }
    public static ProdutosWeb getSales(int index, List<ProdutosWeb> salesList) {
        if (index >= 0 && index < salesList.size()) {
            return salesList.get(index);
        }else {
            return null;
        }
    }
}

