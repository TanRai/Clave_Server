package clave_server;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class DAO_Access {
    private final DAO_Server dao;
    public DAO_Access(){
        this.dao = new DAO_Server();
    }
    public String processRequest(String requestType,String json){
        String result = null;
        switch(requestType) {
            case "loginCheck":
              result = loginCheck(json);
              break;
            case "insertUser":
              result = insertUser(json);
              break;
            case "getMessageList":
              result = getMessageList(json);
              break;
            case "getServerList":
              result = getServerList(json);
              break;
            case "getChannelList":
              result = getChannelList(json);
              break;
            case "getFriendList":
              result = getFriendList(json);
              break;
            case "getServerMessageList":
              result = getServerMessageList(json);
              break;
            case "insertMessage":
              result = insertMessage(json);
              break;
            case "insertServerMessage":
              result = insertServerMessage(json);
              break;
            case "createServer":
              result = createServer(json);
              break;
            case "serverCheck":
              result = serverCheck(json);
              break;
            case "joinServer":
              result = joinServer(json);
              break;
            case "userCheck":
              result = userCheck(json);
              break;
            case "sendRequest":
              result = sendRequest(json);
              break;
            case "getRequestList":
              result = getRequestList(json);
              break;
            case "updateRequest":
              result = updateRequest(json);
              break;
            case "channelCheck":
              result = channelCheck(json);
              break;
            case "createChannel":
              result = createChannel(json);
              break;
            case "insertProfilePicture":
              result = insertProfilePicture(json);
              break;
            case "getProfilePicture":
              result = getProfilePicture(json);
              break;
            case "insertServerPicture":
              result = insertServerPicture(json);
              break;
            default:
              System.out.println("error");
              System.exit(0);
              break;
        }
        return result;
    }
    private String loginCheck(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String u = ((String) jsonObj.get("u"));
        String p = ((String) jsonObj.get("p"));
        if(dao.loginCheck(u, p)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String insertUser(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String u = ((String) jsonObj.get("u"));
        String p = ((String) jsonObj.get("p"));
        if(dao.insertUser(u, p)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String getMessageList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String from = ((String) jsonObj.get("from"));
        String to = ((String) jsonObj.get("to"));
        return dao.getMessageList(from,to);
    }
    private String getServerList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String username = ((String) jsonObj.get("username"));
        return dao.getServerList(username);
    }
    private String getChannelList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        return dao.getChannelList(server_name);
    }
    private String getFriendList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String username = ((String) jsonObj.get("username"));
        return dao.getFriendList(username);
    }
    private String getServerMessageList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String channel_name = ((String) jsonObj.get("channel_name"));
        return dao.getServerMessageList(server_name,channel_name);
    }
    private String insertMessage(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String from_user = ((String) jsonObj.get("from_user"));
        String to_user = ((String) jsonObj.get("to_user"));
        String text = ((String) jsonObj.get("text"));
        if(dao.insertMessage(from_user, to_user,text)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String insertServerMessage(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String channel_name = ((String) jsonObj.get("channel_name"));
        String from_user = ((String) jsonObj.get("from_user"));
        String text = ((String) jsonObj.get("text"));
        if(dao.insertServerMessage(server_name, channel_name,from_user,text)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String createServer(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String admin = ((String) jsonObj.get("admin"));
        if(dao.createServer(server_name, admin)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String serverCheck(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        if(dao.serverCheck(server_name)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String joinServer(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String user_name = ((String) jsonObj.get("user_name"));
        if(dao.joinServer(server_name,user_name)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String userCheck(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String user_name = ((String) jsonObj.get("user_name"));
        if(dao.userCheck(user_name)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String sendRequest(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String from_user = ((String) jsonObj.get("from_user"));
        String to_user = ((String) jsonObj.get("to_user"));
        if(dao.sendRequest(from_user,to_user)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String getRequestList(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String user_name = ((String) jsonObj.get("user_name"));
        return dao.getRequestList(user_name);
    }
    private String updateRequest(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String from_user = ((String) jsonObj.get("from_user"));
        String to_user = ((String) jsonObj.get("to_user"));
        String type = ((String) jsonObj.get("type"));
        if(dao.updateRequest(from_user,to_user,type)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String channelCheck(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String channel_name = ((String) jsonObj.get("channel_name"));
        if(dao.channelCheck(server_name,channel_name)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String createChannel(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String channel_name = ((String) jsonObj.get("channel_name"));
        if(dao.createChannel(server_name,channel_name)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String insertProfilePicture(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String user_name = ((String) jsonObj.get("user_name"));
        String profile_pic = ((String) jsonObj.get("profile_pic"));
        if(dao.insertProfilePicture(user_name,profile_pic)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    private String getProfilePicture(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String user_name = ((String) jsonObj.get("user_name"));
        return dao.getProfilePicture(user_name);
    }
    private String insertServerPicture(String json){
        String result = null;
        Object obj = JSONValue.parse(json);
        JSONObject jsonObj = (JSONObject)obj;
        String server_name = ((String) jsonObj.get("server_name"));
        String server_pic = ((String) jsonObj.get("server_pic"));
        if(dao.insertServerPicture(server_name,server_pic)){
            result = "success";
        }
        else{
            result = "failure";
        }
        return result;
    }
    
}
