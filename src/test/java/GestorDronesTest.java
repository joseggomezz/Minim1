import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class GestorDronesTest {

    private GestorDronesImpl gestor;

    @Before
    public void setUp() {
        gestor = GestorDronesImpl.getInstance();
        gestor.anadirDron("D1", "Phantom", "DJI", "P4");
        gestor.anadirDron("D2", "Mavic", "DJI", "M3");
        gestor.anadirPiloto("P1", "Ana", "Lopez Perez");
        gestor.anadirPiloto("P2", "Luis", "Garcia Ruiz");
    }

    @After
    public void tearDown() {
        gestor.vaciar();
    }

    @Test
    public void testCrearPlanVuelo() throws Exception {
        List<Posicion> posiciones = new ArrayList<>();
        posiciones.add(new Posicion(41.27, 1.98));
        posiciones.add(new Posicion(41.28, 1.99));
        LocalDateTime fecha = LocalDateTime.of(2026, 11, 10, 10, 0);

        PlanDeVuelo plan = gestor.crearPlanVuelo("D1", "P1", fecha, 2, posiciones);

        assertNotNull(plan);
        assertEquals("D1", plan.getDron().getId());
        assertEquals("P1", plan.getPiloto().getId());
        assertEquals(2, plan.getPosiciones().size());
        assertEquals(1, gestor.planesDeDron("D1").size());
    }

    @Test
    public void testListarDronesPorHoras() throws Exception {
        List<Posicion> posiciones = new ArrayList<>();
        posiciones.add(new Posicion(41.27, 1.98));
        LocalDateTime fecha = LocalDateTime.of(2026, 11, 10, 10, 0);

        gestor.crearPlanVuelo("D1", "P1", fecha, 3, posiciones);
        gestor.crearPlanVuelo("D2", "P2", fecha, 5, posiciones);

        List<Dron> lista = gestor.listarDronesPorHoras();

        assertEquals(2, lista.size());
        assertEquals("D2", lista.get(0).getId());
        assertEquals(5, lista.get(0).getHorasVuelo());
        assertEquals("D1", lista.get(1).getId());
        assertEquals(3, lista.get(1).getHorasVuelo());
    }

    @Test
    public void testGuardarYRepararDron() {
        gestor.guardarDron("D1");
        gestor.guardarDron("D2");

        Dron reparado = gestor.repararDron();

        assertEquals("D2", reparado.getId());
        assertFalse(reparado.isEnAlmacen());

        Dron segundo = gestor.repararDron();
        assertEquals("D1", segundo.getId());
    }

    @Test
    public void testPlanesDePiloto() throws Exception {
        List<Posicion> posiciones = new ArrayList<>();
        posiciones.add(new Posicion(41.27, 1.98));

        gestor.crearPlanVuelo("D1", "P1", LocalDateTime.of(2026, 11, 10, 10, 0), 2, posiciones);
        gestor.crearPlanVuelo("D2", "P1", LocalDateTime.of(2026, 11, 10, 14, 0), 2, posiciones);
        gestor.crearPlanVuelo("D1", "P2", LocalDateTime.of(2026, 11, 11, 10, 0), 2, posiciones);

        assertEquals(2, gestor.planesDePiloto("P1").size());
        assertEquals(1, gestor.planesDePiloto("P2").size());
    }
}
