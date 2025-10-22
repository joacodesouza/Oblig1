//Joaquín de Souza (270366)
import java.util.*;

public class Tablero {
    private static final int FILAS = 13;
    private static final int COLUMNAS = 25;
    //private String[][] tableroMatriz;  // Representación del tablero
    
    
    public imprimirTablero() {
        
        
        

            
                int filas = 3;     // Filas (A, B, C)
                int columnas = 6;  // Columnas (1 a 6)
                int alturaCelda = 3; // Altura de cada celda (número de líneas con '|')
        
                char letra = 'A';
        
                // Encabezado de columnas
                System.out.print("    ");
                for (int c = 1; c <= columnas; c++) {
                    System.out.print("  " + c + " ");
                }
                System.out.println();
        
                // Bucle de filas
                for (int f = 0; f < filas; f++) {
        
                    // Línea superior de la fila
                    System.out.print("   ");
                    for (int c = 0; c < columnas; c++) {
                        System.out.print("+---");
                    }
                    System.out.println("+");
        
                    // Celdas internas (altura de 3 líneas)
                    for (int h = 0; h < alturaCelda; h++) {
                        if (h == alturaCelda / 2) { // en la línea del medio imprime la letra
                            System.out.print(letra + "  ");
                        } else {
                            System.out.print("   ");
                        }
        
                        for (int c = 0; c < columnas; c++) {
                            System.out.print("|   ");
                        }
                        System.out.println("|");
                    }
        
                    letra++; // pasa a la siguiente letra
                }
        
                // Línea inferior final
                System.out.print("   ");
                for (int c = 0; c < columnas; c++) {
                    System.out.print("+---");
                }
                System.out.println("+");
            
        
        
    }
    
    
}