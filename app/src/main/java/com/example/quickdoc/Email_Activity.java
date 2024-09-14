package com.example.quickdoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class Email_Activity extends AppCompatActivity
{
    TextView tvname,tvemail;
    AppCompatButton button;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        tvname=findViewById(R.id.tvName);
        tvemail=findViewById(R.id.tvEmailid);
        button=findViewById(R.id.btnSignUp);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(Email_Activity.this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount= GoogleSignIn.getLastSignedInAccount(Email_Activity.this);

        if (googleSignInAccount!=null){
            String name =googleSignInAccount.getDisplayName();
            String emailid =googleSignInAccount.getEmail();
            tvname.setText(name);
            tvemail.setText(emailid);
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut();
                Intent i = new Intent(Email_Activity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        }
    }
}