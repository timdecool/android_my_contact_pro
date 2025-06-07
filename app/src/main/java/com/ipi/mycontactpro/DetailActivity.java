package com.ipi.mycontactpro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ipi.mycontactpro.database.ContactDatabase;
import com.ipi.mycontactpro.pojo.Contact;

public class DetailActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_SMS = 2;
    private static final int REQUEST_LOCATION = 3;
    private Context context;
    private TextView tvFullName;
    private TextView tvCompany;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvEmail;
    private TextView tvSector;
    private Button btnCall;
    private Button btnLocate;
    private Button btnSms;
    private Button btnFav;
    private Contact contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = getApplicationContext();
        // Find views
        tvFullName = findViewById(R.id.tvFullName);
        tvCompany = findViewById(R.id.tvCompany);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvSector = findViewById(R.id.tvSector);
        tvAddress = findViewById(R.id.tvAddress);
        btnCall = findViewById(R.id.btnCall);
        btnLocate = findViewById(R.id.btnLocate);
        btnSms = findViewById(R.id.btnSms);
        btnFav = findViewById(R.id.btnFav);

        // Set texts based on intent
        Intent intent = getIntent();
        contact = (Contact)intent.getSerializableExtra(MainActivity.KEY_CONTACT);
        if(contact != null) {
            tvFullName.setText(String.format("%s %s", contact.getLastName(), contact.getFirstName()));
            tvCompany.setText(contact.getCompany());
            tvPhone.setText(contact.getPhone());
            tvEmail.setText(contact.getEmail());
            tvSector.setText(contact.getSector());
            tvAddress.setText(contact.getAddress());
            if(contact.getFavorite() == 1) {
                btnFav.setText("Retirer des favoris");
            }
        }

        // Button events
        btnCall.setOnClickListener(v -> startCall());
        btnLocate.setOnClickListener(v -> locate());
        btnSms.setOnClickListener(v -> sendSms());
        btnFav.setOnClickListener(v -> toggleFav());

        // Toolbar integration
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void startCall() {
        if(contact == null || contact.getPhone() == null) {
            Toast toast = Toast.makeText(context, "Pas de numéro de téléphone", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contact.getPhone()));
            startActivity(intent);
        }
    }

    public void sendSms() {
        if(contact == null || contact.getPhone() == null) {
            Toast toast = Toast.makeText(context, "Pas de numéro de téléphone", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS);
        } else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + contact.getPhone()));
            startActivity(intent);
        }
    }

    public void locate() {
        if(contact == null || contact.getAddress() == null) {
            Toast toast = Toast.makeText(context, "Pas d'adresse", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=" + contact.getAddress()));
            startActivity(intent);
        }
    }

    public void toggleFav() {
        if(contact.getFavorite() == 0)
        {
            contact.setFavorite(1);
            btnFav.setText("Retirer des favoris");
        }
        else
        {
            contact.setFavorite(0);
            btnFav.setText("Ajouter aux favoris");
        }
        ContactDatabase.getDb(context).contactDAO().update(contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.updateContact) {
            Intent intent = new Intent(context, UpdateContactActivity.class);
            intent.putExtra(MainActivity.KEY_CONTACT, contact);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId() == R.id.deleteContact) {
            ContactDatabase.getDb(context).contactDAO().delete(contact);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}