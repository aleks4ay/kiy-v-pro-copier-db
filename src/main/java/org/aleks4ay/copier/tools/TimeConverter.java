package org.aleks4ay.copier.tools;

import java.time.LocalTime;

public final class TimeConverter {
    public static long convertStrToTimeMillisecond(String s1) {
        if (s1 == null) {
            return 0L;
        }
        long result = 0L;
        int[] setValue = new int[s1.length()];
        for (int i = 0; i < s1.length(); i++) {
            setValue[i] = s1.charAt(s1.length()-i-1);

            if (setValue[i] > 64) {
                setValue[i] -= 55;
            }
            else {
                setValue[i] -= 48;
            }
            result += setValue[i] * Math.pow(36, i);
        }

        return result/10;
    }

    public static LocalTime convertStrToLocalTime(String s1) {
        long millisecond = convertStrToTimeMillisecond(s1);
        int hour = (int) (millisecond/(60 * 60 * 1000));
        int minute = (int) (millisecond/(60 * 1000) - 60 * hour);
        int second = (int) (millisecond/1000 - 60 * 60 * hour - 60 * minute);
        return LocalTime.of(hour, minute, second);
    }
}
