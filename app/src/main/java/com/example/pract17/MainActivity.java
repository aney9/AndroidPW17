package com.example.pract17;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText name, descr, price, img;
    private ListView list;

    private ArrayAdapter<String> adapter;
    private String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Paper.init(this);


        btnAdd = findViewById(R.id.addButton);
        name = findViewById(R.id.Name);
        descr = findViewById(R.id.Description);
        price = findViewById(R.id.Price);
        img = findViewById(R.id.Image);
        list = findViewById(R.id.listView);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getCloth());

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select = adapter.getItem(position);
                Cloth cl = Paper.book().read(select, null);


                if (cl != null){
                    Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                    intent.putExtra("cloth", select);
                    startActivity(intent);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Descr = descr.getText().toString();
                String Price = price.getText().toString();
                String Img = img.getText().toString();
                Float Pricee = Float.parseFloat(Price);
                if (!Name.isEmpty() && !Descr.isEmpty()){
                    Cloth cl = new Cloth(null, Name, Descr, Pricee, Img);
                    Paper.book().write(Name, cl);
                    UpdateList();
                    clearInputs();
                }
            }
        });
    }

    private void clearInputs(){
        name.setText("");
        descr.setText("");
        price.setText("");
        img.setText("");
        select = null;
    }

    private void UpdateList(){
        adapter.clear();
        adapter.addAll(getCloth());
        adapter.notifyDataSetChanged();
    }

    private List<String> getCloth(){
        return new ArrayList<>(Paper.book().getAllKeys());
    }
}