/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cargadadoresprogramas;

import com.sistemasoperativos.pcvirtual.procesos.BCP;
import java.util.List;

/**
 *
 * @author andrewdeni
 */
public interface Cargador {
    public void CargarPrograma(BCP bcp, List<String> programa) throws Exception;
}
