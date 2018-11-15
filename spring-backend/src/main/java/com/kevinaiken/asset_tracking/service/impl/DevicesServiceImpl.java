package com.kevinaiken.asset_tracking.service.impl;

import com.kevinaiken.asset_tracking.dao.DeviceDaoImpl;
import com.kevinaiken.asset_tracking.model.Device;
import com.kevinaiken.asset_tracking.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DevicesServiceImpl implements DeviceService {

    private final DeviceDaoImpl deviceDao;

    @Autowired
    public DevicesServiceImpl(DeviceDaoImpl deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public List<Device> getDevices(String missing) {
        final int ID_COLUMN = 0;
        final int BT_ADDR_COLUMN = 1;
        final int NAME_COLUMN = 2;
        final int ENROLL_DATE_COLUMN = 3;
        final int MISSING_COLUMN = 4;
        List<Device> response = new ArrayList<>();
        List<String[]> stringDevices = deviceDao.selectDevices(missing);
        // i = 1 because first row is column names
        for(int i = 1; i < stringDevices.size(); i++) {
            response.add(new Device());
            response.get(i-1).setId(Integer.parseInt(stringDevices.get(i)[ID_COLUMN]));
            response.get(i-1).setBtAddr(stringDevices.get(i)[BT_ADDR_COLUMN]);
            response.get(i-1).setDateEnrolled(stringDevices.get(i)[ENROLL_DATE_COLUMN]);
            response.get(i-1).setName(stringDevices.get(i)[NAME_COLUMN]);
            response.get(i-1).setMissing(stringDevices.get(i)[MISSING_COLUMN]);
        }
        return response;
    }

    @Override
    public Device getDevice(String id) {
        final int ID_COLUMN = 0;
        final int BT_ADDR_COLUMN = 1;
        final int NAME_COLUMN = 2;
        final int ENROLL_DATE_COLUMN = 3;
        final int MISSING_COLUMN = 4;
        Device response = new Device();
        List<String[]> stringDevices = deviceDao.selectDevice(id);
        response.setId(Integer.parseInt(stringDevices.get(1)[ID_COLUMN]));
        response.setBtAddr(stringDevices.get(1)[BT_ADDR_COLUMN]);
        response.setDateEnrolled(stringDevices.get(1)[ENROLL_DATE_COLUMN]);
        response.setName(stringDevices.get(1)[NAME_COLUMN]);
        response.setMissing(stringDevices.get(1)[MISSING_COLUMN]);
        return response;
    }

    @Override
    public void insertDevices(final List<Device> request) {
        for(int i = 0; i < request.size(); i++){
            System.out.println("putting a log in");
            deviceDao.insertDevice(request.get(i));
        }
    }

    @Override
    public void updateDevice(String id, Device device) {
        deviceDao.updateDevice(device, id);
    }

    @Override
    public void deleteDevice(String id) {
        deviceDao.deleteDevice(id);
    }

}
