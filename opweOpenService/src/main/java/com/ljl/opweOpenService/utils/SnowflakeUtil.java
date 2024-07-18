package com.ljl.opweOpenService.utils;

/**
 * @Author Liu Jialin
 * @Date 2023/12/1 14:54
 * @PackageName com.ljl.ivtData.Utils
 * @ClassName SnowflakeUtil
 * @Description TODO
 * @Version 1.0.0
 */

public class SnowflakeUtil {
    /**
     * 组成部分 最高符号位 + 时间戳 + （机房id+机房id） + 序列号
     */

    // fixed timestamp 2022-08-12 20:30:00
    private static final long FIX_TIME_STAMP = 1660307400L;

    // computer Room Id
    private final long computerRoomId;

    // machine Id
    private final long machineId;

    // sequence number
    private long sequence = 0L;

    /**
     * 所占用的bit个数
     */

    // 41bit timestamp

    // 5bit computer Room id
    private static final long COMPUTER_ROOM_BIT_CNT = 5L;

    // 5bit machine id
    private static final long MACHINE_BIT_CNT = 5L;

    // 12bit sequence number
    private static final long SEQUENCE_BIT_CNT = 12L;

    /**
     * 位移的位数
     */

    // 机器id 左移12位
    private static final long MACHINE_ID_SHIFT = SEQUENCE_BIT_CNT;

    // 机房id 左移17位
    private static final long COMPUTER_ROOM_ID_SHIFT = MACHINE_ID_SHIFT + MACHINE_BIT_CNT;

    // 时间戳 左移22位
    private final static long TIME_STAMP_SHIFT = COMPUTER_ROOM_ID_SHIFT + COMPUTER_ROOM_BIT_CNT;

    /**
     * 聚合信息
     */

    // 支持最大的机房id机房id  5bit
    private static final long MAX_COMPUTER_ROOM_ID = ~(-1 << COMPUTER_ROOM_BIT_CNT);

    // 支持最大的机器id 5bit
    private static final long MAX_MACHINE_ID = ~(-1 << MACHINE_BIT_CNT);

    // 序列号支持的最大的个数 12bit
    private static final long SEQUENCE_MASK = ~(-1 << SEQUENCE_BIT_CNT);

    // 上一次生成的时间戳
    private long lastTimeStamp = -1L;

    /**
     * @param computerRoomId 机房id
     * @param machineId      机器id
     */
    public SnowflakeUtil(long computerRoomId, long machineId) {
        if (computerRoomId < 0 || computerRoomId > MAX_COMPUTER_ROOM_ID) {
            throw new IllegalArgumentException("computerRoomId 不在范围");
        }

        if (machineId < 0 || machineId > MAX_MACHINE_ID) {
            throw new IllegalArgumentException("computerRoomId 不在范围");
        }

        this.computerRoomId = computerRoomId;
        this.machineId = machineId;
    }

    /**
     * @return 返回毫秒级时间戳
     */
    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * @return 雪花刷法生成 id
     */
    public synchronized long getNextId() {
        // 拿到时间戳
        long currentTimeStamp = getCurrentTime();

        // 时间戳回拨问题
        if (currentTimeStamp < lastTimeStamp) {
            throw new RuntimeException(
                    String.format("Server time is not matched，please check server time。current server timestamp:%d,last timestamp:%d", currentTimeStamp,
            lastTimeStamp));
        }

        // 时间为同一毫秒时间,sequence + 1
        if (currentTimeStamp == lastTimeStamp) {
            // 序列号 + 1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 序列号用完
            if (sequence == 0) {
                // 获取下一个毫秒级
                currentTimeStamp = getNextMillis();
            }
        } else {
            sequence = 0;
        }

        // 记录上一次时间戳
        lastTimeStamp = currentTimeStamp;
        // 生成唯一id
        return ((currentTimeStamp - FIX_TIME_STAMP) << TIME_STAMP_SHIFT) |
                (computerRoomId << COMPUTER_ROOM_ID_SHIFT) |
                (machineId << MACHINE_ID_SHIFT) |
                sequence;
    }

    /**
     * @return 下一毫秒
     */
    private long getNextMillis() {
        long currentTimeStamp = getCurrentTime();
        while (currentTimeStamp <= lastTimeStamp) {
            currentTimeStamp = getCurrentTime();
        }
        return currentTimeStamp;
    }

}
