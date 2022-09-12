package clave_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DAO_Server {
    private Connection myConn;
    private Statement myStmt;
    private PreparedStatement preStmt = null;
    private ResultSet myRs = null;
    int rowsAff;
    public DAO_Server(){
        try{
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/clave","root","killernet"); 
            System.out.println("DB connection succesful");
            myStmt = myConn.createStatement();
            //getMessageList(1,2);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean loginCheck(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? AND password = ? LIMIT 1");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertUser(String u,String p){
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("INSERT INTO user(user_name,password) VALUES(?,?)");
            preStmt.setString(1,u);
            preStmt.setString(2,p);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public String getMessageList(String from,String to){
        String ab = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM message WHERE from_user = ? AND to_user = ? UNION SELECT * FROM message WHERE from_user = ? AND to_user = ? ORDER BY message_id");
            preStmt.setString(1, from);
            preStmt.setString(2, to);
            preStmt.setString(3, to);
            preStmt.setString(4, from);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user", myRs.getString("from_user"));
                obj.put("sent",myRs.getTimestamp("sent").toString());  
                obj.put("text", myRs.getString("text"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public String getServerList(String username){
        String result = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM participants WHERE user_name = ?");
            preStmt.setString(1, username);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("server_name",myRs.getString("server_name"));  
                
                
                PreparedStatement temp = myConn.prepareStatement("SELECT * FROM server WHERE server_name = ? LIMIT 1");
                temp.setString(1,myRs.getString("server_name"));
                ResultSet tempSet = temp.executeQuery();
                
                
                tempSet.next();
                
                obj.put("server_pic",tempSet.getString("server_pic")); 
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getChannelList(String server_name){
        String result = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM server_channel WHERE server_name = ?");
            preStmt.setString(1, server_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("channel_name",myRs.getString("channel_name"));
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getFriendList(String username){
        String result = null;
        try{
            preStmt = myConn.prepareStatement("SELECT user2_name FROM direct_participants WHERE user1_name = ? UNION SELECT user1_name FROM direct_participants WHERE user2_name = ?");
            preStmt.setString(1, username);
            preStmt.setString(2, username);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("friend_name",myRs.getString("user2_name")); 
                
                //////////////////////////////////////
                PreparedStatement temp = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? LIMIT 1");
                temp.setString(1,myRs.getString("user2_name"));
                ResultSet tempSet = temp.executeQuery();
                tempSet.next();
                obj.put("friend_pic",tempSet.getString("profile_pic"));
                arr.add(obj);
            }
            result = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return result;
    }
    public String getServerMessageList(String server_name,String channel_name){
        String ab = null;
        try{
            System.out.println("ACCESED DAO");
            preStmt = myConn.prepareStatement("SELECT * FROM server_message WHERE server_name = ? AND channel_name = ?");
            preStmt.setString(1, server_name);
            preStmt.setString(2, channel_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user",myRs.getString("from_user"));
                obj.put("sent",myRs.getTimestamp("sent").toString());  
                obj.put("text", myRs.getString("text"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public boolean insertMessage(String from_user,String to_user,String text){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO message(from_user,to_user,sent,text) VALUES(?,?,?,?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            preStmt.setTimestamp(3, timestamp);
            preStmt.setString(4,text);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertServerMessage(String server_name,String channel_name,String from_user,String text){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO server_message(server_name,channel_name,from_user,sent,text) VALUES(?,?,?,?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,channel_name);
            preStmt.setString(3,from_user);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            preStmt.setTimestamp(4, timestamp);
            preStmt.setString(5,text);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean createServer(String server_name,String admin){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO SERVER(server_name,admin) VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,admin);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO server_channel(server_name,channel_name) VALUES(?,'general-chat')");
            preStmt.setString(1,server_name);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO participants VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,admin);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    public boolean serverCheck(String server_name){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM server WHERE server_name = ? LIMIT 1");
            preStmt.setString(1,server_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean joinServer(String server_name,String user_name){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO participants VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,user_name);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean userCheck(String user_name){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? LIMIT 1");
            preStmt.setString(1,user_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean sendRequest(String from_user,String to_user){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM direct_participants WHERE (user1_name = ? and user2_name = ?) OR (user1_name = ? and user2_name = ?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            preStmt.setString(3,to_user);
            preStmt.setString(4,from_user);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM request WHERE (from_user = ? AND to_user = ?) OR (from_user = ? AND to_user = ?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            preStmt.setString(3,to_user);
            preStmt.setString(4,from_user);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            preStmt = myConn.prepareStatement("INSERT INTO request VALUES(?,?)");
            preStmt.setString(1,from_user);
            preStmt.setString(2,to_user);
            rowsAff = preStmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }
    public String getRequestList(String user_name){
        String ab = null;
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM request WHERE to_user = ?");
            preStmt.setString(1, user_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            while(myRs.next()){
                JSONObject obj = new JSONObject();
                obj.put("from_user",myRs.getString("from_user"));
                arr.add(obj);
            }
            ab = arr.toString();
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public boolean updateRequest(String from_user,String to_user,String type){
        try {
                preStmt = myConn.prepareStatement("DELETE FROM request WHERE from_user = ? AND to_user = ?");
                preStmt.setString(1, from_user);
                preStmt.setString(2, to_user);
                rowsAff = preStmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        if (type.equals("accept")) {
            try {
                preStmt = myConn.prepareStatement("INSERT INTO direct_participants VALUES(?,?)");
                preStmt.setString(1, from_user);
                preStmt.setString(2, to_user);
                rowsAff = preStmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        else if(type.equals("deny")){
            
        }
        return true;
    }
    public boolean channelCheck(String server_name,String channel_name){
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM server_channel WHERE server_name = ? and channel_name = ? LIMIT 1");
            preStmt.setString(1,server_name);
            preStmt.setString(2,channel_name);
            myRs = preStmt.executeQuery();
            if(myRs.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean createChannel(String server_name,String channel_name){
        try{
            preStmt = myConn.prepareStatement("INSERT INTO server_channel(server_name,channel_name) VALUES(?,?)");
            preStmt.setString(1,server_name);
            preStmt.setString(2,channel_name);
            rowsAff = preStmt.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean insertProfilePicture(String user_name,String profile_pic){
        try {
            preStmt = myConn.prepareStatement("UPDATE user set profile_pic = ? where user_name = ?");
            preStmt.setString(1, profile_pic);
            preStmt.setString(2,user_name);
            rowsAff = preStmt.executeUpdate();
            return true;
        } catch (Exception ex) {
           System.out.println(ex);
        }
        return false;
    }
    public String getProfilePicture(String user_name){
        String ab = null;
        try{
            preStmt = myConn.prepareStatement("SELECT * FROM user WHERE user_name = ? LIMIT 1");
            preStmt.setString(1, user_name);
            myRs = preStmt.executeQuery();
            JSONArray arr = new JSONArray();
            
            if(myRs.next()){
                JSONObject obj = new JSONObject();
                ab = myRs.getString("profile_pic");
                return ab;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return ab;
    }
    public boolean insertServerPicture(String server_name,String server_pic){
        try {
            preStmt = myConn.prepareStatement("UPDATE server SET server_pic = ? WHERE server_name = ? LIMIT 1");
            preStmt.setString(1, server_pic);
            preStmt.setString(2, server_name);
            rowsAff = preStmt.executeUpdate();
            return true;
        } catch (Exception ex) {
           System.out.println(ex);
        }
        return false;
    }
}