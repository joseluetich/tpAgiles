package src.interfaces;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;


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

    public static void main (String[] args)
    {
        try
        {
            ImprimirLicencia imp = new ImprimirLicencia();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    public ImprimirLicencia() throws OutputException, BarcodeException {
        nroDeLicencia.setText("12345678");
        apellido.setText("DAVID");
        nombre.setText("FAUSTO");
        direccion.setText("AV. DE LAS AMERICAS 490");
        fechaNacimiento.setText("07/05/1998");
        fechaOtorgamiento.setText("9/12/2020");
        fechaVencimiento.setText("9/12/2024");
        clase.setText("A B G");
        observaciones.setText("CONDUCE CON LENTES");
        donante.setText("NO");
        grupoFactor.setText("0+");
        nroCUIL.setText("20-12345678-9");
        claseC.setVisible(false);
        claseD.setVisible(false);
        claseE.setVisible(false);
        claseF.setVisible(false);

        Barcode barcode = BarcodeFactory.createCode128A("41043652");
        barcode.setFont(null);
        BufferedImage codigoBarras = BarcodeImageHandler.getImage(barcode);
        codigoDeBarras.setIcon(new ImageIcon(codigoBarras));
        pack();

        try {
            printComponent(panelFrente, nroDeLicencia, true);
        } catch (PrinterException exp) {
            exp.printStackTrace();
        }

    }

    public static void printComponent(JComponent component, JLabel nroLicencia, boolean fill) throws PrinterException {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setJobName("LIC_"+nroLicencia.getText());
        pf.setOrientation(PageFormat.LANDSCAPE);
        PageFormat postformat = pjob.pageDialog(pf);
        if (pf != postformat) {
            pjob.setPrintable(new ComponentPrinter(component, fill), postformat);
            if (pjob.printDialog()) {
                pjob.print();
            }
        }
    }

    public static void printComponentToFile(Component comp, boolean fill) throws PrinterException {
        Paper paper = new Paper();
        paper.setSize(8.3 * 72, 11.7 * 72);
        paper.setImageableArea(18, 18, 559, 783);

        PageFormat pf = new PageFormat();
        pf.setPaper(paper);
        pf.setOrientation(PageFormat.LANDSCAPE);

        BufferedImage img = new BufferedImage(
                (int) Math.round(pf.getWidth()),
                (int) Math.round(pf.getHeight()),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fill(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
        ComponentPrinter cp = new ComponentPrinter(comp, fill);
        try {
            cp.print(g2d, pf, 0);
        } finally {
            g2d.dispose();
        }

        try {
            ImageIO.write(img, "png", new File("Page-" + (fill ? "Filled" : "") + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
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

            Graphics2D g2 = (Graphics2D) g;
            g2.translate(format.getImageableX(), format.getImageableY());

            double width = (int) Math.floor(format.getImageableWidth());
            double height = (int) Math.floor(format.getImageableHeight());

            if (!fill) {

                width = Math.min(width, comp.getPreferredSize().width);
                height = Math.min(height, comp.getPreferredSize().height);

            }

            comp.setBounds(0, 0, (int) Math.floor(width), (int) Math.floor(height));
            if (comp.getParent() == null) {
                comp.addNotify();
            }
            comp.validate();
            comp.doLayout();
            comp.printAll(g2);
            if (comp.getParent() != null) {
                comp.removeNotify();
            }

            return Printable.PAGE_EXISTS;
        }

    }


}
