package ucm.fdi.tfg;

import android.content.Intent;
import android.net.Uri;
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
    private Button Perfil;
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
        Perfil = (Button) findViewById(R.id.button_profile);
        Farmacos = (Button) findViewById(R.id.button_drugs);

        //Medico currentLead =


        Nuevo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Registrar nuevo paciente
                Intent intent = new Intent(getApplicationContext(), NuevoPacienteActivity.class);
                startActivity(intent);
            }
        });

        Pacientes_Guardados.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Pacientes guardados
                Intent intent = new Intent(getApplicationContext(), MisPacientesActivity.class);
                startActivity(intent);
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Perfil del medico
                Intent intent = new Intent(getApplicationContext(), MedicoPerfilActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });

        Farmacos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Navegar url de farmacos
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.vademecum.es/medicamentos-a_1"));
                startActivity(intent);
            }
        });

        Algoritmos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Perfil del medico
                Intent intent = new Intent(getApplicationContext(), Algoritmos_Main_MenuActivity.class);
                //intent.putExtra( "medic", currentLead);
                startActivity(intent);
            }
        });
    }
}
