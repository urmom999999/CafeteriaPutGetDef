package com.example.cafeteriajavafx;

public class Buffer {
    private boolean free = true;

    public synchronized void get() {
        while (!free) {
            try {

                wait();


            } catch (InterruptedException e) {
                return;

            }
        }
        System.out.println("TIC");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        free = false;

        notifyAll();

    };
    public synchronized  void put(){
        while(free){
            try {
                wait();
            }catch(InterruptedException e){
                return;
            }
        }
        System.out.println("TAC");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        free=true;
        notifyAll();

    }
}