/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxserverside.exception.factura;

/**
 *
 * @author Carlos
 */
public class FacturaCreateException extends Exception {

    /**
     * Creates a new instance of <code>FacturasCreateException</code> without
     * detail message.
     */
    public FacturaCreateException() {
        super("Ha ocurrido un error al intentar crear la factura.");
    }

    /**
     * Constructs an instance of <code>FacturasCreateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FacturaCreateException(String msg) {
        super(msg);
    }
}
