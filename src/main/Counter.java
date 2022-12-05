package main;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter extends Thread implements Runnable, TimeCounterView{
    private AtomicInteger counter = new AtomicInteger(0);
    private TimeCounterView view;
    private boolean running = true;

    public Counter(TimeCounterView view){
        this.view = view;
    }

    @Override
    public void run() {
        while (running){
            view.onSecondPass();
            counter.incrementAndGet();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getCounter() {
        return counter.get();
    }

    @Override
    public void onSecondPass() {

    }

    public void end() {
        running = false;
    }
}
