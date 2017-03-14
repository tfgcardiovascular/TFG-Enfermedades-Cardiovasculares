package ucm.fdi.tfg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class icmActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "icmActivity";

    private Button icmButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText heightData;
    private EditText weightData;
    private EditText resultData;
    private TextView clasifData;
    private EditText edad;

    // Argument
    private Paciente argumentPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icm_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        heightData = (EditText) findViewById(R.id.heightData);
        weightData = (EditText) findViewById(R.id.añosData);
        resultData = (EditText) findViewById(R.id.resultData);
        clasifData = (TextView) findViewById(R.id.clasifData);

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
