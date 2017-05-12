package ucm.fdi.tfg;

import android.Manifest;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static ucm.fdi.tfg.CreatePDF.verifyStoragePermissions;


public class PacienteValidarActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    // Text
    private TextView identif;
    private TextView eda;
    private TextView sexo;

    private EditText identificacion;
    private EditText edad;

    private RadioButton Masculino;
    private RadioButton Femenino;

    // Buttons
    private Button saveButton;
    private Button removeButton;
    private Button tratamientoButton;
    private Button cardiovascularButton;
    private Button PDFButton;

    // String
    private String identifier;
    private String sex;
    private String age;

    int ageValue;



    private Button Rechazar;
    private TextView Colegiado;
    private TextView Nombre;
    private TextView Apellidos;
    private TextView Mail;
    private TextView Telefono;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private Paciente argumentPaciente;




    // PDF variables
    private EditText identifierPdf;
    private EditText agePdf;
    private EditText genre;

    private EditText sistolica;
    private EditText diastolica;
    private EditText resultHta;

    private EditText height;
    private EditText weight;
    private EditText imc;

    private EditText diaryQuantity;
    private EditText yearsAdict;
    private EditText ipa;

    private EditText hdl;
    private EditText total;
    private EditText trigliceridos;
    private EditText ldl;

    // Second Page
    private EditText tipo;
    private EditText tratamiento;
    private EditText hbac;
    private EditText cgm;
    private EditText yearsSick;
    private EditText lastEyes;
    private EditText creatinina;
    private EditText microalbuminuria;
    private EditText urea;
    private EditText raza;

    // Fourth Page
    private TextView finalTratamientoField;


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MisPacientesActivity.class);
        startActivity(intent);

    }

    private void updateClasif( int sistolicaInt )
    {
        String clasif = "";

        if ( sistolicaInt < 130 )
        {
            clasif = "PRESIÓN ARTERIAL NORMAL";

        }
        else if ( sistolicaInt >= 130 && sistolicaInt <= 139 )
        {

            clasif = "PRESIÓN ARTERIAL ELEVADA NORMAL";

        }
        else if ( sistolicaInt >= 140 && sistolicaInt <= 159 )
        {
            clasif = "HIPERTENSIÓN (LEVE) FASE 1";



        }
        else if ( sistolicaInt >= 160 && sistolicaInt <= 179 )
        {

            clasif = "HIPERTENSIÓN (MODERADA) FASE 2";


        }
        else if ( sistolicaInt >= 180 && sistolicaInt <= 209  )
        {
            clasif = "HIPERTENSIÓN (GRAVE) FASE 3";


        }
        else if ( sistolicaInt >= 210  )
        {
            clasif = "HIPERTENSIÓN (MUY GRAVE) FASE 4";


        }


        resultHta.setText( clasif );
    }

    // View content;
    int contentWidth = 0;
    int contentHeight = 0;

    private void PdfGenerationAction(){

        verifyStoragePermissions( this );

        PdfDocument document = new PdfDocument();
        Paciente argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        // First Page ( Portada )
        // repaint the user's text into the page
        setContentView(R.layout.pdf_info_portada);
        View content = findViewById(R.id.pdfLayout3);

        content.setVisibility(View.GONE);
        // View content = this.getContentView();

        // here the solution
        int left = 0;
        int top = 0;
        int widthContent = 1200;
        int heightContent = 1800;

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        // crate a page description
        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        // create a new page from the PageInfo
        PdfDocument.Page page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        //content.draw(page.getCanvas() );
        //page.getCanvas().drawText( "lkgdsdfgsfd", 300, 300, new Paint() );

        // do final processing of the page
        document.finishPage(page);

        // Second Page Creation
        setContentView(R.layout.pdf_info);
        content = findViewById(R.id.pdfLayout);

        content.setVisibility(View.GONE);

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        // Set Content Data
        identifierPdf = (EditText) findViewById(R.id.id);
        agePdf = (EditText) findViewById(R.id.edadData );
        genre = (EditText) findViewById( R.id.generoData );

        sistolica = (EditText) findViewById(R.id.sistolicaData );
        diastolica = (EditText) findViewById(R.id.diastolicaData);
        resultHta = ( EditText ) findViewById( R.id.resultHtaData );

        height = (EditText) findViewById(R.id.alturaData );
        weight = (EditText) findViewById(R.id.pesoData );
        imc = (EditText) findViewById(R.id.resultImcData );

        diaryQuantity = (EditText) findViewById(R.id.cantidadData  );
        yearsAdict = (EditText) findViewById(R.id.añosData );
        ipa = (EditText) findViewById(R.id.resultTabaquismoData );

        hdl = (EditText) findViewById(R.id.hdlData );
        total = (EditText) findViewById(R.id.totalData);
        trigliceridos = (EditText) findViewById(R.id.trigliceridosData);
        ldl = (EditText) findViewById(R.id.ldlData);

        identifierPdf.setText( argumentPaciente.getId() );
        agePdf.setText( argumentPaciente.getEdad() );

        if ( argumentPaciente.getSexo().equals( "M" ) )  {
            genre.setText( "Masculino" );

        }else  {
            genre.setText( "Femenino" );
        }

        sistolica.setText( argumentPaciente.getSistolica() );
        diastolica.setText( argumentPaciente.getDiastolica() );

        try {

            int sistolicaValue = Integer.parseInt( sistolica.getText().toString() );

            updateClasif( sistolicaValue );

        }  catch(NumberFormatException e)  {

        }

        height.setText( argumentPaciente.getHeight() );
        weight.setText( argumentPaciente.getWeight() );
        imc.setText( argumentPaciente.getImc() );

        diaryQuantity.setText( argumentPaciente.getCantidad() );
        yearsAdict.setText( argumentPaciente.getAdiccion() );
        ipa.setText( argumentPaciente.getIpa() );

        hdl.setText( argumentPaciente.getHdl() );
        total.setText( argumentPaciente.getColesterolTotal() );
        trigliceridos.setText( argumentPaciente.getTrigliceridos() );
        ldl.setText( argumentPaciente.getLdl() );

        System.out.println( "signal" );
        System.out.println( content );
        System.out.println( content.getWidth() );
        System.out.println( content.getHeight() );
        System.out.println( "end signal" );

        // crate a page description
        pageNumber = 2;
        pageInfo = new PdfDocument.PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        // create a new page from the PageInfo
        page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        //content.draw(page.getCanvas() );
        //page.getCanvas().drawText( "lkgdsdfgsfd", 300, 300, new Paint() );

        // do final processing of the page
        document.finishPage(page);

        // Third Page Creation
        setContentView(R.layout.pdf_info_2);
        content = findViewById(R.id.pdfLayout2);

        content.setVisibility(View.GONE);

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        // Set Content Data
        tipo = ( EditText ) findViewById( R.id.spinner_tipo );
        tratamiento = ( EditText ) findViewById( R.id.spinner2_tratamiento );
        hbac = ( EditText ) findViewById( R.id.resultado_hbac );
        cgm = ( EditText ) findViewById( R.id.spinner3_glucosa );
        yearsSick = ( EditText ) findViewById( R.id.años_enfermedad );
        lastEyes = ( EditText ) findViewById( R.id.ultimo );

        creatinina = ( EditText ) findViewById( R.id.creatinina );
        microalbuminuria = ( EditText ) findViewById( R.id.microalbuminuria );
        urea = ( EditText ) findViewById( R.id.urea );
        raza = ( EditText ) findViewById( R.id.raza );

        tipo.setText( argumentPaciente.getTipo() );
        tratamiento.setText( argumentPaciente.getTratamiento() );
        hbac.setText( argumentPaciente.getHbac( ) );
        cgm.setText( argumentPaciente.getMonitorizacion() );
        yearsSick.setText( argumentPaciente.getYearEnfermo() );
        lastEyes.setText( argumentPaciente.getLastEyes() );
        creatinina.setText( argumentPaciente.getCreatinina() );
        microalbuminuria.setText( argumentPaciente.getMicroalbuminuria() );
        urea.setText( argumentPaciente.getUrea() );
        raza.setText( argumentPaciente.getRaza() );

        // Third Page Info
        pageNumber = 3;
        pageInfo = new PdfDocument.PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        // do final processing of the page
        document.finishPage(page);// Fourth Page Creation
        setContentView(R.layout.pdf_info_3);
        content = findViewById(R.id.pdfLayout4);

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        TextView tratamientoInicial = ( TextView ) findViewById( R.id.textView2 );
        String infoTratamiento = DAOCardiovascular.getInstance().infoTratamientoCardiovascular();
        tratamientoInicial.setText( infoTratamiento );

        finalTratamientoField = (  TextView ) findViewById( R.id.textView3 );
        System.out.println( "tratamiento final argumentPaciente" );
        System.out.println( argumentPaciente );
        finalTratamientoField.setText( argumentPaciente.getFinalTratamiento() );

        System.out.println( argumentPaciente.getFinalTratamiento() );
        System.out.println( finalTratamientoField.getText().toString() );

        System.out.println( "END----------tratamiento final argumentPaciente" );

        // Button
        //Button saveTratamientoButton = ( Button ) findViewById( R.id.button_guardar );
        //saveTratamientoButton.setVisibility(View.GONE);

        // Fourth Page Info
        pageNumber = 4;
        pageInfo = new PdfDocument.PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        // do final processing of the page
        document.finishPage(page);




        // Return to the original view
        initializeData();

        // Save
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        String pdfName = argumentPaciente.getId() + "_"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        File outputFile = new File("/sdcard/PDFDemo_AndroidSRC/", pdfName);

        try {
            outputFile.createNewFile();
            OutputStream out = new FileOutputStream(outputFile);
            document.writeTo(out);
            document.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ( outputFile.getPath() != null) {

            Toast.makeText(getApplicationContext(),
                    "PDF guardado en " + outputFile.getPath(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Error al crear PDF" + outputFile.getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNumber( String string )
    {

        if ( string == null || string.isEmpty() )
        {
            return false;

        }

        int i = 0;
        if ( string.charAt( 0 ) == '-')
        {
            if ( string.length() > 1 )
            {
                i++;
            }else{
                return false;
            }
        }


        for ( ; i < string.length(); i++ )
        {
            if ( !Character.isDigit( string.charAt( i ) ) )
            {
                return false;
            }
        }

        return true;
    }

    private boolean dayMonthValid( int day, int month )
    {

        boolean valid = true;
        int limit = 0;

        if ( month < 1 || month > 12 )
        {
            valid = false;
        }else {

            if ( month == 2 )
            {
                limit = 28;

            }else if ( month == 11 || month == 4 || month == 6 || month == 9 )
            {
                limit = 30;
            }else{

                limit = 31;
            }

            if ( day < 1 || day > limit )
            {
                valid = false;
            }


        }

        return valid;
    }

    private boolean validatePatient( String id, String age, String sex )
    {

        boolean valid = true;


        /*if ( age.isEmpty() || Integer.parseInt( age ) < 0 ){

            identificacion.setError( "Valor entre 0 y 99" );
            valid = false;
        }*/

        if ( id.isEmpty() || id.length() != 9 || isNumber( id.substring( 0, 3 ) ) == true || isNumber( id.substring( 3, 9 ) ) == false )
        {
            identificacion.setError( "id debe ser 3 letras iniciales y ddmmaa" );
            valid = false;



            // Check birthday
        }else
        {

            // Get day
            int day = Integer.parseInt( id.substring( 3, 5 ) );

            // Get Month
            int month = Integer.parseInt( id.substring( 5, 7 ) );

            // Get year
            int year = Integer.parseInt( id.substring( 7, 9 ) );

            // Check day and month
            if ( !dayMonthValid( day, month ) )
            {
                identificacion.setError( "el día y el mes no son válidos" );
                valid = false;
            }else
            {
                Calendar c1 = Calendar.getInstance();

                // Get info current date Happy Birthday!!!
                int dia = c1.get( Calendar.DATE );
                int mes = c1.get( Calendar.MONTH ) + 1;
                int annio = c1.get( Calendar.YEAR );

                // Transform annio
                annio = annio % 100;

                System.out.println( "date phoenix" );
                System.out.println( dia );
                System.out.println( mes );
                System.out.println( annio );

                System.out.println( "birth phoenix" );
                System.out.println( day );
                System.out.println( month );
                System.out.println( year );

               // 4 abril 2017
                // 30 marzo 2017

                // Get year
                if ( annio < year )
                {
                    annio = annio + 100;
                }

                ageValue = annio - year;

                if ( mes < month ){

                    ageValue = ageValue -1;
                }
                else if ( dia < day && mes == month )
                {
                    ageValue = ageValue -1;
                }

                if ( ageValue < 0 )
                {
                    ageValue = ageValue + 100;
                }

                edad.setText( Integer.toString( ageValue ) );






            }

        }

        return valid;
    }

    private boolean checkData()
    {

        // Get data
        identifier = identificacion.getText().toString();
        age = edad.getText().toString();

        sex = "N";

        if ( Masculino.isChecked() )
        {

            sex = "M";

        }else
        {

            sex = "F";

        }

        if (!validatePatient( identifier, age, sex )) {
            //onLoginFailed();
            return false;
        }else
        {
            age = edad.getText().toString();
            return true;
        }


    }

    protected void initializeData()
    {

        setContentView(R.layout.activity_paciente_info);


        // Text
        identif = (TextView) findViewById(R.id.identificacion);
        eda = (TextView) findViewById(R.id.weight);
        sexo = (TextView) findViewById(R.id.height);

        identificacion = (EditText) findViewById(R.id.id);
        edad = (EditText) findViewById(R.id.diastolicaData);

        Masculino = (RadioButton) findViewById(R.id.RB_genero_hombre);
        Femenino = (RadioButton) findViewById(R.id.RB_genero_mujer);

        // Buttons
        saveButton = (Button) findViewById( R.id.button_guardar );
        removeButton = ( Button ) findViewById( R.id.button_eliminar );

        tratamientoButton = ( Button ) findViewById( R.id.button_tratamiento );
        cardiovascularButton = ( Button ) findViewById ( R.id.button_calcular );

        PDFButton = ( Button ) findViewById( R.id.pdf );

        // Get in data
        //Bundle bundle = getIntent().getExtras();
        System.out.println( "freeze phoenix" );
        //System.out.println( ( Paciente ) getIntent().getSerializableExtra( "paciente" ) );

        //argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        System.out.println( "phoenix of hunger");
        System.out.println( identificacion );
        System.out.println( argumentPaciente.getId() );
        System.out.println( edad );
        System.out.println( argumentPaciente.getEdad() );


        // Set info data
        identificacion.setText( argumentPaciente.getId() );
        edad.setText( argumentPaciente.getEdad() );
        edad.setEnabled(false);

        String sexValue =  argumentPaciente.getSexo();


        if ( sexValue.equals ( "M" ) )
        {
            Masculino.setChecked( true );

        }else
        {
            Femenino.setChecked( true );
        }




        // Colegiado.setText( bundle.getString( "colegiado" ) );

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( checkData() )
                {
                    new updatePatient().execute( argumentPaciente.getId(), identifier, sex,
                            age, DAOCardiovascular.getInstance().getLoggedUser().getColegiado(), "false");
                }

                //logIn(v);


               /* System.out.println( "gaia phoenix" );
                System.out.println( argumentPaciente );

                System.out.println( "sedna phoenix" );*/

            }
        });

        PDFButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //new CreatePDF().generatePdf();
                //Toast.makeText(getBaseContext(), "PDF Descargado", Toast.LENGTH_LONG).show();

                PdfGenerationAction();

                /*Intent intent = new Intent(getApplicationContext(), CreatePDF.class);
                startActivity(intent);*/
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( checkData() )
                {
                    new updatePatient().execute( argumentPaciente.getId(), identifier, sex,
                            age, DAOCardiovascular.getInstance().getLoggedUser().getColegiado(), "true");
                }

            }
        });


        cardiovascularButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the App activity
                Intent intent = new Intent(getApplicationContext(), CardiovascularActivity.class);
                intent.putExtra( "paciente", argumentPaciente);
                startActivity(intent);
            }
        });

        tratamientoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the App activity
                Intent intent = new Intent(getApplicationContext(), tratamientoActivity.class);
                intent.putExtra( "paciente", argumentPaciente);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        initializeData();

        new getTratamiento().execute( argumentPaciente.getId() );



    }






















    private class getTratamiento extends AsyncTask<String,Void,String> {

        // ProgressDialog pdLoading = new ProgressDialog(SignupActivity.this);
        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl("getTratamiento.php");


            try {

                System.out.println("phoenix white");

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(DAOCardiovascular.getInstance().getReadTimeout());
                conn.setConnectTimeout(DAOCardiovascular.getInstance().getConnectTimeout());
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                System.out.println("phoenix grey");

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pacienteId", params[0])
                        ;
                String query = builder.build().getEncodedQuery();


                System.out.println("phoenix orange");
                for (int i = 0; i < params.length; i++) {
                    System.out.println(params[i]);
                }
                System.out.println("phoenix apple");


                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                System.out.println("phoenix yellow");
                conn.connect();

                System.out.println("phoenix stone");

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                //return "exception";
            }

            // Final result
            String finalResult = "";

            // Tercer try
            try {

                int response_code = conn.getResponseCode();

                System.out.println("phoenix diablo");

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    System.out.println("phoenix gaia");

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    System.out.println("phoenix altmile");

                    finalResult = result.toString();

                    // Pass data to onPostExecute method
                    //return(result.toString());

                } else {

                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
                //return "exception";
                //return "exception";
            } finally {

                System.out.println("phoenix tear");
                conn.disconnect();
            }

            return finalResult;

            // return null;//DAOCuentaCuentas.getInstance().login(strings[0],strings[1]);
        }

        //@Override
        //protected void onPostExecute(Medico usu1) {
        protected void onPostExecute(String result) {

            // Remove loading
            //pdLoading.dismiss();

            System.out.println("phoenix dungeon");
            System.out.println(result);

            // Set tratamiento on the data
            argumentPaciente.setFinalTratamiento( result );
            DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );



           /* if (result.equalsIgnoreCase("Paciente no encontrado")) {

                Toast.makeText(getBaseContext(), "Paciente no encontrado", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getBaseContext(), "Error al guardar el tratamiento", Toast.LENGTH_LONG).show();


            }*/
        }

    }



    private class updatePatient extends AsyncTask<String,Void,String> {

       // ProgressDialog pdLoading = new ProgressDialog(SignupActivity.this);
        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
           /* pdLoading.setMessage("\tProcessing Sign Up...");
            pdLoading.setCancelable(false);
            pdLoading.show();*/

        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl( "updatePatient.php" );


            try {

                System.out.println("phoenix white");
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                System.out.println("phoenix grey");

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("oldIdentifier", params[0])
                        .appendQueryParameter("identifier", params[1])
                        .appendQueryParameter("sexo", params[2])
                        .appendQueryParameter("edad", params[3])
                        .appendQueryParameter("colegiado", params[4])
                        .appendQueryParameter("refuse", params[5])
                        ;
                String query = builder.build().getEncodedQuery();


                System.out.println("phoenix orange");
                for ( int i = 0; i < params.length; i++ )
                {
                    System.out.println( params[i]);
                }
                System.out.println("phoenix apple");


                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                System.out.println("phoenix yellow");
                conn.connect();

                System.out.println("phoenix stone");

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                //return "exception";
            }

            // Final result
            String finalResult = "";

            // Tercer try
            try {

                int response_code = conn.getResponseCode();

                System.out.println("phoenix diablo");

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    System.out.println("phoenix gaia");

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    System.out.println("phoenix altmile");

                    finalResult = result.toString();

                    // Pass data to onPostExecute method
                    //return(result.toString());

                } else {

                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
                //return "exception";
                //return "exception";
            } finally {

                System.out.println("phoenix tear");
                conn.disconnect();
            }

            return finalResult;

            // return null;//DAOCuentaCuentas.getInstance().login(strings[0],strings[1]);
        }

        //@Override
        //protected void onPostExecute(Medico usu1) {
        protected void onPostExecute(String result) {

            // Remove loading
            //pdLoading.dismiss();

            System.out.println( "phoenix dungeon" );
            System.out.println( result );

            if (result.equalsIgnoreCase("error")) {

                // Clear the fields
               /* name_text.getText().clear();
                surname_text.getText().clear();
                number_text.getText().clear();
                telephone_text.getText().clear();
                mail_text.getText().clear();
                password_text.getText().clear();*/

                // On Sign Up Failed
                Toast.makeText(getBaseContext(), "Error en el proceso", Toast.LENGTH_LONG).show();
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase( "exception" )) {


            }else if (  result.equalsIgnoreCase("Paciente ya asignado a un medico")    ){


                Toast.makeText(getBaseContext(), "Paciente ya asignado a un médico", Toast.LENGTH_LONG).show();



            }else if (  result.equalsIgnoreCase("Error actualizando")    ){


                Toast.makeText(getBaseContext(), "Error actualizando", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Actualizado con exito")) {

                argumentPaciente.setId( identificacion.getText().toString () );

                if ( Masculino.isChecked() )
                {
                    argumentPaciente.setSexo( "M" );
                }else
                {
                    argumentPaciente.setSexo( "F" );
                }

                argumentPaciente.setEdad( edad.getText().toString() );

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );

                Toast.makeText(getBaseContext(), "Actualizado con exito", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Eliminado con exito")) {

                Toast.makeText(getBaseContext(), "Eliminado con exito", Toast.LENGTH_LONG).show();


                Intent intent = new Intent( getApplicationContext(), MisPacientesActivity.class);
                // intent.putExtra("colegiado", currentLead.getColegiado());
                startActivity(intent);
                //identificacion.getText().clear();
               // edad.getText().clear();
                //Masculino.setChecked( true );
                //Femenino.setChecked( false );

                //recreate();

                // Prepare next window
                //Intent intent = new Intent(MedicoValidarActivity.this, ValidateActivity.class);
                //startActivity(intent);


                //intent.setFlags( FLAG_ACTIVITY_CLEAR_TASK );
                // finish();
                /*EditText editText = (EditText) findViewById(R.id.edit_message_Name);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);*/
                //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK );
                //intent.setAction("com.package.ACTION_LOGOUT");



                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }
        }


        // if (usu1==null) {
                /*if(primero){
                    //inicializarVentana();
                }else {*/
      /*          Toast toast =
                        Toast.makeText(getApplicationContext(),
                                "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
                toast.show();
                //}
            } else {
                Medico medico;
                medico = Medico.getIntsance();
                medico.setId(usu1.getId());
                medico.setColegiado(usu1.getColegiado());
                medico.setNombre(usu1.getNombre());
                medico.setApellidos(usu1.getApellidos());
                medico.setTelefono(usu1.getTelefono());
                medico.setPassword(usu1.getPassword());

                //usu.setImagen(usu1.getImagen());

                //IniciarAplicacion(usu);
            }
*/
    }
}
