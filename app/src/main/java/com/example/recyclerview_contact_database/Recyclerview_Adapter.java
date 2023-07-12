package com.example.recyclerview_contact_database;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview_contact_database.Model.Contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Recyclerview_Adapter extends RecyclerView.Adapter<Recyclerview_Adapter.Holder> {
    MainActivity mainActivity;
    ArrayList<Contact> contactslist;

    public Recyclerview_Adapter(MainActivity mainActivity, ArrayList<Contact> contactslist) {
        this.mainActivity=mainActivity;
        this.contactslist=contactslist;
    }

    @NonNull
    @Override
    public Recyclerview_Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.recyclerview_item_layout,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclerview_Adapter.Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.t1.setText(contactslist.get(position).getName());
        holder.t2.setText(contactslist.get(position).getNumber());
        Log.d("MMM", "onBindViewHolder: imgUri="+contactslist.get(position).getImgPath());
        loadImageFromStorage(contactslist.get(position).getImgPath(),holder.contact_img);



        holder.more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mainActivity,holder.more1);
                popupMenu.getMenuInflater().inflate(R.menu.update_delete_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.update)
                        {
                            Toast.makeText(mainActivity, "Updated" , Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(mainActivity, Add_Contact_Activity.class);
                            intent.putExtra("id",contactslist.get(holder.getAdapterPosition()).getId());
                            intent.putExtra("name",contactslist.get(position).getName());
                            intent.putExtra("number",contactslist.get(position).getNumber());

                            mainActivity.startActivity(intent);
                        }
                        if (item.getItemId()==R.id.delete)
                        {
                            My_Database my_database=new My_Database(mainActivity);
                            my_database.DeleteData(contactslist.get(position).getId());
                            contactslist.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(mainActivity, "Delete" , Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView contact_img,more1;
        TextView t1,t2;
        public Holder(@NonNull View itemView) {
            super(itemView);
            contact_img=itemView.findViewById(R.id.contact_img);
            t1=itemView.findViewById(R.id.item_name);
            t2=itemView.findViewById(R.id.item_number);
            more1=itemView.findViewById(R.id.more1);

        }
    }
    private void loadImageFromStorage(String path, ImageView contact_img)
    {

        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            contact_img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

}
