package com.ipi.mycontactpro;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ipi.mycontactpro.database.ContactDatabase;
import com.ipi.mycontactpro.pojo.Contact;

public class AddContactActivity extends AppCompatActivity {

    private Context context;
    private EditText etLastName;
    private EditText etFirstName;
    private EditText etCompany;
    private EditText etAddress;
    private EditText etPhone;
    private EditText etEmail;
    private Spinner svSector;
    private Button btnAdd;
    private Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find views by id
        context = getApplicationContext();
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etCompany = findViewById(R.id.etCompany);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        svSector = findViewById(R.id.svSector);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        // Toolbar integration
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Spinner options integration
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sectors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        svSector.setAdapter(adapter);

        // Button event listeners
        btnCancel.setOnClickListener(v -> { finish(); });
        btnAdd.setOnClickListener(v -> { addContact(); });
    }

    public void addContact() {
        if(!isFormComplete()) {
            Toast toast = Toast.makeText(context, "Certains champs sont vides ou incorrects", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Contact contact = new Contact();
            contact.setLastName(etLastName.getText().toString());
            contact.setFirstName(etFirstName.getText().toString());
            contact.setCompany(etCompany.getText().toString());
            contact.setAddress(etAddress.getText().toString());
            contact.setPhone(etPhone.getText().toString());
            contact.setEmail(etEmail.getText().toString());
            contact.setSector(svSector.getSelectedItem().toString());
            contact.setFavorite(0);

            ContactDatabase.getDb(context).contactDAO().add(contact);
            finish();
        }
    }

    public boolean isFormComplete() {
        return !etLastName.getText().toString().trim().isEmpty()
                && !etFirstName.getText().toString().trim().isEmpty()
                && !etCompany.getText().toString().trim().isEmpty()
                && etEmail.getText().toString().trim().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
                && etPhone.getText().toString().trim().matches("^\\+?[0-9]{7,15}$")
                ;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}