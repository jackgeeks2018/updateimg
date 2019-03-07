package com.wpp.controller;

import com.wpp.service.QerallDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GetDate {
    @Autowired
    public QerallDate service;
    @RequestMapping("/")
    @ResponseBody
    public String GetDate(){
       return service.getdate();
    }
}
