//
//  time.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import Foundation

class time {
    
    func getFormatTime() -> Date {
        let date = NSDate();
        let dateFormatter = DateFormatter()
        //To prevent displaying either date or time, set the desired style to NoStyle.
        dateFormatter.timeStyle = DateFormatter.Style.medium //Set time style
        dateFormatter.dateStyle = DateFormatter.Style.medium //Set date style
        dateFormatter.timeZone = NSTimeZone.local
        let localDate = dateFormatter.string(from: date as Date)
        //print("LocalDate:")
        print(localDate)
        print((dateFormatter.date(from:localDate)!))
        return dateFormatter.date(from:localDate)!
    }
    
    func getWeekday() -> String {
        let date = getFormatTime()
        let formatter = DateFormatter()
        formatter.timeZone = NSTimeZone() as TimeZone?
        formatter.dateFormat = "EEEE"
        let dayInWeek = formatter.string(from: date)
        return dayInWeek
    }
    
    func getBlock() -> String {
        
        var Block:String = "None"
        
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from: getFormatTime())
        let minutes = calendar.component(.minute, from: getFormatTime())
        
        if (getWeekday().lowercased() == "monday" || getWeekday().lowercased() == "tuesday" || getWeekday().lowercased() == "friday") {
            // 8 - Period day
            if (hour == 8 && (minutes < 0 && minutes >= 20)) {
                // A block
                Block = "A - normal";
            } else if (hour == 9 && (minutes >= 05 && minutes < 45)) {
                // B Block
                Block = "B - normal"
            }
        }
        
        return String(Block)
    }
}
