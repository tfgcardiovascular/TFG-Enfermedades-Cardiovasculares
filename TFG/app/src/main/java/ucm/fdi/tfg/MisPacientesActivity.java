package ucm.fdi.tfg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;


//import java.util.ArrayList;

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







































}

