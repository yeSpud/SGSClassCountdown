package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Calendar;

import static com.spud.sgsclasscountdownapp.WeekType.getWeekType;

// TODO: Create a system for special schedules

/**
 * Created by Stephen Ogden on 10/3/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public enum Block {
    ANormal,
    BNormal,
    CNormal,
    DNormal,
    ENormal,
    FNormal,
    GNormal,
    HNormal,
    ALong,
    BLong,
    CLong,
    DLong,
    ELong,
    FLong,
    GLong,
    HLong,
    NoBlock,
    LunchNormal,
    LunchLong,
    Special;

    @Deprecated
    static Block getBlock() {
        Block block = NoBlock;
        WeekType weekType;
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Core core = new Core();

        switch (UpdateType.getUpdateType()) {
            case BuiltIn:
                weekType = getWeekType();
                break;
            case Automatic:
                // TODO: Finish this
                weekType = getWeekType();
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

        // TODO: Redo all this to get times from json files
        switch (weekType) {
            case Normal:
                Log.d("Schedule", "Full day");
                if (core.timeToLong(core.getTime()) > core.timeToLong(8, 20, 0) && core.timeToLong(core.getTime()) < core.timeToLong(9, 0, 0)) {
                    block = Block.ANormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(9, 5, 0) && core.timeToLong(core.getTime()) < core.timeToLong(9, 45, 0)) {
                    block = Block.BNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(10, 0, 0) && core.timeToLong(core.getTime()) < core.timeToLong(10, 45, 0)) {
                    block = Block.CNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(10, 50, 0) && core.timeToLong(core.getTime()) < core.timeToLong(11, 30, 0)) {
                    block = Block.DNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(11, 35, 0) && core.timeToLong(core.getTime()) < core.timeToLong(12, 15, 0)) {
                    block = Block.ENormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(13, 0, 0) && core.timeToLong(core.getTime()) < core.timeToLong(13, 40, 0)) {
                    block = Block.FNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(13, 45, 0) && core.timeToLong(core.getTime()) < core.timeToLong(14, 25, 0)) {
                    block = Block.GNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(14, 30, 0) && core.timeToLong(core.getTime()) < core.timeToLong(15, 10, 0)) {
                    block = Block.HNormal;
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(12, 15, 0) && core.timeToLong(core.getTime()) < core.timeToLong(12, 55, 0)) {
                    block = Block.LunchNormal;
                }
                break;
            case Long:
                Log.d("Schedule", "Long day");
                if (core.timeToLong(core.getTime()) > core.timeToLong(8, 20, 0) && core.timeToLong(core.getTime()) < core.timeToLong(9, 45, 9)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.ALong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.ELong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(10, 0, 0) && core.timeToLong(core.getTime()) < core.timeToLong(11, 25, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.BLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.FLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(12, 5, 0) && core.timeToLong(core.getTime()) < core.timeToLong(13, 30, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.CLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.GLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(13, 45, 0) && core.timeToLong(core.getTime()) < core.timeToLong(15, 10, 0)) {
                    if (dayOfWeek == Calendar.WEDNESDAY || UpdateType.getUpdateType().equals(UpdateType.ManualADay)) {
                        block = Block.DLong;
                    } else if (dayOfWeek == Calendar.THURSDAY || UpdateType.getUpdateType().equals(UpdateType.ManualEDay)) {
                        block = Block.HLong;
                    } else {
                        block = Block.NoBlock;
                    }
                } else if (core.timeToLong(core.getTime()) > core.timeToLong(11, 25, 0) && core.timeToLong(core.getTime()) < core.timeToLong(12, 0, 0)) {
                    block = Block.LunchLong;
                } else {
                    block = Block.NoBlock;
                }
                break;
        }
        Log.i("Block", block.name());
        return block;
    }

}
