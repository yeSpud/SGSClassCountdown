//
//  devSettings.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import UIKit

class devSettings: UIViewController {
    
    var update = Timer.scheduledTimer(timeInterval: 1.0, target: self, selector: Selector("updateView"), userInfo: nil, repeats: true)
    
    @IBOutlet weak var getWeekday: UILabel!
    
    @IBOutlet weak var getBlock: UILabel!
    
    @IBOutlet weak var getFormatTime: UILabel!
    
    @IBOutlet weak var back: NSLayoutConstraint!
    
    @IBAction func backPressed(_ sender: Any) {
        update.invalidate()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        //let fooTime = time()
        //getWeekday.text = fooTime.getWeekday()
        //getBlock.text = fooTime.getBlock()
        //getFormatTime.text = fooTime.getFormatTime()
    }
    
    func updateView() {
        let fooTime = time()
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
