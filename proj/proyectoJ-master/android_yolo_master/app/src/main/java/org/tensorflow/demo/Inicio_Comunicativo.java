package org.tensorflow.demo;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Inicio_Comunicativo extends AppCompatActivity {
    public static Fragment select=null;
    BottomNavigationView bview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__comunicativo);
        bview=findViewById(R.id.main_nav3);
        bview.setOnNavigationItemSelectedListener(navlis);
        ini();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navlis=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.opcion1:
                    select=new Fragment_Comunicativo();

                    break;
                case R.id.opcion22:
                    select=new Fragment_Comunicativo2();

                    break;
                case R.id.opcion33:
                    select=new Fragment_Comunicativo3();
                    break;
                case R.id.opcion4:
                    select=new Fragment_Comunicativo4();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frag3,select).commit();
            return true;
        }
    };
    private void ini(){
        select=new Fragment_Visual();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag3,select).commit();
    }
}
