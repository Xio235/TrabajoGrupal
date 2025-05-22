
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazGrafica extends JFrame {
    private ListaDobleAgentes lista = new ListaDobleAgentes();
    private JPanel panelPrincipal;
    private JPanel panelRegistro;
    private JTextField txtId, txtNombre, txtMision, txtNivel, txtPago;
    private JTextArea areaResultados;
    private CardLayout cardLayout;
    private boolean modoEdicion = false;
    private JButton btnGuardar;

    public InterfazGrafica() {
        setTitle("Gestión de Agentes de Seguridad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        // Panel del menú principal
        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnRegistrar = new JButton("Registrar Agente");
        JButton btnModificar = new JButton("Modificar Agente");
        JButton btnListar = new JButton("Listar Agentes");
        JButton btnInforme = new JButton("Informe Financiero");

        panelMenu.add(btnRegistrar);
        panelMenu.add(btnModificar);
        panelMenu.add(btnListar);
        panelMenu.add(btnInforme);

        // Panel de registro/edición
        panelRegistro = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        formulario.add(txtId);

        formulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formulario.add(txtNombre);

        formulario.add(new JLabel("Misión:"));
        txtMision = new JTextField();
        formulario.add(txtMision);

        formulario.add(new JLabel("Nivel de peligrosidad (1-5):"));
        txtNivel = new JTextField();
        formulario.add(txtNivel);

        formulario.add(new JLabel("Pago mensual:"));
        txtPago = new JTextField();
        formulario.add(txtPago);

        btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver al Menú");
        formulario.add(btnGuardar);
        formulario.add(btnVolver);

        panelRegistro.add(formulario, BorderLayout.CENTER);

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(areaResultados);

        // Panel para listar y mostrar informes
        JPanel panelResultados = new JPanel(new BorderLayout());
        panelResultados.add(scrollResultados, BorderLayout.CENTER);
        JButton btnVolverResultados = new JButton("Volver al Menú");
        panelResultados.add(btnVolverResultados, BorderLayout.SOUTH);

        // Agregar paneles al CardLayout
        panelPrincipal.add(panelMenu, "menu");
        panelPrincipal.add(panelRegistro, "registro");
        panelPrincipal.add(panelResultados, "resultados");

        add(panelPrincipal);

        // Eventos
        btnRegistrar.addActionListener(e -> {
            modoEdicion = false;
            txtId.setEditable(true);
            limpiarCampos();
            btnGuardar.setText("Registrar");
            cardLayout.show(panelPrincipal, "registro");
        });

        btnVolver.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "menu");
            limpiarCampos();
        });

        btnVolverResultados.addActionListener(e -> cardLayout.show(panelPrincipal, "menu"));

        btnGuardar.addActionListener(e -> {
            if (modoEdicion) {
                modificarAgenteActual();
            } else {
                guardarNuevoAgente();
            }
        });

        btnListar.addActionListener(e -> {
            listarAgentes();
            cardLayout.show(panelPrincipal, "resultados");
        });

        btnInforme.addActionListener(e -> {
            mostrarInforme();
            cardLayout.show(panelPrincipal, "resultados");
        });

        btnModificar.addActionListener(e -> buscarParaModificar());

        setVisible(true);
    }

    private void buscarParaModificar() {
        String idBuscar = JOptionPane.showInputDialog("Ingrese el ID del agente a modificar:");
        if (idBuscar != null && !idBuscar.isEmpty()) {
            Agente agente = buscarAgentePorId(idBuscar);
            if (agente != null) {
                modoEdicion = true;
                cargarDatosAgente(agente);
                txtId.setEditable(false);
                btnGuardar.setText("Modificar");
                cardLayout.show(panelPrincipal, "registro");
            } else {
                JOptionPane.showMessageDialog(this,
                        "No existe un agente con el ID: " + idBuscar,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Agente buscarAgentePorId(String id) {
        for (Agente agente : lista.listar()) {
            if (agente.getId().equals(id)) {
                return agente;
            }
        }
        return null;
    }

    private void cargarDatosAgente(Agente agente) {
        txtId.setText(agente.getId());
        txtNombre.setText(agente.getNombre());
        txtMision.setText(agente.getMision());
        txtNivel.setText(String.valueOf(agente.getPeligrosidad()));
        txtPago.setText(String.valueOf(agente.getPagoMensual()));
    }

    private void guardarNuevoAgente() {
        try {
            validarCampos();
            Agente nuevo = crearAgenteDesdeFormulario();

            // Verificar si ya existe un agente con ese ID
            if (buscarAgentePorId(nuevo.getId()) != null) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe un agente con el ID: " + nuevo.getId(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            lista.agregarAgente(nuevo);
            JOptionPane.showMessageDialog(this, "Agente registrado exitosamente");
            limpiarCampos();
            cardLayout.show(panelPrincipal, "menu");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarAgenteActual() {
        try {
            validarCampos();
            Agente datosNuevos = crearAgenteDesdeFormulario();
            if (lista.modificarAgente(datosNuevos.getId(), datosNuevos)) {
                JOptionPane.showMessageDialog(this, "Agente modificado exitosamente");
                limpiarCampos();
                cardLayout.show(panelPrincipal, "menu");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarCampos() throws Exception {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtMision.getText().isEmpty() || txtNivel.getText().isEmpty() ||
                txtPago.getText().isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        int nivel = Integer.parseInt(txtNivel.getText());
        if (nivel < 1 || nivel > 5) {
            throw new Exception("El nivel debe estar entre 1 y 5");
        }
    }

    private Agente crearAgenteDesdeFormulario() throws Exception {
        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String mision = txtMision.getText();
            int nivel = Integer.parseInt(txtNivel.getText());
            double pago = Double.parseDouble(txtPago.getText());

            return new Agente(id, nombre, mision, nivel, pago);
        } catch (NumberFormatException e) {
            throw new Exception("Por favor, ingrese valores numéricos válidos");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtMision.setText("");
        txtNivel.setText("");
        txtPago.setText("");
    }

    private void listarAgentes() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Agentes:\n\n");
        for (Agente a : lista.listar()) {
            sb.append("ID: ").append(a.getId())
                    .append(", Nombre: ").append(a.getNombre())
                    .append(", Misión: ").append(a.getMision())
                    .append(", Peligrosidad: ").append(a.getPeligrosidad())
                    .append(", Pago: $").append(a.getPagoMensual()).append("\n");
        }
        areaResultados.setText(sb.toString());
    }

    private void mostrarInforme() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informe Financiero:\n\n");
        for (Agente a : lista.listar()) {
            sb.append(a.getResumen()).append("\n");
        }
        areaResultados.setText(sb.toString());
    }
}