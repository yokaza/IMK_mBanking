package petra.tugas.imk_mbanking;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class adminGantiPin extends AppCompatActivity {
    EditText pinnow,newpin1,newpin2;
    Button bOk;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ganti_pin);

        pinnow = findViewById(R.id.etPinNow);
        newpin1 = findViewById(R.id.etPinNew);
        newpin2 =findViewById(R.id.etPinNewR);

        bOk = findViewById(R.id.bOk);
        context = adminGantiPin.this;
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context,
                        R.style.Theme_AppCompat_Light_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                // TODO: Implement your own authentication logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                if (newpin1.getText().toString().isEmpty() || newpin2.getText().toString().isEmpty() || pinnow.getText().toString().isEmpty()) {
                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                    errorBuilder.setMessage("Tolong isi semua input");
                                    errorBuilder.setCancelable(false);
                                    errorBuilder.setNegativeButton("OK", null);
                                    errorBuilder.show();
                                } else if(!pinnow.getText().toString().equals("123456")){
                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                    errorBuilder.setMessage("m-Pin yang anda masukan salah!");
                                    errorBuilder.setCancelable(false);
                                    errorBuilder.setNegativeButton("OK", null);
                                    errorBuilder.show();
                                }else if(newpin1.getText().toString().length()!=6){
                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                    errorBuilder.setMessage("Pin Harus 6 digit angka");
                                    errorBuilder.setCancelable(false);
                                    errorBuilder.setNegativeButton("OK", null);
                                    errorBuilder.show();
                                }
                                else if (!newpin1.getText().toString().equals(newpin2.getText().toString().isEmpty())) {
                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                    errorBuilder.setMessage("Pin baru tidak sama");
                                    errorBuilder.setCancelable(false);
                                    errorBuilder.setNegativeButton("OK", null);
                                    errorBuilder.show();
                                } else {
                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                    errorBuilder.setMessage("Pin Anda Telah Berhasil DIganti");
                                    errorBuilder.setCancelable(false);
                                    errorBuilder.setNegativeButton("OK", null);
                                    errorBuilder.show();
                                }
                            }
                        },3000);
            }
        });
    }
}
