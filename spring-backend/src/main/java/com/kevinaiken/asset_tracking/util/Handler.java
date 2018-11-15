package com.kevinaiken.asset_tracking.util;


import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    static public ResultSetHandler<List<String[]>> tableToListString() {
        return (ResultSet rs) -> {
            int nCol = rs.getMetaData().getColumnCount();
            List<String[]> table = new ArrayList<>();

            String[] columnNames = new String[nCol];
            for(int i = 0; i < nCol; i++) {
                columnNames[i] = rs.getMetaData().getColumnLabel(i+1);
            }
            table.add(columnNames);

            while( rs.next()) {
                String[] row = new String[nCol];
                for( int iCol = 1; iCol <= nCol; iCol++ ){
                    Object obj = rs.getObject( iCol );
                    row[iCol-1] = (obj == null) ?null:obj.toString();
                }
                table.add( row );
            }
            return table;
        };
    }
}
