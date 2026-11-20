public class HangmanCheck {
    private static int count;
    private static void check(boolean ok, String msg) {
        count++;
        if (!ok) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        ahorcado.JuegoAhorcado game = new ahorcado.JuegoAhorcado("alado");
        check(game.getProgresoFormateado().equals("_ _ _ _ _"), "Initial masked word");
        check(game.intentar("") == ahorcado.JuegoAhorcado.ResultadoIntento.ENTRADA_INVALIDA, "Empty rejected");
        check(game.intentar(null) == ahorcado.JuegoAhorcado.ResultadoIntento.ENTRADA_INVALIDA, "Null rejected");
        check(game.intentar("1") == ahorcado.JuegoAhorcado.ResultadoIntento.ENTRADA_INVALIDA, "Digit rejected");
        check(game.getFallos() == 0, "Invalid input does not consume attempts");

        check(game.intentar("A") == ahorcado.JuegoAhorcado.ResultadoIntento.ACIERTO, "Case-insensitive hit");
        check(game.getProgresoFormateado().equals("a _ a _ _"), "Reveals all occurrences");
        check(game.intentar("a") == ahorcado.JuegoAhorcado.ResultadoIntento.REPETIDA, "Repeated letter detected");
        check(game.intentar("z") == ahorcado.JuegoAhorcado.ResultadoIntento.FALLO, "Wrong letter increments failures");
        check(game.intentar("z") == ahorcado.JuegoAhorcado.ResultadoIntento.REPETIDA, "Repeated wrong letter does not double-penalize");
        check(game.getFallos() == 1, "Exact 1 failure");

        check(game.intentar("ALADO") == ahorcado.JuegoAhorcado.ResultadoIntento.GANO, "Full word wins");
        check(game.isTerminado() && game.isGanado(), "Game marked won");

        ahorcado.JuegoAhorcado lose = new ahorcado.JuegoAhorcado("gato");
        String[] wrongs = {"b", "c", "d", "e", "f", "h", "i", "j"};
        for (int i = 0; i < wrongs.length - 1; i++) {
            check(lose.intentar(wrongs[i]) == ahorcado.JuegoAhorcado.ResultadoIntento.FALLO, "Wrong guess " + i);
        }
        check(lose.intentar(wrongs[wrongs.length - 1]) == ahorcado.JuegoAhorcado.ResultadoIntento.PERDIO, "8th wrong loses");
        check(lose.isTerminado() && !lose.isGanado() && lose.getIntentosRestantes() == 0, "Loss state consistent");

        System.out.println("PASS: " + count + " assertions");
    }
}
