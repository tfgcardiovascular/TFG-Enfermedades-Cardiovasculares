package ucm.fdi.tfg;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePDF extends Activity {


    private EditText identifier;
    private EditText age;
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



    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_info);
        // Put you code to hide the layout
        final RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.main_layout); // assuming its a LinearLayout

        mainLayout.setVisibility(View.GONE);

       /* RelativeLayout mLayout = (RelativeLayout) findViewById();
        mLayout.setVisibility(View.GONE);*/

        verifyStoragePermissions( this );

        /*System.out.println( "prueba pdf" );
        System.out.println( content );

        // set width and height
        contentWidth = content.getWidth();
        contentHeight = content.getHeight();*/


        // We call to save document
        PdfGenerationAction();
    }

    public void generatePdf(){

        setContentView(R.layout.pdf_info);

        verifyStoragePermissions( this );

        // We call to save document
        PdfGenerationAction();
    }

    /**
     * Checks if the app has permission to write to device storage     *
     * If the app does not has permission then the user will be prompted to grant permissions     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }


    private void PdfGenerationAction(){
        PdfDocument document = new PdfDocument();
        Paciente argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        // First Page
        // repaint the user's text into the page
        setContentView(R.layout.pdf_info_portada);
        View content = findViewById(R.id.pdfLayout3);
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
        PageInfo pageInfo = new PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        // create a new page from the PageInfo
        Page page = document.startPage(pageInfo);

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

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        // Set Content Data
        identifier = (EditText) findViewById(R.id.id);
        age = (EditText) findViewById(R.id.edadData );
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

        identifier.setText( argumentPaciente.getId() );
        age.setText( argumentPaciente.getEdad() );

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
        pageInfo = new PageInfo.Builder( content.getWidth(),
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
        pageInfo = new PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        // do final processing of the page
        document.finishPage(page);

        // Fourth Page Creation
        setContentView(R.layout.tratamiento);
        content = findViewById(R.id.pdfLayout4);

        content.measure(widthContent, heightContent);
        content.layout(0,0,widthContent,heightContent);

        // Fourth Page Info
        pageNumber = 4;
        pageInfo = new PageInfo.Builder( content.getWidth(),
                content.getHeight() - 20, pageNumber).create();

        page = document.startPage(pageInfo);

        page.getCanvas().save();
        page.getCanvas().translate(left,top);

        content.draw(page.getCanvas());

        page.getCanvas().restore();

        // do final processing of the page
        document.finishPage(page);


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
}