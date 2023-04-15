package DAL;

import App.ModuleManager.AppControl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public abstract class DataAccess {
    private Connection conn;
    private String host;
    private String port;
    private String username;
    private String pass;

    private void readRs(){
        try {
            File myObj = new File("src/main/resources/datars");
            Scanner myReader = new Scanner(myObj);
            String[] rs = new String[4];
            int idx = 0;
            while (myReader.hasNextLine()) {
                rs[idx++] = myReader.nextLine();
            }
            myReader.close();
            this.host = rs[0];
            this.username = rs[1];
            this.pass = rs[2];
            this.port = rs[3];
//            this.host="localhost";
//            this.port="3307";
//            this.username="root";
//            this.pass="123456";

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    protected void createConnection(){
        if(AppControl.conn == null){
            readRs();
            try {
                AppControl.conn = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/erpcoffee"
                        ,host,port),username,pass);
                PreparedStatement preparedStatement = AppControl.conn.prepareStatement("{call set_timezone()}");
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("fail");
                throw new RuntimeException(e);
            }
        }


    }


    protected void closeConnection(){
        if(conn != null ) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConn() {
        if (conn == null) createConnection();
        return AppControl.conn;
    }

}
