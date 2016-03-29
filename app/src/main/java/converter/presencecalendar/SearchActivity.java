package converter.presencecalendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends Activity {
    private DBHelper db ;
    private EditText nameEditText ;
    private TextView attTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_button1);

        db = new DBHelper(this);
        nameEditText = (EditText) findViewById(R.id.nameText);
        attTextView  = (TextView) findViewById(R.id.nrDates);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = String.valueOf(nameEditText.getText());
                name = name.trim().replaceAll("\\s+", " ");
                name = name.toLowerCase();
                ArrayList<Contact> contacts = db.getAllContactsForName(name);

                String[] studentsArray = new String[contacts.size()] ;
                String[] dateTimeArray = new String[contacts.size()] ;
                int i = 0 ;

                for (Contact cn : contacts) {
                    studentsArray[i]=cn.getName()+" "+cn.getSurname();
                    dateTimeArray[i]="Date: "+cn.getDate()+" Time: "+cn.getTime();
                    i=i+1;
                }

                PersonAdapter adapter = new PersonAdapter(SearchActivity.this ,R.layout.student , contacts);
                ListView listView = (ListView) findViewById(R.id.listView1);
                listView.setAdapter(adapter);

                attTextView.setText("Attendences: "+contacts.size());



            }
        });

    }
}
