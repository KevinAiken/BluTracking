package com.kevinaiken.asset_tracking.service;

import com.kevinaiken.asset_tracking.model.Log;

import java.util.List;

public interface LogService {

    /**
     * @param request request body
     */
     void insertLogs(final List<Log> request) throws Exception;

     List<Log> getLogs(String fromTime, String toTime, String deviceID);

     public void updateLog(String id, String fromTime, String toTime);

     public void deleteLog(String id);

     public Log getLog(String id);

     public void updateMissing(String deviceId, boolean missing);
    //deleteLogByID()

    //getLogByID()
}
