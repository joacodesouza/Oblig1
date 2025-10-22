//Joaquín de Souza (270366)

public class Jugador {
    private String nombre;
    private int edad;
    private int puntaje;

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    //Constructor
    public Jugador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.puntaje = 0;
    }

    @Override
    public String toString() {
        return nombre + " (" + edad + ")";
    }
}
