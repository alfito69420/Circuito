import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JPanel {
    private java.util.List<Rectangle> resistors;
    private String circuitType;

    public Main() {
        resistors = new ArrayList<>();
        circuitType = "Paralelo"; // Valor predeterminado
    }

    public void setCircuitType(String type) {
        this.circuitType = type;
    }

    public void addResistors(int count) {
        resistors.clear();
        int x = 50, y = 50, width = 100, height = 20, spacing = 50;

        for (int i = 0; i < count; i++) {
            if (circuitType.equals("Paralelo")) {
                resistors.add(new Rectangle(x, y + (i * spacing), width, height));
            } else {
                resistors.add(new Rectangle(x + (i * (width + 20)), y, width, height));
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int startX = 30, endX = 250;

        if (circuitType.equals("Paralelo")) {
            // Dibuja las resistencias en paralelo
            for (Rectangle resistor : resistors) {
                int centerY = resistor.y + resistor.height / 2;
                g2d.drawRect(resistor.x, resistor.y, resistor.width, resistor.height);
                g2d.drawString("R", resistor.x + resistor.width / 2 - 5, centerY + 5);
                g2d.drawLine(startX, centerY, resistor.x, centerY);
                g2d.drawLine(resistor.x + resistor.width, centerY, endX, centerY);
            }

            // Dibuja las líneas verticales
            if (!resistors.isEmpty()) {
                int firstY = resistors.get(0).y + resistors.get(0).height / 2;
                int lastY = resistors.get(resistors.size() - 1).y + resistors.get(resistors.size() - 1).height / 2;
                g2d.drawLine(startX, firstY, startX, lastY);
                g2d.drawLine(endX, firstY, endX, lastY);
            }

            // Dibuja el LED
            if (!resistors.isEmpty()) {
                Rectangle lastResistor = resistors.get(resistors.size() - 1);
                int ledX = endX + 40, ledY = (resistors.get(0).y + lastResistor.y) / 2;
                int ledRadius = 10;
                g2d.drawOval(ledX, ledY, ledRadius * 2, ledRadius * 2);
                g2d.drawLine(ledX + ledRadius, ledY, ledX + ledRadius, ledY - 20);
                g2d.drawLine(ledX + ledRadius, ledY + ledRadius * 2, ledX + ledRadius, ledY + ledRadius * 2 + 20);
                g2d.drawString("+", ledX + ledRadius - 3, ledY - 10);
                g2d.drawString("-", ledX + ledRadius - 3, ledY + ledRadius * 2 + 30);

                g2d.drawLine(endX, ledY + ledRadius, ledX, ledY + ledRadius);
                g2d.drawLine(ledX + ledRadius * 2, ledY + ledRadius, ledX + ledRadius * 2 + 20, ledY + ledRadius);
            }
        } else {
            // Dibuja las resistencias en serie
            for (Rectangle resistor : resistors) {
                int centerY = resistor.y + resistor.height / 2;
                g2d.drawRect(resistor.x, resistor.y, resistor.width, resistor.height);
                g2d.drawString("R", resistor.x + resistor.width / 2 - 5, centerY + 5);
                g2d.drawLine(resistor.x - 20, centerY, resistor.x, centerY);
                g2d.drawLine(resistor.x + resistor.width, centerY, resistor.x + resistor.width + 20, centerY);
            }

            // Dibuja el LED al final de las resistencias en serie
            if (!resistors.isEmpty()) {
                Rectangle lastResistor = resistors.get(resistors.size() - 1);
                int ledX = lastResistor.x + lastResistor.width + 40, ledY = lastResistor.y + lastResistor.height / 2 - 10;
                int ledRadius = 10;
                g2d.drawOval(ledX, ledY, ledRadius * 2, ledRadius * 2);
                g2d.drawLine(ledX + ledRadius, ledY, ledX + ledRadius, ledY - 20);
                g2d.drawLine(ledX + ledRadius, ledY + ledRadius * 2, ledX + ledRadius, ledY + ledRadius * 2 + 20);
                g2d.drawString("+", ledX + ledRadius - 3, ledY - 10);
                g2d.drawString("-", ledX + ledRadius - 3, ledY + ledRadius * 2 + 30);

                g2d.drawLine(lastResistor.x + lastResistor.width + 20, lastResistor.y + lastResistor.height / 2, ledX, lastResistor.y + lastResistor.height / 2);
                g2d.drawLine(ledX + ledRadius * 2, lastResistor.y + lastResistor.height / 2, ledX + ledRadius * 2 + 20, lastResistor.y + lastResistor.height / 2);
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Diagrama de Circuito");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main circuitDiagram = new Main();

        // Panel para ingresar número de resistencias y tipo de circuito
        JPanel controlPanel = new JPanel();
        JTextField resistorCountField = new JTextField(5);
        JButton drawButton = new JButton("Dibujar");
        JComboBox<String> circuitTypeBox = new JComboBox<>(new String[]{"Paralelo", "Serie"});

        controlPanel.add(new JLabel("Número de Resistencias:"));
        controlPanel.add(resistorCountField);
        controlPanel.add(new JLabel("Tipo de Circuito:"));
        controlPanel.add(circuitTypeBox);
        controlPanel.add(drawButton);

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = Integer.parseInt(resistorCountField.getText());
                    if (count > 0) {
                        String selectedType = (String) circuitTypeBox.getSelectedItem();
                        circuitDiagram.setCircuitType(selectedType);
                        circuitDiagram.addResistors(count);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(circuitDiagram, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}