package com.spud.sgsclasscountdownapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Stephen Ogden on 4/10/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Core {

    private Calendar calendar = Calendar.getInstance();

    private URL onlineDB = null;

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
        int h = time[0], m = time[1], s = time[2];
        long longTime, hoursToSeconds = h * 3600, minutesToSeconds = m * 60;
        longTime = s + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", Arrays.toString(time) + "->" + Long.toString(longTime));
        return longTime;
    }

    private long timeToLong(int hour, int minute, int second) {
        long longTime, hoursToSeconds = hour * 3600, minutesToSeconds = minute * 60;
        longTime = second + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", String.format("%s:%s:%s -> %s", hour, minute, second, longTime));
        return longTime;
    }

    // TODO: Create a system for special schedules
    Block getBlock() {
        Block block = Block.NoBlock;
        WeekType weekType = getWeekType();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (UpdateType.getUpdateType()) {
            case BuiltIn:
                weekType = getWeekType();
                break;
            case Automatic:
                // TODO: Finish this
                try {
                    onlineDB = new URL("https://raw.githubusercontent.com/jeffrypig23/SGSClassCountdown/database/database.json");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    weekType = getWeekType();
                }
                URLConnection connection = null;
                try {
                    connection = (HttpsURLConnection) onlineDB.openConnection();
                    connection.setReadTimeout(5000);

                    String data;

                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((br.readLine()) != null) {
                        data = br.readLine();
                    }
                    br.close();
                    ((HttpsURLConnection) connection).disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    weekType = getWeekType();
                }
                break;
            case ManualADay:
                dayOfWeek = Calendar.WEDNESDAY;
                weekType = WeekType.Long;
                break;
            case ManualEDay:
                dayOfWeek = Calendar.THURSDAY;
                weekType = WeekType.Long;
                break;
            case ManualFullDay:
                dayOfWeek = Calendar.MONDAY;
                weekType = WeekType.Normal;
                break;
            case ManualCustomDay:
                weekType = WeekType.Custom;
                break;
            default:
                weekType = getWeekType();
                break;

        }
        Log.i("WeekType", weekType.name());

        switch (weekType) {
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
                } else if (timeToLong(getTime()) > timeToLong(12, 15, 0) && timeToLong(getTime()) < timeToLong(12, 55, 0)) {
                    block = Block.LunchNormal;
                }
                break;
            case Long:
                Log.i("Schedule", "Long day");
                if (timeToLong(getTime()) > timeToLong(8, 20, 0) && timeToLong(getTime()) < timeToLong(9, 45, 9)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.ALong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.ELong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (timeToLong(getTime()) > timeToLong(10, 0, 0) && timeToLong(getTime()) < timeToLong(11, 25, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.BLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.FLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (timeToLong(getTime()) > timeToLong(12, 5, 0) && timeToLong(getTime()) < timeToLong(13, 30, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.CLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.GLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (timeToLong(getTime()) > timeToLong(13, 45, 0) && timeToLong(getTime()) < timeToLong(15, 10, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.DLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.HLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (timeToLong(getTime()) > timeToLong(11, 25, 0) && timeToLong(getTime()) < timeToLong(12, 0, 0)) {
                    block = Block.LunchLong;
                } else {
                    block = Block.NoBlock;
                }
                break;
        }
        Log.i("Block", block.name());
        return block;
    }

    // TODO: Create a system for special schedules
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
            case LunchNormal:
                checkTime = timeToLong(12, 55, 0);
                break;
            case LunchLong:
                checkTime = timeToLong(12, 0, 0);
                break;
            default:
                checkTime = timeToLong(15, 10, 0);
                break;
        }
        long seconds = checkTime - timeToLong(getTime()), minutes = seconds / 60;
        Log.i("Time remaining", String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60)));
        return String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60));
    }

    // TODO: Create a system for special schedules
    String changeBlock(Block block) {
        Database database = new Database();
        // https://stackoverflow.com/questions/6705955/why-switch-is-faster-than-if
        // https://stackoverflow.com/questions/798545/what-is-the-java-operator-called-and-what-does-it-do
        switch (block) {
            case ANormal:
                return (database.getBlockName(Block.ANormal).equals("")) ? "A block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ANormal));
            case BNormal:
                return (database.getBlockName(Block.BNormal).equals("")) ? "B block will end in:" : String.format("%s will end in:", database.getBlockName(Block.BNormal));
            case CNormal:
                return (database.getBlockName(Block.CNormal).equals("")) ? "C block will end in:" : String.format("%s will end in:", database.getBlockName(Block.CNormal));
            case DNormal:
                return (database.getBlockName(Block.DNormal).equals("")) ? "D block will end in:" : String.format("%s will end in:", database.getBlockName(Block.DNormal));
            case ENormal:
                return (database.getBlockName(Block.ENormal).equals("")) ? "E block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ENormal));
            case FNormal:
                return (database.getBlockName(Block.FNormal).equals("")) ? "F block will end in:" : String.format("%s will end in:", database.getBlockName(Block.FNormal));
            case GNormal:
                return (database.getBlockName(Block.GNormal).equals("")) ? "G block will end in:" : String.format("%s will end in:", database.getBlockName(Block.GNormal));
            case HNormal:
                return (database.getBlockName(Block.HNormal).equals("")) ? "H block will end in:" : String.format("%s will end in:", database.getBlockName(Block.HNormal));
            case ALong:
                return (database.getBlockName(Block.ALong).equals("")) ? "A block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ALong));
            case BLong:
                return (database.getBlockName(Block.BLong).equals("")) ? "B block will end in:" : String.format("%s will end in:", database.getBlockName(Block.BLong));
            case CLong:
                return (database.getBlockName(Block.CLong).equals("")) ? "C block will end in:" : String.format("%s will end in:", database.getBlockName(Block.CLong));
            case DLong:
                return (database.getBlockName(Block.DLong).equals("")) ? "D block will end in:" : String.format("%s will end in:", database.getBlockName(Block.DLong));
            case ELong:
                return (database.getBlockName(Block.ELong).equals("")) ? "E block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ELong));
            case FLong:
                return (database.getBlockName(Block.FLong).equals("")) ? "F block will end in:" : String.format("%s will end in:", database.getBlockName(Block.FLong));
            case GLong:
                return (database.getBlockName(Block.GLong).equals("")) ? "G block will end in:" : String.format("%s will end in:", database.getBlockName(Block.GLong));
            case HLong:
                return (database.getBlockName(Block.HLong).equals("")) ? "H block will end in:" : String.format("%s will end in:", database.getBlockName(Block.HLong));
            case LunchNormal:
                return "Lunch will be over in:";
            case LunchLong:
                return "Lunch will be over in:";
            default:
                return null;
        }
    }

    private WeekType getWeekType() {
        Log.i("Weekday", Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)));
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return WeekType.Weekend;
            case Calendar.MONDAY:
                return WeekType.Normal;
            case Calendar.TUESDAY:
                return WeekType.Normal;
            case Calendar.WEDNESDAY:
                return WeekType.Long;
            case Calendar.THURSDAY:
                return WeekType.Long;
            case Calendar.FRIDAY:
                return WeekType.Normal;
            case Calendar.SATURDAY:
                return WeekType.Weekend;
            default:
                return WeekType.Weekend;
        }
    }

    int[] getDate() {
        int date[] = new int[3];
        int day, month, year;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.WEEK_OF_YEAR);
        year = calendar.get(Calendar.YEAR);
        date[0] = month;
        date[1] = day;
        date[2] = year;

        Log.i("Date", Arrays.toString(date));
        return date;
    }

    void parseJson(String data) {
        JSONObject object = null;
        try {
            object = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object != null) {

        }
    }

}
