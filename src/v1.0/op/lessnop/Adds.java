package op.lessnop;

import org.apache.commons.lang3.StringUtils;

public class Adds {

	public static String timeToString(int ms) {
		String k = "ms"; 
		
		if(ms >= 20) {
			ms /= 20;
			k = "s";
			if(ms >= 60) {
				ms /= 60;
				k = "min";
				if(ms >= 60) {
					ms /= 60;
					k = "h";
				}
			}
		}
		
		String s = ms + "" + k;
		
		
		return s;
	}
	
	public static int stringToTime(String s) {
		int k = 1;
		s = s.replaceAll(" ", "");
		if(s.contains("ms")) s.replaceAll("ms", "");
		if(s.contains("sek") || s.contains("s")) { k = 20; s = s.replaceAll("sek", "").replaceAll("s", ""); }
		if(s.contains("min") || s.contains("m")) { k = 20 * 60; s = s.replaceAll("min", "").replaceAll("m", ""); }
		if(s.contains("godz") || s.contains("h")) { k = 20 * 60 * 60; s = s.replaceAll("godz", "").replaceAll("h", ""); }
		if(!StringUtils.isNumeric(s)) return -2;
		return Integer.parseInt(s) * k;
	}
	

}
