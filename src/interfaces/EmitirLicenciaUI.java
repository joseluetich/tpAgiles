package src.interfaces;

import src.clases.*;
import src.logica.CalcularVigenciaLicencia;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

import static src.logica.EmitirLicencia.*;

public class EmitirLicenciaUI extends JFrame{

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
    private JButton atrasButton;
    private static EmitirLicenciaUI emitirLicenciaUI;
    private Date fechaNacimiento_date;

    public EmitirLicenciaUI(JFrame frameQueLoEjecuta) throws SQLException {

        emitirLicenciaUI = this;
        add(panelEmitirLicencia);
        setTitle("Emitir Licencia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);

        generacionIDLicencia();
        validacionBuscarTitular();
        inicializarComboTipo();

        campoObservaciones.setLineWrap(true);
        campoObservaciones.setWrapStyleWord(true);

        botonBuscar.addActionListener(e -> {
            String nroDocIngresado = campoBuscarTitular.getText();
            if(nroDocIngresado.equals("") || nroDocIngresado.length()!=8){
                JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Documento válido");
            }
            if(comboTipo.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de Documento");
            }
            else{
                String datosTitular = null;
                try {
                    datosTitular = buscarTitular(nroDocIngresado,tipoDocIngresado());
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
                    } catch (ParseException | SQLException parseException) {
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
            if (validarCampos()) {
                try {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        Date fechaOtorgamientoEmision_actual = new Date();
                        String fechaOtorgamientoEmision_string = sdf.format(fechaOtorgamientoEmision_actual);

                        Date fechaVencimiento_date = CalcularVigenciaLicencia.calcularVigencia(fechaNacimiento_date, campoNroDoc.getText(), String.valueOf(getIdLicencia()));
                        String fechaVencimiento_string = sdf.format(fechaVencimiento_date);


                        double costo = 200.00; // -> Referencia a HISTORIA 3 calcularCosto(...)

                        int nroDoc = Integer.parseInt(campoNroDoc.getText());
                        String tipolicencia = tipoLicencia();
                        String observaciones =  campoObservaciones.getText();
                        int idTitular = getIdTitular(campoNroDoc.getText(), tipoDocIngresado());
                        String claseSolicitada = comboClases.getSelectedItem().toString();
                        int edadMinimaClase =  edadMinima();

                        Licencia lic = new Licencia();
                        lic.setNumeroDeLicencia(nroDoc);
                        lic.setFechaDeModificacion(df.parse(fechaOtorgamientoEmision_string));
                        lic.setFechaDeOtorgamiento(df.parse(fechaOtorgamientoEmision_string));
                        lic.setFechaDeVencimiento(df.parse(fechaVencimiento_string));
                        lic.setEnVigencia(true);
                        lic.setCosto(costo);
                        lic.setObservaciones(observaciones);
                        lic.setTipoLicencia(tipoLicencia.valueOf(tipoLicencia()));
                        lic.setTitular(buscarTitularAll(campoNroDoc.getText(),tipoDocIngresado()));

                        Clase cla = new Clase();
                        cla.setEdadMinima(edadMinimaClase);
                        cla.setLicencia(lic);
                        cla.setTipo(tipoClase.valueOf(claseSolicitada).toString());

                        emitirLicencia(lic,cla);
                        JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");
                        refrescarPantalla();
                        generacionIDLicencia();

            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }

            }
                else {
                JOptionPane.showMessageDialog(null, "Revise los campos. No se pudo emitir la licencia.");
            }
});

        botonCancelar.addActionListener(e -> {
            try {
                refrescarPantalla();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        botonNuevoTitular.addActionListener(e -> {
                emitirLicenciaUI.hide();
                darDeAltaTitular darDeAltaTitularUI = new darDeAltaTitular(emitirLicenciaUI);
                darDeAltaTitularUI.show();
                emitirLicenciaUI.hide();
    });

        atrasButton.addActionListener(e -> {
            emitirLicenciaUI.hide();
            frameQueLoEjecuta.show();
        });
    }

    private void refrescarPantalla() throws SQLException {
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
        generacionIDLicencia();
    }

    private boolean validarCampos() {
        boolean valido;
        valido = !campoNombre.getText().isEmpty() && !campoDireccion.getText().isEmpty() && !campoCUIL.getText().isEmpty() && !campoNroDoc.getText().isEmpty() && !campoGrupo.getText().isEmpty() && !campoFechaOtorgamiento.getText().isEmpty() && !campoFechaNacimiento.getText().isEmpty() && !campoTipoDoc.getText().isEmpty() && comboClases.getSelectedIndex() != -1;
        return valido;
    }

    public void seteoCamposTitular(String datosTitularBD) throws ParseException, SQLException {

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
        campoNombre.setText(nombre + " " + apellido);
        campoCUIL.setText(cuil);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaNacimiento_date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);

        campoFechaNacimiento.setText(sdf.format(fechaNacimiento_date));
        campoDireccion.setText(direccion + ", CP: " + codigoPostal);
        campoGrupo.setText(grupoSanguineo);
        donanteDeOrganosCheckBox.setSelected(Integer.parseInt(donanteOrganos) == 1);

        Date fechaActual = new Date();
        campoFechaOtorgamiento.setText(sdf.format(fechaActual));

        if (calcularEdad(campoFechaNacimiento.getText()) >= 17 && calcularEdad(campoFechaNacimiento.getText()) < 21) {
            if (validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado(), "A")) {
                comboClases.removeAllItems();
                comboClases.addItem("B");
                comboClases.addItem("F");
                comboClases.addItem("G");
            }

            comboClases.setSelectedItem(null);
        } else if (calcularEdad(campoFechaNacimiento.getText()) >= 21 && calcularEdad(campoFechaNacimiento.getText()) <= 65) {
            comboClases.removeAllItems();
            if (validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado(), "B")) {
                Date fechaOtorgamiento = getFechaOtorgamiento(campoNroDoc.getText(),"B");
                LocalDate fechaOtorgamiento_localDate = LocalDate.of(fechaOtorgamiento.getYear(), fechaOtorgamiento.getMonth(), fechaOtorgamiento.getDay());
                LocalDate ahora = LocalDate.now();

                System.out.println(fechaOtorgamiento_localDate);
                System.out.println(ahora);

                Period periodo = Period.between(fechaOtorgamiento_localDate, ahora);

                if(periodo.getMonths()>=12){
                    System.out.println(periodo.getMonths());
                }

                } else {

                }
            comboClases.setSelectedItem(null);
        } else if (calcularEdad(campoFechaNacimiento.getText()) > 65) {
            comboClases.removeAllItems();
            if (validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado(), "C")
            ||  validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado(), "D")
            || validarEmisionPorClase(campoBuscarTitular.getText(), tipoDocIngresado(), "E")) {
                    comboClases.addItem("A");
                    comboClases.addItem("B");
                    comboClases.addItem("F");
                    comboClases.addItem("G");
            }
        }
    }

    public String tipoLicencia(){
            String tipoLicencia = "";
            if (Objects.requireNonNull(comboClases.getSelectedItem()).toString().equals("A") || comboClases.getSelectedItem().toString().equals("B")) {
                tipoLicencia = "COMUN";
            } else if (comboClases.getSelectedItem().toString().equals("C") || comboClases.getSelectedItem().toString().equals("D") || comboClases.getSelectedItem().toString().equals("E") ) {
                tipoLicencia = "PROFESIONAL";
            }
            return tipoLicencia;
        }

    public int edadMinima(){
        int edadMinima = 0;
        if (Objects.requireNonNull(comboClases.getSelectedItem()).toString().equals("A") || comboClases.getSelectedItem().toString().equals("B")) {
            edadMinima=17;
        } else if (comboClases.getSelectedItem().toString().equals("C") || comboClases.getSelectedItem().toString().equals("D") || comboClases.getSelectedItem().toString().equals("E") ) {
            edadMinima=21;
        }
        return edadMinima;
    }

    public String tipoDocIngresado(){
            String tipoDocIngresado = null;
            if (comboTipo.getSelectedItem().toString().equals("DNI")) {
                tipoDocIngresado = "DNI";
            } else if (comboTipo.getSelectedItem().toString().equals("LE")) {
                tipoDocIngresado = "LIBRETA_ENROLAMIENTO";
            } else if (comboTipo.getSelectedItem().toString().equals("LC")) {
                tipoDocIngresado = "LIBRETA_CIVICA";
            }
            return tipoDocIngresado;
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
        comboClases.addItem("F");
        comboClases.addItem("G");
        comboClases.setSelectedItem(null);
    }

}
