import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Triqui3 extends JFrame implements ActionListener {
    private JButton[][] buttons;
    private JLabel statusBar;
    private String jugador1;
    private String jugador2;
    private String turnoActual;
    private int dimensionTablero;

    public Triqui3(String jugador1, String jugador2, int dimensionTablero) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.dimensionTablero = dimensionTablero;
        this.turnoActual = jugador1;

       
        setTitle("Triqui");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel tableroPanel = new JPanel();
        tableroPanel.setLayout(new GridLayout(dimensionTablero, dimensionTablero));
        buttons = new JButton[dimensionTablero][dimensionTablero];
        for (int i = 0; i < dimensionTablero; i++) {
            for (int j = 0; j < dimensionTablero; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(this);
                tableroPanel.add(buttons[i][j]);
            }
        }
        add(tableroPanel, BorderLayout.CENTER);

        
        statusBar = new JLabel(jugador1 + " es el turno", JLabel.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton botonPresionado = (JButton) e.getSource();
        if (!botonPresionado.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Casilla ocupada, intenta en otra.");
            return;
        }
        botonPresionado.setText(turnoActual);
        if (hayGanador()) {
            JOptionPane.showMessageDialog(this, "¡" + turnoActual + " ha ganado!");
            reiniciarJuego();
        } else if (tableroLLeno()) {
            JOptionPane.showMessageDialog(this, "¡Empate!");
            reiniciarJuego();
        } else {
            turnoActual = (turnoActual.equals(jugador1)) ? jugador2 : jugador1;
            statusBar.setText(turnoActual + " es el turno");
        }
    }

    private boolean hayGanador() {
        String[][] tablero = new String[dimensionTablero][dimensionTablero];
        for (int i = 0; i < dimensionTablero; i++) {
            for (int j = 0; j < dimensionTablero; j++) {
                tablero[i][j] = buttons[i][j].getText();
            }
        }
        for (int i = 0; i < dimensionTablero; i++) {
            if (hay3EnLinea(tablero[i][0], tablero[i][1], tablero[i][2])) return true; 
            if (hay3EnLinea(tablero[0][i], tablero[1][i], tablero[2][i])) return true; 
        }
        
        if (hay3EnLinea(tablero[0][0], tablero[1][1], tablero[2][2])) return true; 
        if (hay3EnLinea(tablero[0][2], tablero[1][1], tablero[2][0])) return true; 

        return false;
    }

    private boolean hay3EnLinea(String a, String b, String c) {
        return !a.equals("") && a.equals(b) && b.equals(c);
    }

    private boolean tableroLLeno() {
        for (int i = 0; i < dimensionTablero; i++) {
            for (int j = 0; j < dimensionTablero; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void reiniciarJuego() {
        for (int i = 0; i < dimensionTablero; i++) {
            for (int j = 0; j < dimensionTablero; j++) {
                buttons[i][j].setText("");
            }
        }
        turnoActual = jugador1;
        statusBar.setText(jugador1 + " es el turno de");
    }

    public static void main(String[] args) {
        String jugador1 = JOptionPane.showInputDialog(null, "dijite el nombre del Jugador 1:");
        String jugador2 = JOptionPane.showInputDialog(null, "dijite el nombre del Jugador 2:");

        String[] dimensiones = {"3x3", "4x4", "5x5"};
        String seleccion = (String) JOptionPane.showInputDialog(null, "elije el tamaño del tablero:",
                "Seleccionar Tamaño del Tablero", JOptionPane.QUESTION_MESSAGE, null, dimensiones, dimensiones[0]);

        int dimensionTablero = Integer.parseInt(seleccion.substring(0, 1));

        SwingUtilities.invokeLater(() -> new Triqui3(jugador1, jugador2, dimensionTablero));
    }
}
