package apps.hillavas.com.yoga.classes.tools;

/**
 * Created by A.Mohammadi on 8/7/2017.
 */

public class DateMounthIntToStr {
    public static String getDateShamsiMounthName(int mounth){
        String strMounth = "فروردین";
        switch (mounth){
            case 2:{
                strMounth = "اردیبهشت";
                break;
            }
            case 3:{
                strMounth = "خرداد";
                break;
            }
            case 4:{
                strMounth = "تیر";
                break;
            }
            case 5:{
                strMounth = "مرداد";
                break;
            }
            case 6:{
                strMounth = "شهریور";
                break;
            }
            case 7:{
                strMounth = "مهر";
                break;
            }
            case 8:{
                strMounth = "آبان";
                break;
            }
            case 9:{
                strMounth = "آذر";
                break;
            }
            case 10:{
                strMounth = "دی";
                break;
            }
            case 11:{
                strMounth = "بهمن";
                break;
            }
            case 12:{
                strMounth = "اسفند";
                break;
            }
        }

        return strMounth;
    }
}
