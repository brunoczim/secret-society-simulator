package io.github.brunoczim.secsocsim;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpscChannel<T> {
    private AtomicReference<T>[] buffer;
    private Semaphore readSemaphore;
    private Semaphore writeSemaphore;
    private AtomicInteger front;
    private AtomicInteger back;

    @SuppressWarnings("unchecked")
    public SpscChannel(int bufferCapacity) {
        if (bufferCapacity < 0) {
            throw new IllegalArgumentException("channel buffer capacity cannot be negative");
        }
        this.buffer = (AtomicReference<T>[]) new AtomicReference[bufferCapacity + 1];
        for (int i = 0; i < this.buffer.length; i++) {
            this.buffer[i] = new AtomicReference<>(null);
        }
        this.readSemaphore = new Semaphore(0, true);
        this.writeSemaphore = new Semaphore(bufferCapacity, true);
        this.front = new AtomicInteger(0);
        this.back = new AtomicInteger(0);
    }

    private T read() {
        T message = this.buffer[this.front.get()].get();
        this.buffer[this.front.get()].set(null);
        this.front.set((this.front.get() + 1) % this.buffer.length);
        return message;
    }

    private void write(T message) {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.buffer[this.back.get()].set(message);
        this.back.set((this.back.get() + 1) % this.buffer.length);
    }

    public T tryReceive() {
        if (this.readSemaphore.tryAcquire()) {
            this.writeSemaphore.release();
            T message = this.read();
            return message;
        }
        return null;
    }

    public T receive() throws InterruptedException {
        this.writeSemaphore.release();
        this.readSemaphore.acquire();
        T message = this.read();
        return message;
    }

    public boolean trySend(T message) {
        if (this.writeSemaphore.tryAcquire()) {
            this.write(message);
            this.readSemaphore.release();
            return true;
        }
        return false;
    }

    public void send(T message) throws InterruptedException {
        this.writeSemaphore.acquire();
        this.write(message);
        this.readSemaphore.release();
    }
}
