//
//  time.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import Foundation

class time {
    
    func getFormatTime() -> String {
        
        let date = NSDate()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm:ss"
        let localDate = dateFormatter.string(from: date as Date)
        return localDate
        // Example retun:
        // 09:46:26
    }
    
    func getWeekday() -> String {
        let date = Date()
        let formatter = DateFormatter()
        formatter.timeZone = NSTimeZone() as TimeZone?
        formatter.dateFormat = "EEEE"
        let dayInWeek = formatter.string(from: date)
        return dayInWeek
    }
    
    func getBlock() -> String {
        
        var Block:String = "None"
        
        //let calendar = Calendar.current
        let hour = Int(getFormatTime().split(separator: ":")[0])
        //print(hour)
        let minutes = Int(getFormatTime().split(separator: ":")[1])
        //print(minutes)
        
        if (getWeekday().lowercased() == "monday" || getWeekday().lowercased() == "tuesday" || getWeekday().lowercased() == "friday") {
            // 8 - Period day
            
            if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 8, minute: 20) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 9, minute: 0)) {
                // A block
                Block = "A - normal";
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 9, minute: 5) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 9, minute: 45)) {
                // B block
               Block = "B - normal"
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 10, minute: 5) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 10, minute: 45)) {
                // C block
                Block = "C - normal"
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 10, minute: 50) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 11, minute: 30)) {
                // D block
                Block = "D - normal"
                
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 11, minute: 35) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 12, minute: 15)) {
                // E block
                Block = "E - normal"
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 13, minute: 0) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 13, minute: 40)) {
                // F block
                Block = "F - normal"
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 13, minute: 45) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 14, minute: 25)) {
                // G block
                Block = "G - normal"
            } else if (timeToLong(hour: hour!, minute: minutes!) >= timeToLong(hour: 14, minute: 30) && timeToLong(hour: hour!, minute: minutes!) < timeToLong(hour: 15, minute: 10)) {
                // H block
                Block = "H - normal"
            } else {
                Block = "None"
            }
 
        }
        
        return String(Block)
    }
    
    func timeToLong(hour:Int, minute:Int) -> Int {
        var longTime:Int = 0
        longTime = (hour * 60) + minute
        return longTime
    }
 
}
