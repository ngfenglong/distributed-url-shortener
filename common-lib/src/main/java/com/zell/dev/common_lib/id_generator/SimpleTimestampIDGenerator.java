package com.zell.dev.common_lib.id_generator;

import org.springframework.stereotype.Service;

@Service
public class SimpleTimestampIDGenerator implements IDGenerator{
    private static final int MAX_SEQUENCE = 4095; // 12 bit -> 4096

    private long lastTimeStamp = -1L;
    private int sequence = 0;

    @Override
    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if(currentTimestamp == lastTimeStamp){
            if(sequence == MAX_SEQUENCE){
                currentTimestamp = waitNextMilis(currentTimestamp);
            }

            sequence++;
        }
        else {
            sequence = 0;
        }

        lastTimeStamp = currentTimestamp;
        return (currentTimestamp << 12) | sequence;
    }

    private long waitNextMilis(long timeStamp){
        long ts = System.currentTimeMillis();

        while(ts <= lastTimeStamp){
            ts = System.currentTimeMillis();
        }

        return ts;
    }
}
