package com.example.demo.tcp.constant;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TCP/IP Client channel management class
 * @author allen
 * August 9, 2018
 */
public class NettyChannelMap {
	
	private static Map<String,SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();
	
    public static void add(String clientId,SocketChannel socketChannel){  
        map.put(clientId,socketChannel);
    }
    
    public static Channel get(String clientId){  
       return map.get(clientId);  
    }
    
    public static String get(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                return (String) entry.getKey();
            }
        }
        return null;
    }
    
    public static void removeClient(String clientId){
    	map.remove(clientId);
    }
    
    public static boolean containChannel(String clientId){
       return map.containsKey(clientId);
    }
    
    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
                socketChannel.close();
            }
        }
    }
    
    public static List<SocketChannel> getAllClientIds(){
    	List<SocketChannel> valuesList = new ArrayList<SocketChannel>(map.values());
    	return valuesList;
    }
    
    public static Set<String> getAllKey(){
    	return map.keySet();
    }

}
