package com.example.nagaiakiranari.stringoutput;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.database.Cursor;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.viewText);

        try {
            textView.setText(readDB());
        } catch (Exception e) {
            toast("読み込み失敗しました。");
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    TextView textView = (TextView)findViewById(R.id.viewText);
                    textView.setText(readDB());
                } catch (Exception e) {
                    toast("読み込み失敗しました。");
                }
            }
        });
    }

    private String readDB() throws Exception {
        //コンテンツプロバイダが提供するデータベースを示すURI
        Uri uri = Uri.parse("content://com.example.nagaiakiranari.inputdbprovider/");
        grantUriPermission("com.example.app.permission.Provider",
                Uri.parse("content://com.example.nagaiakiranari.inputdbprovider/"),
                Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //コンテンツプロバイダが提供するデータベースへのアクセス

        Cursor c = this.getContentResolver().query(uri,
                new String[]{"id", "text"}, null, null, null);
        if (c.getCount() == 0) throw new Exception();
        c.moveToFirst();
        String str = c.getString(1);
        c.close();
        revokeUriPermission(Uri.parse("content://com.example.nagaiakiranari.inputdbprovider/"),
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return str;
    }

    private void toast(String text) {
        if (text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
