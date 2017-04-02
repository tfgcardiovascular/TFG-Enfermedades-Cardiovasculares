package ucm.fdi.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main_MenuActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "MainMenuActivity";

    private Button Nuevo;
    private Button Pacientes_Guardados;
    private Button Algoritmos;
    private Button Farmacos;

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Nuevo = (Button) findViewById(R.id.button_new_patient);
        Pacientes_Guardados = (Button) findViewById(R.id.button_patients);
        Algoritmos = (Button) findViewById(R.id.button_algorithms);
        //Farmacos = (Button) findViewById(R.id.button_drugs);

        Nuevo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), NuevoPacienteActivity.class);
                startActivity(intent);
            }
        });

        Pacientes_Guardados.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), MisPacientesActivity.class);
                startActivity(intent);
            }
        });
    }
}
