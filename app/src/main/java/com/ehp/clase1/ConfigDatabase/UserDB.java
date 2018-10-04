package com.ehp.clase1.ConfigDatabase;

import com.ehp.clase1.Model.Usuario;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserDB {
    @POST("Users.json")
    Call<JsonObject> createUser(@Body Usuario user);
}
