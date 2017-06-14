package com.curso.kuromasu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Level extends AppCompatActivity {
    int num, level;
    Button buttons[][] = null;
    StringBuilder data = new StringBuilder();
    StringBuilder sol = new StringBuilder();
    String sol_parse[];
    String data_parse[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Intent intent = getIntent();
        num = intent.getIntExtra("size", -1);
        level = intent.getIntExtra("level", -1);
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        TextView text= (TextView)findViewById(R.id.title);
        if(level != 0){
            text.setText("Nivel "+ level);
        } else {
            text.setText("Nivel Guardado");
        }
        text.setTextSize(30);

        buttons = new Button[num][num];
        Point p = new Point();

        try { drawMap(); }
        catch (IOException e) { e.printStackTrace();    }

        Display d = getWindowManager().getDefaultDisplay();
        d.getSize(p);

        for (int i= 0, c=0, r=0; i<num*num; i++,c++){
            if(c==num){c=0;r++;}
            buttons[r][c] = new Button(this);
            if(isNumeric(data_parse[i])) {
                buttons[r][c].setText(data_parse[i]);
                buttons[r][c].setBackgroundColor(Color.WHITE);
                buttons[r][c].setTag("num");
            }
            else if(data_parse[i].equals(".")){
                buttons[r][c].setTag("gray");
                buttons[r][c].setOnClickListener(new myClick(r,c));
                if((r+c)%2==0){
                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorGray1));
                }else{
                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorGray2));
                }
            } else if(data_parse[i].equals("B")){
                buttons[r][c].setTag("white");
                buttons[r][c].setBackgroundColor(Color.WHITE);
                buttons[r][c].setOnClickListener(new myClick(r,c));
            } else if(data_parse[i].equals("N")){
                buttons[r][c].setTag("black");
                buttons[r][c].setBackgroundColor(Color.BLACK);
                buttons[r][c].setOnClickListener(new myClick(r,c));
            }
            grid.addView(buttons[r][c], i);

            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height= p.x/num;
            param.width = p.x/num;
            param.columnSpec=GridLayout.spec(c);
            param.rowSpec=GridLayout.spec(r);
            buttons[r][c].setLayoutParams(param);
        }
    }

    private void drawMap() throws IOException {
        BufferedReader reader;
        String string= "Kuromasu4x4_01.txt";
        switch(level){
            case 0:
                try{
                    drawSaveMap();
                }
                catch (Exception e) {
                }
                return;
            case 1:
                string="Kuromasu4x4_01.txt";
                break;
            case 2:
                string="Kuromasu4x4_02.txt";
                break;
            case 3:
                string="Kuromasu5x5_01.txt";
                break;
            case 4:
                string="Kuromasu5x5_02.txt";
                break;
            case 5:
                string="Kuromasu5x5_03.txt";
                break;
            case 6:
                string="Kuromasu6x6_01.txt";
                break;
            case 7:
                string="Kuromasu7x7_01.txt";
                break;
            case 8:
                string="Kuromasu7x7_02.txt";
                break;
            case 9:
                string="Kuromasu7x7_03.txt";
                break;
            case 10:
                string="Kuromasu9x9_01.txt";
                break;
            case 11:
                string="Kuromasu9x9_02.txt";
                break;
            case 12:
                string="Kuromasu9x9_03.txt";
                break;
            case 13:
                string="Kuromasu9x9_04.txt";
                break;
            default:
                finish();
        }

        reader = new BufferedReader(new InputStreamReader(getAssets().open(string)));


        String line;
        for(int i=0; (line = reader.readLine()) != null ;i++){
            if(i<num) {
                data.append(line);
                data.append(' ');
            }
            else if(i==num) continue;
            else {
                sol.append(line);
                sol.append(' ');
            }
        }
        data_parse = data.toString().split(" ");
        sol_parse = sol.toString().split(" ");
    }

    private void drawSaveMap() throws Exception {
        File file = new File(getFilesDir(), "saveLevel.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null){
            data.append(line);
            data.append(' ');

        }

        data_parse = data.toString().split(" ");

    }
    private boolean isNumeric(String string){
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private int twoBlackCells(){
        for(int r=0; r< num; r++)
            for(int c=0; c<num;c++) {
                if (buttons[r][c].getTag() == "black") {
                    //check left
                    if (c > 0) {
                        if (buttons[r][c - 1].getTag() == "black") {
                            return 2;
                        }
                    }
                    //check right
                    if (c < num - 1) {
                        if (buttons[r][c + 1].getTag() == "black") {
                            return 2;
                        }
                    }
                    //check up
                    if (r > 0) {
                        if (buttons[r - 1][c].getTag() == "black") {
                            return 2;
                        }
                    }
                    //check down
                    if (r < num - 1) {
                        if (buttons[r + 1][c].getTag() == "black") {
                            return 2;
                        }
                    }
                }
            }

        return 0;
    }

    private void displayMsg(int path, int black_cells, int white_cells){
        int [] msg_id={white_cells,black_cells,path};
        int max=0;
        TextView msg = (TextView)findViewById(R.id.msg);
        for(int i=0; i< 3; i++){
            if(max<msg_id[i]){
                max=msg_id[i];
            }
        }

        switch(max){
            case 0:
                msg.setText("Ganaste!!!!");
                break;
            case 1:
                msg.setText("No hay camino de casillas blancas");
                break;
            case 2:
                msg.setText("No se permiten dos casillas negras juntas");
                break;
            case 3:
                msg.setText("Una(s) casilla(s) ve muchas casillas blancas");
                break;
            default:
                msg.setText("error");
                break;
        }

    }

    private int countWhites(int r,int c){
        int counter = 1;
        int aux = r+1;
        //count up
        while(aux < num){
            if(buttons[aux][c].getTag()=="white" || buttons[aux][c].getTag()=="num"){
            aux++;
            counter++;
            }else break;
        }
        //count down
        aux=r-1;
        while(aux > -1){
            if (buttons[aux][c].getTag()=="white" || buttons[aux][c].getTag()=="num") {
                aux--;
                counter++;
            }else break;
        }

        //count left
        aux= c-1;
        while(aux>-1){
            if(buttons[r][aux].getTag()=="white" || buttons[r][aux].getTag()=="num"){
                aux--;
                counter++;
            }else break;
        }
        //count right
        aux = c+1;
        while(aux<num){
            if(buttons[r][aux].getTag()=="white" || buttons[r][aux].getTag()=="num"){
                aux++;
                counter++;
            }else break;
        }


        return counter;

    }

    private int seeingTooMany(){

        for(int i =0; i<num; i++){
            for(int j=0; j<num; j++){
                if(buttons[i][j].getTag()=="num"){
                    if(countWhites(i,j)> Integer.parseInt(buttons[i][j].getText().toString())){
                        return 3;
                    }
                }
            }
        }

        return 0;
    }

    private void paintNumbers(){
        for (int i=0; i< num; i++)
            for(int j=0; j< num; j++){
                if(buttons[i][j].getTag()=="num"){
                    if(countWhites(i,j)> Integer.parseInt(buttons[i][j].getText().toString()))
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorRed));
                    else
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
    }

    private int checkP(){
        List<String> disponibles = new ArrayList<>();
        List<String> agregados = new ArrayList<>();
        for(int i=0;i<num;i++)
            for(int j=0;j<num;j++)
                if(buttons[i][j].getTag() == "white" || buttons[i][j].getTag() == "num") disponibles.add(""+i+j);
        int x,y;
        if(checkNoGray())
            for(x=0;x<num;x++)
                for(y=0;y<num;y++)
                    if(buttons[x][y].getTag()=="white") {
                        if(checkPath(x,y,disponibles.size(),disponibles,agregados)) return 0;
                        return 1;
                    }
        return 1;
    }

    private boolean checkPath(int r, int c, int total, List<String> disponibles, List<String> agregados) {
        disponibles.remove(""+r+c);
        agregados.add(""+r+c);
        if(disponibles.isEmpty()) return true;
        if(r!=num-1 && disponibles.contains(""+(r+1)+c)) if(checkPath(r+1,c,total,disponibles ,agregados)) return true;
        if(c!=num-1&& disponibles.contains(""+r+(c+1))) if(checkPath(r,c+1,total,disponibles,agregados)) return true;
        if(r!=0&& disponibles.contains(""+(r-1)+c)) if(checkPath(r-1,c,total,disponibles,agregados)) return true;
        if(c!=0&& disponibles.contains(""+r+(c-1))) if(checkPath(r,c-1,total,disponibles,agregados))return true;
        return false;
    }

    private boolean checkNoGray(){
        for(int i=0;i<num;i++)
            for(int j=0;j<num;j++)
                if (buttons[i][j].getTag() == "gray") return false;
        return true;
    }

    public void listenerSave(View view) throws Exception {

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("saveLevel.txt", Context.MODE_PRIVATE));
        String symbol="";
        outputStreamWriter.write(num+"\n");
        for(int i = 0; i < num; i++){
            for(int j=0;j<num; j++){
                if(buttons[i][j].getTag()=="black"){
                    symbol="N";
                } else if(buttons[i][j].getTag()=="white"){
                    symbol="B";
                } else if(buttons[i][j].getTag()=="num") {
                    symbol= buttons[i][j].getText().toString();

                } else if(buttons[i][j].getTag()=="gray"){
                    symbol=".";
                } else{
                }
                if(j==num-1){
                    outputStreamWriter.write(symbol);
                }else{
                    outputStreamWriter.write(symbol+" ");
                }
            }
            outputStreamWriter.write("\n");
        }

        outputStreamWriter.close();
        Toast.makeText(this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
    }

    private class myClick implements View.OnClickListener {
        int r,c;
        private myClick(int _r, int _c){r=_r; c=_c;}

        public void onClick(View view){
            if(buttons[r][c].getTag() == "gray") {
                buttons[r][c].setBackgroundColor(Color.WHITE);
                buttons[r][c].setTag("white");
            }
            else if(buttons[r][c].getTag() == "white"){
                buttons[r][c].setBackgroundColor(Color.BLACK);
                buttons[r][c].setTag("black");
            }
            else if(buttons[r][c].getTag() == "black"){
                buttons[r][c].setTag("gray");
                if((r+c)%2==0){
                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorGray1));
                }else{
                    buttons[r][c].setBackgroundColor(getResources().getColor(R.color.colorGray2));
                }
            }
            displayMsg(checkP(), twoBlackCells(), seeingTooMany());
            paintNumbers();

        }

    }
}
