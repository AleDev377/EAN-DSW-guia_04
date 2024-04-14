package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DialogNombreAlcancia extends JDialog {
    private JTextField textField;
    private String nombre;

    public DialogNombreAlcancia(Frame parent) {
        super(parent, "Alcancia", true);
        initComponents();
    }

    private void initComponents() {
        setSize(300, 120);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Crear el campo de texto
        textField = new JTextField(20);

        // Crear el botón
        JButton button = new JButton("Aceptar");
        button.addActionListener((ActionEvent e) -> {
            nombre = textField.getText();
            dispose(); // Cerrar el diálogo
        });

        // Crear el contenedor y agregar componentes
        JPanel panel = new JPanel();
        panel.add(new JLabel("Nombre Alcancia:"));
        panel.add(textField);
        panel.add(button);

        // Agregar el panel al contenido del diálogo
        getContentPane().add(panel);
    }

    public String obtenerVariable() {
        return nombre;
    }

    public static void main(String[] args) {
        JFrame parentFrame = new JFrame();
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentFrame.setSize(300, 300);
        parentFrame.setVisible(true);

        DialogNombreAlcancia dialog = new DialogNombreAlcancia(parentFrame);
        dialog.setVisible(true);

        String nombreAlcancia = dialog.obtenerVariable();
        System.out.println("Variable capturada: " + nombreAlcancia);
    }
}
