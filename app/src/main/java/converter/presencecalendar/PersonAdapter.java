package converter.presencecalendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Contact> {
    private final Activity activity;
    private final ArrayList<Contact> data;
    private final int layoutResourceId;

    public PersonAdapter(Activity activity, int layoutResourceId, ArrayList<Contact> data) {
        super(activity, layoutResourceId, data);
        this.activity = activity;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView)row.findViewById(R.id.firstLine);
            holder.date = (TextView)row.findViewById(R.id.secondLine);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        Contact person = data.get(position);

        holder.name.setText("First Name: "+person.getName()+" Surname: "+person.getSurname());
        holder.date.setText("Date: "+person.getDate()+" Time: "+person.getTime());

        return row;
    }

    static class ViewHolder
    {
        TextView name;
        TextView date;
    }
}