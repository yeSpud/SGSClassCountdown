package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Stephen Ogden on 10/9/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
enum WeekType {
    Weekend,
    Normal,
    Long,
    Custom;

    static WeekType getWeekType() {

        WeekType returnWeek = Weekend;

        DatabaseFile databaseFile = new DatabaseFile();
        if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.BuiltIn)) {
            int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            Log.d("DayOfWeek", Integer.toString(dayOfWeek));
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    returnWeek = Weekend;
                    break;
                case Calendar.MONDAY:
                    returnWeek = Normal;
                    break;
                case Calendar.TUESDAY:
                    returnWeek = Normal;
                    break;
                case Calendar.WEDNESDAY:
                    returnWeek = Long;
                    break;
                case Calendar.THURSDAY:
                    returnWeek = Long;
                    break;
                case Calendar.FRIDAY:
                    returnWeek = Normal;
                    break;
                case Calendar.SATURDAY:
                    returnWeek = Weekend;
                    break;
                default:
                    returnWeek = Weekend;
                    break;
            }
        } else if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.ManualFullDay)) {
            returnWeek = Normal;
        } else if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.ManualADay)) {
            returnWeek = Long;
        } else if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.ManualEDay)) {
            returnWeek = Long;
        } else if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.Automatic)) {
            // TODO: Add automatic updates
        } else if (databaseFile.getUpdateTypeFromDatabase().equals(UpdateType.ManualCustomDay)) {
            returnWeek = Custom;
        }

        Log.i("ReturnWeek", returnWeek.name());
        return returnWeek;
    }

}
