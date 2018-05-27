package br.com.aylafernandes.orcamento.models;



import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;


import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User {


        public String cell;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
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

    }
