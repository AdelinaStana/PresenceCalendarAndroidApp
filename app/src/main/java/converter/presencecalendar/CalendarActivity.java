package converter.presencecalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private String currentDate ;
    private DBHelper db ;
    private ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ImageButton refreshButton = (ImageButton) findViewById(R.id.search_date);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int  month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        currentDate = new StringBuilder().append(day).append(".")
                .append(month + 1).append(".").append(year).toString();

        db = new DBHelper(this);

        contacts = db.getAllContactsForDate(currentDate);

        String[] studentsArray = new String[contacts.size()] ;
        String[] dateTimeArray = new String[contacts.size()] ;
        int i = 0 ;

        for (Contact cn : contacts) {
            studentsArray[i]=cn.getName()+" "+cn.getSurname();
            dateTimeArray[i]="Date: "+cn.getDate()+" Time: "+cn.getTime();
            i=i+1;
        }

        PersonAdapter adapter = new PersonAdapter(CalendarActivity.this ,R.layout.student , contacts);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        refreshButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MonthView mv = (MonthView) findViewById(R.id.monthView);
                String date = mv.getCurrentDate();
                currentDate = date ;
                contacts = db.getAllContactsForDate(date);

                String[] studentsArray = new String[contacts.size()];
                String[] dateTimeArray = new String[contacts.size()];
                int i = 0;

                    for (Contact cn : contacts) {
                        studentsArray[i] = cn.getName() + " " + cn.getSurname();
                        dateTimeArray[i] = "Date: " + cn.getDate() + " Time: " + cn.getTime();
                        i = i + 1;
                    }

                    PersonAdapter adapter = new PersonAdapter(CalendarActivity.this, R.layout.student, contacts);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                    registerForContextMenu(listView);
                }
        });


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete Record");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Delete Record"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int listPosition = info.position;
            Contact cn = contacts.get(listPosition);
            db.deleteContact(cn);

            contacts = db.getAllContactsForDate(currentDate);

            String[] studentsArray = new String[contacts.size()];
            String[] dateTimeArray = new String[contacts.size()];
            int i = 0;

            for (Contact c : contacts) {
                studentsArray[i] = c.getName() + " " + c.getSurname();
                dateTimeArray[i] = "Date: " + c.getDate() + " Time: " + c.getTime();
                i = i + 1;
            }

            PersonAdapter adapter = new PersonAdapter(CalendarActivity.this, R.layout.student, contacts);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

        }
        else{
            return false;
        }
        return true;
    }
}
