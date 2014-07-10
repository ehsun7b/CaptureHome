/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ehsunbehravesh.capturehome;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Ehsun Behravesh
 */
public class Main {
    private static int interval;
    private static String uploadUrl;
    
    public static void main(String[] args) throws MalformedURLException {
        /*if (args.length != 2) {
            System.out.println("please enter interval and upload URL as parameters.");
            System.exit(1);
        }*/
        
//        interval = Integer.parseInt(args[0]);
  //      uploadUrl = args[1];
        
        CaptureUploader cu = new CaptureUploader(null, new File("."), 2000);
        cu.start();
    }
}
