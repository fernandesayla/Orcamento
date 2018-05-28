package br.com.aylafernandes.orcamento.models;



import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;


import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class Clients {

        public final static String TAG_ANDROID = "android";

        public final static String TAG_CELL = "nome";

        public String cell;
        public String email;

        public Clients() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Clients(String username, String email) {
            this.cell = username;
            this.email = email;
        }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cell", cell);
        result.put("email", email);


        return result;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
