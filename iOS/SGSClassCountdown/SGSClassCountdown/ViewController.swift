//
//  ViewController.swift
//  SGSClassCountdown
//
//  Created by Stephen Ogden on 4/13/18.
//  Copyright © 2018 Spud. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let timeFoo = time()
        print(timeFoo.getFormatTime())
        print(timeFoo.getBlock())
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .lightContent
    }

}

