//Joaquín de Souza (270366)

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Tablero {
    public static final int FILAS = 3;
    public static final int COLUMNAS = 6;

    private final char[][] horizontales;
    private final char[][] verticales;
    private final char[][] diagonalesDescendentes;
    private final char[][] diagonalesAscendentes;

    public Tablero() {
        horizontales = crearMatriz();
        verticales = crearMatriz();
        diagonalesDescendentes = crearMatriz();
        diagonalesAscendentes = crearMatriz();
    }

    private char[][] crearMatriz() {
        char[][] matriz = new char[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                matriz[i][j] = ' ';
            }
        }
        return matriz;
    }

    public boolean colocarPieza(char fila, int columna, char orientacion, char color) {
        int filaIdx = convertirFila(fila);
        int columnaIdx = columna - 1;

        if (!esPosicionValida(filaIdx, columnaIdx) || !esOrientacionValida(orientacion)) {
            return false;
        }

        char[][] matriz = seleccionarMatriz(orientacion);
        if (matriz[filaIdx][columnaIdx] != ' ') {
            System.out.println("Ya existe una pieza en esa orientación.");
            return false;
        }

        matriz[filaIdx][columnaIdx] = color;
        return true;
    }

    public boolean levantarPieza(char fila, int columna, char orientacion, char color) {
        int filaIdx = convertirFila(fila);
        int columnaIdx = columna - 1;

        if (!esPosicionValida(filaIdx, columnaIdx) || !esOrientacionValida(orientacion)) {
            return false;
        }

        char[][] matriz = seleccionarMatriz(orientacion);
        if (matriz[filaIdx][columnaIdx] != color) {
            System.out.println("No hay una pieza propia en esa posición y orientación.");
            return false;
        }

        matriz[filaIdx][columnaIdx] = ' ';
        return true;
    }

    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }

    private boolean esOrientacionValida(char orientacion) {
        char o = Character.toUpperCase(orientacion);
        return o == 'H' || o == 'V' || o == 'D' || o == 'I';
    }

    private int convertirFila(char fila) {
        return Character.toUpperCase(fila) - 'A';
    }

    private char[][] seleccionarMatriz(char orientacion) {
        switch (Character.toUpperCase(orientacion)) {
            case 'H':
                return horizontales;
            case 'V':
                return verticales;
            case 'D':
                return diagonalesDescendentes;
            case 'I':
                return diagonalesAscendentes;
            default:
                throw new IllegalArgumentException("Orientación inválida: " + orientacion);
        }
    }

    public void imprimirTablero() {
        System.out.println("Orientaciones: H = Horizontal, V = Vertical, D = Diagonal \\\\, I = Diagonal /");
        System.out.print("     ");
        for (int c = 1; c <= COLUMNAS; c++) {
            System.out.printf(Locale.ROOT, "%-12s", c);
        }
        System.out.println();

        for (int f = 0; f < FILAS; f++) {
            System.out.print("   +");
            for (int c = 0; c < COLUMNAS; c++) {
                System.out.print("------------+");
            }
            System.out.println();

            System.out.printf(Locale.ROOT, " %s |", (char) ('A' + f));
            for (int c = 0; c < COLUMNAS; c++) {
                System.out.printf(Locale.ROOT, "%-12s|", obtenerValorCelda(f, c));
            }
            System.out.println();
        }

        System.out.print("   +");
        for (int c = 0; c < COLUMNAS; c++) {
            System.out.print("------------+");
        }
        System.out.println();
    }

    private String obtenerValorCelda(int fila, int columna) {
        List<String> piezas = new ArrayList<>();
        agregarDescripcion(piezas, horizontales[fila][columna], 'H');
        agregarDescripcion(piezas, verticales[fila][columna], 'V');
        agregarDescripcion(piezas, diagonalesDescendentes[fila][columna], 'D');
        agregarDescripcion(piezas, diagonalesAscendentes[fila][columna], 'I');
        if (piezas.isEmpty()) {
            return "";
        }
        return String.join(",", piezas);
    }

    private void agregarDescripcion(List<String> piezas, char color, char orientacion) {
        if (color == 'B' || color == 'N') {
            piezas.add(color + String.valueOf(orientacion));
        }
    }

    public Map<String, Character> obtenerLineasFormadas() {
        Map<String, Character> lineas = new HashMap<>();
        boolean[][] letrasO = new boolean[FILAS][COLUMNAS];
        boolean[][] letrasX = new boolean[FILAS][COLUMNAS];

        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                letrasO[f][c] = horizontales[f][c] != ' ' && verticales[f][c] != ' ';
                letrasX[f][c] = diagonalesDescendentes[f][c] != ' ' && diagonalesAscendentes[f][c] != ' ';
            }
        }

        // Horizontales
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c <= COLUMNAS - 3; c++) {
                if (letrasO[f][c] && letrasO[f][c + 1] && letrasO[f][c + 2]) {
                    registrarLinea(lineas, 'O', f, c, f, c + 1, f, c + 2);
                }
                if (letrasX[f][c] && letrasX[f][c + 1] && letrasX[f][c + 2]) {
                    registrarLinea(lineas, 'X', f, c, f, c + 1, f, c + 2);
                }
            }
        }

        // Verticales
        for (int c = 0; c < COLUMNAS; c++) {
            for (int f = 0; f <= FILAS - 3; f++) {
                if (letrasO[f][c] && letrasO[f + 1][c] && letrasO[f + 2][c]) {
                    registrarLinea(lineas, 'O', f, c, f + 1, c, f + 2, c);
                }
                if (letrasX[f][c] && letrasX[f + 1][c] && letrasX[f + 2][c]) {
                    registrarLinea(lineas, 'X', f, c, f + 1, c, f + 2, c);
                }
            }
        }

        // Diagonales descendentes
        for (int f = 0; f <= FILAS - 3; f++) {
            for (int c = 0; c <= COLUMNAS - 3; c++) {
                if (letrasO[f][c] && letrasO[f + 1][c + 1] && letrasO[f + 2][c + 2]) {
                    registrarLinea(lineas, 'O', f, c, f + 1, c + 1, f + 2, c + 2);
                }
                if (letrasX[f][c] && letrasX[f + 1][c + 1] && letrasX[f + 2][c + 2]) {
                    registrarLinea(lineas, 'X', f, c, f + 1, c + 1, f + 2, c + 2);
                }
            }
        }

        // Diagonales ascendentes
        for (int f = 2; f < FILAS; f++) {
            for (int c = 0; c <= COLUMNAS - 3; c++) {
                if (letrasO[f][c] && letrasO[f - 1][c + 1] && letrasO[f - 2][c + 2]) {
                    registrarLinea(lineas, 'O', f, c, f - 1, c + 1, f - 2, c + 2);
                }
                if (letrasX[f][c] && letrasX[f - 1][c + 1] && letrasX[f - 2][c + 2]) {
                    registrarLinea(lineas, 'X', f, c, f - 1, c + 1, f - 2, c + 2);
                }
            }
        }

        return lineas;
    }

    private void registrarLinea(Map<String, Character> lineas, char tipo, int f1, int c1, int f2, int c2, int f3, int c3) {
        String descripcion = formatearCoordenada(f1, c1) + "-" + formatearCoordenada(f2, c2) + "-" + formatearCoordenada(f3, c3);
        lineas.put(tipo + ":" + descripcion, tipo);
    }

    private String formatearCoordenada(int fila, int columna) {
        return String.valueOf((char) ('A' + fila)) + (columna + 1);
    }

}
