package ucm.fdi.tfg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class htaActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "htaActivity";

    private Button icmButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText sistolica;
    private EditText diastolica;
    private EditText resultData;


    // Argument
    private Paciente argumentPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hta_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        sistolica = (EditText) findViewById(R.id.sistolicaData);
        diastolica = (EditText) findViewById(R.id.añosData);
        resultData = (EditText) findViewById(R.id.resultData);

        // Buttons
        icmButton = (Button) findViewById( R.id.button_calcular_tabaco);

        // Get in data
        /*Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        // Set info data
        identificacion.setText( argumentPaciente.getId() );*/

        identificacion.setEnabled( false );
        resultData.setEnabled( false );


        // Buttons Listener




    }
}
