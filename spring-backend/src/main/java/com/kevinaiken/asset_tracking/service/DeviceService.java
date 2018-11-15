package com.kevinaiken.asset_tracking.service;

import com.kevinaiken.asset_tracking.model.Device;

import java.util.List;

public interface DeviceService {

    void insertDevices(final List<Device> request);

    void updateDevice(String id, Device device);

    void deleteDevice(String id);

    Device getDevice(String id);

    List<Device> getDevices(String missing);

}
