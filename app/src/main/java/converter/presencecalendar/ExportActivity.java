package converter.presencecalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public class ExportActivity extends Activity {
    private Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        context = this.getApplicationContext();


        ImageButton downloadButton = (ImageButton) this.findViewById(R.id.download_button);
        downloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    ExportPdf ep = new ExportPdf();
                    String path = ep.createPdf(context);

                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, "File saved at :" + path, duration);
                    toast.show();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton openButton = (ImageButton) this.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OpenFolderActivity.class);
                startActivity(i);

            }
        });

        ImageButton deleteButton = (ImageButton) this.findViewById(R.id.bin_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                yesNoDialog();
            }
        });
    }

    protected void yesNoDialog() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to delete all records?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        DBHelper db = new DBHelper(context);
                        db.deleteAll();

                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, "Records deleted!", duration);
                        toast.show();
                        finish();



                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}