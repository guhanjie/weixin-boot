package com.guhanjie.util;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * ID生成器
 * 
 * @author botao.liao
 * 
 */
public class IdGenerator {
    private static IdGenerator instance = new IdGenerator();
    private long workerId;
    private final static long twepoch = 1361753741828L;//2013-01-01
    private long sequence = 0L;
    private final static long workerIdBits = 8L;
    private final static long maxWorkerId = -1L ^ -1L << workerIdBits;
    private final static long sequenceBits = 5L;

    private final static long workerIdShift = sequenceBits;
    private final static long timestampLeftShift = sequenceBits + workerIdBits;
    private final static long sequenceMask = -1L ^ -1L << sequenceBits;

    private long lastTimestamp = -1L;

    private IdGenerator() {
        try {
            this.workerId = getWorkerId();
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static IdGenerator getInstance() {
        return instance;
    }

    /**
     * 15位
     * @return
     */
    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & sequenceMask;
            if (this.sequence == 0) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(String.format(
                        "Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp  - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (this.workerId << workerIdShift) | (this.sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
//        Calendar c = Calendar.getInstance();
//        c.set(2050, 1, 1);
//        return c.getTimeInMillis();
        return System.currentTimeMillis();
    }

    private static long getWorkerId() throws Exception {
        byte[] ip = InetAddress.getLocalHost().getAddress();
        long id = (0x000000FF & (long) ip[ip.length - 1]);
        return id;
    }

    public static String uuid() {
		return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
	}

    public static void main(String[] args) {
        IdGenerator generator = IdGenerator.getInstance();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            System.out.println(generator.nextId());
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

	/**
	 * 获取随机数字字符串
	 * @param size
	 * @return
	 */
	public static String randomNumCode(int size) {
		Random random = new Random();
		if(size < 1 || size > 1024){
			throw new IllegalAccessError("invalid size:"+size);
		}
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}
