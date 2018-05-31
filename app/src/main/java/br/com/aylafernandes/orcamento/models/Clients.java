package br.com.aylafernandes.orcamento.models;



import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class Clients implements Serializable {



        public String cell;
        public String email;
        public String photoUrl;
        public String cabelo;

        public Clients() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Clients(String username, String email, String photoUrl,  String cabelo) {
            this.cell = username;
            this.email = email;
            this.photoUrl = photoUrl;
            this.cabelo = cabelo;
        }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cell", cell);
        result.put("email", email);
        result.put("photoUrl", photoUrl);

        return result;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getCabelo() {
        return cabelo;
    }

    public void setCabelo(String cabelo) {
        this.cabelo = cabelo;
    }
}
