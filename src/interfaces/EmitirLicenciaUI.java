package src.interfaces;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import src.clases.*;
import src.logica.CalcularCosto;
import src.logica.CalcularVigenciaLicencia;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static src.clases.tipoDeDocumento.tipoDocToString;
import static src.logica.EmitirLicencia.*;

public class EmitirLicenciaUI extends JFrame {
    private JButton botonCancelar;
    private JButton botonNuevoTitular;
    private JButton botonConfirmar;
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
    private JButton atrasButton;
    private JLabel labelBuscarTitular;
    private JTextField campoCodigoPostal;
    private static EmitirLicenciaUI emitirLicenciaUI;
    private Date fechaNacimiento_date;
    ArrayList<String> clasesTitular;

    public EmitirLicenciaUI(MenuPrincipalUI menuPrincipalUI) throws SQLException {
        emitirLicenciaUI = this;
        add(panelEmitirLicencia);
        setTitle("Emitir Licencia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        validacionBuscarTitular();
        inicializarComboTipo();

        campoCodigoPostal.setVisible(false);

        campoObservaciones.setLineWrap(true);
        campoObservaciones.setWrapStyleWord(true);

        botonBuscar.addActionListener(e -> {
            String nroDocIngresado = campoBuscarTitular.getText();
            if (nroDocIngresado.equals("") || nroDocIngresado.length() != 8) {
                JOptionPane.showMessageDialog(null, "Ingrese un Nro. de Documento válido");
            }
            if (comboTipo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de Documento");
            } else {
                String datosTitular = null;
                try {
                    datosTitular = buscarTitular(nroDocIngresado, tipoDocIngresado());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                assert datosTitular != null;
                if (datosTitular.equals("")) {
                    JOptionPane.showMessageDialog(null, "No se pudo encontrar el titular");
                } else {
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

                    int nroDoc = Integer.parseInt(campoNroDoc.getText());
                    String observaciones = campoObservaciones.getText();
                    String claseSolicitada = Objects.requireNonNull(comboClases.getSelectedItem()).toString();
                    int edadMinimaClase = edadMinima(claseSolicitada);

                    Licencia lic = new Licencia();
                    lic.setNumeroDeLicencia(nroDoc);
                    lic.setFechaDeModificacion(df.parse(fechaOtorgamientoEmision_string));
                    lic.setFechaDeOtorgamiento(df.parse(fechaOtorgamientoEmision_string));
                    lic.setFechaDeVencimiento(df.parse(fechaVencimiento_string));
                    lic.setEnVigencia(true);
                    lic.setObservaciones(observaciones);
                    lic.setTipoLicencia(tipoLicencia.valueOf(tipoLicencia()));
                    lic.setTitular(buscarTitularAll(campoNroDoc.getText(), tipoDocIngresado()));
                    lic.getTitular().setDonante(donanteDeOrganosCheckBox.isSelected());

                    Clase cla = new Clase();
                    cla.setEdadMinima(edadMinimaClase);
                    cla.setLicencia(lic);
                    cla.setTipo(tipoClase.valueOf(claseSolicitada).toString());

                    ArrayList<Clase> clases = new ArrayList<>();
                    clases.add(cla);

                    //Se recorren las clases de DB y se setean localmente en Licencia para calcular el costo a partir de estas
                    for(String clases_tipo : clasesTitular)
                    {
                        Clase claseAGuardar = new Clase();
                        claseAGuardar.setEdadMinima(edadMinima(clases_tipo));
                        claseAGuardar.setTipo(tipoClase.valueOf(clases_tipo).toString());
                        claseAGuardar.setLicencia(lic);
                        clases.add(claseAGuardar);
                    }

                    lic.setClases(clases);

                    double costo = CalcularCosto.calcularCostoLicencia(lic, fechaVencimiento_date);
                    lic.setCosto(costo);

                    int idLicenciaInsertado = emitirLicencia(lic, cla, donanteDeOrganosCheckBox.isSelected());

                    JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");

                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Desea imprimir la licencia y el comprobante de pago?", "Imprimir", dialogButton);
                    if(dialogResult == 0) {
                        new ImprimirLicencia(lic, campoFechaNacimiento.getText(), fechaVencimiento_string, campoFechaOtorgamiento.getText(),getClaseByTitular(campoBuscarTitular.getText(), tipoDocIngresado()));
                        new EmitirComprobante(lic, fechaVencimiento_string, campoFechaOtorgamiento.getText(), tipoClase.valueOf(claseSolicitada).toString(), idLicenciaInsertado);
                    } else {

                    }

                    botonCancelar.doClick();

                } catch (SQLException | ParseException | OutputException | BarcodeException throwables) {
                    throwables.printStackTrace();
                }
            } else {
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
            DarDeAltaTitular darDeAltaTitularUI = new DarDeAltaTitular(emitirLicenciaUI);
            darDeAltaTitularUI.show();
            emitirLicenciaUI.hide();
        });

        atrasButton.addActionListener(e -> {
            emitirLicenciaUI.hide();
            menuPrincipalUI.show();
        });
    }

    public EmitirLicenciaUI(MotivoRenovación motivoRenovación, String motivo, String idLicencia) throws SQLException{
        emitirLicenciaUI = this;
        add(panelEmitirLicencia);
        setTitle("Emitir Licencia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        campoObservaciones.setLineWrap(true);
        campoObservaciones.setWrapStyleWord(true);

        Titular titular = getTitularByIdLicencia(idLicencia);
        String tipoClase = getTipoClaseByIdLicencia(idLicencia);
        Licencia licencia = getLicenciaByIdLicencia(idLicencia);

        campoCUIL.setText(titular.getCuil());
        campoDireccion.setText(titular.getDireccion());
        campoCodigoPostal.setText(titular.getCodigoPostal());
        campoGrupo.setText(titular.getGrupoSanguineo());
        campoNombre.setText(titular.getNombre()+" "+titular.getApellido());
        campoTipoDoc.setText(titular.getTipoDoc().toString());
        campoNroDoc.setText(titular.getNumeroDeDocumento());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaActual = new Date();
        campoFechaOtorgamiento.setText(sdf.format(fechaActual));
        campoFechaNacimiento.setText(sdf.format(titular.getFechaDeNacimiento()));

        campoBuscarTitular.setText(titular.getNumeroDeDocumento());
        comboTipo.addItem(tipoDocToString(titular.getTipoDoc()));
        comboTipo.setSelectedIndex(0);

        labelBuscarTitular.setVisible(false);
        campoBuscarTitular.setVisible(false);
        comboTipo.setVisible(false);
        botonBuscar.setVisible(false);
        botonCancelar.setVisible(false);
        botonNuevoTitular.setVisible(false);
        labelTipoClase.setVisible(false);

        comboClases.addItem(tipoClase);
        comboClases.setSelectedIndex(0);
        comboClases.setEnabled(false);
        comboClases.addActionListener(e -> {
            if (comboClases.getSelectedItem() != null) {
                if (comboClases.getSelectedItem().equals("C") || comboClases.getSelectedItem().equals("D") || comboClases.getSelectedItem().equals("E")) {
                    labelTipoClase.setText("Conductor profesional");
                } else if (comboClases.getSelectedItem().equals("A") || comboClases.getSelectedItem().equals("B")) {
                    labelTipoClase.setText("Conductor común");
                }
            }
        });

        campoNombre.setEnabled(false);
        campoTipoDoc.setEnabled(false);
        campoNroDoc.setEnabled(false);
        campoCUIL.setEnabled(false);
        campoFechaNacimiento.setEnabled(false);
        campoFechaOtorgamiento.setEnabled(false);

        if(motivo.equals("VENCIMIENTO")) {
            campoCodigoPostal.setVisible(false);
            campoDireccion.setEnabled(false);
            campoGrupo.setEnabled(false);
        }

        if(motivo.equals("MODIFICACION")){
            campoDireccion.setEditable(true);
            campoCodigoPostal.setEditable(true);
            campoGrupo.setEditable(true);
        }

        botonConfirmar.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    fechaNacimiento_date = new SimpleDateFormat("yyyy-MM-dd").parse(df.format(titular.getFechaDeNacimiento()));

                    Date fechaVencimiento_date = CalcularVigenciaLicencia.calcularVigencia(fechaNacimiento_date, campoNroDoc.getText(), String.valueOf(getIdLicencia()));
                    String fechaVencimiento_string = df.format(fechaVencimiento_date);

                    int nroDoc = Integer.parseInt(campoNroDoc.getText());
                    String observaciones = campoObservaciones.getText();
                    int edadMinimaClase = edadMinima(tipoClase);

                    Licencia lic = new Licencia();
                    lic.setNumeroDeLicencia(nroDoc);
                    lic.setFechaDeModificacion(df.parse(df.format(fechaActual)));
                    lic.setFechaDeOtorgamiento(df.parse(df.format(fechaActual)));
                    lic.setFechaDeVencimiento(df.parse(df.format(fechaVencimiento_date)));
                    lic.setEnVigencia(true);
                    lic.setObservaciones(observaciones);
                    lic.setTipoLicencia(tipoLicencia.valueOf(tipoLicencia()));
                    lic.setTitular(titular);
                    titular.setDonante(donanteDeOrganosCheckBox.isSelected());
                    titular.setDireccion(campoDireccion.getText());
                    titular.setCodigoPostal(campoCodigoPostal.getText());

                    Clase cla = new Clase();
                    cla.setEdadMinima(edadMinimaClase);
                    cla.setLicencia(lic);
                    cla.setTipo(tipoClase);

                    ArrayList<Clase> clases = new ArrayList<>();
                    clases.add(cla);

                    clasesTitular = getClaseByTitular(campoBuscarTitular.getText(), tipoDocIngresado());

                    //Se recorren las clases de DB y se setean localmente en Licencia para calcular el costo a partir de estas
                    for (String clases_tipo : clasesTitular) {
                        Clase claseAGuardar = new Clase();
                        claseAGuardar.setEdadMinima(edadMinima(clases_tipo));
                        claseAGuardar.setTipo(tipoClase);
                        claseAGuardar.setLicencia(lic);
                        clases.add(claseAGuardar);
                    }

                    lic.setClases(clases);

                    double costo = CalcularCosto.calcularCostoLicencia(lic, fechaVencimiento_date);
                    lic.setCosto(costo);

                    int idLicenciaRenovado = renovarLicencia(lic, cla, titular);

                    JOptionPane.showMessageDialog(null, "Licencia emitida correctamente.");

                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(this, "¿Desea imprimir la licencia y el comprobante de pago?", "Imprimir", dialogButton);
                    if (dialogResult == 0) {
                        new ImprimirLicencia(lic, campoFechaNacimiento.getText(), fechaVencimiento_string, campoFechaOtorgamiento.getText(), getClaseByTitular(campoBuscarTitular.getText(), tipoDocIngresado()));
                        new EmitirComprobante(lic,fechaVencimiento_string, campoFechaOtorgamiento.getText(), tipoClase, idLicenciaRenovado);
                    } else {

                    }

                    botonConfirmar.setEnabled(false);

                } catch (SQLException | ParseException | OutputException | BarcodeException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Revise los campos. No se pudo emitir la licencia.");
            }
        });

        atrasButton.addActionListener(e -> {
            emitirLicenciaUI.hide();
            motivoRenovación.show();
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

        clasesTitular = getClaseByTitular(campoBuscarTitular.getText(), tipoDocIngresado());

        //Si tiene entre 17 y 21 años solo puede sacar Licencias Comunes
        if (calcularEdad(campoFechaNacimiento.getText()) >= 17 && calcularEdad(campoFechaNacimiento.getText()) < 21) {
            if (clasesTitular.isEmpty()) {                                  //Tipo A,B,F,G si no tiene ninguna licencia
                comboClases.removeAllItems();
                comboClases.addItem("A");
                comboClases.addItem("B");
                comboClases.addItem("F");
                comboClases.addItem("G");
            }

            comboClases.removeAllItems();

            if (!clasesTitular.contains("A")) {
                comboClases.addItem("A");
            }

            if (!clasesTitular.contains("B")) {
                comboClases.addItem("B");
            }

            if (!clasesTitular.contains("G")) {
                comboClases.addItem("G");
            }

            if (clasesTitular.contains("F")) {
                comboClases.removeAllItems();
            } else {
                comboClases.addItem("F");
            }

        }

        //Si tiene entre 21 y 65 años puede sacar licencias profesionales si tiene una de clase B un año antes
        else if (calcularEdad(campoFechaNacimiento.getText()) >= 21 && calcularEdad(campoFechaNacimiento.getText()) < 65) {
            comboClases.removeAllItems();

            if (!clasesTitular.contains("A")) {
                comboClases.addItem("A");
            }

            if (!clasesTitular.contains("G")) {
                comboClases.addItem("G");
            }

            if (clasesTitular.contains("F")) {
                comboClases.removeAllItems();
                return;
            } else {
                comboClases.addItem("F");
            }

            if (!clasesTitular.contains("B")) {
                comboClases.addItem("B");
            } else {
                Calendar inicio = new GregorianCalendar();
                Calendar fin = new GregorianCalendar();
                SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy-MM-dd");

                String fechaOtorgamiento = getFechaOtorgamiento(campoNroDoc.getText(), "B");

                inicio.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(fechaOtorgamiento));
                fin.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(sdfBD.format(fechaActual)));

                int difAnios = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
                int difMeses = difAnios * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH);

                //Se le emitió una licencia de tipo B al menos un año antes
                if (difMeses >= 12) {
                    if (!clasesTitular.contains("C")) {
                        comboClases.addItem("C");
                    }

                    if (!clasesTitular.contains("D")) {
                        comboClases.addItem("D");
                    }

                    if (!clasesTitular.contains("E")) {
                        comboClases.addItem("E");
                    }
                }

                }
            }   //Si tiene mas de 65 años solo puede sacar una licencia profesional si se le emitio alguna antes
                else if (calcularEdad(campoFechaNacimiento.getText()) >= 65) {
                comboClases.removeAllItems();

                if (!clasesTitular.contains("A")) {
                    comboClases.addItem("A");
                }
                if (!clasesTitular.contains("B")) {
                    comboClases.addItem("B");
                }
                if (!clasesTitular.contains("G")) {
                    comboClases.addItem("G");
                }
                if (clasesTitular.contains("F")) {
                    comboClases.removeAllItems();
                    return;
                } else {
                    comboClases.addItem("F");
                }

                if (clasesTitular.contains("C")) {
                    comboClases.addItem("D");
                    comboClases.addItem("E");
                } else if (clasesTitular.contains("D")){
                    comboClases.addItem("E");
                }


        }
    }

    public String tipoLicencia(){
            String tipoLicencia = "";
            if (Objects.requireNonNull(comboClases.getSelectedItem()).toString().equals("A") || comboClases.getSelectedItem().toString().equals("B") || comboClases.getSelectedItem().toString().equals("F") || comboClases.getSelectedItem().toString().equals("G")) {
                tipoLicencia = "COMUN";
            } else if (comboClases.getSelectedItem().toString().equals("C") || comboClases.getSelectedItem().toString().equals("D") || comboClases.getSelectedItem().toString().equals("E") ) {
                tipoLicencia = "PROFESIONAL";
            }
            return tipoLicencia;
        }

    public int edadMinima(String tipoClase){
        int edadMinima = 0;
        if (tipoClase.equals("A") || tipoClase.equals("B") || tipoClase.equals("G") || tipoClase.equals("F")) {
            edadMinima=17;
        } else if (tipoClase.equals("C") || tipoClase.equals("D") || tipoClase.equals("E") ) {
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

    public void validacionBuscarTitular(){
        campoBuscarTitular.setDocument(new CampoLimitado(8));
        campoBuscarTitular.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                try{
                    int i = Integer.parseInt(campoBuscarTitular.getText());
                } catch (NumberFormatException ex){
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
