package interfaces;

import clases.tipoLicencia;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static logica.EmitirLicencia.*;

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
    private JComboBox<String> comboClases;
    private JCheckBox donanteDeOrganosCheckBox;
    private JTextField campoGrupo;
    private JTextField campoFechaOtorgamiento;
    private JTextField campoDireccion;
    private JTextField campoFechaNacimiento;
    private JTextArea campoObservaciones;
    private JLabel labelTipoClase;
    private JTextField campoBuscarTitular;
    private JButton botonBuscar;
    private JComboBox<String> comboTipo;
    private JPanel panelEmitirLicencia;
    private JLabel labelBuscarTitular;


    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Emitir una licencia");

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            ///
        }
        frame.setContentPane(new EmitirLicenciaUI().panelEmitirLicencia);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public EmitirLicenciaUI() throws SQLException {
        generacionIDLicencia();
        validacionBuscarTitular();
        inicializarComboTipo();

        botonBuscar.addActionListener(e -> {
            String nroDocIngresado = campoBuscarTitular.getText();
            String tipoDocIngresado = null;

            if(nroDocIngresado.equals("") || nroDocIngresado.length()!=8){
                JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Documento válido");
            }
            if(comboTipo.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de Documento");
            }
            else{

                switch (Objects.requireNonNull(comboTipo.getSelectedItem()).toString()) {
                    case "DNI" -> tipoDocIngresado = "DNI";
                    case "LC" -> tipoDocIngresado = "LIBRETA_CIVICA";
                    case "LE" -> tipoDocIngresado = "LIBRETA_ENROLAMIENTO";
                }

                String datosTitular = null;
                try {
                    datosTitular = buscarTitular(nroDocIngresado,tipoDocIngresado);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                assert datosTitular != null;
                if(datosTitular.equals("")){
                    JOptionPane.showMessageDialog(null, "No se pudo encontrar el titular");
                }
                else{
                    try {
                        seteoCamposTitular(datosTitular);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }

            }
        });

        inicializarComboClases();

        comboClases.addActionListener(e -> {
            if (comboClases.getSelectedItem() != null) {
                if (comboClases.getSelectedItem().equals("C") || comboClases.getSelectedItem().equals("D") || comboClases.getSelectedItem().equals("E")) {
                    labelTipoClase.setText("Conductor profesional");
                } else if (comboClases.getSelectedItem().equals("A") || comboClases.getSelectedItem().equals("B")) {
                    labelTipoClase.setText("Conductor común");
                }
            }
        });

        botonConfirmar.addActionListener(e -> {
            Date fechaVencimiento = new Date(); //-> Referencia a HISTORIA 2 calcularVigencia(...);
            double costo = 0.0; // -> Referencia a HISTORIA 3 calcularCosto(...)

            if(validarCampos()) {
                if (calcularEdad(campoFechaNacimiento.getText()) >= 17) {
                    try {
                        String tipoDocIngresado = switch (Objects.requireNonNull(comboTipo.getSelectedItem()).toString()) {
                            case "DNI" -> "DNI";
                            case "LC" -> "LIBRETA_CIVICA";
                            case "LE" -> "LIBRETA_ENROLAMIENTO";
                            default -> null;
                        };

                        if (labelTipoClase.getText().equals("Conductor profesional")) {
                            if (validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado)) {
                                if (calcularEdad(campoFechaNacimiento.getText()) >= 21 && calcularEdad(campoFechaNacimiento.getText()) <= 65) {
                                    JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "El titular no cumple con la edad mínima.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El titular no tiene una licencia de tipo B anterior.");
                            }
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if (labelTipoClase.getText().equals("Conductor profesional")) {
                        try {
                            //Falta definir idTitular e idLicencia y su relación con Licencia para ejecutar el INSERT.

                            Date fechaOtorgamiento =  new SimpleDateFormat("yyyy-MM-dd").parse(campoFechaOtorgamiento.getText());
                            Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(campoFechaNacimiento.getText());
                            emitirLicencia(Integer.parseInt(campoNroLicencia.toString()),
                                    tipoLicencia.PROFESIONAL,
                                    fechaOtorgamiento,
                                    fechaOtorgamiento,
                                    fechaVencimiento,
                                    true,
                                    costo,
                                    campoObservaciones.getText(),
                                    0);

                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                    else if(labelTipoClase.getText().equals("Conductor común")){
                        //Falta definir idTitular e idLicencia y su relación con Licencia para ejecutar el INSERT.
                        try{
                        Date fechaOtorgamiento =  new SimpleDateFormat("yyyy-MM-dd").parse(campoFechaOtorgamiento.getText());
                        Date fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(campoFechaNacimiento.getText());

                        emitirLicencia(Integer.parseInt(campoNroLicencia.toString()),
                                tipoLicencia.COMUN,
                                fechaOtorgamiento,
                                fechaOtorgamiento,
                                fechaVencimiento,
                                true,
                                costo,
                                campoObservaciones.getText(),
                                0);
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "El titular no cumple con la edad mínima.");
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Revise los campos, la licencia no se pudo emitir.");
            }

});

        botonCancelar.addActionListener(e -> {
            campoBuscarTitular.setText("");
            comboTipo.setSelectedIndex(-1);
            comboClases.setSelectedIndex(-1);
            campoGrupo.setText("");
            campoObservaciones.setText("");
            donanteDeOrganosCheckBox.setSelected(false);
            campoTipoDoc.setText("");
            campoNroDoc.setText("");
            campoNombre.setText("");
            campoCUIL.setText("");
            campoFechaNacimiento.setText("");
            campoDireccion.setText("");
            campoFechaOtorgamiento.setText("");
        });

        botonNuevoTitular.addActionListener(e -> JOptionPane.showMessageDialog(null, "Abriendo interfaz de carga de nuevo titular..."));
    }

    private boolean validarCampos() {
        boolean valido;
        valido= !campoNombre.getText().isEmpty() && !campoDireccion.getText().isEmpty() && !campoCUIL.getText().isEmpty() && !campoNroDoc.getText().isEmpty() && !campoGrupo.getText().isEmpty() && !campoFechaOtorgamiento.getText().isEmpty() && !campoFechaNacimiento.getText().isEmpty() && !campoTipoDoc.getText().isEmpty() && comboClases.getSelectedIndex() != -1;
        return valido;
    }

    public void seteoCamposTitular(String datosTitularBD) throws ParseException {

            String[] datosSplitteados = datosTitularBD.split(",");

            String nombre = datosSplitteados[0];
            String apellido = datosSplitteados[1];
            String cuil = datosSplitteados[2];
            String direccion = datosSplitteados[3];
            String fechaNacimiento = datosSplitteados[4];
            String grupoSanguineo = datosSplitteados[5];
            String donanteOrganos = datosSplitteados[6];
            String codigoPostal = datosSplitteados[7];

            campoTipoDoc.setText(Objects.requireNonNull(comboTipo.getSelectedItem()).toString());
            campoNroDoc.setText(campoBuscarTitular.getText());
            campoNombre.setText(nombre+" "+apellido);
            campoCUIL.setText(cuil);

            Date fechaNacimiento_date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaActual = new Date();
            String fechaFormateada = sdf.format(fechaActual);

            campoFechaNacimiento.setText(sdf.format(fechaNacimiento_date));

            campoDireccion.setText(direccion+", CP: "+codigoPostal);
            campoGrupo.setText(grupoSanguineo);

            donanteDeOrganosCheckBox.setSelected(Integer.parseInt(donanteOrganos) == 1);

            campoFechaOtorgamiento.setText(fechaFormateada);

    }

    public void generacionIDLicencia() throws SQLException {
        campoNroLicencia.setText(getIdLicencia());
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

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            for (int i=0;i<str.length();i++)
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
