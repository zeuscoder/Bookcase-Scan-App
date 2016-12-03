package com.zeus.scan.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.zeus.scan.app.ui.BookViewActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanBookBtn;

    private Button inputISBNBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        scanBookBtn = (Button) findViewById(R.id.scan_book);
        inputISBNBtn = (Button) findViewById(R.id.input_isbn);
        scanBookBtn.setOnClickListener(this);
        inputISBNBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.scan_book:
                goToScanActivityView();
                break;
            case R.id.input_isbn:
                Toast.makeText(HomeActivity.this, "你点击了输入按钮", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToScanActivityView() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan Book ISBN");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator
                .parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String isbn = result.getContents();
                Intent intent = new Intent(HomeActivity.this, BookViewActivity.class);
                intent.putExtra("isbn", isbn);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
