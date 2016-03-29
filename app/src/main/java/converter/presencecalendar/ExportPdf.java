package converter.presencecalendar;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Adelina on 3/29/2016.
 */
public class ExportPdf {

    private String currentPdfPath;
    private DBHelper db;
    private ArrayList<Contact> contacts;
    private Context context ;



    public String createPdf(Context context) throws IOException, DocumentException {
        this.context = context;
        Document document = new Document();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PDF_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File im = File.createTempFile(
                imageFileName,
                ".pdf",
                storageDir
        );

        currentPdfPath = "file:" + im.getAbsolutePath();
        db = new DBHelper(context);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(im));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        document.add(createTable());
        document.close();

        return currentPdfPath;
    }

    private PdfPTable createTable()
    {
        PdfPTable table = new PdfPTable(4);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("First Name"));
        cell.setColspan(1);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Surname"));
        cell.setRowspan(1);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Date"));
        cell.setRowspan(1);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Time"));
        cell.setRowspan(1);
        table.addCell(cell);


        contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            cell = new PdfPCell(new Phrase(cn.getName()));
            cell.setRowspan(1);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(cn.getSurname()));
            cell.setRowspan(1);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(cn.getDate()));
            cell.setRowspan(1);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(cn.getTime()));
            cell.setRowspan(1);
            table.addCell(cell);

        }

        return table;
    }
}
