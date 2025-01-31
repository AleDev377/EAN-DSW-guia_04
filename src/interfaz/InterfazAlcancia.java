/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogot� - Colombia)
 * Programa de Ingenier�a de Sistemas
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Desarrollo de Software - Gu�a 2 - Actividad 2
 * Ejercicio: alcancia
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package interfaz;

import conexion.AlcanciaCRUD;
import mundo.Alcancia;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Clase principal de la interfaz.
 */
@SuppressWarnings("serial")
public class InterfazAlcancia extends JFrame {
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo.
     */
    private Alcancia alcancia;


    String nombreAlcancia="";
    Boolean yaExisteAlcancia=false;

    // -----------------------------------------------------------------
    // Atributos de la Interfaz
    // -----------------------------------------------------------------

    /**
     * Panel con la imagen.
     */
    private PanelImagen panelImagen;

    /**
     * Panel donde se muestra el estado actual de la alcanc�a.
     */
    private PanelAlcancia panelAlcancia;

    /**
     * Panel con los botones de las acciones de la aplicaci�n.
     */
    private PanelBotones panelBotones;

    /**
     * Panel donde se seleccionan y agregan las monedas.
     */
    private PanelMonedas panelMonedas;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye la interfaz de la aplicaci�n y la inicializa con una alcanc�a vac�a. <br>
     * <b>post: </b> Se inicializ� la ventana principal de la aplicaci�n con sus paneles.
     */
    public InterfazAlcancia() {
        alcancia = new Alcancia();

        setTitle("Alcanc�a "+ alcancia.darNombre());
        setSize(730, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelImagen = new PanelImagen();
        getContentPane().add(panelImagen, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        panelMonedas = new PanelMonedas(this);
        panelCentral.add(panelMonedas, BorderLayout.NORTH);

        panelAlcancia = new PanelAlcancia();
        panelCentral.add(panelAlcancia, BorderLayout.CENTER);

        panelBotones = new PanelBotones(this);
        panelCentral.add(panelBotones, BorderLayout.EAST);

       getContentPane().add(panelCentral, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void actualizaTitulo(String nombre){
        if (nombre==null){
            nombre="";
        }
        setTitle("Alcanc�a "+ nombre);
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Agrega una moneda a la alcanc�a siempre y cuando no est� rota. <br>
     * <b>post: </b> Se agreg� la cantidad indicada a la alcanc�a o se present� un mensaje de error si estaba rota.
     * @param pDenominacion Denominaci�n de la moneda que se va a agregar a la alcanc�a. pDenominacion >0.
     */
    public void agregarMoneda(int pDenominacion) {
        if (alcancia.darEstado().equals("NO ROTA")) {
            if (pDenominacion == 1000) {
                alcancia.agregarMoneda1000();
                panelMonedas.cambiarCantidad(1000, alcancia.darNumeroMonedas1000());
            } else if (pDenominacion == 50) {
                alcancia.agregarMoneda50();
                panelMonedas.cambiarCantidad(50, alcancia.darNumeroMonedas50());
            } else if (pDenominacion == 100) {
                alcancia.agregarMoneda100();
                panelMonedas.cambiarCantidad(100, alcancia.darNumeroMonedas100());
            } else if (pDenominacion == 200) {
                alcancia.agregarMoneda200();
                panelMonedas.cambiarCantidad(200, alcancia.darNumeroMonedas200());
            } else if (pDenominacion == 500) {
                alcancia.agregarMoneda500();
                panelMonedas.cambiarCantidad(500, alcancia.darNumeroMonedas500());
            }

            panelAlcancia.cambiarMensaje("Se agregaron $" + pDenominacion + " a la alcanc�a.\nEn la alcanc�a hay $" + alcancia.calcularTotalDinero() + " pesos.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pueden agregar monedas a una alcanc�a rota.", "Agregar moneda", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea una nueva alcanc�a. <br>
     * <b>post: </b> Se cre� una alcanc�a nueva.
     */
    public void nuevaAlcancia() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        DialogNombreAlcancia dialog = new DialogNombreAlcancia(this);
        dialog.setVisible(true);

        AlcanciaCRUD alcanciaCRUD;
        nombreAlcancia = dialog.obtenerVariable();
        alcanciaCRUD = new AlcanciaCRUD();
        actualizaTitulo(nombreAlcancia);

        if (nombreAlcancia==null){
            nombreAlcancia="";
        }

        if (!nombreAlcancia.isEmpty()){
            panelMonedas.activarBoton(true);
            ArrayList<Object> datosRegistro =  alcanciaCRUD.devuelveRegistro(nombreAlcancia);
            if (!datosRegistro.isEmpty()) {
                alcancia.asignarNombre(nombreAlcancia);
                alcancia.actualizarMonedas(datosRegistro);
                yaExisteAlcancia = true;
                panelAlcancia.cambiarMensaje("La alcanc�a ya existe.");
                panelMonedas.cambiarCantidad(50, alcancia.darNumeroMonedas50());
                panelMonedas.cambiarCantidad(100, alcancia.darNumeroMonedas100());
                panelMonedas.cambiarCantidad(200, alcancia.darNumeroMonedas200());
                panelMonedas.cambiarCantidad(500, alcancia.darNumeroMonedas500());
                panelMonedas.cambiarCantidad(1000, alcancia.darNumeroMonedas1000());
            } else {
                panelAlcancia.cambiarMensaje("La Alcanc�a es nueva.");
                alcancia = new Alcancia();
                alcancia.asignarNombre(nombreAlcancia);
                yaExisteAlcancia = false;
                panelMonedas.cambiarCantidad(50, 0);
                panelMonedas.cambiarCantidad(100, 0);
                panelMonedas.cambiarCantidad(200, 0);
                panelMonedas.cambiarCantidad(500, 0);
                panelMonedas.cambiarCantidad(1000, 0);
            }
        }  else {
            yaExisteAlcancia = false;
            panelMonedas.activarBoton(false);
            panelMonedas.cambiarCantidad(50, 0);
            panelMonedas.cambiarCantidad(100, 0);
            panelMonedas.cambiarCantidad(200, 0);
            panelMonedas.cambiarCantidad(500, 0);
            panelMonedas.cambiarCantidad(1000, 0);
            alcancia.inicializaAlcancia();
            alcancia.romperAlcancia(0);
            panelAlcancia.cambiarImagenAlcancia(false);
            panelAlcancia.cambiarMensaje("La alcanc�a est� vacia!!!");
        }
    }

    /**
     * Rompe la alcanc�a y actualiza la informaci�n mostrada. <br>
     * <b>post: </b> La alcanc�a est� rota.
     */
    public void romperAlcancia() {
        String mensaje = alcancia.darEstadoAlcancia();
        if (!nombreAlcancia.isEmpty()){
            AlcanciaCRUD alcanciaCRUD = new AlcanciaCRUD();
            alcanciaCRUD.deleteRegistro(nombreAlcancia);
            nombreAlcancia="";
            yaExisteAlcancia = false;
            panelMonedas.cambiarCantidad(50, 0);
            panelMonedas.cambiarCantidad(100, 0);
            panelMonedas.cambiarCantidad(200, 0);
            panelMonedas.cambiarCantidad(500, 0);
            panelMonedas.cambiarCantidad(1000, 0);
            alcancia.inicializaAlcancia();
            actualizaTitulo("");
        }
        alcancia.romperAlcancia(1);
        panelAlcancia.cambiarImagenAlcancia(true);
        panelAlcancia.cambiarMensaje("La alcanc�a est� rota.");
        JOptionPane.showMessageDialog(this, mensaje, "Romper alcanc�a", JOptionPane.INFORMATION_MESSAGE);
    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * Punto de extensi�n 1.
     */
    public void reqFunc1() {
        int resultado = alcancia.obtenerDenominacionMasNumerosa();
        String mensaje_moneda = "La moneda m�s numerosa en la alcanc�a es la de " + resultado + " pesos";
        if (resultado == 0){
            mensaje_moneda = "No existen monedas en la alcancia";
        }
        JOptionPane.showMessageDialog(this,
                mensaje_moneda,
                "Respuesta",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void guardar() {
        if (nombreAlcancia.isEmpty()) {
            return;
        }
        int moneda0050 = alcancia.darNumeroMonedas50();
        int moneda0100 = alcancia.darNumeroMonedas100();
        int moneda0200 = alcancia.darNumeroMonedas200();
        int moneda0500 = alcancia.darNumeroMonedas500();
        int moneda1000 = alcancia.darNumeroMonedas1000();
        AlcanciaCRUD alcanciaCRUD = new AlcanciaCRUD();
        if (yaExisteAlcancia){
            alcanciaCRUD.updateRegistro(nombreAlcancia, moneda0050,
                    moneda0100, moneda0200, moneda0500, moneda1000);
        }else{
            alcanciaCRUD.createRegistro(nombreAlcancia, moneda0050,
                    moneda0100, moneda0200, moneda0500, moneda1000);
            yaExisteAlcancia=true;
        }
    }

    /**
     * Punto de extensi�n 2.
     */
    public void reqFunc2() {
        String resultado;
        if (alcancia.valiosa()) {
            resultado = "La alcanc�a es valiosa, solo posee monedas de 500 y/o de 1000";
        }
        else {
            resultado = "La alcancia no es valiosa, posee monedas de denominaciones diferentes a 1000 o de 500";
        }
        JOptionPane.showMessageDialog(this,
                resultado,
                "Respuesta",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicaci�n.
     * @param pArgs Par�metros de la ejecuci�n. No son necesarios.
     */
    public static void main(String[] pArgs) {
        try {
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            InterfazAlcancia ia = new InterfazAlcancia();
            ia.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}