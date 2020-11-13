package src.logica;

import clases.*;
import clases.Licencia;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class calcularCosto {

public static double calcularCostoLicencia (Licencia lic, Date fechaVigencia){

    LocalDate fechaV = fechaVigencia.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate ahora = LocalDate.now();
    Period periodo = Period.between(ahora, fechaV);
    int vigencia = periodo.getYears();

    double costo = 8.0;
    ArrayList<Clase> clases = lic.getClases();
    for (int i=0; i<clases.size(); i++){
        if(clases.get(i).toString().equals("A")||clases.get(i).toString().equals("B")||clases.get(i).toString().equals("G")){
            if (vigencia==5)  costo=costo+40.0;
            if (vigencia==4)  costo=costo+30.0;
            if (vigencia==3)  costo=costo+25.0;
            if (vigencia==1)  costo=costo+20.0;
            }  else if(clases.get(i).toString().equals("C")){
                if (vigencia==5)  costo=costo+47.0;
                if (vigencia==4)  costo=costo+35.0;
                if (vigencia==3)  costo=costo+30.0;
                if (vigencia==1)  costo=costo+23.0;
                }   else if(clases.get(i).toString().equals("D")||clases.get(i).toString().equals("E")||clases.get(i).toString().equals("F")){
                    if (vigencia==5)  costo=costo+59.0;
                    if (vigencia==4)  costo=costo+44.0;
                    if (vigencia==3)  costo=costo+39.0;
                    if (vigencia==1)  costo=costo+29.0;
            }

        }
    return costo;
    }

}
