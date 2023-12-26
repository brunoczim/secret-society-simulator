package io.github.brunoczim.secsocsim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpscChannelTest {
    @Test
    void singleThreadOneshot() {
        SpscChannel<String> channel = new SpscChannel<>(1);
        assertDoesNotThrow(() -> channel.send("abc"));
        assertEquals(assertDoesNotThrow(() -> channel.receive()), "abc");
    }

    @Test
    void singleThreadOneshotTry() {
        SpscChannel<String> channel = new SpscChannel<>(1);
        assertTrue(channel.trySend("1234"));
        assertEquals(channel.tryReceive(), "1234");
    }

    @Test
    void singleThreadOneshotTryFail() {
        SpscChannel<String> channel = new SpscChannel<>(1);
        assertEquals(channel.tryReceive(), null);
        assertTrue(channel.trySend("abc"));
        assertFalse(channel.trySend("1234"));
    }

    @Test
    void singleThreadOneshotTryZero() {
        SpscChannel<String> channel = new SpscChannel<>(0);
        assertFalse(channel.trySend("$"));
        assertEquals(channel.tryReceive(), null);
    }

    @Test
    void singleThreadedBufferReuse() {
        SpscChannel<String> channel = new SpscChannel<>(3);

        assertDoesNotThrow(() -> channel.send("abc"));
        assertDoesNotThrow(() -> channel.send("1234"));
        assertEquals(channel.tryReceive(), "abc");
        assertTrue(channel.trySend("$"));
        assertEquals(assertDoesNotThrow(() -> channel.receive()), "1234");
        assertEquals(assertDoesNotThrow(() -> channel.receive()), "$");

        assertEquals(channel.tryReceive(), null);

        assertDoesNotThrow(() -> channel.send("def"));
        assertTrue(channel.trySend("5678"));
        assertEquals(assertDoesNotThrow(() -> channel.receive()), "def");
        assertDoesNotThrow(() -> channel.send("#"));
        assertEquals(channel.tryReceive(), "5678");
        assertEquals(assertDoesNotThrow(() -> channel.receive()), "#");

        assertEquals(channel.tryReceive(), null);

        assertDoesNotThrow(() -> channel.send("a"));
        assertDoesNotThrow(() -> channel.send("b"));
        assertDoesNotThrow(() -> channel.send("c"));
        assertFalse(channel.trySend("d"));
    }

    @Test
    void multiThreadedSpscNoWait() {
        final SpscChannel<Integer> channel = new SpscChannel<>(8000);
        Thread sender = new Thread(() -> {
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 8000; i++) {
                    channel.send(i);
                }
            });
        });

        sender.start();

        for (int i = 0; i < 8000; i++) {
            assertEquals(i, assertDoesNotThrow(() -> channel.receive()));
        }

        assertDoesNotThrow(() -> sender.join());
    }

    @Test
    void multiThreadedSpscWithWait() {
        final SpscChannel<Integer> channel = new SpscChannel<>(5);
        Thread sender = new Thread(() -> {
            assertDoesNotThrow(() -> {
                for (int i = 0; i < 8000; i++) {
                    channel.send(i);
                }
            });
        });

        sender.start();

        for (int i = 0; i < 8000; i++) {
            assertEquals(i, assertDoesNotThrow(() -> channel.receive()));
        }

        assertDoesNotThrow(() -> sender.join());
    }
}
