package ucm.fdi.tfg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class tratamientoMenuActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "TratamientoMenuActivity";

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Algoritmos_Main_MenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tratamiento);

        Button Hombres = (Button) findViewById(R.id.button_hombres);
        Hombres.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the App activity
                Intent intent = new Intent(getApplicationContext(), RCV_Hombres_AlgoritmosActivity.class);
                startActivity(intent);
            }
        });

        Button Hombres_Diabeticos = (Button) findViewById(R.id.button_hombres_diabeticos);
        Hombres_Diabeticos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Intro activity
                Intent intent = new Intent(getApplicationContext(), RCV_Hombres_Diabeticos_AlgoritmosActivity.class);
                startActivity(intent);
            }
        });

        Button Mujeres = (Button) findViewById(R.id.button_mujeres);
        Mujeres.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the About us activity
                Intent intent = new Intent(getApplicationContext(), RCV_Muejeres_AlgoritmosActivity.class);
                startActivity(intent);
            }
        });

        Button Mujeres_Diabeticas = (Button) findViewById(R.id.button_mujeres_diabeticas);
        Mujeres_Diabeticas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the About us activity
                Intent intent = new Intent(getApplicationContext(), RCV_Muejeres_Diabeticas_AlgoritmosActivity.class);
                startActivity(intent);
            }
        });
    }
}
