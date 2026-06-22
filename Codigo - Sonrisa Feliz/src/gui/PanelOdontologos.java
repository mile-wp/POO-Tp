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
    private Long idOdontologoSeleccionado = null; // Almacena el ID en caso de edición

    public PanelOdontologos(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JPanel pnlForm = new JPanel(new GridLayout(7, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Registrar / Editar Odontólogo"));

        pnlForm.add(new JLabel("Nombre:")); txtNombre = new JTextField(); pnlForm.add(txtNombre);
        pnlForm.add(new JLabel("Apellido:")); txtApellido = new JTextField(); pnlForm.add(txtApellido);
        pnlForm.add(new JLabel("DNI:")); txtDni = new JTextField(); pnlForm.add(txtDni);
        pnlForm.add(new JLabel("Matrícula:")); txtMatricula = new JTextField(); pnlForm.add(txtMatricula);

        pnlForm.add(new JLabel("Especialidad:"));
        cmbEspecialidad = new JComboBox<>(new String[]{"Ortodoncia", "Endodoncia", "Extracción"});
        pnlForm.add(cmbEspecialidad);

        pnlForm.add(new JLabel("Recargo Especialidad:")); txtRecargo = new JTextField(); pnlForm.add(txtRecargo);

        JButton btnGuardar = new JButton("Guardar Odontólogo");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardarOdontologo();
            }
        });

        JButton btnModificar = new JButton("Modificar Odontólogo");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                modificarOdontologo();
            }
        });

        JButton btnLimpiar = new JButton("Limpiar Formulario");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                limpiarFormulario();
            }
        });

        JPanel pnlBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnLimpiar);

        JPanel pnlIzquierdo = new JPanel(new BorderLayout());
        pnlIzquierdo.add(pnlForm, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlBotones, BorderLayout.SOUTH);
        add(pnlIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido", "Matrícula"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaOdontologos = new JTable(modeloTabla);

        // Captura de clic tradicional en la tabla sin lambdas
        tablaOdontologos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaOdontologos.getSelectedRow();
                if (fila != -1) {
                    idOdontologoSeleccionado = (Long) tablaOdontologos.getValueAt(fila, 0);

                    java.util.Optional<entity.Odontologo> op = controller.buscarOdontologoId(idOdontologoSeleccionado);
                    if (op.isPresent()) {
                        entity.Odontologo o = op.get();
                        cargarOdontologoEnFormulario(o);
                    }
                }
            }
        });

        add(new JScrollPane(tablaOdontologos), BorderLayout.CENTER);
    } // Cierre correcto de inicializarComponentes()

    private void modificarOdontologo() {
        if (idOdontologoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un odontólogo de la tabla.");
            return;
        }
        try {
            Double recargo = Double.parseDouble(txtRecargo.getText());
            Odontologo o;

            switch (cmbEspecialidad.getSelectedIndex()) {
                case 0:
                    o = new entity.OdOrtodoncia();
                    break;
                case 1:
                    o = new entity.OdEndodoncia();
                    break;
                default:
                    o = new entity.OdExtraccion();
                    break;
            }

            o.setId(idOdontologoSeleccionado);
            o.setNombre(txtNombre.getText());
            o.setApellido(txtApellido.getText());
            o.setDni(txtDni.getText());
            o.setMatricula(txtMatricula.getText());
            o.setRecargoEspecialidad(recargo);

            controller.modificarOdontologo(o);

            JOptionPane.showMessageDialog(this, "Odontólogo modificado con éxito.");
            limpiarFormulario();
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage());
        }
    }

    private void cargarOdontologoEnFormulario(Odontologo o) {
        idOdontologoSeleccionado = o.getId();
        txtNombre.setText(o.getNombre());
        txtApellido.setText(o.getApellido());
        txtDni.setText(o.getDni());
        txtMatricula.setText(o.getMatricula());
        txtRecargo.setText(String.valueOf(o.getRecargoEspecialidad()));

        if (o instanceof entity.OdOrtodoncia) {
            cmbEspecialidad.setSelectedIndex(0);
        } else if (o instanceof entity.OdEndodoncia) {
            cmbEspecialidad.setSelectedIndex(1);
        } else if (o instanceof entity.OdExtraccion) {
            cmbEspecialidad.setSelectedIndex(2);
        }
    }

    private void guardarOdontologo() {
        try {
            Odontologo o;
            switch (cmbEspecialidad.getSelectedIndex()) {
                case 0:
                    o = new OdOrtodoncia();
                    break;
                case 1:
                    o = new OdEndodoncia();
                    break;
                default:
                    o = new OdExtraccion();
                    break;
            }

            if (idOdontologoSeleccionado != null) {
                o.setId(idOdontologoSeleccionado);
            }
            o.setNombre(txtNombre.getText());
            o.setApellido(txtApellido.getText());
            o.setDni(txtDni.getText());
            o.setMatricula(txtMatricula.getText());
            o.setRecargoEspecialidad(Double.parseDouble(txtRecargo.getText()));

            controller.registrarOdontologo(o);
            JOptionPane.showMessageDialog(this, "Odontólogo guardado con éxito.");
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
        for (Odontologo o : controller.listarOdontologos()) {
            modeloTabla.addRow(new Object[]{o.getId(), o.getNombre(), o.getApellido(), o.getMatricula()});
        }
    }

    private void limpiarFormulario() {
        idOdontologoSeleccionado = null;
        txtNombre.setText(""); txtApellido.setText(""); txtDni.setText("");
        txtMatricula.setText(""); txtRecargo.setText("");
        cmbEspecialidad.setSelectedIndex(0);
        tablaOdontologos.clearSelection();
    }
}