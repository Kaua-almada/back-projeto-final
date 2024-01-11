package Domain;

import org.json.JSONObject;

import java.util.List;

public class Usuarios {
    public int id = 0;
    public String name = "";
    public String lastName = "";

    public String email = "";

    public String passeword = "";

    public String cpf = "";

    public Usuarios(int id,String name, String lastName, String email, String passeword, String cpf) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.passeword = passeword;
        this.cpf = cpf;
    }

    public Usuarios() {

    }

    public Usuarios(String name, String lastName, String email, String passeword, String cpf) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.passeword = passeword;
        this.cpf = cpf;
    }

    //getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getpasseword() {
        return passeword;
    }

    public void setpasseword(String passeword) {
        this.passeword = passeword;
    }

    public String getcpf() {
        return cpf;
    }

    public void setcpf(String cpf) {
        this.cpf = cpf;
    }

    public  int getId(){
        return  id;
    }
    public void setId(int id){this.id = id;};

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("lastName", lastName);
        json.put("email", email);
        json.put("passeword", passeword);
        json.put("cpf", cpf);
        return json;
    }

    public static Usuarios getUser(int index, List<Usuarios> usersList) {

        if (index >= 0 && index < usersList.size()) {
            return usersList.get(index);
        } else {
            return null;
        }

    }

    public static List<Usuarios> getAllUser(List<Usuarios> usersList) {

        return usersList;
    }

    public JSONObject arraytoJson(List<Usuarios> usersListArray) {
        JSONObject json = new JSONObject();

        if (!usersListArray.isEmpty()) {
            var keyJson = 0;
            for (Usuarios users : usersListArray) {
                JSONObject valorJson = new JSONObject();
                valorJson.put("id",users.getId());
                valorJson.put("name", users.getName());
                valorJson.put("lastName", users.getLastname());
                valorJson.put("email", users.getemail());
                valorJson.put("passeword", users.getpasseword());
                valorJson.put("cpf", users.getcpf());
                json.put(String.valueOf(keyJson), valorJson);

                keyJson++;
            }
            return json;
        }else {
            return null;
        }

    }
}
