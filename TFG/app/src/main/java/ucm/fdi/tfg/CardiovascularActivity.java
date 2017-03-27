package ucm.fdi.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CardiovascularActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "CardiovascularActivity";

    // Buttons
    private Button tobaccoButton;
    private Button colTotalButton;
    private Button colHdlButton;
    private Button htaButton;
    private Button imcButton;
    private Button tabaquismoButton;
    private Button diabetesButton;


    // Text
    private TextView identif;
    private TextView eda;
    private TextView sexo;

    private EditText identificacion;
    private EditText edad;

    private RadioButton Masculino;
    private RadioButton Femenino;

    // Argument
    private Paciente argumentPaciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_riesgo_paciente);

        // Buttons
        tabaquismoButton = ( Button ) findViewById ( R.id.toggleFumador );
        diabetesButton = (Button) findViewById(R.id.toggleDiabetes);

        colTotalButton = (Button) findViewById( R.id.button_col_total );
        colHdlButton = ( Button ) findViewById( R.id.button_col_HDL );

        htaButton = ( Button ) findViewById( R.id.button_calcular_HTA );
        imcButton = ( Button ) findViewById( R.id.button_calcular_IMC );

        // Text
        identif = (TextView) findViewById(R.id.identificacion);
        eda = (TextView) findViewById(R.id.weight);
        sexo = (TextView) findViewById(R.id.height);

        identificacion = (EditText) findViewById(R.id.id);
        edad = (EditText) findViewById(R.id.diastolicaData);

        Masculino = (RadioButton) findViewById(R.id.RB_genero_hombre);
        Femenino = (RadioButton) findViewById(R.id.RB_genero_mujer);

        // Get in data
        Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        // Set info data
        identificacion.setText( argumentPaciente.getId() );
        edad.setText( argumentPaciente.getEdad() );

        String sexValue =  argumentPaciente.getSexo();

        if ( sexValue.equals ( "M" ) )
        {
            Masculino.setChecked( true );

        }else
        {
            Femenino.setChecked( true );
        }

        identificacion.setEnabled( false );
        edad.setEnabled( false );
        Masculino.setEnabled( false );
        Femenino.setEnabled( false );

        // Buttons Listener
        diabetesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), diabetesActivity.class);
                startActivity(intent);
            }
        });

        imcButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), icmActivity.class);
                intent.putExtra( "paciente", argumentPaciente );
                startActivity(intent);
            }
        });

        htaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), htaActivity.class);
                intent.putExtra( "paciente", argumentPaciente );
                startActivity(intent);
            }
        });

        tabaquismoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), tabacoActivity.class);
                intent.putExtra( "paciente", argumentPaciente );
                startActivity(intent);
            }
        });



/*

        colTotalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), colTotalActivity.class);
                startActivity(intent);

            }
        });

        colHdlButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), colHdlActivity.class);
                startActivity(intent);

            }
        });

             cardiovascularButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), cardiovascularResultActivity.class);
                startActivity(intent);

            }
        });


*/


    }
}
