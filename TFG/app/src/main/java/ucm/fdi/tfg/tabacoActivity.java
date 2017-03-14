package ucm.fdi.tfg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class tabacoActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "tabacoActivity";

    private Button tabacoButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText años;
    private EditText cantidad;

    // Argument
    private Paciente argumentPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabaco_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        años = (EditText) findViewById(R.id.cantidadData);
        cantidad = (EditText) findViewById(R.id.añosData);


        // Buttons
        tabacoButton = (Button) findViewById( R.id.button_calcular_tabaco);

        // Get in data
        /*Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        // Set info data
        identificacion.setText( argumentPaciente.getId() );*/

        identificacion.setEnabled( false );

        // Buttons Listener


    }
}
