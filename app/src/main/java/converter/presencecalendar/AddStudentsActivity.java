package converter.presencecalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddStudentsActivity extends Activity {
        private static final int DATE_DIALOG_ID = 1;
        private static final int TIME_DIALOG_ID = 2;
        private int year;
        private int month;
        private int day;
        private int hour;
        private int min;
        EditText editTextDate , editTextTime , editTextName , editTextSurname;
        ImageButton calendarButton , clockButton , addButton;
        private String currentDate , currentTime;
        private Context context;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_add_students);

                editTextDate = (EditText) findViewById(R.id.dateText);
                editTextTime = (EditText) findViewById(R.id.timeText);
                editTextName =  (EditText) findViewById(R.id.nameText);
                editTextSurname = (EditText) findViewById(R.id.surnameText);
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                hour = c.get(Calendar.HOUR);
                min = c.get(Calendar.MINUTE);

                currentDate = new StringBuilder().append(day).append(".")
                        .append(month + 1).append(".").append(year).toString();
                editTextDate.setText(currentDate);

                currentTime = new StringBuilder().append(hour).append(":")
                        .append(min).toString();
                editTextTime.setText(currentTime);


                calendarButton = (ImageButton) findViewById(R.id.searchDate);
                clockButton = (ImageButton) findViewById(R.id.searchTime);

                context = getApplicationContext();
                View.OnClickListener listenerDate = new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                                final Calendar c = Calendar.getInstance();
                                year = c.get(Calendar.YEAR);
                                month = c.get(Calendar.MONTH);
                                day = c.get(Calendar.DAY_OF_MONTH);
                                showDialog(DATE_DIALOG_ID);
                        }
                };
                calendarButton.setOnClickListener(listenerDate);

                View.OnClickListener listenerTime = new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {

                                final Calendar c = Calendar.getInstance();
                                hour = c.get(Calendar.HOUR);
                                min = c.get(Calendar.MINUTE);
                                showDialog(TIME_DIALOG_ID);
                        }
                };
                clockButton.setOnClickListener(listenerTime);

                addButton = (ImageButton) findViewById(R.id.add);

                View.OnClickListener listenerAdd = new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                        String name = String.valueOf(editTextName.getText());
                        String surname = String.valueOf(editTextSurname.getText());

                        if(name.equals("") || surname.equals("")) {
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context,"Name and Surname must be completed!", duration);
                                toast.show();
                        }
                         else{
                         DBHelper db = new DBHelper(getApplication().getApplicationContext());
                         db.addContact(new Contact(name, surname,currentDate,currentTime));

                         editTextName.setText("");
                         editTextSurname.setText("");

                         int duration = Toast.LENGTH_SHORT;
                         Toast toast = Toast.makeText(context,"Added student :"+name+" "+surname+"\nTime : "+currentTime+"\nDate : "+currentDate, duration);
                         toast.show();
                        }

                        }
                };
                addButton.setOnClickListener(listenerAdd);

        }


        private void updateDisplay() {
                currentDate = new StringBuilder().append(day).append(".")
                        .append(month + 1).append(".").append(year).toString();
                currentTime = new StringBuilder().append(hour).append(":")
                        .append(min).toString();
                editTextTime.setText(currentTime);


        }

        DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int i, int j, int k) {

                        year = i;
                        month = j;
                        day = k;
                        updateDisplay();
                        editTextDate.setText(currentDate);
                }
        };

        TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker datePicker, int i, int j) {

                        hour = i;
                        min = j;
                        updateDisplay();
                        editTextTime.setText(currentTime);
                }
        };



        @Override
        protected Dialog onCreateDialog(int id) {
                switch (id) {
                        case DATE_DIALOG_ID:
                                return new DatePickerDialog(this, myDateSetListener, year, month,day);
                        case TIME_DIALOG_ID:
                                return new TimePickerDialog(this,myTimeSetListener,hour,min,true);
                }
                return null;
        }
}