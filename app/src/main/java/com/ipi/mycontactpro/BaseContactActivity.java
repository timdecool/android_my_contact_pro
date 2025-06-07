package com.ipi.mycontactpro;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ipi.mycontactpro.pojo.Contact;

public abstract class BaseContactActivity extends AppCompatActivity {

    protected Context context;
    protected EditText etLastName, etFirstName, etCompany, etAddress, etPhone, etEmail;
    protected Spinner svSector;
    protected Button btnAdd, btnCancel;

    protected Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact); // layout commun

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = getApplicationContext();
        initView();
        setupToolbar();
        setupSpinner();
        setupListeners();
    }

    private void initView() {
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etCompany = findViewById(R.id.etCompany);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        svSector = findViewById(R.id.svSector);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sectors_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        svSector.setAdapter(adapter);
    }

    private void setupListeners() {
        btnCancel.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> onSubmit());
    }

    protected boolean isFormComplete() {
        return !etLastName.getText().toString().trim().isEmpty()
                && !etFirstName.getText().toString().trim().isEmpty()
                && !etCompany.getText().toString().trim().isEmpty()
                && etEmail.getText().toString().trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                && etPhone.getText().toString().trim().matches("^\\+?[0-9]{7,15}$");
    }

    protected Contact buildContactFromForm() {
        Contact c = (contact != null) ? contact : new Contact();
        c.setLastName(etLastName.getText().toString());
        c.setFirstName(etFirstName.getText().toString());
        c.setCompany(etCompany.getText().toString());
        c.setAddress(etAddress.getText().toString());
        c.setPhone(etPhone.getText().toString());
        c.setEmail(etEmail.getText().toString());
        c.setSector(svSector.getSelectedItem().toString());
        return c;
    }

    protected abstract void onSubmit();
}