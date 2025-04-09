package hu.techbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class Start_activity extends AppCompatActivity {
    private static final int SK = 34788;

    EditText login_email, password;
    CheckBox remember_check;
    TextView forgotPassword;

    private FirebaseAuth Main_Auth;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_email = findViewById(R.id.login_email);
        password = findViewById(R.id.password);
        remember_check = findViewById(R.id.remember_check);

        Main_Auth = FirebaseAuth.getInstance();

        sp = getSharedPreferences("RUsers", MODE_PRIVATE);
        loadSavedC();

        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(v -> {
            Forgot_password_fragment dialog = new Forgot_password_fragment();
            dialog.show(getSupportFragmentManager(), "PasswordReset");
        });
    }

    private void saveC(String email, String password){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("remember_me", true);
        editor.apply();
    }

    private void loadSavedC() {
        boolean isRemembered = sp.getBoolean("remember_me", false);
        if(isRemembered){
            login_email.setText(sp.getString("email",""));
            password.setText(sp.getString("password",""));
            remember_check.setChecked(true);
        }
    }

    private void clearC(){
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("email");
        editor.remove("password");
        editor.remove("remember_me");
        editor.apply();
    }

    public void register(View view) {
        Intent reg_intent = new Intent(this, Reg_activity.class);
        reg_intent.putExtra("SK", SK);
        startActivity(reg_intent);
    }

    private void home(){
        Intent home_intent = new Intent(this, Home_activity.class);
        startActivity(home_intent);
    }

    public void signInWithEmail(View view) {
        String email = login_email.getText().toString();
        String jelszo = password.getText().toString();

        if(email.isEmpty()) login_email.setError("Add meg az email címedet a bejelentkezéshez!");
        else if(jelszo.isEmpty()) password.setError("Add meg a jelszódat a bejelentkezéshez!");
        else {
            Main_Auth.signInWithEmailAndPassword(email, jelszo).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    if(remember_check.isChecked()){
                        saveC(email, jelszo);
                    }
                    else {
                        clearC();
                    }
                    Toast.makeText(Start_activity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    home();
                } else {
                    Toast.makeText(Start_activity.this, "Sikertelen belépés: "
                            + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void signInWithGuest(View view) {
        Main_Auth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Start_activity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                home();
            } else {
                Toast.makeText(Start_activity.this, "Sikertelen belépés: "
                        + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}