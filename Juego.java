//Joaquín de Souza (270366)
import java.util.*;

public class Juego {
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Jugador turnoActual;
    private Tablero tablero;
    private int bandasRestantes;
    private int bandasTotales = 10; // Valor por defecto

    public void comenzarPartida(Jugador blanco, Jugador negro) {
        this.jugadorBlanco = blanco;
        this.jugadorNegro = negro;
        this.turnoActual = jugadorBlanco; // El blanco siempre empieza
        this.tablero = new Tablero();
        this.bandasRestantes = bandasTotales;
        
        jugar(tablero);
    }
    
    private void jugar(Tablero tablero) {
        Scanner scanner = new Scanner(System.in);
        
        while (bandasRestantes > 0) {
            // Mostrar estado del juego
            System.out.println("\n=== TURNO ACTUAL ===");
            System.out.println("\nJugador " + (turnoActual == jugadorBlanco ? "BLANCO" : "NEGRO") + 
                                ": " + turnoActual.getNombre());
            System.out.println("\nBandas restantes: " + bandasRestantes + " de " + bandasTotales +"\n");
            
            // Mostrar tablero
            tablero.imprimirTablero();
            
            // Pedir jugada
            System.out.print("\nIngrese su jugada o 'X' para terminar: ");
            String jugada = scanner.nextLine().toUpperCase();
            
            if (jugada.equals("X")) {
                System.out.println("Partida terminada por el jugador");
                break;
            }
            
            // Procesar jugada
            if (validarJugada(jugada)) {
                // Colocar banda en el tablero
                colocarBanda(jugada);
                // Cambiar turno
                turnoActual = (turnoActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
                bandasRestantes--;
            } else {
                System.out.println("Jugada inválida. Intente nuevamente.");
            }
        }
        
        // Mostrar resultado final
        mostrarResultadoFinal();
    }
    
    private boolean validarJugada(String jugada) {
        // Implementar lógica de validación
        return true;
    }
    
    private void colocarBanda(String jugada) {
        char letra = jugada.toUpperCase().charAt(0);
        int fila = Character.getNumericValue(jugada.charAt(1)) - 1;
        char direccion = jugada.charAt(2);
        int longitud = jugada.length() > 3 ? Character.getNumericValue(jugada.charAt(3)) : 4;
        
        // Convertir letra a columna
        int columna = (letra - 'A') * 2;
        
        // Colocar banda en el tablero
        tablero.colocarBanda(fila, columna, String.valueOf(direccion), longitud);
    }

    private void mostrarResultadoFinal() {
        System.out.println("\n=== RESULTADO FINAL ===");
        System.out.println("Jugador BLANCO (" + jugadorBlanco.getNombre() + "): " + 
                            jugadorBlanco.getPuntaje() + " triángulos");
        System.out.println("Jugador NEGRO (" + jugadorNegro.getNombre() + "): " + 
                            jugadorNegro.getPuntaje() + " triángulos");
        
        if (jugadorBlanco.getPuntaje() > jugadorNegro.getPuntaje()) {
            System.out.println("\n¡GANADOR: " + jugadorBlanco.getNombre() + " (BLANCO)!");
        } else if (jugadorNegro.getPuntaje() > jugadorBlanco.getPuntaje()) {
            System.out.println("\n¡GANADOR: " + jugadorNegro.getNombre() + " (NEGRO)!");
        } else {
            System.out.println("\n¡EMPATE!");
        }
    }
}