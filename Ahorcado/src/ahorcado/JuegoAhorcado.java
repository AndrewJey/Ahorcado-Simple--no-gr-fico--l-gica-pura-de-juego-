package ahorcado;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Motor lógico del juego del Ahorcado, desacoplado de la interfaz para facilitar pruebas.
 */
public class JuegoAhorcado {
    public static final int MAX_FALLOS = 8;
    public static final List<String> PALABRAS_PREDETERMINADAS = Collections.unmodifiableList(
        Arrays.asList("alado", "automotor", "herradura", "extravaganza", "caballeria")
    );

    public enum ResultadoIntento {
        ACIERTO,
        FALLO,
        REPETIDA,
        ENTRADA_INVALIDA,
        GANO,
        PERDIO
    }

    private final String palabraOculta;
    private final char[] progreso;
    private final Set<Character> letrasIntentadas = new LinkedHashSet<>();
    private int fallos;
    private boolean terminado;
    private boolean ganado;

    public JuegoAhorcado() {
        this(PALABRAS_PREDETERMINADAS.get(ThreadLocalRandom.current().nextInt(PALABRAS_PREDETERMINADAS.size())));
    }

    public JuegoAhorcado(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra oculta no puede estar vacía.");
        }
        this.palabraOculta = palabra.trim().toLowerCase(Locale.ROOT);
        this.progreso = new char[this.palabraOculta.length()];
        Arrays.fill(this.progreso, '_');
    }

    public ResultadoIntento intentar(String entrada) {
        if (terminado) {
            return ganado ? ResultadoIntento.GANO : ResultadoIntento.PERDIO;
        }
        if (entrada == null) {
            return ResultadoIntento.ENTRADA_INVALIDA;
        }
        String limpia = entrada.trim().toLowerCase(Locale.ROOT);
        if (limpia.isEmpty()) {
            return ResultadoIntento.ENTRADA_INVALIDA;
        }
        if (limpia.length() > 1) {
            if (limpia.equals(palabraOculta)) {
                for (int i = 0; i < palabraOculta.length(); i++) {
                    progreso[i] = palabraOculta.charAt(i);
                }
                terminado = true;
                ganado = true;
                return ResultadoIntento.GANO;
            }
            fallos = Math.min(MAX_FALLOS, fallos + 1);
            if (fallos >= MAX_FALLOS) {
                terminado = true;
                ganado = false;
                return ResultadoIntento.PERDIO;
            }
            return ResultadoIntento.FALLO;
        }

        char letra = limpia.charAt(0);
        if (!Character.isLetter(letra)) {
            return ResultadoIntento.ENTRADA_INVALIDA;
        }
        if (!letrasIntentadas.add(letra)) {
            return ResultadoIntento.REPETIDA;
        }

        boolean encontrada = false;
        for (int i = 0; i < palabraOculta.length(); i++) {
            if (palabraOculta.charAt(i) == letra) {
                progreso[i] = letra;
                encontrada = true;
            }
        }
        if (!encontrada) {
            fallos++;
            if (fallos >= MAX_FALLOS) {
                terminado = true;
                ganado = false;
                return ResultadoIntento.PERDIO;
            }
            return ResultadoIntento.FALLO;
        }
        if (palabraOculta.equals(String.valueOf(progreso))) {
            terminado = true;
            ganado = true;
            return ResultadoIntento.GANO;
        }
        return ResultadoIntento.ACIERTO;
    }

    public String getPalabraOculta() { return palabraOculta; }
    public int getFallos() { return fallos; }
    public int getIntentosRestantes() { return Math.max(0, MAX_FALLOS - fallos); }
    public boolean isTerminado() { return terminado; }
    public boolean isGanado() { return ganado; }
    public Set<Character> getLetrasIntentadas() { return Collections.unmodifiableSet(letrasIntentadas); }

    public String getProgresoFormateado() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < progreso.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(progreso[i]);
        }
        return sb.toString();
    }
}
