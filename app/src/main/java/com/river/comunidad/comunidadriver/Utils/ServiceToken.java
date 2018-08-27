package com.river.comunidad.comunidadriver.Utils;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceToken {

    @Multipart
    @POST("http://www.comunidadriver.com/pnfw/register")
    Call<ResponseBody> agregarToken(
            @Part("token") RequestBody requestBodyToken, @Part("os") RequestBody sistemaOp


    );
}
