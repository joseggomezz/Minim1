import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;

public class GestorDronesImpl implements GestorDrones {

    private static final Logger logger = LogManager.getLogger(GestorDronesImpl.class);

    private static GestorDronesImpl instance;

    private Map<String, Dron> drones;
    private Map<String, Piloto> pilotos;
    private Stack<Dron> almacen;

    private GestorDronesImpl() {
        this.drones = new HashMap<>();
        this.pilotos = new HashMap<>();
        this.almacen = new Stack<>();
    }

    public static GestorDronesImpl getInstance() {
        if (instance == null) {
            instance = new GestorDronesImpl();
        }
        return instance;
    }

    @Override
    public void anadirDron(String id, String nombre, String fabricante, String modelo) {
        logger.info("anadirDron - inicio: id={}, nombre={}, fabricante={}, modelo={}", id, nombre, fabricante, modelo);
        drones.put(id, new Dron(id, nombre, fabricante, modelo));
        logger.info("anadirDron - fin: dron {} añadido, total drones={}", id, drones.size());
    }

    @Override
    public void anadirPiloto(String id, String nombre, String apellidos) {
        logger.info("anadirPiloto - inicio: id={}, nombre={}, apellidos={}", id, nombre, apellidos);
        pilotos.put(id, new Piloto(id, nombre, apellidos));
        logger.info("anadirPiloto - fin: piloto {} añadido, total pilotos={}", id, pilotos.size());
    }

    @Override
    public List<Dron> listarDronesPorHoras() {
        logger.info("listarDronesPorHoras - inicio");
        List<Dron> lista = new ArrayList<>(drones.values());
        lista.sort(Comparator.comparingInt(Dron::getHorasVuelo).reversed());
        logger.info("listarDronesPorHoras - fin: {} drones", lista.size());
        return lista;
    }

    @Override
    public List<Piloto> listarPilotosPorHoras() {
        logger.info("listarPilotosPorHoras - inicio");
        List<Piloto> lista = new ArrayList<>(pilotos.values());
        lista.sort(Comparator.comparingInt(Piloto::getHorasVuelo).reversed());
        logger.info("listarPilotosPorHoras - fin: {} pilotos", lista.size());
        return lista;
    }

    @Override
    public void guardarDron(String idDron) {
        logger.info("guardarDron - inicio: idDron={}", idDron);
        Dron dron = drones.get(idDron);
        dron.setEnAlmacen(true);
        almacen.push(dron);
        logger.info("guardarDron - fin: dron {} en el almacén, drones en almacén={}", idDron, almacen.size());
    }

    @Override
    public Dron repararDron() {
        logger.info("repararDron - inicio");
        if (almacen.isEmpty()) {
            logger.error("repararDron - el almacén está vacío");
            return null;
        }
        Dron dron = almacen.pop();
        dron.setEnAlmacen(false);
        logger.info("repararDron - fin: dron {} reparado y fuera del almacén", dron.getId());
        return dron;
    }

    @Override
    public PlanDeVuelo crearPlanVuelo(String idDron, String idPiloto, LocalDateTime fecha,
                                      int duracionHoras, List<Posicion> posiciones)
            throws DroneNotAvailableException, FlightPlanConflictException {

        logger.info("crearPlanVuelo - inicio: idDron={}, idPiloto={}, fecha={}, duracionHoras={}, posiciones={}",
                idDron, idPiloto, fecha, duracionHoras, posiciones.size());

        Dron dron = drones.get(idDron);
        Piloto piloto = pilotos.get(idPiloto);

        if (dron.isEnAlmacen()) {
            logger.error("crearPlanVuelo - el dron {} está en el almacén", idDron);
            throw new DroneNotAvailableException("El dron " + idDron + " está en el almacén");
        }

        LocalDateTime fin = fecha.plusHours(duracionHoras);
        if (haySolape(dron.getPlanes(), fecha, fin)) {
            logger.error("crearPlanVuelo - el dron {} ya tiene un plan en ese intervalo", idDron);
            throw new FlightPlanConflictException("El dron " + idDron + " ya tiene un plan en ese intervalo");
        }
        if (haySolape(piloto.getPlanes(), fecha, fin)) {
            logger.error("crearPlanVuelo - el piloto {} ya tiene un plan en ese intervalo", idPiloto);
            throw new FlightPlanConflictException("El piloto " + idPiloto + " ya tiene un plan en ese intervalo");
        }

        PlanDeVuelo plan = new PlanDeVuelo(dron, piloto, fecha, duracionHoras, posiciones);
        dron.addPlan(plan);
        piloto.addPlan(plan);
        dron.sumarHoras(duracionHoras);
        piloto.sumarHoras(duracionHoras);
        logger.info("crearPlanVuelo - fin: plan creado para dron {} y piloto {}, empieza {} y acaba {}",
                idDron, idPiloto, fecha, fin);
        return plan;
    }

    private boolean haySolape(List<PlanDeVuelo> planes, LocalDateTime inicio, LocalDateTime fin) {
        for (PlanDeVuelo p : planes) {
            if (inicio.isBefore(p.getFin()) && p.getFecha().isBefore(fin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<PlanDeVuelo> planesDePiloto(String idPiloto) {
        logger.info("planesDePiloto - inicio: idPiloto={}", idPiloto);
        List<PlanDeVuelo> planes = pilotos.get(idPiloto).getPlanes();
        logger.info("planesDePiloto - fin: {} planes", planes.size());
        return planes;
    }

    @Override
    public List<PlanDeVuelo> planesDeDron(String idDron) {
        logger.info("planesDeDron - inicio: idDron={}", idDron);
        List<PlanDeVuelo> planes = drones.get(idDron).getPlanes();
        logger.info("planesDeDron - fin: {} planes", planes.size());
        return planes;
    }
    public void vaciar() {
        logger.info("vaciar inicio");
        drones.clear();
        pilotos.clear();
        almacen.clear();
        logger.info("vaciar fin");
    }
}