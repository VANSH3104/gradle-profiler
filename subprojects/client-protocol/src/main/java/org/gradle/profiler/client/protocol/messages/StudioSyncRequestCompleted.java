package org.gradle.profiler.client.protocol.messages;

public class StudioSyncRequestCompleted implements Message {

    public enum StudioSyncRequestResult {
        SUCCEEDED,
        FAILED,
        SKIPPED
    }

    private final int id;
    private final long durationMillis;
    private final StudioSyncRequestResult result;

    public StudioSyncRequestCompleted(int id, long durationMillis, StudioSyncRequestResult result) {
        this.id = id;
        this.durationMillis = durationMillis;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public StudioSyncRequestResult getResult() {
        return result;
    }
}