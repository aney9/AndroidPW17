package com.example.pract17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class Second_Activity extends AppCompatActivity {

    Button edit, delete, exit;
    EditText name, discr, price, img;
    ImageView imgg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        edit = findViewById(R.id.editButton);
        delete = findViewById(R.id.deleteButton);
        exit = findViewById(R.id.exitButton);
        name = findViewById(R.id.Name);
        discr = findViewById(R.id.Description);
        price = findViewById(R.id.Price);
        img = findViewById(R.id.Image);
        imgg = findViewById(R.id.imgg);

        String select = getIntent().getStringExtra("cloth");
        if (select != null) {
            Cloth cl = Paper.book().read(select);
            if (cl != null) {
                name.setText(cl.getName());
                discr.setText(cl.getDescription());
                price.setText(cl.getPrice().toString());
                img.setText(cl.getImage());

                String imggg = cl.getImage();
                int imga = getResources().getIdentifier(imggg, "drawable", getPackageName());
                imgg.setImageResource(imga);
            }
        }

        // Кнопка выхода
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Кнопка редактирования
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Descr = discr.getText().toString();
                String Price = price.getText().toString();
                String Img = img.getText().toString();

                if (Name.isEmpty() || Descr.isEmpty() || Price.isEmpty()) {
                    Toast.makeText(Second_Activity.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float Pricee;
                try {
                    Pricee = Float.parseFloat(Price);
                } catch (NumberFormatException e) {
                    Toast.makeText(Second_Activity.this, "Цена должна быть числом!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Name.equals(select)) {
                    Paper.book().delete(select);
                }

                Cloth cl = new Cloth(null, Name, Descr, Pricee, Img);
                Paper.book().write(Name, cl);

                Toast.makeText(Second_Activity.this, "Данные обновлены!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                if (!Name.isEmpty()) {
                    Paper.book().delete(select);
                    Toast.makeText(Second_Activity.this, "Элемент удалён!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Second_Activity.this, "Невозможно удалить пустой элемент", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
