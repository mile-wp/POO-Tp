package gui;

import controller.ClinicaController;
import entity.*;
import service.ClinicaException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class PanelTurnos extends JPanel {
    private final ClinicaController controller;
    private JTextField txtIdPaciente, txtIdOdontologo;
    private JSpinner spinnerFecha;
    private JSpinner spinnerHora; // Nuevo selector gráfico para la hora
    private JComboBox<EstadoTurno> cmbEstado;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;
    private Long idTurnoSeleccionado = null;

    public PanelTurnos(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Datos del Turno"));

        pnlForm.add(new JLabel("ID Paciente:")); txtIdPaciente = new JTextField(); pnlForm.add(txtIdPaciente);
        pnlForm.add(new JLabel("ID Odontólogo:")); txtIdOdontologo = new JTextField(); pnlForm.add(txtIdOdontologo);

        // Selector de fecha gráfico
        pnlForm.add(new JLabel("Fecha del Turno:"));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd-MM-yyyy");
        spinnerFecha.setEditor(dateEditor);
        pnlForm.add(spinnerFecha);

        // Selector de hora gráfico (Configurado solo para HH:mm)
        pnlForm.add(new JLabel("Hora del Turno:"));
        spinnerHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(timeEditor);
        pnlForm.add(spinnerHora);

        // Combo Box usando los valores reales del Enum EstadoTurno
        pnlForm.add(new JLabel("Estado:"));
        cmbEstado = new JComboBox<>(EstadoTurno.values());
        pnlForm.add(cmbEstado);

        JButton btnGuardar = new JButton("Guardar / Actualizar");
        btnGuardar.addActionListener(e -> guardarTurno());

        JButton btnLimpiar = new JButton("Limpiar / Nuevo");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        pnlBotones.add(btnGuardar); pnlBotones.add(btnLimpiar);

        JPanel pnlIzquierdo = new JPanel(new BorderLayout());
        pnlIzquierdo.add(pnlForm, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlBotones, BorderLayout.SOUTH);
        add(pnlIzquierdo, BorderLayout.WEST);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Fecha", "Hora", "Paciente", "Odontólogo", "Estado", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaTurnos = new JTable(modeloTabla);

        // Listener para cargar el turno seleccionado de la tabla al formulario
        tablaTurnos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaTurnos.getSelectedRow();
            if (fila != -1 && !e.getValueIsAdjusting()) {
                Long id = (Long) tablaTurnos.getValueAt(fila, 0);
                controller.listarTurnos().stream()
                        .filter(t -> t.getId().equals(id))
                        .findFirst().ifPresent(this::cargarTurnoEnFormulario);
            }
        });

        add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);
    }

    private void cargarTurnoEnFormulario(Turno t) {
        idTurnoSeleccionado = t.getId();
        txtIdPaciente.setText(String.valueOf(t.getPaciente().getId()));
        txtIdOdontologo.setText(String.valueOf(t.getOdontologo().getId()));
        cmbEstado.setSelectedItem(t.getEstado());

        // Mapear LocalDate al JSpinner de Fecha
        Date date = Date.from(t.getFecha().atStartOfDay(ZoneId.systemDefault()).toInstant());
        spinnerFecha.setValue(date);

        // Mapear LocalTime al JSpinner de Hora
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, t.getHora().getHour());
        cal.set(Calendar.MINUTE, t.getHora().getMinute());
        spinnerHora.setValue(cal.getTime());
    }

    private void guardarTurno() {
        try {
            Long idPac = Long.parseLong(txtIdPaciente.getText());
            var pacOpt = controller.buscarPacienteId(idPac);
            if (pacOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long idOdo = Long.parseLong(txtIdOdontologo.getText());
            var odoOpt = controller.buscarOdontologoId(idOdo);
            if (odoOpt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Odontólogo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Turno t = new Turno();
            if (idTurnoSeleccionado != null) {
                t.setId(idTurnoSeleccionado);
            }
            t.setPaciente(pacOpt.get());
            t.setOdontologo(odoOpt.get());

            // Convertir JSpinner Fecha a LocalDate
            Date dateValue = (Date) spinnerFecha.getValue();
            LocalDate fecha = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            t.setFecha(fecha);

            // Convertir JSpinner Hora a LocalTime
            Date timeValue = (Date) spinnerHora.getValue();
            LocalTime hora = timeValue.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            t.setHora(hora);

            t.setEstado((EstadoTurno) cmbEstado.getSelectedItem());

            Turno registrado = controller.agendarTurno(t);
            if (registrado != null) {
                String mensajeFactura = (registrado.getMontoFacturacion() == 0.0)
                        ? "Cubierto por Obra Social"
                        : "Abonar en caja: $" + registrado.getMontoFacturacion();

                JOptionPane.showMessageDialog(this, "✅ Turno procesado con éxito!\n" + mensajeFactura, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTabla();
            }
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Regla de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifique que los IDs ingresados sean correctos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Turno t : controller.listarTurnos()) {
            String cobro = (t.getMontoFacturacion() == null || t.getMontoFacturacion() == 0.0)
                    ? "Obra Social"
                    : "$" + t.getMontoFacturacion();

            modeloTabla.addRow(new Object[]{
                    t.getId(),
                    t.getFecha(),
                    t.getHora(),
                    t.getPaciente().getApellido(),
                    t.getOdontologo().getApellido(),
                    t.getEstado(),
                    cobro
            });
        }
    }

    private void limpiarFormulario() {
        idTurnoSeleccionado = null;
        txtIdPaciente.setText(""); txtIdOdontologo.setText("");
        spinnerFecha.setValue(new Date());
        spinnerHora.setValue(new Date()); // Resetea a la hora actual
        cmbEstado.setSelectedIndex(0);
        tablaTurnos.clearSelection();
    }
}