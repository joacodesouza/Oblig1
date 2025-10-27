//Joaquín de Souza (270366)

import java.util.*;

public class Interfaz{

    public static void menu(Sistema sist){
        int opcion = 0;

        while (opcion != 5) {
            System.out.println("==== MEDIO TATETI ====\n");
            System.out.println("Trabajo desarrollado por: Joaquín de Souza (270366)\n");
            System.out.println("1) Registrar un jugador");
            System.out.println("2) Comienzo de partida común");
            System.out.println("3) Continuación de partida");
            System.out.println("4) Mostrar ranking e invictos");
            System.out.println("5) Terminar el programa");

            opcion = pedirNumero("Ingrese su opcion (1-5) ",1,5);
            switch(opcion){
                case 1:
                    sist.agregarJugador(ingresarJugador());
                    break;                    
                case 2:
                    elegirJugadores(sist);
                    break;
                case 3:
                    elegirJugadores(sist);

                    break;
                case 4:
                    sist.mostrarRanking();
                    break;
            }
        }
    }

    public static int pedirNumero(String mensaje, int minimo, int maximo){
        Scanner in = new Scanner(System.in);
        boolean ok = false;
        int numero=0;

        while (!ok){
            try {
                System.out.print(mensaje);
                numero = in.nextInt();
                in.nextLine();
                if (numero<minimo || numero>maximo){
                    System.out.println("Valor fuera de rango (" + minimo + "-" + maximo + ")");
                }
                else {
                    ok = true;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Por favor, ingrese solo numeros");
                in.nextLine();
            }        
        }
        return numero;
    }

    public static Jugador ingresarJugador(){
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese el nombre del jugador: ");
        String nombre = in.nextLine();
        int edad = pedirNumero("Ingrese la edad del jugador: ", 1, 100);
        Jugador jugador = new Jugador(nombre, edad);

        return jugador;
    }

    public static void elegirJugadores(Sistema sist) {
        //Elijo jugadores
        if (sist.getListaJugadores() == null || sist.getListaJugadores().isEmpty()) {
            System.out.println("\nNo hay jugadores disponibles.\n");
            return;
        }else if(sist.getListaJugadores().size() < 2){
            System.out.println("\nNo hay suficientes jugadores disponibles. Se necesitan al " +
            "menos 2 jugadores.\n");
            return;
        }
        sist.ordernarJugadores();

        // Lista de jugadores disponibles
        int numero = 1;
        System.out.println("\nJugadores disponibles\n");
        for (Jugador j : sist.getListaJugadores()) {
            System.out.println(numero + ". " + j + "\n");
            numero++;
        }
        System.out.println("\n");
        
        //Eligo jugador blanco
        int jugBlanco = pedirNumero("Eliga el jugador blanco: ", 1,
                        sist.getListaJugadores().size());
        sist.elegirJugador(jugBlanco, "blanco");

        //Eligo jugador negro
        int jugNegro = pedirNumero("Eliga el jugador negro: ", 1,
                        sist.getListaJugadores().size());
        while (jugBlanco == jugNegro){
            System.out.println("\nEse jugador ya está seleccionado. Seleccione otro\n");
            jugNegro = pedirNumero("Eliga el jugador negro: ", 1,
                        sist.getListaJugadores().size());
        }
        sist.elegirJugador(jugNegro, "negro");

        Jugador jugadorBlanco = sist.getListaJugadores().get(jugBlanco-1);
        Jugador jugadorNegro = sist.getListaJugadores().get(jugNegro-1);
        Juego juego = new Juego();
        System.out.println("\nFormato de jugadas: \n"
                + "- Para colocar una pieza: <fila><columna><orientación> (ej.: A3H).\n"
                + "- Para retirar una pieza propia: -<fila><columna><orientación> (ej.: -B4D).\n"
                + "  Orientaciones válidas: H (horizontal), V (vertical), D (diagonal \\\\), I (diagonal /).\n"
                + "- Cada alineación de tres O u X otorga un punto al color correspondiente.\n"
                + "- Ingrese X para finalizar la partida anticipadamente.\n");
        juego.comenzarPartida(jugadorBlanco, jugadorNegro);

    }
}
