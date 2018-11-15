package com.kevinaiken.asset_tracking.controller;

import com.kevinaiken.asset_tracking.model.Log;
import com.kevinaiken.asset_tracking.service.LogService;
import com.kevinaiken.asset_tracking.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class LogsController {
    private final LogService logService;

    @Autowired
    public LogsController(LogService logService){
        this.logService = logService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.OK)
    public void createLogs(
            @RequestHeader HttpHeaders requestHeader,
            @RequestBody final List<Log> requestBody)
            throws Exception{
        System.out.println("putting logs in");
        logService.insertLogs(requestBody);
        //send the request to the service, let the service run a for loop adding each log with the DAO
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/logs")
    public List<Log> getLogs(
            @RequestParam(value="fromTime", required=false) String fromTime,
            @RequestParam(value="toTime", required=false) String toTime,
            @RequestParam(value="deviceID", required=false) String deviceID){

        List<Log> response = logService.getLogs(fromTime, toTime, deviceID);

        for(Log log : response)
            getChance(log.getDeviceId() + "");

        return response;
    }

    //no need
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/logs/{id}")
    public void updateLog(@PathVariable String id, @PathVariable String fromTime, @PathVariable String toTime) {
        logService.updateLog(id, fromTime, toTime);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/logs/{id}")
    public void deleteLog(@PathVariable String id){
        System.out.println("Deleting Log " + id);
        logService.deleteLog(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/logs/{id}")
    public Log getLog(@PathVariable String id){
        return logService.getLog(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/logs/lastSeen/{deviceId}")
    public String lastSeen(@PathVariable String deviceId){
        List<Log> response = logService.getLogs(null,null,deviceId);
        return response.get(response.size()-1).getTime();
    }

    //Checks the chance that the device is still active using the last logs used
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/logs/deviceChance/{deviceId}")
    public float getChance(@PathVariable String deviceId){
        String time = lastSeen(deviceId);
        int day = Integer.parseInt(time.substring(8,10));
        int hours = Integer.parseInt(time.substring(11,13));
        int min = Integer.parseInt(time.substring(14,16));
        int sec = Integer.parseInt(time.substring(17,19));

        Date date = new Date();
        String d = date.toString();
        System.out.println(day + " : " + hours + " : " + min + " : " + sec);
        System.out.println(d);
        int curDay = Integer.parseInt(d.substring(8,10));
        int curHours = Integer.parseInt(d.substring(11,13));
        int curMin = Integer.parseInt(d.substring(14,16));
        int curSec = Integer.parseInt(d.substring(17,19));

        int dDay = curDay - day;
        int dHours;
        int dMin;
        int dSec;

        dHours = curHours - hours;
        dMin = curMin - min;
        dSec = curSec - sec;

        if(dSec < 0){
            dMin--;
            dSec += 60;
        }
        if(dMin < 0){
            dHours--;
            dMin += 60;
        }
        if(dHours < 0){
            dDay--;
            dHours += 24;
        }

        float prob = (float)((dDay) + (dHours) + (dMin * .1f));

        if(prob > .7)
            logService.updateMissing(deviceId, true);
        else
            logService.updateMissing(deviceId, false);

        return prob;
    }
}
