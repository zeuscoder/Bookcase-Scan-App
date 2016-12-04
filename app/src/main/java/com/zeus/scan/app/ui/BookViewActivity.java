package com.zeus.scan.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class BookViewActivity extends AppCompatActivity implements View.OnClickListener {

    private BookService bookService;

    private Book book;

    private TextView isbnText;

    private TextView titleText;

    private Button postBookBtn;

    private Button backHomeBtn;

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
        postBookBtn = (Button) findViewById(R.id.btn_post_book);
        backHomeBtn = (Button) findViewById(R.id.btn_back_home);
        postBookBtn.setOnClickListener(this);
        backHomeBtn.setOnClickListener(this);
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
        bookService = APIUtil.initDouBanService().create(BookService.class);
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
            book = bookResp.body();
            if (book != null) {
                titleText.setText(book.getTitle());
                Gson gson = new Gson();
                String bookJson = gson.toJson(book);
                Log.e("zeus", bookJson);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_post_book:
                postBook();
                break;
            case R.id.btn_back_home:
                backHome();
                break;
        }
    }

    private void postBook() {
        if (book != null) {
            bookService = APIUtil.initService().create(BookService.class);
            Call<String> bookCall = bookService.postBook(book);
            bookCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(BookViewActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(BookViewActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BookViewActivity.this, "没有书籍", Toast.LENGTH_SHORT).show();
        }
    }

    private void backHome() {
        BookViewActivity.this.finish();
    }

}
