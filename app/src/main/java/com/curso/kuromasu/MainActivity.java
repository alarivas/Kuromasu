package com.curso.kuromasu;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.MainTitle);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/snickles.ttf");
        text.setTypeface(font);

    }


    public void listenerPlay(View view){
        Intent intent = new Intent(this, SelectLevel.class);
        startActivity(intent);
    }

    public void listenerInstructions(View view){
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void listenerLoad(View view) throws Exception {

        File file = new File(getFilesDir(), "saveLevel.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string = reader.readLine();
        int size = Integer.parseInt(string);
        reader.close();
        Intent intent = new Intent(this, Level.class);
        intent.putExtra("size", size);
        intent.putExtra("level", 0);
        startActivity(intent);

    }

    public void listenerExit(View view){
        finishAffinity();
    }



}
