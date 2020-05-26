package com.encdec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.encdec.utils.Cryptor;
import com.encdec.utils.ApplicationUtils;

public class Receive extends AppCompatActivity {

    private EditText decryptionKeyEditText;
    private EditText messageEditText;
    private Button showMessageBtn;
    private String encryptedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_message);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if ("android.intent.action.SEND".equals(action) && type != null && "text/plain".equals(type)) {
            encryptedString = intent.getStringExtra("android.intent.extra.TEXT");
        }

        decryptionKeyEditText = findViewById(R.id.decryptionKey);
        messageEditText = findViewById(R.id.message);
        showMessageBtn = findViewById(R.id.showMessage);

        showMessageBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String encryptionKey = decryptionKeyEditText.getText().toString();

                if (validateParameters(encryptionKey)) {
                    String decodedMsgString = Cryptor.decryptString(encryptionKey, encryptedString, getBaseContext());
                    messageEditText.setText(decodedMsgString);
                }
            }
        });


    }

    private boolean validateParameters(String encryptionKey) {
        if(encryptionKey == null || encryptionKey.length() != 16){
            ApplicationUtils.showToast(getBaseContext(),"Please enter 16-character secret key!" );
            return false;
        }

        return true;
    }
}
