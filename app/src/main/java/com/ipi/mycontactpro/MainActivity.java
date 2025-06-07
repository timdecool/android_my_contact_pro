package com.ipi.mycontactpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipi.mycontactpro.database.ContactDatabase;
import com.ipi.mycontactpro.pojo.Contact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_CONTACT = "contact";
    private Context context;
    private RecyclerView rvContacts;
    private boolean favoriteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = getApplicationContext();
        initView();
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        rvContacts = findViewById(R.id.rvContact);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvContacts.setHasFixedSize(true);
        rvContacts.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayList(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addContact) {
            Intent intent = new Intent(context, AddContactActivity.class);
            startActivity(intent);
            return  true;
        }
        else if(item.getItemId() == R.id.favorites) {
                favoriteMode = !favoriteMode;
                displayList(favoriteMode);
                item.setTitle(favoriteMode ? getString(R.string.menu_all):getString(R.string.menu_favorites));
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayList(boolean favorites) {
        List<Contact> contacts;
        if(favorites) {
            contacts = ContactDatabase.getDb(context).contactDAO().favorites();
        }
        else {
            contacts = ContactDatabase.getDb(context).contactDAO().list();
        }

        new Thread(() -> runOnUiThread(() -> {
            ContactAdapter contactAdapter = new ContactAdapter(contacts, contact -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(KEY_CONTACT, contact);
                startActivity(intent);
            });
            rvContacts.setAdapter(contactAdapter);
        })).start();
    }
}