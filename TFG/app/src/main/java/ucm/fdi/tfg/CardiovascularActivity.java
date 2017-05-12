package ucm.fdi.tfg;

import android.content.Intent;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CardiovascularActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "CardiovascularActivity";

    // Buttons
    private Button tobaccoButton;
    private Button colesterol;
    private Button htaButton;
    private Button imcButton;
    private Button tabaquismoButton;
    private Button diabetesButton;
    private Button cardiovascularButton;


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

    // Results
    private TextView imcResult;
    private TextView tabaquismoResult;
    private TextView colesterolResult;

    private void calculateCardiovascular()
    {

        int cardiovascularResult = 0;



        try
        {
            // Edad
            double edad = 0;

            try{

                edad = Double.parseDouble( argumentPaciente.getEdad() );

            }catch( NumberFormatException e )
            {
                Toast.makeText(getBaseContext(), "Edad vacía. Se asume 0", Toast.LENGTH_LONG).show();

            }

            // Colesterol total
            double colesterol = 0;

            try{

                colesterol = Double.parseDouble( argumentPaciente.getColesterolTotal() );

            }catch( NumberFormatException e )
            {
                Toast.makeText(getBaseContext(), "Colesterol vacío. Se asume 0", Toast.LENGTH_LONG).show();
            }
            
            // Sistolica
            double sistolica = 0;

            try{

                sistolica = Double.parseDouble( argumentPaciente.getSistolica() );

            }catch( NumberFormatException e )
            {
                Toast.makeText(getBaseContext(), "Sistolica vacía. Se asume 0", Toast.LENGTH_LONG).show();
            }
            
            // Years Enfermo
            double yearSick = 0;
            
            try{
                
                yearSick = Double.parseDouble( argumentPaciente.getYearEnfermo() );
                
            }catch( NumberFormatException e )
            {
                Toast.makeText(getBaseContext(), "Años de enfermedad de diabetes vacío. Se asume 0", Toast.LENGTH_LONG).show();
            }

            // Ipa
            double ipa = 0;

            try{

                ipa = Double.parseDouble( argumentPaciente.getIpa() );

            }catch( NumberFormatException e )
            {
                Toast.makeText(getBaseContext(), "Ipa vacío. Se asume 0", Toast.LENGTH_LONG).show();
            }

            // No diabetes
            if ( yearSick == 0 )
            {

                // No diabetes Hombre
                if ( argumentPaciente.getSexo().equals( "M" ) )
                {

                    // No Diabetes Hombre No Fumador
                    if ( ipa == 0 )
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 1;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }

                        }






                    // No diabetes Hombre Fumador
                    }else
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 15;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 19;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 20;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 21;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 23;

                                }

                            }

                        }







                    }

                // No diabetes Mujer
                }else
                {
                    // No Diabetes Mujer No Fumador
                    if ( ipa == 0 )
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 1;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }

                        }






                        // No diabetes Mujer Fumador
                    }else
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 10;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 12;

                                }

                            }

                        }







                    }




                }

            // Diabetes
            }else{



                // Diabetes Hombre
                if ( argumentPaciente.getSexo().equals( "M" ) )
                {

                    // Diabetes Hombre No Fumador
                    if ( ipa == 0 )
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 12;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 14;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 12;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 14;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 20;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 16;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 20;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 21;

                                }

                            }

                        }






                        // Diabetes Hombre Fumador
                    }else
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 12;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 18;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 20;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 20;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 22;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 18;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 19;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 13;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 21;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 22;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 17;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 22;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 27;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 29;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 20;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 20;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 25;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 31;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 33;

                                }

                            }

                        }







                    }

                    // Diabetes Mujer
                }else
                {
                    // Diabetes Mujer No Fumador
                    if ( ipa == 0 )
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 2;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 8;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 8;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 13;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }

                        }






                        // Diabetes Mujer Fumador
                    }else
                    {

                        // Edad
                        if ( edad >= 35 && edad <= 44 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 1;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 3;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 4;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 5;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 2;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 6;

                                }

                            }




                        }else if ( edad >= 45 && edad <= 54 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 3;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 7;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 9;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 5;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 10;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 15;

                                }

                            }

                        }else if ( edad >= 55 && edad <= 64 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 14;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 15;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 15;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 19;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 22;

                                }

                            }

                        }else if ( edad >= 65 && edad <= 74 )
                        {

                            if ( colesterol < 160 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 4;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 11;

                                }



                            }else if ( colesterol >= 160 && colesterol < 180 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 6;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 12;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 14;

                                }

                            }else if ( colesterol >= 180 && colesterol < 220 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }else if ( colesterol >= 220 && colesterol < 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 7;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 11;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 14;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 17;

                                }

                            }else if ( colesterol >= 260 )
                            {

                                if ( sistolica < 120 )
                                {

                                    cardiovascularResult = 9;

                                }else if ( sistolica >= 120 && sistolica <= 129 )
                                {

                                    cardiovascularResult = 15;

                                }else if ( sistolica >= 130 && sistolica <= 139 )
                                {

                                    cardiovascularResult = 15;

                                }else if ( sistolica >= 140 && sistolica <= 159 )
                                {

                                    cardiovascularResult = 19;

                                }else if ( sistolica >= 160 )
                                {
                                    cardiovascularResult = 22;

                                }

                            }


                        }



                    }




                }



            }

            

            

        }catch( NumberFormatException e )
        {

            System.out.println( "Number exception" );
            System.out.println( e.getMessage() );



        }

        argumentPaciente.setCardiovascular( String.valueOf( cardiovascularResult ) );

        System.out.println( "argumentPaciente" );
        System.out.println( argumentPaciente );









    }


    protected void setData()
    {

        argumentPaciente =  DAOCardiovascular.getInstance().getCurrentPatient();

        System.out.println( "tabaco argumentPaciente DAO" );
        System.out.println( DAOCardiovascular.getInstance().getCurrentPatient() );

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


        // Results
        imcResult = (TextView) findViewById( R.id.imc_result );
        tabaquismoResult = (TextView) findViewById( R.id.tabaquismo_result );
        colesterolResult = (TextView) findViewById( R.id.colesterol_result );

        imcResult.setText( argumentPaciente.getImc() );
        tabaquismoResult.setText( argumentPaciente.getIpa() );
        colesterolResult.setText( argumentPaciente.getLdl() );


        System.out.println( "Set Data argumentPaciente" );
        System.out.println( argumentPaciente );

    }

    protected void onResume()
    {

        super.onResume();

        setData();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_riesgo_paciente);

        // Buttons
        tabaquismoButton = ( Button ) findViewById ( R.id.toggleFumador );
        diabetesButton = (Button) findViewById(R.id.toggleDiabetes);

        colesterol = (Button) findViewById( R.id.button_col );


        htaButton = ( Button ) findViewById( R.id.button_calcular_HTA );
        imcButton = ( Button ) findViewById( R.id.button_calcular_IMC );

        // Result
        cardiovascularButton = ( Button ) findViewById( R.id.button_add );

        // Text
        identif = (TextView) findViewById(R.id.identificacion);
        eda = (TextView) findViewById(R.id.weight);
        sexo = (TextView) findViewById(R.id.height);

        identificacion = (EditText) findViewById(R.id.id);
        edad = (EditText) findViewById(R.id.diastolicaData);

        Masculino = (RadioButton) findViewById(R.id.RB_genero_hombre);
        Femenino = (RadioButton) findViewById(R.id.RB_genero_mujer);



        // Set Data
        setData();


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

        colesterol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get data of the patient
                Intent intent = new Intent(getApplicationContext(), colesterolActivity.class);
                intent.putExtra( "paciente", argumentPaciente );
                startActivity(intent);

            }
        });


         cardiovascularButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Calculate cardiovascular
                calculateCardiovascular();

                System.out.println( "argumentPaciente2" );
                System.out.println( argumentPaciente );

                // Calculate cardiovascular
                new saveCardiovascular().execute( argumentPaciente.getId(), argumentPaciente.getCardiovascular() );

            }
        });







    }




    private class saveCardiovascular extends AsyncTask<String,Void,String> {

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

            url = DAOCardiovascular.getInstance().getUrl("saveCardiovascular.php");


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
                        .appendQueryParameter("cardiovascular", params[1]);
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

            if (result.equalsIgnoreCase("error")) {

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase("exception")) {


            } else if (result.equalsIgnoreCase("Riesgo cardiovascular calculado. Se han actualizado los datos en tratamiento")) {

                //argumentPaciente.setCardiovascular( )
                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );

                Toast.makeText(getBaseContext(), "Riesgo cardiovascular calculado. Se han actualizado los datos en tratamiento", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Ha ocurrido un error al actualizar los datos")) {

                Toast.makeText(getBaseContext(), "Riesgo cardiovascular calculado. El valor obtenido es idéntico al anterior", Toast.LENGTH_LONG).show();


            } else if (result.equalsIgnoreCase("Paciente no encontrado")) {

                Toast.makeText(getBaseContext(), "Paciente no encontrado", Toast.LENGTH_LONG).show();


            }
        }

    }




}
