package com.kevinaiken.asset_tracking.service.impl;

import com.kevinaiken.asset_tracking.dao.DeviceDaoImpl;
import com.kevinaiken.asset_tracking.dao.LogDaoImpl;
import com.kevinaiken.asset_tracking.model.Log;
import com.kevinaiken.asset_tracking.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LogServiceImpl implements LogService {

    private final LogDaoImpl logDao;
    private final DeviceDaoImpl deviceDao;

    @Autowired
    public LogServiceImpl(LogDaoImpl logDao, DeviceDaoImpl deviceDao){
        this.logDao = logDao;
        this.deviceDao = deviceDao;
    }

    @Override
    public void insertLogs(final List<Log> request){
        System.out.println(request.get(0).getBtAddr());
        if(request.get(0).getDeviceId() == null) {
            //need to set the deviceID based on bt_addr
            for(Log log : request){
                log.setDeviceId(deviceDao.selectIDFromBTAddr(log.getBtAddr()));
            }
        }
        for(int i = 0; i < request.size(); i++){
            System.out.println("putting a log in");
            logDao.insertLog(request.get(i));
        }
    }

    public List<Log> getLogs(String fromTime, String toTime, String deviceID) {
        final int ID_COLUMN = 0;
        final int TIME_COLUMN = 1;
        final int DEVICE_ID_COLUMN = 2;
        List<Log> response = new ArrayList<>();
        List<String[]> stringLogs = logDao.selectLogs(fromTime, toTime, deviceID);
        // start at 1 because first row is column names
        for(int i = 1; i < stringLogs.size(); i++) {
            response.add(new Log());
            response.get(i-1).setLogId(Integer.parseInt(stringLogs.get(i)[ID_COLUMN]));
            response.get(i-1).setTime(stringLogs.get(i)[TIME_COLUMN]);
            response.get(i-1).setDeviceId(Integer.parseInt(stringLogs.get(i)[DEVICE_ID_COLUMN]));
        }
        return response;
    }

    public Log getLog(String id){
        final int ID_COLUMN = 0;
        final int TIME_COLUMN = 1;
        final int DEVICE_ID_COLUMN = 2;
        List<String[]> stringLogs = logDao.getLog(id);
        Log log = new Log();
        log.setLogId(Integer.parseInt(stringLogs.get(0)[ID_COLUMN]));
        log.setTime(stringLogs.get(0)[TIME_COLUMN]);
        log.setDeviceId(Integer.parseInt(stringLogs.get(0)[DEVICE_ID_COLUMN]));
        return log;
    }

    public void updateLog(String id, String fromTime, String toTime){
        logDao.updateLog(id, fromTime, toTime);
    }

    public void deleteLog(String id){
        logDao.deleteLog(id);
    }

    @Override
    public void updateMissing(String deviceId, boolean missing) {
        int val = missing ? 1 : 0;
        deviceDao.updateMissing(deviceId, val);
    }
}
