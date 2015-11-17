package com.bingshanguxue.tenyears;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private static final int REQ_CODE_SELECT_ADDRESS = 1;

    private EditText textName;
    private EditText textTelphone;
    private ImageButton btnSelectAddress;
    private TextView textAddress;
    private Button btnSave;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        textName = (EditText) findViewById(R.id.edit_name);
        textTelphone = (EditText) findViewById(R.id.edit_telphone);

        btnSelectAddress = (ImageButton) findViewById(R.id.button_add_adderss);
        btnSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddContactActivity.this, AddAddressActivity.class), REQ_CODE_SELECT_ADDRESS);
            }
        });

        btnSave = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_ADDRESS){
            if (resultCode == RESULT_OK){
                if (data != null){
                    latitude = data.getDoubleExtra("Latitude", 0D);
                    longitude = data.getDoubleExtra("Longitude", 0D);
                    Toast.makeText(AddContactActivity.this, String.format("latitude=%f, longitude=%f", latitude, longitude), Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        contact.setLatitude(latitude);
        contact.setLongitude(longitude);

        if (contact.save()) {
            Toast.makeText(this, "存储成功", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
        }
    }
}
