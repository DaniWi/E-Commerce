package rest.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtility {
	
	public static void main(String[] args) {
		System.out.println(DateUtility.toGmtString(new Date(1449900000000L)));
		
	}
	
	public static String toGmtString(Date date){
	    SimpleDateFormat sd = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
	    sd.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return sd.format(date) + " GMT";
	}

}
