package src.interfaces;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import src.bd.EmitirComprobanteBD;
import src.clases.Licencia;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.ParseException;

import javax.swing.JPanel;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTable;

import static src.bd.EmitirComprobanteBD.getIdComprobanteBD_int;

public class EmitirComprobante extends Frame {
    JPanel panelComprobante;
    JTable table1;
    JLabel nroFactura;
    JLabel fechaVencimiento;
    JLabel fechaEmision;
    JLabel nombre;
    JLabel apellido;
    JLabel domicilio;
    JLabel codigoPostal;
    JLabel importeNeto;
    JLabel observaciones;
    JLabel importeBruto;
    private JPanel panelFactura;
    private JPanel panelFacturaA;
    private JPanel panelTabla;
    private JPanel panelImporteObs;
    EmitirComprobante emitirComprobante;
    ModeloTabla modelo;
    private int columnasTabla;

    public EmitirComprobante(Licencia licencia, String fechaVenc, String fechaOtorg, String tipoClase, int idLicencia) throws OutputException, BarcodeException, ParseException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaVenc_date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVenc);

        fechaVencimiento.setText(sdf.format(fechaVenc_date));
        fechaEmision.setText(fechaOtorg);
        nombre.setText(licencia.getTitular().getNombre());
        apellido.setText(licencia.getTitular().getApellido());
        domicilio.setText(licencia.getTitular().getDireccion());
        codigoPostal.setText(licencia.getTitular().getCodigoPostal());
        observaciones.setText(licencia.getObservaciones());
        importeNeto.setText(licencia.getCosto().toString());
        importeBruto.setText(licencia.getCosto().toString());
        String padded = String.format("%03d", getIdComprobanteBD_int()+1);
        nroFactura.setText(padded);

        String concepto = construirTabla(licencia, tipoClase);

        pack();
       try {
            imprimirComponente(panelComprobante, true);
        } catch (PrinterException exp) {
            exp.printStackTrace();
        }

        insertarComprobanteDePago(licencia, concepto, licencia.getCosto().toString(), licencia.getCosto().toString(), String.valueOf(idLicencia));
    }

    private void insertarComprobanteDePago(Licencia licencia, String concepto, String importeNeto, String importeBruto, String idLicencia) throws SQLException {
        EmitirComprobanteBD.insertarComprobanteDePago(licencia, concepto, importeNeto, importeBruto, idLicencia);
    }


    public static void imprimirComponente(JComponent component, boolean fill) throws PrinterException {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setJobName("Comprobante de Pago");
        pf.setOrientation(PageFormat.PORTRAIT);

        pjob.setPrintable(new ComponentPrinter(component, fill), pf);
        if (pjob.printDialog()) {
            pjob.print();
        }
    }

    public static class ComponentPrinter implements Printable {
        private Component comp;
        private boolean fill;

        public ComponentPrinter(Component comp, boolean fill) {
            this.comp = comp;
            this.fill = fill;
        }

        @Override
        public int print(Graphics g, PageFormat format, int page_index) throws PrinterException {
            if (page_index > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            format.setOrientation(PageFormat.LANDSCAPE);

            Graphics2D g2 = (Graphics2D) g;
            double width = (int) Math.floor(format.getImageableWidth());
            double height = (int) Math.floor(format.getImageableHeight());
            g2.translate(format.getImageableX(), format.getImageableY());

            if (!fill) {
                width = Math.min(width, comp.getPreferredSize().width);
                height = Math.min(height, comp.getPreferredSize().height);
            }

            comp.setBounds(0, 0, (int) Math.floor(width), (int) Math.floor(height));
            if (comp.getParent() == null) {
                comp.addNotify();
            }
            comp.validate();
            comp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            comp.doLayout();
            comp.printAll(g2);

            if (comp.getParent() != null) {
                comp.removeNotify();
            }

            return Printable.PAGE_EXISTS;
        }

    }

    private String construirTabla(Licencia licencia, String tipoClase) throws SQLException {

        ArrayList<String> titulosList = new ArrayList<>();

        titulosList.add("Pos.");
        titulosList.add("Concepto/Descripción");
        titulosList.add("Cantidad");
        titulosList.add("Precio unitario");
        titulosList.add("Importe");

        //se asignan las columnas al arreglo para enviarse al momento de construir la tabla
        String titulos[] = new String[titulosList.size()];
        for (int i = 0; i < titulos.length; i++) {
            titulos[i]=titulosList.get(i);
        }

        //obtenemos los datos de la lista y los guardamos en la matriz
        //que luego se manda a construir la tabla

        Object[][] data = obtenerMatrizDatos(titulosList, licencia, tipoClase);
        construirTabla(titulos,data);

        return (data[1][1]+" y "+data[2][1]);
    }

    private Object[][] obtenerMatrizDatos(ArrayList titulosList, Licencia licencia, String tipoClase) throws SQLException {
        //se crea la matriz donde las filas son dinamicas pues corresponde mientras que las columnas son estaticas

       // String informacion[][] = RenovarLicenciaBD.getInformacionTabla();
        String informacion[][] = new String[3][5];


        informacion[0][0] = "Pos.";
        informacion[0][1] = "Concepto/Descripción";
        informacion[0][2] = "Cantidad";
        informacion[0][3] = "Precio unitario";
        informacion[0][4] = "Importe";

        double costoLicSinGastosAdm = (licencia.getCosto()-8.00);

        informacion[1][0] = String.valueOf(1);
        informacion[1][1] = "CLASE "+tipoClase;
        informacion[1][2] = "1";
        informacion[1][3] = String.valueOf(costoLicSinGastosAdm);
        informacion[1][4] = String.valueOf(costoLicSinGastosAdm);

        informacion[2][0] = String.valueOf(2);
        informacion[2][1] = "Gastos administrativos";
        informacion[2][2] = "1";
        informacion[2][3] = "8.00";
        informacion[2][4] = "8.00";

        return informacion;
    }

    private void construirTabla(String[] titulos, Object[][] data) {
        modelo = new ModeloTabla(data, titulos);

        //se asigna el modelo a la tabla
        table1.setModel(modelo);

        //filasTabla=data.length;
        //filasTabla=licenciasTable.getRowCount();
        //columnasTabla=table1.getColumnCount();
        columnasTabla = 5;

        //se asigna el tipo de dato que tendrán las celdas de cada columna definida respectivamente para validar su personalización
        table1.getColumnModel().getColumn(0).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(1).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(2).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(3).setCellRenderer(new GestionCeldas("texto"));
        table1.getColumnModel().getColumn(4).setCellRenderer(new GestionCeldas("texto"));

        table1.getTableHeader().setReorderingAllowed(false);
        table1.setRowHeight(20);//tamaño de las celdas
        table1.setGridColor(new java.awt.Color(0, 0, 0));

        //Se define el tamaño de largo para cada columna y su contenido
        table1.getColumnModel().getColumn(0).setPreferredWidth(2);
        table1.getColumnModel().getColumn(1).setPreferredWidth(2);
        table1.getColumnModel().getColumn(2).setPreferredWidth(2);
        table1.getColumnModel().getColumn(3).setPreferredWidth(2);
        table1.getColumnModel().getColumn(4).setPreferredWidth(2);

        //personaliza el encabezado
        JTableHeader jtableHeader = table1.getTableHeader();
        jtableHeader.setDefaultRenderer(new GestionEncabezadoTabla());
        table1.setTableHeader(jtableHeader);

        //se asigna la tabla al scrollPane
        //scrollPaneTabla.setViewportView(licenciasTable);

    }

}
