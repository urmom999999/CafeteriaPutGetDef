package com.example.cafeteriajavafx;

public class Buffer {
    private boolean free = true;
    private Cliente pedidoActual;

    public synchronized Cliente get(MenuController controller, String nombreBarista) {
        while (free || pedidoActual == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        System.out.println("Barista recibe el pedido de " + pedidoActual.getnombre());
        controller.agregarMensajeCafeteria("Barista recibe el pedido de " + pedidoActual.getnombre());
        free = true;
        Cliente cliente = pedidoActual;
        pedidoActual = null;

        notifyAll();

        try {
            Thread.sleep((int) (Math.random() * 2000) + 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return cliente;
    }

    public synchronized void put(Cliente cliente) {
        while (!free) {
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }

        System.out.println("Camarero dirigiendose a " + cliente.getnombre());

        pedidoActual = cliente;
        free = false;

        notifyAll();
    }

    //Limpiar Buffer
    public synchronized void limpiarSiClienteMarchado(String nombreCliente) {
        if (pedidoActual != null && pedidoActual.getnombre().equals(nombreCliente)) {
            pedidoActual = null;
            free = true;
            notifyAll();
        }
    }
}