package petra.tugas.imk_mbanking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class bayarcc extends AppCompatActivity {
    Button bOk, bBank;
    EditText etJumlah,etKartu,etDesc;
    int pinCounter;
    SharedPreferences appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayarcc);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_size_32);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("MnH M-Banking");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#18305b")));

        bOk = findViewById(R.id.bOk);
        bBank = findViewById(R.id.bBank);
        etJumlah = findViewById(R.id.etJumlah);
        etKartu = findViewById(R.id.etKartu);
        etDesc = findViewById(R.id.etDesc);

        appData = getSharedPreferences("appData",MODE_PRIVATE);
        pinCounter = appData.getInt("pinCounter",0);

        bBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(bayarcc.this);
                View bankView = li.inflate(R.layout.jenispulsa, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(bayarcc.this);
                final Button button1 = (Button) bankView.findViewById(R.id.bTsel);
                final Button button2 = (Button) bankView.findViewById(R.id.bSmarfren);
                final Button button3 = (Button) bankView.findViewById(R.id.bIndosat);
                final Button button4 = (Button) bankView.findViewById(R.id.bXl);
                builder.setView(bankView);
                button1.setText("Bank Sendiri");
                button2.setText("Bank C");
                button3.setText("Bank B");
                button4.setText("Bank D");
                final AlertDialog dialog = builder.create();
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bBank.setText(button1.getText().toString());
                        dialog.cancel();
                        //kalo bank sendiri otomatis
                        final ProgressDialog progressDialog = new ProgressDialog(bayarcc.this,
                                R.style.Theme_AppCompat_Light_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Please Wait....");
                        progressDialog.show();
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        etJumlah.setText("100000");
                                        etJumlah.setEnabled(false);

                                    }
                                }, 2000);
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bBank.setText(button2.getText().toString());
                        etJumlah.setEnabled(true);
                        dialog.cancel();
                    }
                });
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bBank.setText(button3.getText().toString());
                        etJumlah.setEnabled(true);
                        dialog.cancel();
                    }
                });
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bBank.setText(button4.getText().toString());
                        etJumlah.setEnabled(true);
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
        bOk.setOnClickListener(new View.OnClickListener() {
            int jumlah,totalbyr;
            String kartu,bank,desc;
            @Override
            public void onClick(View v) {
                //cek inputan g kosong kecuali desc boleh kosong
                //try krn kalo INT parseInt kosong error
                try{
                    jumlah = Integer.parseInt(etJumlah.getText().toString());
                    kartu = etKartu.getText().toString();
                    bank = bBank.getText().toString();
                    desc = etDesc.getText().toString();

                    if (kartu.isEmpty()||bank.isEmpty()){
                        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(bayarcc.this);
                        errorBuilder.setMessage("Tolong isi semua input");
                        errorBuilder.setCancelable(false);
                        errorBuilder.setNegativeButton("OK", null);
                        errorBuilder.show();
                    }
                    else{
                        //inflate layout konfirmasi
                        LayoutInflater li = LayoutInflater.from(bayarcc.this);
                        final View konfirmasiView = li.inflate(R.layout.layoutkonfirmasi, null);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(bayarcc.this);
                        final TextView tvJudul1 = konfirmasiView.findViewById(R.id.tvJudul);
                        final TextView tv11 = konfirmasiView.findViewById(R.id.tv1);
                        final TextView tv21 = konfirmasiView.findViewById(R.id.tv2);
                        final TextView tv31 = konfirmasiView.findViewById(R.id.tv3);
                        final TextView tv41 = konfirmasiView.findViewById(R.id.tv4);
                        final TextView tv51 = konfirmasiView.findViewById(R.id.tv5);
                        final TextView tv61 = konfirmasiView.findViewById(R.id.tv6);
                        tvJudul1.setText("KONFIRMASI");
                        tv11.setText("Pembayaran Kartu Kredit");
                        tv21.setText(bank);
                        tv31.setText("Rp. "+jumlah);
                        totalbyr =jumlah;
                        if (!bank.equals("Bank Sendiri")){
                            tv41.setText("Biaya Admin: Rp. 1500");
                            tv51.setText("Total Bayar: Rp. "+(jumlah+=1500));
                            tv61.setText("Deskripsi: "+desc);
                            totalbyr+=1500;
                        }
                        builder.setView(konfirmasiView);
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (pinCounter < 3) {
                                    LayoutInflater li = LayoutInflater.from(bayarcc.this);
                                    View pinView = li.inflate(R.layout.pin, null);
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(bayarcc.this);
                                    final EditText etPin = (EditText) pinView.findViewById(R.id.etPin);
                                    builder.setView(pinView);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final ProgressDialog progressDialog = new ProgressDialog(bayarcc.this,
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
                                                            String mpin = etPin.getText().toString();
                                                            if (mpin.equals("123456")) {
                                                                if ((appData.getInt("saldo",0)-totalbyr<0)){
                                                                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(bayarcc.this);
                                                                    errorBuilder.setMessage("Saldo Anda Tidak Mencukupi");
                                                                    errorBuilder.setCancelable(false);
                                                                    errorBuilder.setNegativeButton("OK", null);
                                                                    errorBuilder.show();
                                                                }else{
                                                                    Date date = Calendar.getInstance().getTime();
                                                                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                                    String formattedDate = df.format(date);
                                                                    int random =new Random().nextInt((99999999 - 10000000) + 1) + 10000000;
                                                                    int newSaldo = appData.getInt("saldo",0);
                                                                    newSaldo = newSaldo-totalbyr;

                                                                    SharedPreferences.Editor editor = appData.edit();
                                                                    editor.putInt("pinCounter",0);
                                                                    editor.putInt("saldo",newSaldo);
                                                                    editor.commit();

                                                                    LayoutInflater liResi = LayoutInflater.from(bayarcc.this);
                                                                    View resiView = liResi.inflate(R.layout.layoutkonfirmasi, null);
                                                                    AlertDialog.Builder resiBuilder = new AlertDialog.Builder(bayarcc.this);
                                                                    final TextView tvJudul = resiView.findViewById(R.id.tvJudul);
                                                                    final TextView tv1 = resiView.findViewById(R.id.tv1);
                                                                    final TextView tv2 = resiView.findViewById(R.id.tv2);
                                                                    final TextView tv3 = resiView.findViewById(R.id.tv3);
                                                                    final TextView tv4 = resiView.findViewById(R.id.tv4);
                                                                    final TextView tv5 = resiView.findViewById(R.id.tv5);
                                                                    final TextView tv6 = resiView.findViewById(R.id.tv6);
                                                                    final TextView tv7 = resiView.findViewById(R.id.tv7);
                                                                    final TextView tv8 = resiView.findViewById(R.id.tv8);
                                                                    resiBuilder.setView(resiView);
                                                                    resiBuilder.setCancelable(false);
                                                                    tvJudul.setText("Bukti Transaksi");
                                                                    tv1.setText("Transaksi Berhasil");
                                                                    tv2.setText(formattedDate);
                                                                    tv3.setText("");
                                                                    tv4.setText("Jenis Transaksi: Pembayaran Kartu Kredit");
                                                                    tv5.setText("Bank: "+ bank);
                                                                    tv6.setText("Total Bayar: "+totalbyr);
                                                                    tv7.setText("Deskripsi: "+desc);
                                                                    tv8.setText("No. Referensi: "+random);


                                                                    resiBuilder.setPositiveButton("Kembali ke Menu Awal", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.cancel();
                                                                            Intent iMain = new Intent(bayarcc.this,mainMenu.class);
                                                                            startActivity(iMain);
                                                                        }
                                                                    });
                                                                    resiBuilder.show();
                                                                }
                                                            }else {
                                                                pinCounter+=1;
                                                                SharedPreferences.Editor editor = appData.edit();
                                                                editor.putInt("pinCounter",pinCounter);
                                                                editor.commit();
                                                                AlertDialog.Builder myBuilder = new AlertDialog.Builder(bayarcc.this);
                                                                myBuilder.setMessage("m-PIN yang Anda masukan Salah!");
                                                                myBuilder.setCancelable(false);
                                                                myBuilder.setNegativeButton("OK", null);
                                                                myBuilder.show();

                                                            }
                                                        }
                                                    }, 3000);


                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.show();
                                }else {
                                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(bayarcc.this);
                                    myBuilder.setMessage("m-Banking Anda di Blokir\nHubungi Kantor Cabang Terdekat");
                                    myBuilder.setCancelable(false);
                                    myBuilder.setNegativeButton("OK", null);
                                    myBuilder.show();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel",null);
                        builder.show();
                        etJumlah.setEnabled(true);
                    }

                }catch (Exception e){

                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(bayarcc.this);
                    errorBuilder.setMessage("Tolong isi semua input");
                    errorBuilder.setCancelable(false);
                    errorBuilder.setNegativeButton("OK", null);
                    errorBuilder.show();
                }
            }
        });
    }
}
