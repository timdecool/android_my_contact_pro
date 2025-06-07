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

public class AddContactActivity extends BaseContactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSubmit() {
        if(!isFormComplete()) {
            Toast toast = Toast.makeText(context, getString(R.string.incorrect_form), Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            contact = buildContactFromForm();
            contact.setFavorite(0);
            ContactDatabase.getDb(context).contactDAO().add(contact);
            finish();
        }
    }
}