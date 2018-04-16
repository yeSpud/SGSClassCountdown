//
//  time.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import Foundation

class time {
    
    func getWeekday() -> String {
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateFormat = "EEEE"
        let dayInWeek = formatter.string(from: date)
        return dayInWeek
    }
    
    func getBlock() -> String {
        
        var Block:String = "None"
        
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from: Date())
        let minutes = calendar.component(.minute, from: Date())
        
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
