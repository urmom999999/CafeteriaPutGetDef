package com.example.cafeteriajavafx;
/*
Cola Camarero atiende, activa varista, varista devuelve cafe y Camarero sirve

 */
public class Barista  extends Thread {
    public String nombre;
    private boolean trabajando;
    private boolean activo;
    private MenuController controller;
    private int hilo;
    private Buffer buffer;
    public Barista(String nombre,MenuController controller,int hilo,Buffer buffer){
        this.nombre=nombre;
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
        if(cliente.getServido()){
            return;
        }
        if (controller != null) {
           // controller.actualizarEstadoBarista(nombre, true);
            controller.agregarMensajeCafeteria(nombre + " empezó a preparar café para " + cliente.getnombre());
        }

        System.out.println(nombre + " empezó a preparar el café para " + cliente.getnombre());
        int preparacion = (int) (Math.random() * 2000) + 1000;
        //SI EL CLIENTE SE MARCHA DETENER EL PROCESO
        Thread.sleep(preparacion);
        //ENTREGAR SI ESTA A TIEMPO

        if(cliente.isAlive() && !cliente.getServido()){
            cliente.servir();
            if (controller != null) {
                controller.agregarMensajeCafeteria(nombre + " terminó de preparar el café para " + cliente.getnombre());
             //   controller.actualizarEstadoBarista(nombre, false);
            }
            System.out.println(nombre + " terminó de preparar el café para "+ cliente.getnombre()+ " y tardó " + preparacion/1000 + "segundos!");
        }
        else{
            if (controller != null) {
                controller.agregarMensajeCafeteria(nombre + " terminó pero " + cliente.getnombre() + " se marchó");
             //   controller.actualizarEstadoBarista(nombre, false);
            }
            System.out.println(nombre + " terminó de preparar el café, pero el cliente se marchó ya");}
    }
    //thread.sleep

    @Override
    public void run() {
        System.out.println(nombre + " comenzó a trabajar.");
        if (controller != null) {
            controller.agregarMensajeCafeteria(nombre + " comenzó a trabajar");
         //   controller.actualizarEstadoBarista(nombre, true);
        }




        if (controller != null) {
            controller.agregarMensajeCafeteria(nombre + " terminó de trabajar");
            //controller.actualizarEstadoBarista(nombre, false);
        }
        System.out.println(nombre + " terminó de trabajar.");
    }
}