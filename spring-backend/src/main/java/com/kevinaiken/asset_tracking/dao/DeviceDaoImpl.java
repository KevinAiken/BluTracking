package com.kevinaiken.asset_tracking.dao;

import ch.qos.logback.core.joran.spi.ConsoleTarget;
import com.kevinaiken.asset_tracking.model.Device;
import com.kevinaiken.asset_tracking.util.Handler;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DeviceDaoImpl {
    private final HikariDataSource localds;

    @Autowired
    public DeviceDaoImpl(@Qualifier("localDataSource") HikariDataSource localds) {
        this.localds = localds;
    }

    public List<String[]> selectDevices(String missing){
        String query = "SELECT * FROM device";
        if(missing != null)
            query += " WHERE missing =" + missing;

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

    public List<String[]> selectDevice(String id) {
        String query = "SELECT * FROM device WHERE id =" + id;

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

    public void updateDevice(Device device, String id){
        String query = String.format("UPDATE device SET bt_addr = '%s', product = '%s' WHERE id =" + id, device.getBtAddr(), device.getName());

        System.out.println(device.getBtAddr() + " : " + device.getName());

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        try{
            System.out.println("updating device");
            //run.query(query, h);
            run.update(query);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }


    public void deleteDevice(String id){
        String query = "DELETE FROM device WHERE id =" + id;

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        try{
            run.update(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void insertDevice(Device device){
        String query = String.format("INSERT INTO device (bt_addr, product) VALUES ('%s', '%s')", device.getBtAddr(), device.getName());

        // Result handler is required, but not used.
        ResultSetHandler<Object> h = ((ResultSet rs) -> null);

        QueryRunner run;
        run = new QueryRunner(localds);

        try {
            System.out.println("inserting device into db");
            run.insert(query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int selectIDFromBTAddr(String addr){
        String query = String.format("SELECT id FROM device WHERE bt_addr='%s'", addr);

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run;
        run = new QueryRunner(localds);

        List<String[]> resultTable = null;
        try {
            resultTable = run.query(query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
        for(int i = 0; i < resultTable.size(); i++)
            for(int j = 0; j < resultTable.get(i).length; j++)
                System.out.println(resultTable.get(i)[j]);
        */

        System.out.println(resultTable.get(1)[0]);
        return Integer.parseInt(resultTable.get(1)[0]);
    }

    //Other table
    public void insertIntoDeletedTable(Device device){
        String query = String.format("INSERT INTO deletedDevice (bt_addr, product) VALUES ('%s', '%s')", device.getBtAddr(), device.getName());

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run = new QueryRunner(localds);

        try{
            System.out.println("Inserting device into deleted table!");
            run.insert(query, h);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<String[]> selectDeletedDevices(){
        String query = "SELECT * FROM deletedDevice";

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run = new QueryRunner(localds);

        List<String[]> resultTable = null;
        try{
            System.out.println("Getting all deleted devices!");
            run.query(query, h);
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultTable;
    }

    public void updateMissing(String id, int value) {
        String query = String.format("UPDATE device SET missing = %s WHERE id =" + id, value);

        ResultSetHandler<List<String[]>> h = Handler.tableToListString();

        QueryRunner run = new QueryRunner(localds);

        try{
            run.update(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
