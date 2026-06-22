package gui;

import controller.ClinicaController;
import entity.*;
import service.ClinicaException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelOdontologos extends JPanel {
    private final ClinicaController controller;
    private JTextField txtNombre, txtApellido, txtDni, txtMatricula, txtRecargo;
    private JComboBox<String> cmbEspecialidad;
    private JTable tablaOdontologos;
    private DefaultTableModel modeloTabla;

    public PanelOdontologos(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar Odontólogo"));

        pnlForm.add(new JLabel("Nombre:")); txtNombre = new JTextField(); pnlForm.add(txtNombre);
        pnlForm.add(new JLabel("Apellido:")); txtApellido = new JTextField(); pnlForm.add(txtApellido);
        pnlForm.add(new JLabel("DNI:")); txtDni = new JTextField(); pnlForm.add(txtDni);
        pnlForm.add(new JLabel("Matrícula:")); txtMatricula = new JTextField(); pnlForm.add(txtMatricula);
        
        pnlForm.add(new JLabel("Especialidad:"));
        cmbEspecialidad = new JComboBox<>(new String[]{"Ortodoncia", "Endodoncia", "Extracción"});
        pnlForm.add(cmbEspecialidad);

        pnlForm.add(new JLabel("Recargo Especialidad:")); txtRecargo = new JTextField(); pnlForm.add(txtRecargo);

        JButton btnGuardar = new JButton("Guardar Odontólogo");
        btnGuardar.addActionListener(e -> registrarOdontologo());

        JPanel pnlIzquierdo = new JPanel(new BorderLayout());
        pnlIzquierdo.add(pnlForm, BorderLayout.CENTER);
        pnlIzquierdo.add(btnGuardar, BorderLayout.SOUTH);
        add(pnlIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Matrícula"}, 0);
        tablaOdontologos = new JTable(modeloTabla);
        add(new JScrollPane(tablaOdontologos), BorderLayout.CENTER);
    }

    private void registrarOdontologo() {
        try {
            Double recargo = Double.parseDouble(txtRecargo.getText());
            Odontologo o = switch (cmbEspecialidad.getSelectedIndex()) {
                case 0 -> { OdOrtodoncia od = new OdOrtodoncia(); od.setRecargoEspecialidad(recargo); yield od; }
                case 1 -> { OdEndodoncia od = new OdEndodoncia(); od.setRecargoEspecialidad(recargo); yield od; }
                case 2 -> { OdExtraccion od = new OdExtraccion(); od.setRecargoEspecialidad(recargo); yield od; }
                default -> null;
            };

            if (o != null) {
                o.setNombre(txtNombre.getText());
                o.setApellido(txtApellido.getText());
                o.setDni(txtDni.getText());
                o.setMatricula(txtMatricula.getText());

                controller.registrarOdontologo(o);
                JOptionPane.showMessageDialog(this, "Odontólogo registrado con éxito.");
                limpiarFormulario();
                cargarTabla();
            }
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Odontologo o : controller.listarOdontologos()) {
            modeloTabla.addRow(new Object[]{o.getId(), o.getNombre(), o.getApellido(), o.getMatricula()});
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText(""); txtApellido.setText(""); txtDni.setText(""); txtMatricula.setText(""); txtRecargo.setText("");
    }
}