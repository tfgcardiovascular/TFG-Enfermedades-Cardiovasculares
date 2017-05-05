package ucm.fdi.tfg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Algoritmos_Main_MenuActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "Algoritmos_MainMenuActivity";

    private Button Diabetes;
    private Button Colesterol;
    private Button Imc;
    private Button Hta;
    private Button Tabaquismo;
    private Button Tratamineto;

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), Main_MenuActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmos_menu);

        Diabetes = (Button) findViewById(R.id.diabetes);
        Colesterol = (Button) findViewById(R.id.colesterol);
        Imc = (Button) findViewById(R.id.imc);
        Hta = (Button) findViewById(R.id.hta);
        Tabaquismo = (Button) findViewById(R.id.tabaquismo);
        Tratamineto = (Button) findViewById(R.id.tratamiento);

        Diabetes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Registrar Diabetes paciente
                Intent intent = new Intent(getApplicationContext(), diabetesAlgoritmosActivity.class);
                startActivity(intent);
            }
        });

        Colesterol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Hta del medico
                Intent intent = new Intent(getApplicationContext(), colesterolAlgoritmosActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

        Tratamineto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Hta del medico
                Intent intent = new Intent(getApplicationContext(), tratamientoMenuActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

        Tabaquismo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Hta del medico
                Intent intent = new Intent(getApplicationContext(), tabacoAlgoritmosActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

        Imc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Hta del medico
                Intent intent = new Intent(getApplicationContext(), imcAlgoritmosActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

        Hta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Hta del medico
                Intent intent = new Intent(getApplicationContext(), htaAlgoritmosActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

    }
}
