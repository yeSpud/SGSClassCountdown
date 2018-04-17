//
//  devSettings.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import UIKit

class devSettings: UIViewController {
    
    var timer: Timer?
    
    let fooTime = time()
    
    @IBOutlet weak var getWeekday: UILabel!
    
    @IBOutlet weak var getBlock: UILabel!
    
    @IBOutlet weak var getFormatTime: UILabel!
    
    @IBOutlet weak var back: NSLayoutConstraint!
    
    @IBAction func backPressed(_ sender: Any) {
        timer?.invalidate()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(update), userInfo: nil, repeats: true)
    }
    
    @objc func update() {
        getWeekday.text = fooTime.getWeekday()
        getBlock.text = fooTime.getBlock()
        getFormatTime.text = fooTime.getFormatTime()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
}
