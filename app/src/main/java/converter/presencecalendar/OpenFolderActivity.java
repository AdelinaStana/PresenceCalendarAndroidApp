package converter.presencecalendar;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;
import java.io.FilenameFilter;

public class OpenFolderActivity extends ListActivity {

    String[] pdflist;
    File[] imagelist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File pdfs = Environment.getExternalStorageDirectory();
        imagelist = pdfs.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return ((name.endsWith(".pdf")));
            }
        });
        pdflist = new String[imagelist.length];
        for (int i = 0; i < imagelist.length; i++) {
            pdflist[i] = imagelist[i].getName();
        }
        this.setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pdflist));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String path_ = imagelist[(int) id].getAbsolutePath();
        openPdfIntent(path_);
    }

    private void openPdfIntent(String path) {
        try {
            File file = new File(path);
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");

            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent1 = new Intent(getApplicationContext(), OpenFolderActivity.class);
            intent1.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
            startActivity(intent1);
        }

    }
}
