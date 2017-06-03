package ucm.fdi.tfg;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class diabetesActivity extends AppCompatActivity implements OnClickListener {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "diabetesActivity";

   //Buttons

    // Text
    private EditText identificacion;
    private Spinner tipo;
    private Spinner tratamiento;
    private EditText hbac;
    private Spinner monitor;
    private EditText años;
    private EditText ultimo;
    private EditText creatinina;
    private EditText microalbuminuria;
    private Switch raza;
    private EditText urea;

    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat dateFormatter;

    String race;
    int hbacValue;

    // Argument
    private Paciente argumentPaciente;

    private Button diabetesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_paciente);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        identificacion = (EditText) findViewById(R.id.id);

        tipo = (Spinner) findViewById(R.id.spinner_tipo);
        tratamiento = (Spinner) findViewById(R.id.spinner2_tratamiento);
        hbac = (EditText) findViewById(R.id.resultado_hbac);
        monitor = (Spinner) findViewById(R.id.spinner3_glucosa);
        años = (EditText) findViewById(R.id.años_enfermedad);

        ultimo = (EditText) findViewById(R.id.ultimo);
        ultimo.setInputType(InputType.TYPE_NULL);

        creatinina = ( EditText ) findViewById( R.id.creatinina );
        microalbuminuria = ( EditText ) findViewById( R.id.microalbuminuria );
        raza = (Switch) findViewById( R.id.raza );
        urea = ( EditText ) findViewById( R.id.urea );

        // Buttons
        diabetesButton = (Button) findViewById( R.id.button_diabetes );

        //Spinners
        //Tipo
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Tipo 1");
        spinnerArray.add("Tipo 2");
        spinnerArray.add("Gestacional");
        spinnerArray.add("Otras");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tipo.setAdapter(adapter);

        //Tratamiento
        List<String> spinnerArray2 =  new ArrayList<String>();
        spinnerArray2.add("Sin medicación");
        spinnerArray2.add("Oral");
        spinnerArray2.add("Insulina");
        spinnerArray2.add("Bomba");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tratamiento.setAdapter(adapter2);

        //Monitor continuo glucosa
        List<String> spinnerArray3 =  new ArrayList<String>();
        spinnerArray3.add("Si. FreeStyle");
        spinnerArray3.add("Si. Medtronic");
        spinnerArray3.add("Si. Dexcom");
        spinnerArray3.add("No");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, R.layout.spinner_item, spinnerArray3);

        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);
        monitor.setAdapter(adapter3);


        identificacion.setEnabled( false );

        //Date picker
        setDateTimeField();

        // Raza switch
        raza.setTextOn( "Yes" );
        raza.setTextOff( "No" );

        // ArgumentPaciente
        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        identificacion.setText( argumentPaciente.getId() );

        //System.out.println( "argumentPaciente" );
        //System.out.println( argumentPaciente.getTratamiento() );

        tipo.setSelection( spinnerArray.indexOf( argumentPaciente.getTipo() ) );
        tratamiento.setSelection( spinnerArray2.indexOf( argumentPaciente.getTratamiento() ) );
        monitor.setSelection( spinnerArray3.indexOf( argumentPaciente.getMonitorizacion()));

        hbac.setText( argumentPaciente.getHbac() );
        años.setText( argumentPaciente.getYearEnfermo() );

        ultimo.setText( argumentPaciente.getLastEyes() );

        creatinina.setText( argumentPaciente.getCreatinina() );
        microalbuminuria.setText( argumentPaciente.getMicroalbuminuria() );
        urea.setText( argumentPaciente.getUrea() );

        if ( argumentPaciente.getRaza().equals( "Yes" ) )
        {
            raza.setChecked( true );
        }
        else if ( argumentPaciente.getRaza().equals( "No" ) ){
            raza.setChecked( false );
        }

        // Buttons
        diabetesButton = (Button) findViewById( R.id.button_diabetes);

        // Buttons Listener
        diabetesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(hbac.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(años.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow( creatinina.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow( microalbuminuria.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow( raza.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow( urea.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                // Get race
                race = "No";

                if ( raza.isChecked() )
                {
                    race = "Yes";
                }

                try {

                    hbacValue = 0;

                    if ( hbac.getText().toString().length() > 0 )
                    {
                        hbacValue = Integer.parseInt(hbac.getText().toString());
                    }


                    if ( hbacValue > 7) {

                        hbac.setError("Hbac no puede ser mayor que 7");

                    } else {

                        // Save
                        new saveDiabetes().execute(argumentPaciente.getId(), tipo.getSelectedItem().toString(),
                                tratamiento.getSelectedItem().toString(),
                                hbac.getText().toString(), monitor.getSelectedItem().toString(),
                                años.getText().toString(), ultimo.getText().toString(),
                                creatinina.getText().toString(),
                                microalbuminuria.getText().toString(), race,
                                urea.getText().toString()
                        );
                    }

                }catch(NumberFormatException e)
                {

                }

            }
        });
    }

    //Listener for
    private void setDateTimeField() {
        ultimo.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ultimo.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void onClick(View view) {
        if(view == ultimo) {
            DatePickerDialog.show();
        }
    }

    // Save Colesterol
    public class saveDiabetes extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl("saveDiabetes.php");

            // Ver datos
            //System.out.println( "Parametros" );
            for ( int i = 0; i < 11; i++ )
            {
                System.out.println( params[i] );
            }

            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( DAOCardiovascular.getInstance().getReadTimeout() );
                conn.setConnectTimeout( DAOCardiovascular.getInstance().getConnectTimeout() );
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pacienteId", params[0])
                        .appendQueryParameter("tipo", params[1])
                        .appendQueryParameter("tratamiento", params[2])
                        .appendQueryParameter("hbac", params[3])
                        .appendQueryParameter("monitor", params[4])
                        .appendQueryParameter("years", params[5])
                        .appendQueryParameter("lastEyes", params[6])
                        .appendQueryParameter("creatinina", params[7])
                        .appendQueryParameter("microalbuminuria", params[8])
                        .appendQueryParameter("race", params[9])
                        .appendQueryParameter("urea", params[10])


                        ;
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return null;
                //return "exception";
            }

            // Final result
            String finalResult = "";

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    finalResult = result.toString();
                    //return(result.toString());

                } else {
                    return null;
                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
                // return "exception";
            } finally {
                conn.disconnect();
            }

            return finalResult;
        }

        // @Override
        protected void onPostExecute(String result) {
            //System.out.println(result);

            if (result.equalsIgnoreCase("error")) {

                // Clear the fields

            } else if (result.equalsIgnoreCase("Diabetes Data actualizado")) {


                Toast.makeText(getBaseContext(), "Diabetes Data actualizado", Toast.LENGTH_LONG).show();

                argumentPaciente.setTipo( tipo.getSelectedItem().toString() );
                argumentPaciente.setTratamiento( tratamiento.getSelectedItem().toString() );
                argumentPaciente.setHbac( hbac.getText().toString() );
                argumentPaciente.setMonitorizacion( monitor.getSelectedItem().toString() );
                argumentPaciente.setYearEnfermo( años.getText().toString() );
                argumentPaciente.setLastEyes( ultimo.getText().toString() );
                argumentPaciente.setCreatinina( creatinina.getText().toString() );
                argumentPaciente.setMicroalbuminuria( microalbuminuria.getText().toString() );
                argumentPaciente.setRaza( race );
                argumentPaciente.setUrea( urea.getText().toString() );

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );


            } else if (result.equalsIgnoreCase("Error al guardar Diabetes Data")) {

                Toast.makeText(getBaseContext(), "Error al guardar Diabetes Data", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

            }
        }
    }
}
