package com.kevinaiken.asset_tracking.controller;

import com.kevinaiken.asset_tracking.model.Device;
import com.kevinaiken.asset_tracking.service.LogService;
import com.kevinaiken.asset_tracking.service.impl.DevicesServiceImpl;
import com.kevinaiken.asset_tracking.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DevicesController {

    private final DevicesServiceImpl deviceService;

    @Autowired
    public DevicesController(DevicesServiceImpl tagsService) {
        this.deviceService = tagsService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/devices")
    public List<Device> getDevices(@RequestParam (value="missing", required=false) String missing) {
        List<Device> response = deviceService.getDevices(missing);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/devices/{id}")
    public Device getDevice(@PathVariable String id) {
        Device response = deviceService.getDevice(id);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/devices")
    public void createDevices(
            @RequestHeader HttpHeaders requestHeader,
            @RequestBody final List<Device> requestBody)
        throws Exception{
        deviceService.insertDevices(requestBody);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/devices/{id}")
    public void updateDevices(@PathVariable String id,
                              @RequestBody final Device device){
        deviceService.updateDevice(id, device);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/devices/{id}")
    public void deleteDevice(@PathVariable String id){
        deviceService.deleteDevice(id);
    }
}