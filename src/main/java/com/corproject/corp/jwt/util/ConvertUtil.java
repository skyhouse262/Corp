package com.corproject.corp.jwt.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ConvertUtil {
    public static HashMap<String,Object> convertObjectToMap(Object obj){
        try{
            Field[] fields = obj.getClass().getDeclaredFields();
            HashMap<String,Object> resultMap = new HashMap<>();
            for(int i = 0; i <= fields.length -1; i++){
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        }catch (IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject convertMapToJsonObject(Map<String,Object> param){return new JSONObject(param);}

    public static Object convertMapToObject(Map<String,Object> map, Object obj){
        String keyAttribute = null;
        String setMethodString = "set";
        String methodString = null;

        for(String s : map.keySet()){
            keyAttribute = s;
            methodString = setMethodString + keyAttribute.substring(0,1).toUpperCase() + keyAttribute.substring(1);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for(Method method : methods){
                if(methodString.equals(method.getName())){
                    try {
                        method.invoke(obj, map.get(keyAttribute));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    public static Object convertObjectToJsonObject(Object obj){
        ObjectMapper om = new ObjectMapper();
        JSONParser parser = new JSONParser();
        String convertJsonString = "";
        Object convertObj = new Object();

        try{
            convertJsonString = om.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        try{
            convertObj = parser.parse(convertJsonString);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return convertObj;
    }
}
