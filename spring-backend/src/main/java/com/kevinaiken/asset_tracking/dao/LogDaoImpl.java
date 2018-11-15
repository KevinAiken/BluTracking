package com.kevinaiken.asset_tracking.dao;

import com.kevinaiken.asset_tracking.model.Log;
import com.kevinaiken.asset_tracking.util.Handler;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class LogDaoImpl {
    private final HikariDataSource localds;

    @Autowired
    public LogDaoImpl(@Qualifier("localDataSource") HikariDataSource localds) {
        this.localds = localds;
    }

    public void insertLog(Log log){
        String query = String.format("INSERT INTO log (time, device_id) VALUES ('%s', '%s')", log.getTime(), log.getDeviceId());

        // Result handler is required, but not used.
        ResultSetHandler<Object> h = ((ResultSet rs) -> null);

        QueryRunner run;
        run = new QueryRunner(localds);

        try {
            System.out.println("inserting into db");
            run.insert(query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> selectLogs(String fromTime, String toTime, String deviceID) {
        String query = "SELECT * FROM log";
        if(fromTime != null && toTime != null && deviceID != null){
            query += String.format(" WHERE device_id = %s AND time between '%s' AND '%s'",
                    deviceID, fromTime, toTime);
        } else if(fromTime != null && toTime != null){
            query += String.format(" WHERE time between '%s' and '%s'", fromTime, toTime);
        } else if(fromTime != null && deviceID != null){
            query += String.format(" WHERE time > '%s' AND device_id = %s", fromTime, deviceID);
        } else if(toTime != null && deviceID != null){
            query += String.format(" WHERE time < '%s' AND device_id = %s", toTime, deviceID);
        } else if (deviceID != null) {
            query += String.format(" WHERE device_ID = %s", deviceID);
        } else if(toTime != null) {
            query += String.format(" WHERE time < '%s'", toTime);
        } else if(fromTime != null) {
            query += String.format(" WHERE time > '%s'", fromTime);
        }

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        List<String[]> resultTable = null;
        try {
            resultTable = run.query(query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultTable;
    }

    public List<String[]> getLog(String id){

        String query = "SELECT * FROM log WHERE id =" + id;

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        List<String[]> resultTable = null;
        try {
            resultTable = run.query(query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultTable;
    }

    public void updateLog(String id, String fromTime, String toTime){
        String query = "UPDATE log SET ";
    }

    public void deleteLog(String id){
        String query = "DELETE FROM log WHERE id =" + id;

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        try{
            run.update(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteLogsByDeviceId(String deviceId){
        String query = "DELETE FROM log WHERE device_id =" + deviceId;

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run = new QueryRunner(localds);

        try{
            run.update(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
