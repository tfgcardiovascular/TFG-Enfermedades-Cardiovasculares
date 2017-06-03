package ucm.fdi.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

public class MisPacientesActivity extends AppCompatActivity{
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "MisPacientesActivity";
    private ListView mListView;
    private Button Validar_Button;
    private Button Rechazar_Button;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public void onBackPressed() {

        Intent intent;

        if ( DAOCardiovascular.getInstance().isAdmin() )
        {
            intent = new Intent(MisPacientesActivity.this, adminActivity.class);
        }else {
            intent = new Intent(MisPacientesActivity.this, Main_MenuActivity.class);
        }

        startActivity(intent);

    }

    protected void onResume(){
        super.onResume();


    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Crear lista
        setContentView(R.layout.activity_lista_paciente);

        FragmentPaciente leadsFragment = (FragmentPaciente)
                getSupportFragmentManager().findFragmentById(R.id.leads_container);

        if (leadsFragment == null) {
            leadsFragment = FragmentPaciente.newInstance();
            //leadsFragment.onCreateView( R.id.category_container, android.R.layout.simple_list_item_1, false );
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.leads_container, leadsFragment)
                    .commit();
        }
    }
}

