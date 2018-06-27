package br.com.aylafernandes.orcamento.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.aylafernandes.orcamento.models.Client;

public class ClientDAO extends SQLiteOpenHelper {


    public ClientDAO(Context context) {
        super(context, "Orcamento", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE Clients (id INTEGER PRIMARY KEY, name TEXT NOT NULL, address TEXT, phone TEXT, email TEXT, image_url TEXT, price TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        String sql = "DROP TABLE IF EXISTS Clients;";

        db.execSQL(sql);
        onCreate(db);

    }

    public void insere(Client client) {
        SQLiteDatabase database =  getWritableDatabase();


        database.insert("Clients", null, getClientInfo(client));

    }

    public List<Client> findAll()  {
        String sql = "SELECT * FROM Clients;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Client> alunos = new ArrayList<Client>();
        while (c.moveToNext()) {
            Client client = new Client();
            client.setUid(String.valueOf(c.getLong(c.getColumnIndex("id"))));
            client.setName(c.getString(c.getColumnIndex("name")));
            client.setAddress(c.getString(c.getColumnIndex("address")));
            client.setCell(c.getString(c.getColumnIndex("phone")));
            client.setEmail(c.getString(c.getColumnIndex("email")));
            client.setPrice(c.getString(c.getColumnIndex("price")));
            client.setPhotoUrl(c.getString(c.getColumnIndex("image_url")));
            alunos.add(client);

        }
        c.close();

        return alunos;

    }
    public void deleta(Client client) {
        SQLiteDatabase database =  getWritableDatabase();

        String[] params ={client.getUid().toString()};

        database.delete("Clients", "id =  ?", params);
    }

    public void altera(Client client) {
        SQLiteDatabase database =  getWritableDatabase();


        String[] params ={client.getUid().toString()};

        database.update("Clients",getClientInfo(client), "id =  ?", params);
    }

    private ContentValues getClientInfo(Client client){
        ContentValues dados = new ContentValues();
        dados.put("name", client.getName());
        dados.put("address", client.getAddress());
        dados.put("phone", client.getCell());
        dados.put("email", client.getEmail());
        dados.put("image_url", client.getPhotoUrl());
        dados.put("price", client.getPrice());
        return dados;

    }
}
