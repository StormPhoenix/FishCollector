package com.stormphoenix.fishcollector.network.progress;

/**
 * Created by StormPhoenix on 17-6-7.
 * StormPhoenix is a intelligent Android developer.
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
