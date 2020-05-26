package com.encdec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.encdec.utils.Cryptor;
import com.encdec.utils.ApplicationUtils;

public class MainActivity extends AppCompatActivity {
    private EditText encryptionKeyEditText;
    private EditText messageEditText;
    private Button shareBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_message);

        encryptionKeyEditText = findViewById(R.id.encryptionKey);
        messageEditText = findViewById(R.id.message);
        shareBtn = findViewById(R.id.share);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String encryptionKey = encryptionKeyEditText.getText().toString();
                String message = messageEditText.getText().toString();

                if (validateParameters(encryptionKey, message)) {
                    String encodedMsgString = Cryptor.encryptString(encryptionKey, message, getBaseContext());

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, encodedMsgString);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
            }
        });
    }

    private boolean validateParameters(String encryptionKey, String message) {
        if(encryptionKey == null || encryptionKey.length() != 16){
            ApplicationUtils.showToast(getBaseContext(),"Please enter 16-character secret key!" );
            return false;
        } else if(message == null || message.length() == 0){
            ApplicationUtils.showToast(getBaseContext(),"Please enter some message!" );
            return false;
        }

        return true;
    }

}