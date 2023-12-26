package io.github.brunoczim.secsocsim;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpscChannel<T> {
    private Semaphore writePermits;
    private Semaphore readPermits;
    private AtomicReference<T>[] buffer;
    private int front;
    private int back;

    @SuppressWarnings("unchecked")
    public SpscChannel(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(
                "channel capacity cannot be negative, given" + capacity
            );
        }
        this.writePermits = new Semaphore(capacity, true);
        this.readPermits = new Semaphore(0, true);
        this.buffer = (AtomicReference<T>[]) new AtomicReference[capacity];
        for (int i = 0; i < this.buffer.length; i++) {
            this.buffer[i] = new AtomicReference<T>(null);
        }
        this.front = 0;
        this.back = 0;
    }

    private void completeAcquiredWrite(T message) {
        this.buffer[this.back].set(message);
        this.back = (this.back + 1) % this.buffer.length;
        this.readPermits.release();
    }

    private T completeAcquiredRead() {
        T message = this.buffer[this.front].get();
        this.buffer[this.front].set(null);
        this.front = (this.front + 1) % this.buffer.length;
        this.writePermits.release();
        return message;
    }

    public boolean trySend(T message) {
        if (message == null) {
            throw new IllegalArgumentException(
                "channel message cannot be null"
            );
        }
        if (this.writePermits.tryAcquire()) {
            this.completeAcquiredWrite(message);
            return true;
        }
        return false;
    }

    public T tryReceive() {
        if (this.readPermits.tryAcquire()) {
            return this.completeAcquiredRead();
        }
        return null;
    }

    public void send(T message) throws InterruptedException {
        if (message == null) {
            throw new IllegalArgumentException(
                "channel message cannot be null"
            );
        }
        this.writePermits.acquire();
        this.completeAcquiredWrite(message);
    }

    public T receive() throws InterruptedException {
        this.readPermits.acquire();
        return this.completeAcquiredRead();
    }
}
