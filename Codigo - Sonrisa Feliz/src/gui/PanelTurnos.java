package gui;

import controller.ClinicaController;
import entity.*;
import service.ClinicaException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PanelTurnos extends JPanel {
    private final ClinicaController controller;
    private JTextField txtIdPaciente, txtIdOdontologo, txtFecha, txtHora;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;

    public PanelTurnos(ClinicaController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Agendar Nuevo Turno"));

        pnlForm.add(new JLabel("ID Paciente:")); txtIdPaciente = new JTextField(); pnlForm.add(txtIdPaciente);
        pnlForm.add(new JLabel("ID Odontólogo:")); txtIdOdontologo = new JTextField(); pnlForm.add(txtIdOdontologo);
        pnlForm.add(new JLabel("Fecha (AAAA-MM-DD):")); txtFecha = new JTextField(); pnlForm.add(txtFecha);
        pnlForm.add(new JLabel("Hora (HH:MM):")); txtHora = new JTextField(); pnlForm.add(txtHora);

        JButton btnAgendar = new JButton("Agendar Turno");
        btnAgendar.addActionListener(e -> agendarTurno());

        JPanel pnlIzquierdo = new JPanel(new BorderLayout());
        pnlIzquierdo.add(pnlForm, BorderLayout.CENTER);
        pnlIzquierdo.add(btnAgendar, BorderLayout.SOUTH);
        add(pnlIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Fecha", "Hora", "Paciente", "Odontólogo", "Monto / Cobertura"}, 0);
        tablaTurnos = new JTable(modeloTabla);
        add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);
    }

    private void agendarTurno() {
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
            t.setPaciente(pacOpt.get());
            t.setOdontologo(odoOpt.get());
            t.setFecha(LocalDate.parse(txtFecha.getText()));
            t.setHora(LocalTime.parse(txtHora.getText()));

            Turno registrado = controller.agendarTurno(t);
            if (registrado != null) {
                String mensajeFactura = (registrado.getMontoFacturacion() == 0.0) 
                        ? "Cubierto por Obra Social" 
                        : "Abonar en caja: $" + registrado.getMontoFacturacion();
                
                JOptionPane.showMessageDialog(this, "Turno Agendado!\n" + mensajeFactura, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla();
            }
        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Regala de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en formato de datos (Fecha/Hora/IDs).", "Error", JOptionPane.ERROR_MESSAGE);
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
                cobro
            });
        }
    }
}