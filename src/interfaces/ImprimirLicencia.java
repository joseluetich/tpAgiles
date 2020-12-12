package src.interfaces;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;
import src.clases.Clase;
import src.clases.Licencia;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static java.awt.print.Printable.PAGE_EXISTS;

public class ImprimirLicencia extends Frame {
    private JPanel panelFrente;
    private JPanel panelReverso;
    private JLabel apellido;
    private JLabel nombre;
    private JLabel direccion;
    private JLabel fechaOtorgamiento;
    private JLabel nroDeLicencia;
    private JLabel fechaNacimiento;
    private JLabel clase;
    private JPanel panelDatos;
    private JPanel panelClase;
    private JPanel panelDorso;
    private JLabel donante;
    private JLabel observaciones;
    private JLabel grupoFactor;
    private JLabel fechaVencimiento;
    private JLabel nroCUIL;
    private JLabel claseA;
    private JLabel claseB;
    private JLabel claseC;
    private JLabel claseD;
    private JLabel claseE;
    private JLabel claseF;
    private JLabel claseG;
    private JLabel codigoDeBarras;

    public ImprimirLicencia(Licencia licencia, String fechaNac, String fechaVenc, String fechaOtorg, ArrayList<String> clases) throws OutputException, BarcodeException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaVenc_date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaVenc);

        String listaClases = Arrays.toString(clases.toArray()).replace("[", "").replace("]", "");

        nroDeLicencia.setText(licencia.getNumeroDeLicencia().toString());
        apellido.setText(licencia.getTitular().getApellido());
        nombre.setText(licencia.getTitular().getNombre());
        direccion.setText(licencia.getTitular().getDireccion());
        fechaNacimiento.setText(fechaNac);
        fechaOtorgamiento.setText(fechaOtorg);
        fechaVencimiento.setText(sdf.format(fechaVenc_date));
        clase.setText(listaClases);
        observaciones.setText(licencia.getObservaciones());

        if(licencia.getTitular().getDonante()){
            donante.setText("SI");
        }
        else{
            donante.setText("NO");
        }

        grupoFactor.setText(licencia.getTitular().getGrupoSanguineo());
        nroCUIL.setText(licencia.getTitular().getCuil());

        claseA.setVisible(false);
        claseB.setVisible(false);
        claseC.setVisible(false);
        claseD.setVisible(false);
        claseE.setVisible(false);
        claseF.setVisible(false);
        claseG.setVisible(false);

        if (clases.contains("A")){
            claseA.setVisible(true);
        }

        if (clases.contains("B")){
            claseB.setVisible(true);
        }

        if (clases.contains("C")){
            claseC.setVisible(true);
        }

        if (clases.contains("D")){
            claseD.setVisible(true);
        }

        if (clases.contains("E")){
            claseE.setVisible(true);
        }

        if (clases.contains("F")){
            claseF.setVisible(true);
        }

        if (clases.contains("G")){
            claseG.setVisible(true);
        }

        Barcode barcode = BarcodeFactory.createCode128A(licencia.getNumeroDeLicencia().toString());
        barcode.setFont(null);
        BufferedImage codigoBarras = BarcodeImageHandler.getImage(barcode);
        codigoDeBarras.setIcon(new ImageIcon(codigoBarras));
        pack();

        try {
            imprimirComponente(panelFrente, nroDeLicencia, true);

        } catch (PrinterException exp) {
            exp.printStackTrace();
        }

    }

    public static void imprimirComponente(JComponent component, JLabel nroLicencia, boolean fill) throws PrinterException {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setJobName("LIC_"+nroLicencia.getText());
        pf.setOrientation(PageFormat.LANDSCAPE);

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

}
