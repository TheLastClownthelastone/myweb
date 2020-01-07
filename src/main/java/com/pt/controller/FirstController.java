package com.pt.controller;

import com.pt.annition.PtAnno;
import com.pt.annition.PtGet;

/**
 * @Author :pt
 * @Date :Created in 17:21 2020/1/6
 */
@PtAnno
public class FirstController {

    @PtGet(value = "say",comsumer = "application/json",procude = "application/json")
    public String say(String say){
        return say;
    }
}
