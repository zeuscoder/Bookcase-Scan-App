package com.zeus.scan.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zeus.scan.app.R;
import com.zeus.scan.app.Service.BookService;
import com.zeus.scan.app.entity.Book;
import com.zeus.scan.app.utils.APIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lvzimou on 2016/11/28.
 */

public class BookViewActivity extends AppCompatActivity {

    private BookService bookService;

    private TextView isbnText;

    private TextView titleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        initViews();
        initIntentData();
    }

    private void initViews() {
        isbnText = (TextView) findViewById(R.id.isbn_text);
        titleText = (TextView) findViewById(R.id.title_text);
    }

    private void initIntentData() {
        if (getIntent().hasExtra("isbn")) {
            isbnText.setText(getIntent().getStringExtra("isbn"));
            fetchBookViewData(getIntent().getStringExtra("isbn"));
        } else {
            BookViewActivity.this.finish();
        }
    }

    private void fetchBookViewData(String isbn) {
        bookService = APIUtil.initService().create(BookService.class);
        Call<Book> bookCall = bookService.fetchBook(isbn);
        bookCall.enqueue(new Callback<Book>() {

            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                updateToView(response);
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });
    }

    private void updateToView(Response<Book> bookResp) {
        if (bookResp != null) {
            Book book = bookResp.body();
            if (book != null) {
                titleText.setText(book.getTitle());
                Gson gson = new Gson();
                String bookJson = gson.toJson(book);
                Log.e("zeus", bookJson);
            }
        }
    }

}
