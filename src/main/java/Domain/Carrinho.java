package Domain;

import org.json.JSONObject;
import java.util.List;

public class Carrinho {
    public int id = 0;
    public String title = "";
    public String value = "";
    public String image = "";
    public String description = "";

    // Constructors
    public Carrinho() {
    }


    public Carrinho(String title, String value, String image, String description) {
        this.title = title;
        this.value = value;
        this.image = image;
        this.description = description;
    }



    public Carrinho(int id, String title, String value, String image, String description) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.image = image;
        this.description = description;
    }

    public Carrinho(int id){
        this.id = id;
    }

    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("value", value);
        json.put("image", image);
        json.put("description", description);
        return json;
    }

    public static Carrinho getProduct(int index, List<Carrinho> productList) {
        if (index >= 0 && index < productList.size()) {
            return productList.get(index);
        } else {
            return null;
        }
    }

    public static List<Carrinho> getAllProducts(List<Carrinho> carrinhoList) {
        return carrinhoList;
    }

    public JSONObject arrayToJson(List<Carrinho> carrinhoList) {
        System.out.println("carrinhoList no aray to json :  " + carrinhoList);
        JSONObject json = new JSONObject();
        if (!carrinhoList.isEmpty()) {
            int keyJson = 0;

            for (Carrinho carrinho : carrinhoList) {
                System.out.println( "aqui esta o carrinho list que vai para o json "+carrinhoList);
                JSONObject valueJson = new JSONObject();
                valueJson.put("id", carrinho.getId());
                valueJson.put("title", carrinho.getTitle());
                valueJson.put("value", carrinho.getValue());
                valueJson.put("image", carrinho.getImage());
                valueJson.put("description", carrinho.getDescription());
                json.put(String.valueOf(keyJson), valueJson);
                keyJson++;
            }
            System.out.println( "Aqui esta o json do array to json "+ json);
            return json;
        } else {
            return null;
        }
    }
}
