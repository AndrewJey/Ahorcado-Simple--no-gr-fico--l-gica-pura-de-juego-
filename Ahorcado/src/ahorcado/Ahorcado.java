package ahorcado;

import javax.swing.JOptionPane;

/**
 * Interfaz interactiva de Ahorcado con manejo seguro de cancelaciones, estado claro y reinicio de partida.
 */
public class Ahorcado {

    public static void main(String[] args) {
        boolean jugarDeNuevo = true;
        while (jugarDeNuevo) {
            JuegoAhorcado juego = new JuegoAhorcado();
            String mensaje = "Adivina la palabra letra por letra o escríbela completa.";

            while (!juego.isTerminado()) {
                String estado = mensaje
                    + "\n\nPalabra: " + juego.getProgresoFormateado()
                    + "\nIntentos restantes: " + juego.getIntentosRestantes() + " / " + JuegoAhorcado.MAX_FALLOS
                    + "\nLetras usadas: " + (juego.getLetrasIntentadas().isEmpty() ? "(ninguna)" : juego.getLetrasIntentadas())
                    + "\n\nDigita una letra o la palabra completa:";

                String entrada = JOptionPane.showInputDialog(null, estado, "Ahorcado", JOptionPane.QUESTION_MESSAGE);
                if (entrada == null) {
                    return;
                }

                JuegoAhorcado.ResultadoIntento res = juego.intentar(entrada);
                switch (res) {
                    case ACIERTO:
                        mensaje = "¡Bien! La letra está en la palabra.";
                        break;
                    case FALLO:
                        mensaje = "No coincide. Sigue intentando.";
                        break;
                    case REPETIDA:
                        mensaje = "Ya intentaste esa letra. No pierdes intentos.";
                        break;
                    case ENTRADA_INVALIDA:
                        mensaje = "Ingresa una letra válida o una palabra sin dejar el campo vacío.";
                        break;
                    case GANO:
                        JOptionPane.showMessageDialog(null, "¡GANASTE!\nLa palabra era: " + juego.getPalabraOculta(), "Ahorcado", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case PERDIO:
                        JOptionPane.showMessageDialog(null, "Perdiste.\nLa palabra era: " + juego.getPalabraOculta(), "Ahorcado", JOptionPane.WARNING_MESSAGE);
                        break;
                }
            }

            int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas jugar otra partida?", "Ahorcado", JOptionPane.YES_NO_OPTION);
            jugarDeNuevo = (opcion == JOptionPane.YES_OPTION);
        }
    }
}
