package com.ipi.mycontactpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

public class UpdateContactActivity extends BaseContactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnAdd.setText(getString(R.string.update));

        contact = (Contact) getIntent().getSerializableExtra(MainActivity.KEY_CONTACT);
        if(contact != null) {
            etLastName.setText(contact.getLastName());
            etFirstName.setText(contact.getFirstName());
            etCompany.setText(contact.getCompany());
            etAddress.setText(contact.getAddress());
            etPhone.setText(contact.getPhone());
            etEmail.setText(contact.getEmail());

            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) svSector.getAdapter();
            svSector.setSelection(adapter.getPosition(contact.getSector()));
        }
    }

    @Override
    protected void onSubmit() {
        if (!isFormComplete()) {
            Toast.makeText(context, getString(R.string.incorrect_form), Toast.LENGTH_SHORT).show();
        } else {
            contact = buildContactFromForm();
            ContactDatabase.getDb(context).contactDAO().update(contact);

            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.KEY_CONTACT, contact);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}