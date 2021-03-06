package petra.tugas.imk_mbanking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

public class mainMenu extends AppCompatActivity {
    TextView tvNama;
    Intent logout;
    SharedPreferences appData;
    int pinCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_size_32);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("MnH M-Banking");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#18305b")));


        tvNama = (TextView)findViewById(R.id.tvNama);
        appData = getSharedPreferences("appData",MODE_PRIVATE);
        String nama = appData.getString("nama",null);
        pinCounter = appData.getInt("pinCounter",0);
        tvNama.setText(nama);
        logout = new Intent(this,MainActivity.class);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
            myBuilder.setMessage("Anda yakin ingin keluar?");
            myBuilder.setCancelable(true);
            myBuilder.setNegativeButton("Tidak",null);
            myBuilder.setPositiveButton("Ya",new Ok1OnClickListener());

            AlertDialog dialog = myBuilder.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void mainMenuClick(View view) {
        switch (view.getId()){
            case R.id.mInfo:
                Intent iinfo = new Intent(this,mInfo.class);
                startActivity(iinfo);
                break;

            case R.id.mPayment:
                Intent ipayment = new Intent(this,mPayment.class);
                startActivity(ipayment);
                break;

            case R.id.mTransfer:
                Intent itransfer = new Intent(this,mTransfer.class);
                startActivity(itransfer);
                break;
            case R.id.mEcomm:
                Intent iComm = new Intent(this,mCommerce.class);
                startActivity(iComm);
                break;

            case R.id.mLain:
                Intent i = new Intent(this,tariktunai.class);
                startActivity(i);
                break;
            case R.id.mSetting:
                Intent iSetting = new Intent(this,mAdmin.class);
                startActivity(iSetting);
        }

    }

    @Override
    public void onBackPressed() {

    }

    private class Ok1OnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            final ProgressDialog progressDialog = new ProgressDialog(mainMenu.this,
                    R.style.Theme_AppCompat_Light_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait....");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            startActivity(logout);
                        }
                    }, 2000);

        }
    }
}
