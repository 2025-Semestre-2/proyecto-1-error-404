/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package balanceador;

import com.sistemasoperativos.pcvirtual.componentes.CPU;
import com.sistemasoperativos.pcvirtual.procesos.BCP;

/**
 *
 * @author andrewdeni
 */
public interface Balanceador {
    public boolean AsignarProcesoACPU(BCP bcp);
    public boolean AsignarProcesoACPU(BCP bcp, int CPU);
    public void AsignarCPU(CPU cpu);
    public boolean EstanCPUsOcupados();
}
