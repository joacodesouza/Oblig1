//Joaquín de Souza (270366)

import java.util.*;

public class Sistema {
    private ArrayList<Jugador> listajugadores;

    public ArrayList<Jugador> getListaJugadores() {
        return listajugadores;
    }

    public ArrayList<Jugador> agregarJugador(Jugador unJugador) {
        if (listajugadores == null) {
            listajugadores = new ArrayList<>();
        }
        if (jugadorYaExiste(unJugador)) {
            //System.out.println("");
            System.out.println("\nEl jugador ya existe.\n\n");
        } else {
            listajugadores.add(unJugador);
            //System.out.println("");
            System.out.println("\nEl jugador ha sido registrado.\n\n");
        }
        return listajugadores;
    }

    private boolean jugadorYaExiste(Jugador unJugador) {
        for (Jugador jugador : listajugadores) {
            if (jugador.getNombre().equals(unJugador.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public void ordernarJugadores() {
        Collections.sort(listajugadores, new Comparator<Jugador>() {
            public int compare(Jugador jugador1, Jugador jugador2) {
                return jugador1.getNombre().toLowerCase().compareTo(jugador2.getNombre().toLowerCase());
            }
        });
    }

    public void mostrarRanking() {
        if (listajugadores == null || listajugadores.isEmpty()) {
            System.out.println("\nNo hay jugadores registrados.\n");
            return;
        }

        List<Jugador> copia = new ArrayList<>(listajugadores);
        copia.sort(new Comparator<Jugador>() {
            @Override
            public int compare(Jugador j1, Jugador j2) {
                return Integer.compare(j2.getPuntaje(), j1.getPuntaje());
            }
        });

        System.out.println("\n=== Ranking de Jugadores ===\n");
        int posicion = 1;
        for (Jugador jugador : copia) {
            System.out.println(posicion + ". " + jugador.getNombre() + " - Puntos: " + jugador.getPuntaje());
            posicion++;
        }
        System.out.println();
    }

    public void elegirJugador(int numElegido, String color){
        // Validar que el índice esté dentro de los límites del ArrayList
        if (numElegido > 0 && numElegido <= getListaJugadores().size()) {
            Jugador jug;
            jug = getListaJugadores().get(numElegido - 1);
            System.out.println("\nJugador " + color + " elegido: " + jug.getNombre() + "\n");
        }
    }

}
