package converter.presencecalendar;

public class Contact {

    String date;
    String time;
    String name;
    String surname;

    public Contact( String name, String surname ,String date, String time){
        this.date = date;
        this.name = name;
        this.time = time;
        this.surname = surname;
    }

    public String getName(){
        return this.name;
    }

    public String getDate(){
        return this.date;
    }


    public String getTime(){
        return this.time;
    }

    public String getSurname(){
        return this.surname;
    }



}