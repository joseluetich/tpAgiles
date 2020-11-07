package interfaces;

import javax.swing.table.DefaultTableModel;

public class ModeloTabla extends DefaultTableModel{

    String[] titulos;
    Object[][] datos;

// Determina el modelo con el que se va a construir la tabla

    public ModeloTabla(Object[][] datos, String[] titulos) {
        super();
        this.titulos=titulos;
        this.datos=datos;
        setDataVector(datos, titulos);
    }

    public ModeloTabla() {
        // TODO Auto-generated constructor stub
    }

    public boolean isCellEditable (int row, int column)
    {
        //Definimos si una celda puede ser o no editable
        /*if (column!=ColumnasTabla.APELLIDO && column!=ColumnasTabla.NOMBRE){
            return false;
        }else{
            return true;
        }*/

        return false;
    }
}