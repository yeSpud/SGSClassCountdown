package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 4/10/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Core {

    private Calendar calendar = Calendar.getInstance();

    public int[] getTime() {
        int s = calendar.get(Calendar.SECOND), m = calendar.get(Calendar.MINUTE), h = calendar.get(Calendar.HOUR_OF_DAY);
        int[] time = new int[3];
        time[0] = h;
        time[1] = m;
        time[2] = s;
        Log.i("Formatted time", Arrays.toString(time));
        return time;
    }

    private long timeToLong(int[] time) {
        long longTime;
        int h = time[0], m = time[1], s = time[2];
        long hoursToSeconds = h * 3600, minutesToSeconds = m * 60;
        longTime = s + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", Arrays.toString(time) + "->" + Long.toString(longTime));
        return longTime;
    }

    private long timeToLong(int hour, int minute, int second) {
        long longTime;
        long hoursToSeconds = hour * 3600;
        long minutesToSeconds = minute * 60;
        longTime = second + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", String.format("%s:%s:%s -> %s", hour, minute, second, longTime));
        return longTime;
    }

    Block getBlock() {
        Block block = Block.NoBlock;

        weekType weekday = getWeekType();

        // TODO: Work with database
        /*
        if (database.is_a_thing()) {
            if (database.getUpdateType().equals(Database.updateType.Automatic)) {
                // TODO: Get from URL
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else if (database.getUpdateType().equals(Database.updateType.ManualADay)) {
                weekday = "Wednesday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualEDay)) {
                weekday = "Thursday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualFullDay)) {
                weekday = "Monday";
            } else if (database.getUpdateType().equals(Database.updateType.BuiltIn)) {
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else {
                Log.e("E", "Cannot identify the updateType from database");
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            }
            Log.i("Weekday", weekday);
        } else {
            weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        }
        */

        switch (weekday) {
            case Normal:
                Log.i("Schedule", "Full day");
                if (timeToLong(getTime()) > timeToLong(8, 20, 0) && timeToLong(getTime()) < timeToLong(9, 0, 0)) {
                    block = Block.ANormal;
                } else if (timeToLong(getTime()) > timeToLong(9, 5, 0) && timeToLong(getTime()) < timeToLong(9, 45, 0)) {
                    block = Block.BNormal;
                } else if (timeToLong(getTime()) > timeToLong(10, 0, 0) && timeToLong(getTime()) < timeToLong(10, 45, 0)) {
                    block = Block.CNormal;
                } else if (timeToLong(getTime()) > timeToLong(10, 50, 0) && timeToLong(getTime()) < timeToLong(11, 30, 0)) {
                    block = Block.DNormal;
                } else if (timeToLong(getTime()) > timeToLong(11, 35, 0) && timeToLong(getTime()) < timeToLong(12, 15, 0)) {
                    block = Block.ENormal;
                } else if (timeToLong(getTime()) > timeToLong(13, 0, 0) && timeToLong(getTime()) < timeToLong(13, 40, 0)) {
                    block = Block.FNormal;
                } else if (timeToLong(getTime()) > timeToLong(13, 45, 0) && timeToLong(getTime()) < timeToLong(14, 25, 0)) {
                    block = Block.GNormal;
                } else if (timeToLong(getTime()) > timeToLong(14, 30, 0) && timeToLong(getTime()) < timeToLong(15, 10, 0)) {
                    block = Block.HNormal;
                }
                break;
            case Long:
                Log.i("Schedule", "Long day");
                if (timeToLong(getTime()) > timeToLong(8, 20, 0) && timeToLong(getTime()) < timeToLong(9, 45, 9)) {
                    block = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? Block.ALong : (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ? Block.ELong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(10, 0, 0) && timeToLong(getTime()) < timeToLong(11, 25, 0)) {
                    block = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? Block.BLong : (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ? Block.FLong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(12, 5, 0) && timeToLong(getTime()) < timeToLong(13, 30, 0)) {
                    block = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? Block.CLong : (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ? Block.GLong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(13, 45, 0) && timeToLong(getTime()) < timeToLong(15, 10, 0)) {
                    block = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? Block.DLong : (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY ? Block.HLong : Block.NoBlock));
                } else {
                    block = Block.NoBlock;
                }
                break;
        }
        Log.i("Block", block.name());
        return block;
    }

    String getTimeRemaining() {
        long checkTime;
        // https://stackoverflow.com/questions/6705955/why-switch-is-faster-than-if
        switch (getBlock()) {
            case ANormal:
                checkTime = timeToLong(9, 0, 0);
                break;
            case BNormal:
                checkTime = timeToLong(9, 45, 0);
                break;
            case CNormal:
                checkTime = timeToLong(10, 45, 0);
                break;
            case DNormal:
                checkTime = timeToLong(11, 30, 0);
                break;
            case ENormal:
                checkTime = timeToLong(12, 15, 0);
                break;
            case FNormal:
                checkTime = timeToLong(13, 40, 0);
                break;
            case GNormal:
                checkTime = timeToLong(14, 25, 0);
                break;
            case HNormal:
                checkTime = timeToLong(15, 10, 0);
                break;
            case ALong:
                checkTime = timeToLong(9, 45, 0);
                break;
            case BLong:
                checkTime = timeToLong(11, 25, 0);
                break;
            case CLong:
                checkTime = timeToLong(13, 30, 0);
                break;
            case DLong:
                checkTime = timeToLong(15, 10, 0);
                break;
            case ELong:
                checkTime = timeToLong(9, 45, 0);
                break;
            case FLong:
                checkTime = timeToLong(11, 25, 0);
                break;
            case GLong:
                checkTime = timeToLong(13, 30, 0);
                break;
            case HLong:
                checkTime = timeToLong(15, 10, 0);
                break;
            default:
                checkTime = timeToLong(15, 10, 0);
                break;
        }
        long seconds = checkTime - timeToLong(getTime()), minutes = seconds / 60;
        Log.i("Time remaining", String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60)));
        return String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60));
    }

    String changeBlock(Block block) {

        // TODO: Set it up so that it gets the class name, unless its blank
        // https://stackoverflow.com/questions/6705955/why-switch-is-faster-than-if
        switch (block) {
            case ANormal:
                return "A block will end in:";
            case BNormal:
                return "B block will end in:";
            case CNormal:
                return "C block will end in:";
            case DNormal:
                return "D block will end in:";
            case ENormal:
                return "E block will end in";
            case FNormal:
                return "F block will end in";
            case GNormal:
                return "G block will end in";
            case HNormal:
                return "H block will end in";
            case ALong:
                return "A block will end in";
            case BLong:
                return "B block will end in";
            case CLong:
                return "C block will end in";
            case DLong:
                return "D block will end in";
            case ELong:
                return "E block will end in";
            case FLong:
                return "F block will end in";
            case GLong:
                return "G block will end in";
            case HLong:
                return "H block will end in";
            default:
                return null;
        }
    }

    private weekType getWeekType() {

        Log.i("Weekday", Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return weekType.Weekend;
            case Calendar.MONDAY:
                return weekType.Normal;
            case Calendar.TUESDAY:
                return weekType.Normal;
            case Calendar.WEDNESDAY:
                return weekType.Long;
            case Calendar.THURSDAY:
                return weekType.Long;
            case Calendar.FRIDAY:
                return weekType.Normal;
            case Calendar.SATURDAY:
                return weekType.Weekend;
            default:
                return weekType.Weekend;
        }

    }

    enum weekType {
        Weekend,
        Normal,
        Long
    }

}
