package converter.presencecalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ext.SatelliteMenu;
import ext.SatelliteMenuItem;


public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);

        float distance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        //The distance of items from the center button
        menu.setSatelliteDistance((int) distance);
        //The duration of expand and collapse operations in milliseconds.
        menu.setExpandDuration(200);
        menu.setCloseItemsOnClick(true);

        String[] days = {"Monday","Tuesday","Wednesday",
                "Thursday","Friday","Saturday","Sunday"};

        String[] months = {"January","February","March","April","May","June",
                "July","August","September","October","November","December"};

        TextView txtCurrentTime= (TextView)findViewById(R.id.appTime);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int dayNr = c.get(Calendar.DAY_OF_WEEK);
        int month = c.get(Calendar.MONTH);
        String curTime =days[dayNr]+" , "+day + "\n" + months[month] ;
        txtCurrentTime.setText(curTime);
        Typeface face = Typeface.createFromAsset(getAssets(), "Sansation_Bold.ttf");
        txtCurrentTime.setTypeface(face);


        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.drawable.ic_calendar));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_add));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_find));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_download));
        menu.addItems(items);

        menu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            public void eventOccured(int id)
            {
                if(id==4) {
                Intent i = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(i);
                }
                if(id==3) {
                    Intent i = new Intent(getApplicationContext(), AddStudentsActivity.class);
                    startActivity(i);
                }

                if(id==2) {
                    Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(i);
                }

                if(id==1) {
                    Intent i = new Intent(getApplicationContext(), ExportActivity.class);
                    startActivity(i);
                }


            }
        });


    }
}