package com.icm.gestioncargaapi.utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class TimeController {
    @GetMapping
    public String getCurrentTimeInLima() {
        return TimeUtil.getCurrentTime();
    }
}
