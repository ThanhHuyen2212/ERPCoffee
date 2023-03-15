package Util;

import java.sql.Date;
import java.util.ArrayList;

public class DateTool {
    public static ArrayList<Date> getDateBetween(Date start, Date end){
        ArrayList<Date> list = new ArrayList<>();
        while(start.compareTo(end)<=0){
            list.add(new Date(start.getTime()));
            start.setDate(start.getDate()+1);
        }
        return list;
    }

    public static ArrayList<Date> getDayOfMonth(int year,int month){
        month -= 1;
        Date start = new Date(year-1900,month,1);
        Date end = new Date(year-1900,month+1,1);
        ArrayList<Date> list = new ArrayList<>();
        while(start.compareTo(end)<0){
            list.add(new Date(start.getTime()));
            start.setDate(start.getDate()+1);
        }
        return list;
    }
}
