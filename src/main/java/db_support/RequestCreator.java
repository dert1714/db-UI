package db_support;

import java.sql.*;
import java.util.ArrayList;

public class RequestCreator {
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/", "postgres",
                "ASdf56");
        return connection;
    }

    public String getRequest(String type, String table, String column, String value) {
        if("insert".equals(type)){
            String s = "INSERT INTO " + table + " (" + column + ") VALUES(" + "\'" + value + "\'" + ")";
            System.out.println(s);
            return s;
        }
        return "";
    }

    public String getSelectRequest(String table, String column, String value) {
        String s = "SELECT id FROM " + table + " WHERE " + column + "=" + "\'" + value + "\'";
        System.out.println(s);
        return s;
    }

    private String getValue(Object value) {
        Class cl = value.getClass();
        if(cl == String.class)
            return "\'" + value + "\'";
        else
            return new Integer((int)value).toString();
    }

    public boolean isUnique(Connection con, String table, String column, String value) throws SQLException {
        Statement stmt = null;
        boolean res = false;
        try {
            stmt = con.createStatement();
            try {
                ResultSet rs = stmt.executeQuery("SELECT id FROM " + table + " WHERE " + column + "=" + getValue(value));
                if(rs.next())
                    res = true;
            }catch(org.postgresql.util.PSQLException e){
                return res;
            }
        } finally {
            if (stmt != null) stmt.close();
        }
        return res;
    }

    public int updateTable(Connection con, String request, String select) throws SQLException {
        Statement stmt;
        stmt = null;
        int res = 0;
        try {
            stmt = con.createStatement();
            try {
                res = stmt.executeUpdate(request);
            }catch(org.postgresql.util.PSQLException e){
                return res;
            }
        } finally {
            if(! "".equals(select)) {
                ResultSet rs = stmt.executeQuery(select);
                rs.next();
                res = (Integer) rs.getObject(1);
                return res;
            }
            if (stmt != null) stmt.close();
        }
        return res;
    }

    private String getName(Connection conn, String table, String key, int id) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String res ="";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT " + key + " FROM " + table + " WHERE " + "id=" + id);
            while (rs.next()) {
                res = (String) rs.getObject(1);
            }
            return res;
        }finally{
            if  (rs != null ) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    public ArrayList selectRequest(Connection conn, String table) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ArrayList<String> tmp_lst = null;
        ArrayList res = new ArrayList();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + table);
            while ( rs.next() ) {
                tmp_lst = new ArrayList<>();
                int id = (Integer)rs.getObject(1);
                String name = (String)rs.getObject(2);
                int region_id = (int)rs.getObject(3);
                String region = getName(conn,"regions","region",region_id);
                int hbar_id = (int)rs.getObject(4);
                String hbar = getName(conn,"hbars","hbar",hbar_id);
                tmp_lst.add(name);
                tmp_lst.add(region);
                tmp_lst.add(hbar);
                res.add(tmp_lst);
            }
            System.out.println(res);
            return res;
        } finally {
            if  (rs != null ) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}
