//Joaquín de Souza (270366)
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Juego {
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Jugador turnoActual;
    private Tablero tablero;
    private final Scanner scanner = new Scanner(System.in);
    private Map<String, Character> alineacionesRegistradas = new HashMap<>();

    public void comenzarPartida(Jugador blanco, Jugador negro) {
        this.jugadorBlanco = blanco;
        this.jugadorNegro = negro;
        this.turnoActual = jugadorBlanco; // El blanco siempre empieza
        this.tablero = new Tablero();
        this.alineacionesRegistradas = new HashMap<>();
        jugar();
    }

    private void jugar() {
        while (true) {
            System.out.println("\n=== TURNO ACTUAL ===");
            System.out.println("Jugador " + obtenerColorActualTexto() + ": " + turnoActual.getNombre());
            System.out.println("Alineaciones registradas: " + alineacionesRegistradas.size() + "\n");

            tablero.imprimirTablero();

            System.out.print("\nIngrese su jugada o 'X' para terminar: ");
            String jugada = scanner.nextLine();
            if (jugada == null) {
                continue;
            }
            jugada = jugada.trim();
            if (jugada.equalsIgnoreCase("X")) {
                System.out.println("Partida terminada por el jugador");
                break;
            }
            if (jugada.isEmpty()) {
                System.out.println("Debe ingresar una jugada.");
                continue;
            }

            if (validarJugada(jugada)) {
                cambiarTurno();
            } else {
                System.out.println("Jugada inválida. Intente nuevamente.");
            }
        }

        System.out.println();
        tablero.imprimirTablero();
        mostrarResultadoFinal();
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
    }

    private boolean validarJugada(String jugada) {
        String comando = jugada.toUpperCase(Locale.ROOT).replaceAll("\\s+", "");

        if (comando.startsWith("-")) {
            return procesarRetiro(comando);
        }

        return procesarColocacion(comando);
    }

    private boolean procesarColocacion(String comando) {
        if (comando.length() != 3) {
            return false;
        }
        char fila = comando.charAt(0);
        char digitoColumna = comando.charAt(1);
        char orientacion = comando.charAt(2);

        if (!Character.isDigit(digitoColumna)) {
            return false;
        }
        int columna = Character.getNumericValue(digitoColumna);
        if (!esFilaValida(fila) || !esColumnaValida(columna)) {
            return false;
        }

        boolean exito = tablero.colocarPieza(fila, columna, orientacion, obtenerColorTurno());
        if (exito) {
            System.out.println("Se colocó una pieza en " + fila + columna + orientacion + ".");
            actualizarAlineaciones();
        }
        return exito;
    }

    private boolean procesarRetiro(String comando) {
        if (comando.length() != 4) {
            return false;
        }
        char fila = comando.charAt(1);
        char digitoColumna = comando.charAt(2);
        char orientacion = comando.charAt(3);

        if (!Character.isDigit(digitoColumna)) {
            return false;
        }
        int columna = Character.getNumericValue(digitoColumna);
        if (!esFilaValida(fila) || !esColumnaValida(columna)) {
            return false;
        }

        boolean exito = tablero.levantarPieza(fila, columna, orientacion, obtenerColorTurno());
        if (exito) {
            System.out.println("Se retiró una pieza de " + fila + columna + orientacion + ".");
            actualizarAlineaciones();
        }
        return exito;
    }

    private boolean esFilaValida(char fila) {
        char f = Character.toUpperCase(fila);
        return f >= 'A' && f < 'A' + Tablero.FILAS;
    }

    private boolean esColumnaValida(int columna) {
        return columna >= 1 && columna <= Tablero.COLUMNAS;
    }

    private void actualizarAlineaciones() {
        Map<String, Character> lineasActuales = tablero.obtenerLineasFormadas();

        for (String id : alineacionesRegistradas.keySet().toArray(new String[0])) {
            if (!lineasActuales.containsKey(id)) {
                char tipo = alineacionesRegistradas.remove(id);
                restarPunto(tipo, id);
            }
        }

        for (Map.Entry<String, Character> entry : lineasActuales.entrySet()) {
            String id = entry.getKey();
            if (!alineacionesRegistradas.containsKey(id)) {
                alineacionesRegistradas.put(id, entry.getValue());
                sumarPunto(entry.getValue(), id);
            }
        }
    }

    private void sumarPunto(char tipo, String id) {
        String descripcion = descripcionLinea(id);
        if (tipo == 'O') {
            jugadorBlanco.incrementarPuntaje();
            System.out.println("Se formó una alineación de O en " + descripcion + ". Punto para " + jugadorBlanco.getNombre() + ".");
        } else if (tipo == 'X') {
            jugadorNegro.incrementarPuntaje();
            System.out.println("Se formó una alineación de X en " + descripcion + ". Punto para " + jugadorNegro.getNombre() + ".");
        }
    }

    private void restarPunto(char tipo, String id) {
        String descripcion = descripcionLinea(id);
        if (tipo == 'O') {
            jugadorBlanco.decrementarPuntaje();
            System.out.println("Se perdió la alineación de O en " + descripcion + ".");
        } else if (tipo == 'X') {
            jugadorNegro.decrementarPuntaje();
            System.out.println("Se perdió la alineación de X en " + descripcion + ".");
        }
    }

    private String descripcionLinea(String id) {
        int separador = id.indexOf(':');
        if (separador >= 0 && separador < id.length() - 1) {
            return id.substring(separador + 1);
        }
        return id;
    }

    private char obtenerColorTurno() {
        return (turnoActual == jugadorBlanco) ? 'B' : 'N';
    }

    private String obtenerColorActualTexto() {
        return (turnoActual == jugadorBlanco) ? "BLANCO" : "NEGRO";
    }

    private void mostrarResultadoFinal() {
        System.out.println("\n=== RESULTADO FINAL ===");
        System.out.println("Alineaciones activas: " + alineacionesRegistradas.size());
        System.out.println("Jugador BLANCO (" + jugadorBlanco.getNombre() + "): "
                + jugadorBlanco.getPuntaje() + " alineaciones de O");
        System.out.println("Jugador NEGRO (" + jugadorNegro.getNombre() + "): "
                + jugadorNegro.getPuntaje() + " alineaciones de X");

        if (jugadorBlanco.getPuntaje() > jugadorNegro.getPuntaje()) {
            System.out.println("\n¡GANADOR: " + jugadorBlanco.getNombre() + " (BLANCO)!");
        } else if (jugadorNegro.getPuntaje() > jugadorBlanco.getPuntaje()) {
            System.out.println("\n¡GANADOR: " + jugadorNegro.getNombre() + " (NEGRO)!");
        } else {
            System.out.println("\n¡EMPATE!");
        }
    }
}
