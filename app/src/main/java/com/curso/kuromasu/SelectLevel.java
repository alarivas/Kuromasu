package com.curso.kuromasu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SelectLevel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        Button boton1 = (Button) findViewById(R.id.button1);
        boton1.setOnClickListener(new click(1,4));
        Button boton2 = (Button) findViewById(R.id.button2);
        boton2.setOnClickListener(new click(2,4));
        Button boton3 = (Button) findViewById(R.id.button3);
        boton3.setOnClickListener(new click(3,5));
        Button boton4 = (Button) findViewById(R.id.button4);
        boton4.setOnClickListener(new click(4,5));
        Button boton5 = (Button) findViewById(R.id.button5);
        boton5.setOnClickListener(new click(5,5));
        Button boton6 = (Button) findViewById(R.id.button6);
        boton6.setOnClickListener(new click(6,6));
        Button boton7 = (Button) findViewById(R.id.button7);
        boton7.setOnClickListener(new click(7,7));
        Button boton8 = (Button) findViewById(R.id.button8);
        boton8.setOnClickListener(new click(8,7));
        Button boton9 = (Button) findViewById(R.id.button9);
        boton9.setOnClickListener(new click(9,7));
        Button boton10 = (Button) findViewById(R.id.button10);
        boton10.setOnClickListener(new click(10,9));
        Button boton11 = (Button) findViewById(R.id.button11);
        boton11.setOnClickListener(new click(11,9));
        Button boton12 = (Button) findViewById(R.id.button12);
        boton12.setOnClickListener(new click(12,9));
        Button boton13 = (Button) findViewById(R.id.button13);
        boton13.setOnClickListener(new click(13,9));


    }


    class click implements View.OnClickListener{
        int level, size;
        public click(int _level, int _size){
            level = _level;
            size = _size;
        }

        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Level.class);
            intent.putExtra("level", level);
            intent.putExtra("size", size);
            startActivity(intent);
        }

    }
}
