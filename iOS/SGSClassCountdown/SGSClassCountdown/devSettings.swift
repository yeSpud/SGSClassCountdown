//
//  devSettings.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/16/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import UIKit

class devSettings: UIViewController {
    
    
    @IBOutlet weak var getWeekday: UILabel!
    
    @IBOutlet weak var getBlock: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let fooTime = time()
        getWeekday.text = fooTime.getWeekday()
        getBlock.text = fooTime.getBlock()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }
}
