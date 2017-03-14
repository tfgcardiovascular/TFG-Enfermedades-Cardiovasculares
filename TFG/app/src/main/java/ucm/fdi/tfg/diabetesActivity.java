package ucm.fdi.tfg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class diabetesActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "diabetesActivity";

   //Buttons
    private Button diabetesButton;

    // Text
    private EditText identificacion;
    private Spinner tipo;
    private Spinner tratamiento;
    private EditText hbac;
    private Spinner monitor;
    private EditText años;
    private EditText ultimo;


    // Argument
    private Paciente argumentPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_paciente);

        // Text
        /*heightLabel = (TextView) findViewById(R.id.height);
        weightLabel = (TextView) findViewById(R.id.weight);
        resultLabel = (TextView) findViewById(R.id.result);*/

        identificacion = (EditText) findViewById(R.id.id);

        tipo = (Spinner) findViewById(R.id.spinner_tipo);
        tratamiento = (Spinner) findViewById(R.id.spinner2_tratamiento);
        hbac = (EditText) findViewById(R.id.resultado_hbac);
        monitor = (Spinner) findViewById(R.id.spinner3_glucosa);
        años = (EditText) findViewById(R.id.años_enfermedad);
        ultimo = (EditText) findViewById(R.id.ultimo);

        // Buttons
        diabetesButton = (Button) findViewById( R.id.button_diabetes );

        identificacion.setEnabled( false );

        // Get in data
        /*Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        // Set info data
        identificacion.setText( argumentPaciente.getId() );*/


        // Buttons Listener




    }
}
