package org.tensorflow.demo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.tensorflow.demo.SQLite.Puntuacion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Resultados_Duolingo extends AppCompatActivity {

    int total,aciertos;
    ImageView img;
    Button btnRegresar,btnIntentarlo;
    TextView txtRes,txtRestxt;
    boolean update=false;
    String PPa[],PAc[];

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados__duolingo);

        img=findViewById(R.id.IMGResultado);
        txtRes=findViewById(R.id.txtResultado);
        txtRestxt=findViewById(R.id.txtResultadotxt);

        Puntuacion cnx = new Puntuacion(getApplicationContext(), "Puntuacion", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM Puntuacion",null);

        while(c.moveToNext()) {
            txtRestxt.setText("Máxima puntuacion: " + c.getString(0) + " del " + c.getString(1) + "\n");
            PPa = c.getString(0).split("/");
        }


        btnRegresar=findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),Inicio_Visual.class));
            }
        });

        btnIntentarlo=findViewById(R.id.btnIntentar);
        btnIntentarlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(),Duolingo.class));
            }
        });

        total=getIntent().getIntExtra("total",10);
        aciertos=getIntent().getIntExtra("aciertos",0);
        Log.i("AQUI","Resultados  \nAciertos "+aciertos+" \nTotal "+total);
        Settear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Settear() {
        double rs=aciertos/total;
    rs=rs*100;
        txtRes.setText(rs+"%");
    if(rs==0.0){
        txtRes.setText(aciertos+"/"+total);
    }

int imagen=0;
String txt="";
        if(total==aciertos){
         imagen = (R.drawable.resultadotodas);
         txt = "¡Perfecto!, has acertado el 100% de las respuestas.\nAprendes muy rápido.";
        }else if(aciertos>(total/2)){
           imagen= (R.drawable.resultadocasitodas);
            txt = "¡Casi!, has acertado la mayoría de las respuestas.\nPuedes hacerlo mejor.";
        }else if(aciertos==(total/2)) {
            imagen=(R.drawable.resultadomitad);
            txt = "¡Puedes mejorar!, has acertado la mitad de las respuestas.\nEstudia más.";
        }else if(aciertos==0){
            imagen=(R.drawable.resultadoninguna);
            txt = "¡Muy mal!, no has acertado ninguna de las respuestas.\n¡A repasar todo!";
        }else {
           imagen = (R.drawable.resultadocasininguna);
            txt = "¡Estudia!, has acertado menos de la mitad de las respuestas.\nNecesitas repasar más la lección.";
        }
        img.setImageResource(imagen);
        txtRestxt.append(txt);
        Puntuacion cnx = new Puntuacion(getApplicationContext(), "Puntuacion", null, 1);
        SQLiteDatabase db = cnx.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("PuntuacionMax",txtRes.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        cv.put("fecha",fecha);
        //db.update("Puntuacion",cv,"",null);
        PAc=txtRes.getText().toString().split("/");
        if(Integer.parseInt(PAc[0])>Integer.parseInt(PPa[0])){
            db.update("Puntuacion", cv, "", null);
        }
    }
}