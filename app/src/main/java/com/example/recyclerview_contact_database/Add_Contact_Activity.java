package com.example.recyclerview_contact_database;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Add_Contact_Activity extends AppCompatActivity {
     ImageView back,maore2,add_update;
     ImageView update_image;
     EditText name,number;
     int id;
     String name1,number1,image1;
     Uri  image_uri;
     String imgName;
    Bitmap bitmap;
    String imgPath;
    My_Database my_database=new My_Database(Add_Contact_Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        back=findViewById(R.id.back);
        maore2=findViewById(R.id.more2);
        add_update=findViewById(R.id.add_update);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        update_image=findViewById(R.id.update_image);

        id=getIntent().getIntExtra("id",0);
        name1=getIntent().getStringExtra("name");
        number1=getIntent().getStringExtra("number");
        imgName="Img_"+new Random().nextInt(10000)+".jpg";
        update_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });

        if (getIntent().getExtras()!=null)
        {
            name.setText(""+name1);
            number.setText(""+number1);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Add_Contact_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        add_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromStorage(imgPath);
                if (getIntent().getExtras()==null)
                {
                    String n1=name.getText().toString();
                    String n2=number.getText().toString();
                    my_database.addContact(""+n1,""+n2,imgPath);
                }
                else
                {
                    String n1=name.getText().toString();
                    String n2=number.getText().toString();
                    my_database.updateContact(id,""+n1,""+n2);
                }
                Intent intent=new Intent(Add_Contact_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 100 && data != null) {
                image_uri = data.getData();
                Log.d("TTT", "onActivityResult: ImgUri="+image_uri);
                update_image.setImageURI(image_uri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                imgPath=saveToInternalStorage(bitmap);
                imgPath=imgPath+"/"+imgName;
            }
        }


    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imgName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=findViewById(R.id.otherImg);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
    
}