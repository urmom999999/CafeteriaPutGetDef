package com.example.cafeteriajavafx;
public class Camarero extends Thread {
    public String nombre;
    private Cola cola;
    private boolean trabajando;
    private boolean activo;
    private MenuController controller;
    private int hilo;
    private Buffer buffer;
    public Camarero(String nombre,Cola cola,MenuController controller,int hilo,Buffer buffer){
        this.nombre=nombre;
        this.cola=cola;
        this.activo=false;
        this.trabajando=false;
        this.controller = controller;
        this.hilo=hilo;
        this.buffer=buffer;
    }

    public String getNombre() {
        return nombre;
    }
    public boolean estaTrabajando() {
        return trabajando;
    }
public void activar(){
        this.activo=true;
        //Error si no esta iniciado correctamente por primera vez
    if (!this.isAlive()) {
        this.start();
    }
}

    public void prepararCafe(Cliente cliente) throws InterruptedException {
//ANUNCIAR COMIEZO PREPARANDO EL CAFE, THREAD.SLEEP
        //Si servido es true el cliente fue servido o se fue
        if (cliente.getServido()) {
            return;
        }
        //ENVIO A BARISTA
        controller.agregarMensajeCafeteria(nombre + " tiene el pedido de " + cliente.getnombre());
//PUT
        buffer.put(cliente);
        Thread.sleep(500);
        if (cliente.isAlive() && !cliente.getServido()) {
            cliente.servir();
            controller.agregarMensajeCafeteria(nombre + " sirve el café a " + cliente.getnombre());
        } else {
            controller.agregarMensajeCafeteria(nombre + " tiene el café listo pero " + cliente.getnombre() + " se marchó");
            //Boorrado
            buffer.limpiarSiClienteMarchado(cliente.getnombre());
        }
        controller.actualizarEstadoCamarero(nombre, false);
    }
    //thread.sleep

    @Override
    public void run() {
        System.out.println(nombre + " comenzó a trabajar.");
        if (controller != null) {
            controller.agregarMensajeCafeteria(nombre + " comenzó a trabajar");
            controller.actualizarEstadoCamarero(nombre, true);
        }

while (activo|| cola.hayMasClientes()){
    trabajando=true;
    while (cola.hayMasClientes()) {
        try {
            Cliente cliente = cola.siguienteCliente();
            if (cliente != null) {
                prepararCafe(cliente);
            }
        } catch (InterruptedException e) {
            System.out.println(nombre + " ERROR");
            activo = false;
            break;
        }
    }
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        break;
    }
    //DEJAR DE TRABAJAR
    trabajando = false;
    //-------------------PRUEBA
   // activo = false;
}

        if (controller != null) {
            controller.agregarMensajeCafeteria(nombre + " terminó de trabajar");
            controller.actualizarEstadoCamarero(nombre, false);
        }
        System.out.println(nombre + " terminó de trabajar.");
    }
}
