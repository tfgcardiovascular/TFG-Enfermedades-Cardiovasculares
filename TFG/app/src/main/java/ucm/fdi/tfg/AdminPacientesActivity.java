package ucm.fdi.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


//import java.util.ArrayList;

public class AdminPacientesActivity extends AppCompatActivity{
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "MisPacientesActivity";
    private ListView mListView;
    private Button Validar_Button;
    private Button Rechazar_Button;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), adminActivity.class);
        startActivity(intent);

    }

    protected void onResume(){

        super.onResume();

        System.out.println( "computer phoenix" );
        System.out.println( "pegasus phoenix" );


    }

    protected void onCreate(Bundle savedInstanceState){


        System.out.println( "1" );
        // Silly create things
        super.onCreate(savedInstanceState);

        //Crear lista
        setContentView(R.layout.activity_lista_paciente);

        System.out.println( "2" );
        // Get the medics from the database
        //getMedicsValidateData();

        //new AsyncMedicValidate().execute( null, null );

        // Get the medics to validate
        //ArrayList < Medico > medicValidate = AsyncMedicValidate

        // Toolbar
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        //FragmentPaciente leadsFragment = (FragmentPaciente)
                //getSupportFragmentManager().findFragmentById(R.id.category_container);

        FragmentPaciente leadsFragment = (FragmentPaciente)
                getSupportFragmentManager().findFragmentById(R.id.leads_container);


        if (leadsFragment == null) {
            leadsFragment = FragmentPaciente.newInstance();
            //leadsFragment.onCreateView( R.id.category_container, android.R.layout.simple_list_item_1, false );
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.leads_container, leadsFragment)
                    .commit();
        }









       // DAOCardiovascular.getInstance().prueba();

       // DAOCardiovascular dao = DAOCardiovascular.getInstance();

       // new AsyncMedicValidate().execute( null, null );




        //Inicializar lista
        /*ArrayList<Category> category = new ArrayList<Category>();

        category.add ( new Category ( "1", "Ricardo Cajigas", "555555555", "ricardo@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        category.add ( new Category ( "2", "Angel Sanchez", "666666666", "angel@gmail.com") );
        // Inicializar listView y llenarlo con los elementos del ArrayList

        ListView lista;
        //ArrayAdapter<String> adaptador;

        lista = (ListView)findViewById(R.id.ListViewValidate);

        //adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        AdapterCategory adapter = new AdapterCategory(this, category);

        lista.setAdapter(adapter);

        */
/*
        mListView = (ListView)findViewById(R.id.ListViewValidate);
        Validar_Button = (Button) findViewById(R.id.button_validar);
        Validar_Button.setAlpha(.5f);
        Validar_Button.setEnabled(false);
        Rechazar_Button = (Button) findViewById(R.id.button_rechazar);
        Rechazar_Button.setAlpha(.5f);
        Rechazar_Button.setEnabled(false);
*/

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                for (int i = 0; i < mListView.getChildCount(); i++) {
                    if(position == i ){
                        mListView.getChildAt(i).setBackgroundColor(Color.LTGRAY);
                        //Validar_Button.setAlpha(1f);
                        Validar_Button.setClickable(true);
                        //Rechazar_Button.setAlpha(1f);
                        Rechazar_Button.setClickable(true);
                    }else{
                        mListView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }

            }
        });*/
    }



    private void getMedicsValidateData()
    {

        // Initialize
        // New Medico Object
        Medico medic = new Medico();
        System.out.println( "3" );
        ArrayList< String > linePhp = new ArrayList <  > ();

        ArrayList < Medico > medicPhp = new ArrayList<>();

        HttpURLConnection conn;
        URL url = null;

        url = DAOCardiovascular.getInstance().getUrl( "getMedicValidate.php" );


        System.out.println( "3/5" );
        /*try {


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            //return null;
            //return "exception";
        }*/

        try {
            System.out.println( "4" );
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection)url.openConnection();
            System.out.println(conn);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);
            System.out.println( "5" );
            // Append parameters to URL
                /*Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();*/

            System.out.println( "5/5" );
            conn.connect();
            System.out.println( "6" );

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));



                System.out.println( "phoenix lion" );
                System.out.println( reader );
                System.out.println( "phoenix falcon" );
                StringBuilder result = new StringBuilder();
                String line;



                while ((line = reader.readLine()) != null) {
                    System.out.println( "phoenix fox" );
                    System.out.println( line  );
                    System.out.println( "phoenix wolf" );

                    linePhp.add( line );

                    if ( linePhp.size() == 6 )
                    {

                        // Create medic Object
                        medic = new Medico();

                        // Update Medic Object
                        medic.setNombre(linePhp.get(0));
                        medic.setApellidos(linePhp.get(1));
                        medic.setTelefono(linePhp.get(2));
                        medic.setPassword(linePhp.get(3));
                        medic.setColegiado(linePhp.get(4));
                        medic.setMail(linePhp.get(5));

                        // Update Medic ArrayList
                        medicPhp.add( medic );

                        // Reset linePhp
                        linePhp.clear();



                    }


                    //result.append(line);
                }

                System.out.println( "simorgh fire" );
                System.out.println( medicPhp );

                conn.disconnect();

                // Pass data to onPostExecute method
                //return medicPhp;
                //return(result.toString());

            }else{
                //return null;
                //return("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
           // return null;
            // return "exception";
        } finally {

        }




        // Save the obtained data
        //this method will be running on UI thread
        System.out.println( "Phoenix blue wave");
        System.out.println( medicPhp );
        System.out.println( "Phoenix alter ego");


        //pdLoading.dismiss();

        if ( medicPhp != null ) {

            // Let LeadsRepository know the result
            LeadsRepository.getInstance().saveMedicList( medicPhp );

            System.out.println( "phoenix de hielo y storm" );
            System.out.println( LeadsRepository.getInstance().getCategorys() );





            //if(result.equalsIgnoreCase("true")){
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

            // Clear the fields
                /*user_text.getText().clear();
                password_text.getText().clear();

                // Restaurar cursor
                user_text.requestFocus();*/

            // Change frame
               /* Class<? extends Activity> activityClass;
                activityClass = InicioActivity.class;

                // Medico -> Menu Inicio
                String i = result.getRol();
                System.out.println("Llega aqui");
                System.out.println(i);
                if (i.equals( "1") ) {
                    activityClass = InicioActivity.class;

                    // Admin -> Menú admin
                } else if (i.equals( "0")){
                    activityClass = ValidateActivity.class;

                }*/

            // Start new frame
               /* Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);*/

            //EditText editText = (EditText) findViewById(R.id.edit_message_User);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);

               /* Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                startActivity(intent);
                MainActivity.this.finish();*/

        }else{
            //if (result.equalsIgnoreCase("false")){
            //onLoginFailed();
            // If username and password does not match display a error message
            // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

        }/* else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }*/


    }








































}

