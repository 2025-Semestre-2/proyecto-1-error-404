/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package algoritmos;

import com.sistemasoperativos.pcvirtual.componentes.BUS2;
import com.sistemasoperativos.pcvirtual.componentes.CPUModelo2;
import com.sistemasoperativos.pcvirtual.procesos.BCP;
import com.sistemasoperativos.pcvirtual.procesos.ColaProcesos;
import com.sistemasoperativos.pcvirtual.procesos.EstadoBCP;
import com.sistemasoperativos.pcvirtual.instrucciones.Instruccion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Algoritmo FCFS (First Come, First Served)
 * Los procesos se ejecutan en orden de llegada (FIFO) sin expulsión.
 * Cada CPU corre en su propio hilo utilizando BUS2.
 */
public class FCFS implements Algoritmo, Runnable {

    private BUS2 bus;
    private final ColaProcesos cola;
    private final int cantidadCPUs;
    private final List<CPUModelo2> cpus;
    private final List<BCP> procesosFinalizados;
    private boolean ejecutando = false;

    /**
     * Constructor del algoritmo FCFS.
     * 
     * @param cola Cola de procesos listos.
     * @param cantidadCPUs Número de CPUs disponibles.
     */
    public FCFS(ColaProcesos cola, int cantidadCPUs) {
        this.cola = cola;
        this.cantidadCPUs = cantidadCPUs;
        this.cpus = new ArrayList<>();
        this.procesosFinalizados = new ArrayList<>();
    }

    @Override
    public void AsignarBUS(BUS2 bus) {
        this.bus = bus;
    }

    @Override
    public void IniciarEjecucion() {
        if (bus == null) {
            System.err.println("[FCFS] Error: No se ha asignado el BUS antes de iniciar.");
            return;
        }

        if (!ejecutando) {
            ejecutando = true;

            // Crear CPUs (simuladas)
            for (int i = 0; i < cantidadCPUs; i++) {
                CPUModelo2 cpu = new CPUModelo2(new LinkedHashMap<String, Instruccion>(), new LinkedHashMap<>());
                try {
                    cpu.AsignarBUS(bus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cpus.add(cpu);
            }

            // Lanzar el planificador
            Thread planificador = new Thread(this, "Planificador_FCFS");
            planificador.start();
            System.out.println("[FCFS] Planificador iniciado con " + cantidadCPUs + " CPUs virtuales.");
        }
    }

    @Override
    public void run() {
        while (ejecutando) {
            if (!cola.estaVacia()) {
                BCP proceso = null;
                try {
                    proceso = cola.obtener();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (proceso != null) {
                    proceso.marcarEjecucion();
                    ejecutarEnCPUDisponible(proceso);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void ejecutarEnCPUDisponible(BCP proceso) {
        new Thread(() -> {
            try {
                CPUModelo2 cpu = cpus.get(0); // asigna siempre la primera CPU
                cpu.CambiarRegistros(proceso.getRegistros());
                proceso.setCpuAsignado("CPU0");

                System.out.println("[FCFS] Ejecutando proceso " + proceso.getID() + " (" + proceso.getNombre() + ") en CPU0");

                while (proceso.getEstado() == EstadoBCP.EJECUTANDO) {
                    cpu.EjecutarInstruccion();
                    Thread.sleep(1000);
                }

                proceso.marcarFinalizado();
                procesosFinalizados.add(proceso);

                System.out.println("[FCFS] Proceso " + proceso.getID() + " finalizado.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public List<Map<String, String>> ObtenerBCPs() {
        List<Map<String, String>> lista = new ArrayList<>();

        for (BCP bcp : procesosFinalizados) {
            Map<String, String> datos = new LinkedHashMap<>();
            datos.put("ID", String.valueOf(bcp.getID()));
            datos.put("Nombre", bcp.getNombre());
            datos.put("CPU", bcp.getCpuAsignado());
            datos.put("Estado", bcp.getEstado().toString());
            lista.add(datos);
        }

        return lista;
    }
}
