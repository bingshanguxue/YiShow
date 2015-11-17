package com.bingshanguxue.tenyears;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactCardActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textTelphone;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_card);

        textName = (EditText) findViewById(R.id.edit_name);
        textTelphone = (EditText) findViewById(R.id.edit_telphone);

        btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact(v);
            }
        });
    }

    private void saveContact(View v){
        if (textName.length() < 1){
//            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();

                Snackbar.make(v, "请输入姓名", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            return;
        }

        if (textTelphone.length() < 1){
            Snackbar.make(v, "请输入手机号", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }

        Contact contact = new Contact();
        contact.setName(textName.getText().toString());
        contact.setTelphone(textTelphone.getText().toString());
        if (contact.save()) {
            Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
        }
    }
}
