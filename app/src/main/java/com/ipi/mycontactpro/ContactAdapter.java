package com.ipi.mycontactpro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipi.mycontactpro.pojo.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnContactClickListener {
        void onContactClick(Contact contact);
    }

    private List<Contact> contacts = new ArrayList<>();
    private final OnContactClickListener listener;

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFullName;
        public TextView tvCompany;

        public ContactViewHolder(View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvCompany = itemView.findViewById(R.id.tvCompany);
        }

        public void bind(final Contact contact, final OnContactClickListener listener) {

            itemView.setOnClickListener(v -> {
                listener.onContactClick(contact);
            });
        }
    }

    public ContactAdapter(List<Contact> contacts, OnContactClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.tvFullName.setText(String.format("%s %s", contact.getLastName(), contact.getFirstName()));
        holder.tvCompany.setText(contact.getCompany());
        holder.bind(contacts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
