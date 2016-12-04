package com.zeus.scan.app.Service;

import com.zeus.scan.app.entity.Book;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lvzimou on 2016/11/28.
 */

public interface BookService {

    @GET("v2/book/isbn/{isbn}")
    Call<Book> fetchBook(@Path("isbn") String isbn);

    @POST("books")
    Call<String> postBook(@Body Book book);
}
