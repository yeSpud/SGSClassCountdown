//
//  ViewController.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/13/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var timer: Timer?
    
    let fooTime = time()
    
    @IBOutlet weak var block: UILabel!
    
    @IBOutlet weak var endTime: UILabel!
    
    @IBOutlet weak var countdown: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(update), userInfo: nil, repeats: true)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
    
    @objc func update() {
        //print(fooTime.timeRemaining(hour: 23, minute: 0))
        if ((!(fooTime.getWeekday().lowercased() == "sunday") && !(fooTime.getWeekday().lowercased() == "saturday")) && !(fooTime.getBlock() == "None")) {
            block.text = (String(fooTime.getBlock().first!))
            block.text?.append(" block")
            if (fooTime.getWeekday().lowercased() == "monday" || fooTime.getWeekday().lowercased() == "tuesday" || fooTime.getWeekday().lowercased() == "friday") {
                // 8 period day
                if (fooTime.getBlock().lowercased().contains("a -")) {
                    // A block
                    endTime.text = "9:00"
                    countdown.text = fooTime.timeRemaining(hour: 9, minute: 0)
                } else if (fooTime.getBlock().lowercased().contains("b -")) {
                    // B block
                    endTime.text = "9:45"
                    countdown.text = fooTime.timeRemaining(hour: 9, minute: 45)
                } else if (fooTime.getBlock().lowercased().contains("c -")) {
                    // C block
                    endTime.text = "10:45"
                    countdown.text = fooTime.timeRemaining(hour: 10, minute: 45)
                } else if (fooTime.getBlock().lowercased().contains("d -")) {
                    // D block
                    endTime.text = "11:30"
                    countdown.text = fooTime.timeRemaining(hour: 11, minute: 30)
                } else if (fooTime.getBlock().lowercased().contains("e -")) {
                    // E block
                    endTime.text = "12:15"
                    countdown.text = fooTime.timeRemaining(hour: 12, minute: 15)
                } else if (fooTime.getBlock().lowercased().contains("f -")) {
                    // F block
                    endTime.text = "1:40"
                    countdown.text = fooTime.timeRemaining(hour: 13, minute: 40)
                } else if (fooTime.getBlock().lowercased().contains("g -")) {
                    // G block
                    endTime.text = "2:25"
                    countdown.text = fooTime.timeRemaining(hour: 14, minute: 25)
                } else if (fooTime.getBlock().lowercased().contains("h -")) {
                    // H block
                    endTime.text = "3:10"
                    countdown.text = fooTime.timeRemaining(hour: 15, minute: 10)
                } else {
                    endTime.text = "3:10"
                    countdown.text = fooTime.timeRemaining(hour: 15, minute: 10)
                }
            } else if (fooTime.getWeekday().lowercased() == "wednesday" || fooTime.getWeekday().lowercased() == "thursday") {
                // Long block days
                
                if (fooTime.getBlock().lowercased().contains("a -") || fooTime.getBlock().lowercased().contains("e -")) {
                    endTime.text = "9:45"
                    countdown.text = fooTime.timeRemaining(hour: 9, minute: 45)
                } else if (fooTime.getBlock().lowercased().contains("b -") || fooTime.getBlock().lowercased().contains("f -")) {
                    endTime.text = "11:25"
                    countdown.text = fooTime.timeRemaining(hour: 11, minute: 25)
                } else if (fooTime.getBlock().lowercased().contains("c -") || fooTime.getBlock().lowercased().contains("g -")) {
                    endTime.text = "1:30"
                    countdown.text = fooTime.timeRemaining(hour: 13, minute: 30)
                } else if (fooTime.getBlock().lowercased().contains("d -") || fooTime.getBlock().lowercased().contains("h -")) {
                    endTime.text = "3:10"
                    countdown.text = fooTime.timeRemaining(hour: 15, minute: 10)
                } else {
                    endTime.text = "3:10"
                    countdown.text = fooTime.timeRemaining(hour: 15, minute: 10)
                }
            }
        } else {
            block.text = "- Block"
            endTime.text = "--:--"
            countdown.text = "--:--"
        }
    }

    @IBAction func devSettings(_ sender: Any) {
        timer?.invalidate()
    }
}

