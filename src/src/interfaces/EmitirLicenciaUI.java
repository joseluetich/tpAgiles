package interfaces;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class EmitirLicenciaUI {
    boolean validarCampos;
    private JButton botonCancelar;
    private JButton botonNuevoTitular;
    private JButton botonConfirmar;
    private JTextField campoNroLicencia;
    private JTextField campoTipoDoc;
    private JTextField campoNroDoc;
    private JTextField campoNombre;
    private JTextField campoCUIL;
    private JComboBox comboClases;
    private JCheckBox donanteDeÓrganosCheckBox;
    private JTextField campoGrupo;
    private JTextField campoFechaOtorgamiento;
    private JTextField campoDireccion;
    private JTextField campoFechaNacimiento;
    private JTextArea campoObservaciones;
    private JLabel labelTipoClase;
    private JTextField campoBuscarTitular;
    private JButton botonBuscar;
    private JComboBox comboTipo;
    private JPanel panelEmitirLicencia;
    private JLabel labelBuscarTitular;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Emitir una licencia");
        frame.setContentPane(new EmitirLicenciaUI().panelEmitirLicencia);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public EmitirLicenciaUI() {
        generacionIDLicencia();
        validacionBuscarTitular();
        inicializarComboTipo();

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nroDocIngresado = campoBuscarTitular.getText();
                String tipoDocIngresado = (String) comboTipo.getSelectedItem();
                if(nroDocIngresado.equals("") || nroDocIngresado.length()!=8){
                    JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Documento válido");
                }
                if(comboTipo.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(null, "Seleccione un tipo de Documento");
                }
                else{
                    seteoCamposTitular(nroDocIngresado, tipoDocIngresado);
                }
            }
        }); //Presionar listener Buscar

        inicializarComboClases();

        comboClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboClases.getSelectedItem() != null) {
                    if (comboClases.getSelectedItem().equals("C") || comboClases.getSelectedItem().equals("D") || comboClases.getSelectedItem().equals("E")) {
                        labelTipoClase.setText("Conductor profesional");
                    } else if (comboClases.getSelectedItem().equals("A") || comboClases.getSelectedItem().equals("B")) {
                        labelTipoClase.setText("Conductor común");
                    }
                }
            }
        });

        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(campoBuscarTitular.getText().equals("")){
                    validarCampos=false;
                }
                else{
                    validarCampos=true;
                }

                if (validarCampos) {
                    JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Revise los campos, la licencia no se pudo emitir.");
                }
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            campoBuscarTitular.setText("");
            botonBuscar.doClick();
            comboTipo.setSelectedIndex(-1);
            comboClases.setSelectedIndex(-1);
            campoGrupo.setText("");
            campoObservaciones.setText("");
            donanteDeÓrganosCheckBox.setSelected(false);
            }
        });

        botonNuevoTitular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Abriendo interfaz de carga de nuevo titular...");
            }
        });
    }

    public void seteoCamposTitular(String nroDocIngresado, String tipoDocIngresado){
        //TitularDao = BuscarTitularEnBD(nroDocIngresado,tipoDocIngresado);
        if(nroDocIngresado.equals("41043652") && tipoDocIngresado.equals("DNI")){
            campoTipoDoc.setText("DNI");                    //campoTipoDoc.setText(TitularDao.getNroDocumento())
            campoNroDoc.setText("41043652");
            campoNombre.setText("DAVID, FAUSTO");
            campoCUIL.setText("20-41043652-4");
            campoFechaNacimiento.setText("07/05/1998");
            campoDireccion.setText("Sarobe 694");
            campoFechaOtorgamiento.setText("20/01/2020");
        }
        else{
            campoTipoDoc.setText("");                    //campoTipoDoc.setText(TitularDao.getNroDocumento())
            campoNroDoc.setText("");
            campoNombre.setText("");
            campoCUIL.setText("");
            campoFechaNacimiento.setText("");
            campoDireccion.setText("");
            campoFechaOtorgamiento.setText("");
        }

    }

    public void generacionIDLicencia(){
        Random rnd = new Random();
        int IDRandom = 100000 + rnd.nextInt(900000);
        campoNroLicencia.setText(String.valueOf(IDRandom)); //Se setea el ID autogenerado en la BD

    }

    public void validacionBuscarTitular(){
        campoBuscarTitular.setDocument(new CampoLimitado(8));
        campoBuscarTitular.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try{
                    int i = Integer.parseInt(campoBuscarTitular.getText());
                    labelBuscarTitular.setText("Seleccione el tipo e ingrese el Nro. de Doc.");
                    labelBuscarTitular.setForeground(Color.black);
                } catch (NumberFormatException ex){
                    labelBuscarTitular.setText("* Ingrese solo números.");
                    labelBuscarTitular.setForeground(Color.RED);
                }
            }
        });
    }

    static class CampoLimitado extends PlainDocument {
        private int limit;
        CampoLimitado(int limit) {
            super();
            this.limit = limit;
        }

        CampoLimitado(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            for (int i=0;i<str.length();i++)
                // si no es digit, volvemos
                if (!Character.isDigit(str.charAt(i)))
                    return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }

        }
    }

    public void inicializarComboTipo(){
        comboTipo.addItem("DNI");
        comboTipo.addItem("LE");
        comboTipo.addItem("LC");
        comboTipo.setSelectedItem(null);
    }

    public void inicializarComboClases(){
        comboClases.addItem("A");
        comboClases.addItem("B");
        comboClases.addItem("C");
        comboClases.addItem("D");
        comboClases.addItem("E");
        comboClases.addItem("G");
        comboClases.setSelectedItem(null);

    }


}
