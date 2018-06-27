package br.com.aylafernandes.orcamento.models;



import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class Client implements Serializable {

    private String address;
    private String uid;
    private String name;
    private String cell;
    private String email;
    private String photoUrl;
    private String hair;
    private String price;


    public Client(){};

        public Client(String uid, String name, String cell, String email, String photoUrl, String hair, String address, String price) {
            this.uid = uid;
            this.name = name;
            this.cell = cell;
            this.email = email;
            this.photoUrl = photoUrl;
            this.hair = hair;
            this.address = address;
            this.price = price;
        }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("cell", cell);
        result.put("email", email);
        result.put("photoUrl", photoUrl);
        result.put("name", name);

        result.put("address", address);
        result.put("hair", hair);
        result.put("price", price);

        return result;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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


}
