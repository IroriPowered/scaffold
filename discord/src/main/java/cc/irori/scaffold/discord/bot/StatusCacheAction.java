package cc.irori.scaffold.discord.bot;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class StatusCacheAction {
    
    private static final int MAX_UPDATES = 2;
    private static final long TIME_WINDOW_MS = 10 * 60 * 1000;
    
    private volatile int cachedPlayerCount;
    private final Deque<Long> history = new ArrayDeque<>();
    
    private final Consumer<Integer> action;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    
    private boolean isTaskScheduled = false;
    
    public StatusCacheAction(Consumer<Integer> action) {
        this.action = action;
    }
    
    public synchronized void setPlayerCount(int count) {
        this.cachedPlayerCount = count;
        evaluateAndRun();
    }
    
    private synchronized void evaluateAndRun() {
        long now = System.currentTimeMillis();
        
        while (!history.isEmpty() && (now - history.peekFirst() > TIME_WINDOW_MS)) {
            history.pollFirst();
        }
        
        if (history.size() < MAX_UPDATES) {
            if (!isTaskScheduled) {
                runActionInternal();
            }
        } else {
            if (!isTaskScheduled) {
                scheduleAction();
            }
        }
    }
    
    private void scheduleAction() {
        isTaskScheduled = true;
        
        long oldestRunTime = history.peekFirst();
        long nextAvailableTime = oldestRunTime + TIME_WINDOW_MS;
        long delay = nextAvailableTime - System.currentTimeMillis();
        
        if (delay < 0) delay = 0;
        
        scheduler.schedule(() -> {
            synchronized (StatusCacheAction.this) {
                runActionInternal();
                
                isTaskScheduled = false;
                
                long now = System.currentTimeMillis();
                while (!history.isEmpty() && (now - history.peekFirst() > TIME_WINDOW_MS)) {
                    history.pollFirst();
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
    
    private void runActionInternal() {
        history.addLast(System.currentTimeMillis());
        action(this.cachedPlayerCount);
    }
    
    protected void action(int value) {
        action.accept(value);
    }
    
    public void shutdown() {
        scheduler.shutdown();
    }
}
