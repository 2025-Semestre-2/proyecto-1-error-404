/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package algoritmos;

import com.sistemasoperativos.pcvirtual.procesos.BCP;
import com.sistemasoperativos.pcvirtual.procesos.ColaProcesos;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Implementación del algoritmo HRRN (Highest Response Ratio Next)
 * para la planificación de procesos.
 *
 * HRR = (T_espera + T_servicio) / T_servicio
 * 
 * - T_servicio (tiempo de ejecución total) está en BCP (tiempoTotalEjecucion)
 * - T_espera se calcula y actualiza localmente en este planificador
 * - tiempoCPU representa el uptime del planificador
 * 
 * @author (tu nombre)
 */
public class HRRN {

    private final ColaProcesos cola;
    private final Map<Integer, Integer> tiemposEspera; // tiempo de espera por ID de proceso
    private int tiempoCPU; // uptime del CPU simulado

    public HRRN(ColaProcesos cola) {
        this.cola = cola;
        this.tiemposEspera = new HashMap<>();
        this.tiempoCPU = 0;
    }

    /**
     * Selecciona el proceso con el mayor HRR.
     * Si la cola está vacía, devuelve null.
     */
    public BCP seleccionarMayorHRR() {
        if (cola.estaVacia()) {
            System.out.println("[HRRN] No hay procesos en la cola.");
            return null;
        }

        Queue<BCP> lista = new LinkedList<>(cola.getProcesosOrdenadorPorMemoria());
        if (lista.isEmpty()) return null;

        double maxHRR = -1.0;
        BCP seleccionado = null;

        for (BCP p : lista) {
            int espera = tiemposEspera.getOrDefault(p.getID(), 0);
            long servicio = p.getTiempoTotalEjecucion(); // definido en BCP

            if (servicio <= 0) servicio = 1; // evitar división por cero

            double hrr = (double) (espera + servicio) / servicio;

            if (hrr > maxHRR) {
                maxHRR = hrr;
                seleccionado = p;
            }
        }

        if (seleccionado != null) {
            lista.remove(seleccionado);
            tiemposEspera.remove(seleccionado.getID());
            tiempoCPU += seleccionado.getTiempoTotalEjecucion();

            System.out.printf("[HRRN] Proceso seleccionado: %s (ID=%d) | HRR=%.2f | Tiempo CPU total=%d\n",
                    seleccionado.getNombre(), seleccionado.getID(), maxHRR, tiempoCPU);
        }

        return seleccionado;
    }

    /**
     * Incrementa el tiempo de espera de todos los procesos restantes.
     */
    public void actualizarTiemposEspera() {
        for (BCP p : cola.getProcesosOrdenadorPorMemoria()) {
            int actual = tiemposEspera.getOrDefault(p.getID(), 0);
            tiemposEspera.put(p.getID(), actual + 1);
        }
    }

    /**
     * Retorna el tiempo total del CPU (uptime del simulador).
     */
    public int getTiempoCPU() {
        return tiempoCPU;
    }
}
