public class DronDTO {
    private String id;
    private String nombre;
    private String fabricante;
    private String modelo;
    private int horasVuelo;
    private boolean enAlmacen;

    public DronDTO() {
    }

    public DronDTO(String id, String nombre, String fabricante, String modelo, int horasVuelo, boolean enAlmacen) {
        this.id = id;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.horasVuelo = horasVuelo;
        this.enAlmacen = enAlmacen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(int horasVuelo) {
        this.horasVuelo = horasVuelo;
    }

    public boolean isEnAlmacen() {
        return enAlmacen;
    }

    public void setEnAlmacen(boolean enAlmacen) {
        this.enAlmacen = enAlmacen;
    }
}
