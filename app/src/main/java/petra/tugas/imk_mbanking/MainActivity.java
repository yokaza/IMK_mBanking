package petra.tugas.imk_mbanking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etId;
    EditText etPass;
    Button butLogin;
    TextView tvLink;
    int counter,pinCounter,saldo;
    SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        etId = (EditText)findViewById(R.id.input_id);
        etPass = (EditText)findViewById(R.id.input_password);
        butLogin = (Button)findViewById(R.id.btn_login);
        tvLink = (TextView)findViewById(R.id.link_web);
        appData = getSharedPreferences("appData",MODE_PRIVATE);
        counter=0;
        pinCounter = appData.getInt("pinCounter",-1);
        saldo = appData.getInt("pinCounter",-1);
        if (saldo==-1){
            SharedPreferences.Editor editor = appData.edit();
            editor.putInt("saldo",1000000);
            editor.commit();
        }

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                startActivity(i);
            }
        });

    }
    public void login() {
        Log.d("Login", "Login");

        if (!validate()) {
            onLoginFailed(0);
            return;
        }

        butLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String sId = etId.getText().toString();
        final String sPass = etPass.getText().toString();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        boolean valid = true;
                        int num=1;
                        if(sId.equals("admin") && sPass.equals("123456")){
                            etPass.setError(null);
                            etId.setError(null);
                            SharedPreferences.Editor editor = appData.edit();
                            editor.putString("nama","Nama Lengkap Admin");
                            editor.commit();

                        }else{
                            valid = false;
                            if(sId.equals("admin")){
                                counter++;
                                etPass.setError("wrong access code");
                            }else{
                                etId.setError("enter a valid user ID");
                                num=0;
                            }

                        }
                        if(counter>3){
                            valid=false;
                            etPass.setError(null);
                            etId.setError(null);
                        }
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(valid){
                            onLoginSuccess();
                        }
                        else {
                            onLoginFailed(num);
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        butLogin.setEnabled(true);
        counter=0;
        SharedPreferences.Editor editor = appData.edit();
        if (pinCounter==-1) {
            editor.putInt("pinCounter", 0);
            editor.commit();
        }else{
            editor.putInt("pinCounter", pinCounter);
            editor.commit();
        }
        Toast toast = Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        toast.show();
        Intent i = new Intent(this,mainMenu.class);
        startActivity(i);
    }
    public void onLoginFailed(int n) {
            if (n == 1) {
                if(counter>=3) {
                    Toast toast =Toast.makeText(getBaseContext(), "Login failed\nBLOCKED", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getBaseContext(), "Login failed\nChance Remaining: " + (3 - counter), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }
            }
            else{
                Toast toast = Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }


        butLogin.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String sId = etId.getText().toString();
        String sPass = etPass.getText().toString();

        if (sId.isEmpty() ) {
            etId.setError("enter a valid user ID");
            valid = false;
        } else {
            etId.setError(null);
        }

        if (sPass.isEmpty() || sPass.length() != 6) {
            etPass.setError("6 digit number");
            valid = false;
        } else {
            etPass.setError(null);
        }

        return valid;
    }

}
