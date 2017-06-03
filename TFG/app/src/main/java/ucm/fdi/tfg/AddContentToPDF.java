package ucm.fdi.tfg;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;

import com.qoppa.android.pdfProcess.PDFCanvas;
import com.qoppa.android.pdfProcess.PDFDocument;
import com.qoppa.android.pdfProcess.PDFFontStandard;
import com.qoppa.android.pdfProcess.PDFFontStandard.PDFFontFamily;
import com.qoppa.android.pdfProcess.PDFPage;
import com.qoppa.android.pdfProcess.PDFPaint;
import com.qoppa.android.pdfViewer.fonts.StandardFontTF;


public class AddContentToPDF extends Activity{
    public void onCreate(Bundle saveInstBundle){
        super.onCreate(saveInstBundle);

        try{
            //this static allows the sdk to access font assets,
            //it must be set prior to utilizing libraries
            StandardFontTF.mAssetMgr = getAssets();

            // Load a document and get the first page
            PDFDocument pdf = new PDFDocument("/sdcard/input.pdf", null);
            PDFPage page = pdf.getPage(0);

            //the PDFCanvas object is used to draw to the page
            PDFCanvas canvas = page.createCanvas();

            //add some text
            PDFFontStandard font = new PDFFontStandard(PDFFontFamily.HELVETICA, PDFFontStandard.Style.NORMAL, 22);
            canvas.drawText("Some example text", Color.BLACK, 20, 20, font);

            // Create paint object to draw shapes
            PDFPaint paint = new PDFPaint();
            paint.setStyle(PDFPaint.Style.STROKE);
            paint.setStrokeWidth(1);
            paint.setStrokeColor(Color.RED);

            //draw a red rectangle
            canvas.drawRect(40, 100, 120, 240, paint);

            // Load a bitmap to draw to the page
           // Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.icon);

            //the matrix is used to size and position the bitmap on the page
            Matrix matrix = new Matrix();
            matrix.preTranslate(300, 300);
            matrix.preScale(2, 2);

            //add an image to the page
            //canvas.drawBitmap(image, matrix, null);

            //save the document
            pdf.saveDocument("/sdcard/output.pdf");
        }
        catch(Exception e){
            Log.e("error", Log.getStackTraceString(e));
        }
    }
}