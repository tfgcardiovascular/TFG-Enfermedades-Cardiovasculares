package ucm.fdi.tfg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ValidateActivity extends AppCompatActivity{
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "ValidateActivity";
    private ListView mListView;
    private Button Validar_Button;
    private Button Rechazar_Button;

    public void onBackPressed() {
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("Cerrar sesión")
                .setMessage("Deseas cerrar la sesión?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ValidateActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Crear lista
        setContentView(R.layout.activity_validar);

        //Inicializar lista
        ArrayList<Category> category = new ArrayList<Category>();

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

        mListView = (ListView)findViewById(R.id.ListViewValidate);
        Validar_Button = (Button) findViewById(R.id.button_validar);
        //Validar_Button.setAlpha(.5f);
        Validar_Button.setClickable(false);
        Rechazar_Button = (Button) findViewById(R.id.button_rechazar);
        //Rechazar_Button.setAlpha(.5f);
        Rechazar_Button.setClickable(false);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });
    }


}

