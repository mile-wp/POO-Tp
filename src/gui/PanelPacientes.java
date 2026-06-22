package gui;

import controller.ClinicaController;
import entity.*;
import service.ClinicaException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class PanelPacientes extends JPanel {
    private final ClinicaController controller;
    private JTextField txtNombre, txtApellido, txtDni, txtEmail, txtTelefono;
    private JTextField txtCalle, txtAltura, txtLocalidad, txtProvincia;
    private JComboBox<String> cmbCobertura;
    private JTextField txtDatoCobertura; // Sirve para Obra Social o Tarifa Base
    private JLabel lblDatoCobertura;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;

    public PanelPacientes(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        // Formulario (Izquierda)
        JPanel pnlForm = new JPanel(new GridLayout(12, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar / Editar Paciente"));

        pnlForm.add(new JLabel("Nombre:")); txtNombre = new JTextField(); pnlForm.add(txtNombre);
        pnlForm.add(new JLabel("Apellido:")); txtApellido = new JTextField(); pnlForm.add(txtApellido);
        pnlForm.add(new JLabel("DNI:")); txtDni = new JTextField(); pnlForm.add(txtDni);
        pnlForm.add(new JLabel("Email:")); txtEmail = new JTextField(); pnlForm.add(txtEmail);
        pnlForm.add(new JLabel("Teléfono:")); txtTelefono = new JTextField(); pnlForm.add(txtTelefono);
        pnlForm.add(new JLabel("Calle:")); txtCalle = new JTextField(); pnlForm.add(txtCalle);
        pnlForm.add(new JLabel("Altura:")); txtAltura = new JTextField(); pnlForm.add(txtAltura);
        pnlForm.add(new JLabel("Localidad:")); txtLocalidad = new JTextField(); pnlForm.add(txtLocalidad);
        pnlForm.add(new JLabel("Provincia:")); txtProvincia = new JTextField(); pnlForm.add(txtProvincia);

        pnlForm.add(new JLabel("Cobertura:"));
        cmbCobertura = new JComboBox<>(new String[]{"Particular", "Obra Social"});
        pnlForm.add(cmbCobertura);

        lblDatoCobertura = new JLabel("Tarifa Base:"); pnlForm.add(lblDatoCobertura);
        txtDatoCobertura = new JTextField(); pnlForm.add(txtDatoCobertura);

        // Evento de cambio de cobertura
        cmbCobertura.addActionListener(e -> {
            if (cmbCobertura.getSelectedIndex() == 0) {
                lblDatoCobertura.setText("Tarifa Base:");
            } else {
                lblDatoCobertura.setText("Obra Social (Nombre):");
            }
        });

        JButton btnGuardar = new JButton("Guardar Paciente");
        btnGuardar.addActionListener(e -> registrarPaciente());
        
        JPanel pnlIzquierdo = new JPanel(new BorderLayout());
        pnlIzquierdo.add(pnlForm, BorderLayout.CENTER);
        pnlIzquierdo.add(btnGuardar, BorderLayout.SOUTH);
        add(pnlIzquierdo, BorderLayout.WEST);

        // Tabla (Derecha)
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "DNI"}, 0);
        tablaPacientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);
    }

    private void registrarPaciente() {
        try {
            Domicilio dom = new Domicilio(txtCalle.getText(), txtAltura.getText(), txtLocalidad.getText(), txtProvincia.getText());
            Paciente p;

            if (cmbCobertura.getSelectedIndex() == 0) {
                PacienteParticular particular = new PacienteParticular();
                particular.setTarifaBase(Double.parseDouble(txtDatoCobertura.getText()));
                p = particular;
            } else {
                PacienteObraSocial os = new PacienteObraSocial();
                os.setNombreObraSocial(txtDatoCobertura.getText());
                os.setNumAfiliado("AF-" + txtDni.getText()); // Simplificación para asignación rápida
                p = os;
            }

            p.setNombre(txtNombre.getText());
            p.setApellido(txtApellido.getText());
            p.setDni(txtDni.getText());
            p.setEmail(txtEmail.getText());
            p.setTelefono(txtTelefono.getText());
            p.setFechaIngreso(LocalDate.now());
            p.setDomicilio(dom);

            controller.registrarPaciente(p);
            JOptionPane.showMessageDialog(this, "Paciente registrado con éxito.");
            limpiarFormulario();
            cargarTabla();
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifique los datos ingresados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Paciente p : controller.listarPacientes()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getApellido(), p.getDni()});
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText(""); txtApellido.setText(""); txtDni.setText("");
        txtEmail.setText(""); txtTelefono.setText(""); txtCalle.setText("");
        txtAltura.setText(""); txtLocalidad.setText(""); txtProvincia.setText("");
        txtDatoCobertura.setText("");
    }
}