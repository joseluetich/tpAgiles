package src.logica;

import src.clases.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static java.util.Calendar.*;

public class CalcularCosto {

    public static double calcularCostoLicencia (Licencia lic, Date fechaVigencia){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate ahora = LocalDate.now();
        Date ahora_date = Date.from(ahora.atStartOfDay(defaultZoneId).toInstant());
        int vigencia = añosDeDiferencia(ahora_date,fechaVigencia);

        double costo = 8.0;
        ArrayList<Clase> clases = lic.getClases();

        for (int i=0; i<clases.size(); i++){
            if(clases.get(i).getTipo().equals("A")||clases.get(i).getTipo().equals("B")||clases.get(i).getTipo().equals("G")){
                if (vigencia<=1){
                    costo=costo+20.0;
                    break;
                }
                else if(vigencia<=3){
                    costo=costo+25.0;
                    break;
                }
                else if(vigencia<=4){
                    costo=costo+30.0;
                    break;
                }
                else if(vigencia<=5){
                    costo=costo+40.0;
                    break;
                }
            }  else if(clases.get(i).getTipo().equals("C")){
                if(vigencia <= 1){
                    costo=costo+23.0;
                    break;
                }
                else if (vigencia <= 3){
                    costo=costo+30.0;
                    break;
                }
                else if(vigencia <= 4){
                    costo=costo+35.0;
                    break;
                }
                else if(vigencia <= 5){
                    costo=costo+47.0;
                    break;
                }
            }   else if(clases.get(i).getTipo().equals("D")||clases.get(i).getTipo().equals("E")||clases.get(i).getTipo().equals("F")){
                if(vigencia <= 1){
                    costo=costo+29.0;
                    break;
                }
                else if (vigencia <= 3){
                    costo=costo+39.0;
                    break;
                }
                else if(vigencia <= 4){
                    costo=costo+44.0;
                    break;
                }
                else if(vigencia <= 5){
                    costo=costo+59.0;
                    break;
                }
            }
        }

        System.out.println(vigencia);
        System.out.println(costo);
        return costo;
    }

    public static int añosDeDiferencia(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(defaultZoneId));
        cal.setTime(date);
        return cal;
    }

}
