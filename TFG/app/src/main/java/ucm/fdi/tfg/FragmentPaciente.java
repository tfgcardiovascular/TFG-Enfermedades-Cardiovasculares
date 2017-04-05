package ucm.fdi.tfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class FragmentPaciente extends Fragment {

    private ListView mLeadsList;
    private AdapterPaciente mLeadsAdapter;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private String m_Text = "";

    private String identifier ="";
    private String genre = "";
    private String edad = "";



    public FragmentPaciente() {
        // Required empty public constructor
    }

    public static FragmentPaciente newInstance(/*parámetros*/) {
        FragmentPaciente fragment = new FragmentPaciente();
        // Setup parámetros
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public void onResume()
    {


        super.onResume();


        System.out.println( "onResume" );

        System.out.println( "umbrah phoenix" );
        System.out.println( "fulgor phoenix" );

        // Get medic
        String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();
        String rol = DAOCardiovascular.getInstance().getLoggedUser().getRol();

        if ( rol.equals( "0" ) )
        {
            colegiado = "admin";
        }

        new AsyncPacienteValidate().execute( colegiado );
        System.out.println( "hope phoenix" );

        update();
        System.out.println( "despair phoenix" );



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_fragment_paciente, container, false);

        System.out.println( "prueba onCreateView" );
        System.out.println( inflater );
        System.out.println( container );
        System.out.println( savedInstanceState );
        System.out.println( R.id.leads_list );
        System.out.println( root );

        // Instancia del ListView.
        mLeadsList = (ListView) root.findViewById(R.id.leads_list);

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.search);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getActivity().getBaseContext(), "Busqueda", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Buscar Paciente");
                builder.setIcon(R.mipmap.search_icon);

                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.activity_search_patient, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);

                final EditText input2 = (EditText) viewInflated.findViewById(R.id.input2);

                final RadioButton Masculino = (RadioButton) viewInflated.findViewById(R.id.RB_genero_hombre);
                final RadioButton Femenino = (RadioButton) viewInflated.findViewById(R.id.RB_genero_mujer);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Dismiss Dialog
                        dialog.dismiss();

                        // Get fields
                        identifier = input.getText().toString();
                        edad = input2.getText().toString();

                        genre = "N";

                        if ( Masculino.isChecked() )
                        {

                            genre = "M";

                        }else if ( Femenino.isChecked() )
                        {
                            genre = "F";
                        }

                        // Get medic
                        String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();
                        String rol = DAOCardiovascular.getInstance().getLoggedUser().getRol();

                        if ( rol.equals( "0" ) )
                        {
                            colegiado = "admin";
                        }

                        // Get result
                        new AsyncPacienteSearch().execute( colegiado, identifier, edad, genre );

                        // Share Patient

                        //new AsyncPacienteShare().execute( currentLead.getId(), input.getText().toString() );

                        //m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });


        mLeadsAdapter = new AdapterPaciente(getActivity(),
                PacienteRepository.getInstance().getCategorys());

        System.out.println( "Black clock" );
        System.out.println( getActivity() );
        System.out.println( PacienteRepository.getInstance().getCategorys() );

        System.out.println( "system panther jungle");
        System.out.println( mLeadsAdapter );
        System.out.println( mLeadsList );

        //Relacionando la lista con el adaptador
        mLeadsList.setAdapter(mLeadsAdapter);

        System.out.println( "wolf of the dark moon" );
        // Update with the database
        // Get medic
        /*String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();
        String rol = DAOCardiovascular.getInstance().getLoggedUser().getRol();

        if ( rol.equals( "0" ) )
        {
            colegiado = "admin";
        }

        //new AsyncPacienteValidate().execute( colegiado );
        new AsyncPacienteValidate().execute( null, null );

        update();*/


        /*System.out.println( "Black clock" );
        System.out.println( getActivity() );
        System.out.println( category );


        // Inicializar el adaptador con la fuente de datos.
        mLeadsAdapter = new AdapterPaciente( getActivity(), category);

        System.out.println( "system panther jungle");
        System.out.println( mLeadsAdapter );
        System.out.println( mLeadsList );

        //Relacionando la lista con el adaptador
        mLeadsList.setAdapter(mLeadsAdapter);*/

        System.out.println( "wolf of the light moon" );

        // Long Pressed
        mLeadsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                /*Paciente currentLead = mLeadsAdapter.getItem(position);
                Toast.makeText(getActivity(),
                        "Vista detalle para: " + currentLead.getId(),
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getActivity(), PacienteValidarActivity.class);
                intent.putExtra( "paciente", currentLead);
                // intent.putExtra("colegiado", currentLead.getColegiado());
                startActivity(intent);*/

                final Paciente currentLead = mLeadsAdapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Compartir Paciente");
                builder.setIcon(R.mipmap.share_icon);

                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.activity_share_patient, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Dismiss Dialog
                        dialog.dismiss();

                        // Share Patient

                        new AsyncPacienteShare().execute( currentLead.getId(), input.getText().toString() );




                        //m_Text = input.getText().toString();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }

        });


        // Eventos
        mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Paciente currentLead = mLeadsAdapter.getItem(position);
                Toast.makeText(getActivity(),
                        "Vista detalle para: " + currentLead.getId(),
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getActivity(), PacienteValidarActivity.class);
                intent.putExtra( "paciente", currentLead);
                // intent.putExtra("colegiado", currentLead.getColegiado());
                startActivity(intent);
            }
        });



        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_menu_category_list, menu);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_menu_context, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.share:
                // Tareas a realizar
                return true;
            //case R.id.delete:
                // Tareas a realizar
                //return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            System.out.println( "devil phoenix" );
            // Eliminar todos los leads
            mLeadsAdapter.clear();
           /* ArrayList<Paciente> result = new ArrayList<>();
            Paciente medic = new Paciente( "35346", "345436346", "s", "35", "pegaso", "fgdsdfdsf",  "sdgasdgasad@gmail", "1" );
            result.add( medic );


            // Let PacienteRepository know the result
            System.out.println( "phoenix artic ");
            System.out.println( PacienteRepository.getInstance().getCategorys() );
           PacienteRepository.getInstance().saveMedicList( result );
            System.out.println( PacienteRepository.getInstance().getCategorys() );
            System.out.println( "phoenix antartic" );

           // medic = new Paciente( "35346", "345436346", "s", "35", "fire phoenix", "fgdsdfdsf",  "sdgasdgasad@gmail", "1" );
           // PacienteRepository.getInstance().saveCategory( medic );

            System.out.println( "god phoenix" );

            update();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void update()
    {
       // mLeadsAdapter.clear();
        mLeadsAdapter.notifyDataSetChanged();
    }




































    // Get all the medics to validate on the database
    private class AsyncPacienteValidate extends AsyncTask<   String, Void,  ArrayList<Paciente> > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Paciente> doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "getListPacientes.php" );


            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("colegiado", params[0])
                        ;
                String query = builder.build().getEncodedQuery();

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

            try {

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

                    // New Paciente Object
                    Paciente medic = new Paciente();

                    ArrayList< String > linePhp = new ArrayList <  > ();

                    ArrayList < Paciente > medicPhp = new ArrayList<>();

                    while ((line = reader.readLine()) != null) {
                        System.out.println( "phoenix fox" );
                        System.out.println( line  );
                        System.out.println( "phoenix wolf" );

                        linePhp.add( line );

                        if ( linePhp.size() == 3 )
                        {

                            // Create medic Object
                            medic = new Paciente();

                            // Update Medic Object
                            medic.setId(linePhp.get(0));
                            medic.setSexo(linePhp.get(1));
                            medic.setEdad(linePhp.get(2));

                            // Update Medic ArrayList
                            medicPhp.add( medic );

                            // Reset linePhp
                            linePhp.clear();



                        }


                        //result.append(line);
                    }

                    System.out.println( "simorgh fire" );
                    System.out.println( medicPhp );

                    // Pass data to onPostExecute method
                    return medicPhp;
                    //return(result.toString());

                }else{
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
        }



        // @Override
        protected void onPostExecute(ArrayList<Paciente> result) {

            // Check obtained result

            //this method will be running on UI thread
            System.out.println( "Phoenix blue wave");
            System.out.println( result );
            System.out.println( "Phoenix alter ego");


            //pdLoading.dismiss();

            if ( result.size() > 0 ) {

                // Let PacienteRepository know the result
                PacienteRepository.getInstance().saveMedicList( result );

                System.out.println( "phoenix de hielo y storm" );
                System.out.println( PacienteRepository.getInstance().getCategorys() );
                update();


                // Class<? extends Activity> activityClass;
                // activityClass = ValidateActivity.class;

                //  Intent intent = new Intent(DAOCardiovascular.this, activityClass);
                // startActivity(intent);





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

                // Paciente -> Menu Inicio
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




    // Get all the medics to validate on the database
    private class AsyncPacienteShare extends AsyncTask<   String, Void,  String > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "sharePatient.php" );


            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("paciente", params[0])
                        .appendQueryParameter("colegiado", params[1])
                        ;
                String query = builder.build().getEncodedQuery();

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
        }



        // @Override
        protected void onPostExecute( String result) {

            // Check obtained result

            //this method will be running on UI thread
            System.out.println( "Phoenix blue wave");
            System.out.println( result );
            System.out.println( "Phoenix alter ego");


            if (result.equalsIgnoreCase("error")) {

                // Clear the fields
               /* name_text.getText().clear();
                surname_text.getText().clear();
                number_text.getText().clear();
                telephone_text.getText().clear();
                mail_text.getText().clear();
                password_text.getText().clear();*/

                // On Sign Up Failed
                Toast.makeText( getActivity().getBaseContext(), "Error en el proceso", Toast.LENGTH_LONG).show();

                //SignUp_Button.setEnabled(true);

            }else if (  result.equalsIgnoreCase("No existe el medico")    ){


                Toast.makeText( getActivity().getBaseContext(), "No existe el médico", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("Paciente ya asignado al medico")    ){


                Toast.makeText( getActivity().getBaseContext(), "Paciente ya asignado al médico", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("Error al compartir paciente")    ){


                Toast.makeText( getActivity().getBaseContext(), "Error al compartir paciente", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Compartido con exito")) {

                Toast.makeText( getActivity().getBaseContext(), "Compartido con éxito", Toast.LENGTH_LONG).show();

            }
        }
    }




    /*

    public Paciente login(String usun, String cont){
        Paciente usu = null;
        try {

            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);

            //conn.setConnectTimeout(50);

            String peticion = "select * from Paciente WHERE nombre= ? AND password = ?";
            PreparedStatement consulta = conn.prepareStatement(peticion);
            System.out.println("Conexion establecida");
            consulta.setString(1,usun);
            consulta.setString(2,cont);
            ResultSet result = consulta.executeQuery();

            if (result != null) {
                if (!result.next()) {
                    return null;

                } else {
                    usu= new Paciente();
                    usu.setId(result.getString("id"));
                    usu.setColegiado(result.getString("colegiado"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    usu.setPassword(result.getString("password"));

                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){
                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }

                    return usu;
                }

            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }*/















    // Get all the medics to validate on the database
    private class AsyncPacienteSearch extends AsyncTask<   String, Void,  ArrayList<Paciente> > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Paciente> doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "searchPatient.php" );


            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("colegiado", params[0])
                        .appendQueryParameter("pacienteId", params[1])
                        .appendQueryParameter("age", params[2])
                        .appendQueryParameter("genre", params[3])
                        ;
                String query = builder.build().getEncodedQuery();

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

            try {

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

                    // New Paciente Object
                    Paciente medic = new Paciente();

                    ArrayList< String > linePhp = new ArrayList <  > ();

                    ArrayList < Paciente > medicPhp = new ArrayList<>();

                    while ((line = reader.readLine()) != null) {
                        System.out.println( "phoenix fox" );
                        System.out.println( line  );
                        System.out.println( "phoenix wolf" );

                        linePhp.add( line );

                        if ( linePhp.size() == 3 )
                        {

                            // Create medic Object
                            medic = new Paciente();

                            // Update Medic Object
                            medic.setId(linePhp.get(0));
                            medic.setSexo(linePhp.get(1));
                            medic.setEdad(linePhp.get(2));

                            // Update Medic ArrayList
                            medicPhp.add( medic );

                            // Reset linePhp
                            linePhp.clear();



                        }


                        //result.append(line);
                    }

                    System.out.println( "simorgh fire" );
                    System.out.println( medicPhp );

                    // Pass data to onPostExecute method
                    return medicPhp;

                    //return(result.toString());

                }else{
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
        }



        // @Override
        protected void onPostExecute(ArrayList<Paciente> result) {

            // Check obtained result

            //this method will be running on UI thread
            System.out.println( "Phoenix blue wave");
            System.out.println( result );
            System.out.println( "Phoenix alter ego");


            //pdLoading.dismiss();

            if ( result.size() > 0 ) {

                // Let PacienteRepository know the result
                PacienteRepository.getInstance().saveMedicList( result );

                System.out.println( "phoenix de hielo y storm" );
                System.out.println( PacienteRepository.getInstance().getCategorys() );
                update();

                // Messages
                String message = "";
                boolean extra = false;

                if ( !identifier.equals( "" ) )
                {
                    message = message + " identificador: " + identifier;
                    extra = true;
                }

                if ( !edad.equals( "" ) )
                {
                    if ( extra )
                    {
                        message = message + ",";
                    }
                    message = message + " edad: " + edad;
                    extra = true;
                }

                if ( !genre.equals( "N" ) )
                {
                    if ( extra )
                    {
                        message = message + " y";
                    }
                    message = message + " género: " + genre;
                }

                if ( !message.equals( "" ) )
                {

                    message = "Paciente encontrado con" + message;

                }else
                {
                    message = "Pacientes encontrados";
                }



                Toast.makeText( getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();





                // Class<? extends Activity> activityClass;
                // activityClass = ValidateActivity.class;

                //  Intent intent = new Intent(DAOCardiovascular.this, activityClass);
                // startActivity(intent);





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

                // Paciente -> Menu Inicio
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

                Toast.makeText( getActivity().getBaseContext(), "No hay pacientes encontrados", Toast.LENGTH_LONG).show();




                //if (result.equalsIgnoreCase("false")){
                //onLoginFailed();
                // If username and password does not match display a error message
                // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            }/* else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }*/
        }
    }







}
