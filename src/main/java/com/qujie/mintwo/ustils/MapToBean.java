package com.qujie.mintwo.ustils;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MapToBean<T> {

    private T t;
    /**
     * @Date 2018-12-28 14:55
     * @param map
     * @param clazz
     * @return
     * @throws Exception
     * @Other 转换Bean为Map
     */
    public static Object transMapToBean(Map<String, Object> map, Class clazz) throws Exception{
        Constructor constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        try{
            Map<String,String> allBeanKey = getKeyForBean(map);
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field: declaredFields) {
                if(allBeanKey.keySet().contains( field.getName() )){
                    Object pul = map.get(allBeanKey.get(field.getName()));
                    field.setAccessible(true);

                    String beanType =  field.getType().getCanonicalName().toUpperCase();
                    if(map.get(allBeanKey.get(field.getName())) == null){
                        continue;
                    }
                    String converType = map.get(allBeanKey.get(field.getName())).getClass().getCanonicalName().toUpperCase();

                    if(beanType.equals(converType)){
                        field.set(object,map.get( allBeanKey.get(field.getName()) ));
                    }else{
                        if( map.get(allBeanKey.get(field.getName())) != null ){
                            if(  converType.contains("STRING") ){
                                if( beanType.contains("LONG") ){
                                    Long out = Long.parseLong( map.get(allBeanKey.get(field.getName())).toString() );
                                    field.set(object,out);
                                }else if( beanType.contains("INT") ){
                                    Integer out = Integer.parseInt( map.get(allBeanKey.get(field.getName())).toString() );
                                    field.set(object,out);
                                }else{
                                    System.out.println("该功能暂未开发");
                                }
                            } else if( converType.contains("INTEGER") ){

                                if( beanType.contains("STRING") ){
                                    String out = String.valueOf( map.get(allBeanKey.get(field.getName())) );
                                    field.set(object,out );
                                } else {
                                    Object out = map.get(allBeanKey.get(field.getName()));
                                    field.set(object,out);
                                }
                            }else if( converType.contains("DOUBLE") ){

                                if( beanType.contains("STRING") ){
                                    String out = String.valueOf( map.get(allBeanKey.get(field.getName())) );
                                    field.set(object,Double.valueOf(out) );
                                } else {
                                    Object out = map.get(allBeanKey.get(field.getName()));
                                    field.set(object,out);
                                }
                            }  else {
                                try {
                                    Object out = map.get(allBeanKey.get(field.getName()));
                                    field.set(object,out);
                                }catch (Exception e){
                                    System.err.println("异常字段未转换完成");
//                                    e.printStackTrace();
                                    field.setAccessible(false);
                                    continue;
                                }
                            }
                        }

                    }

                    field.setAccessible(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return object;
        }

    }

    /**
     * 转换且 过滤 _* 后缀
     * @param map
     * @param clazz
     * @return
     * @throws Exception
     */
    public static Object transMapToBeanForForm(Map<String, Object> map, Class clazz) throws Exception{
        Constructor constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        try{
            Map<String,String> allBeanKey = getKeyForBean(map);
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field: declaredFields) {
                if(allBeanKey.keySet().contains( field.getName() )){
                    String beanType =  field.getType().getCanonicalName().toUpperCase();
                    String converType = map.get(allBeanKey.get(field.getName())).getClass().getCanonicalName().toUpperCase();
//                    System.out.println(beanType + converType);
                    field.setAccessible(true);
                    if(beanType.equals(converType)){
                        field.set(object,map.get( allBeanKey.get(field.getName()) ));
                    }else{
                        if( map.get(allBeanKey.get(field.getName())) != null &&  converType.contains("STRING") ){
                            if( beanType.contains("LONG") ){
                                Long out = Long.parseLong( map.get(allBeanKey.get(field.getName())).toString() );
                                field.set(object,out);
                            }else if( beanType.contains("INT") ){
                                Integer out = Integer.parseInt( map.get(allBeanKey.get(field.getName())).toString() );
                                field.set(object,out);
                            }else{
                                System.out.println("该功能暂未开发");
                            }
                        }
                    }
                    field.setAccessible(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return object;
        }

    }
    /**
     * TODO 未完成状态
     * @Autoer 程科星
     * @Date 2018-12-28 15:12
     * @param map
     * @return T 泛型类型的参数
     * @throws Exception
     * @Other 这里利用泛型实现转换Bean为Map
     */
    protected T transBean(Map<String, Object> map ) throws Exception{

        Class<T> clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Constructor constructor = clazz.getConstructor();
        Object object = constructor.newInstance();
        try{
            Map<String,String> allBeanKey = getKeyForBean(map);
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field: declaredFields) {
                if(allBeanKey.keySet().contains( field.getName() )){
                    field.setAccessible(true);
                    field.set(object,map.get( allBeanKey.get(field.getName()) ));
                    field.setAccessible(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return (T)object;
        }

    }

    /**
     * 利用ascll编码转换首字母大写
     * @param string
     * @return
     */
    public static String toUpperFristChar(String string) {
        char[] charArray = string.toCharArray(); charArray[0] -= 32; return String.valueOf(charArray);
    }
    /**
    /**
     * 获取所有Map中的可能与bean对应的key
     */
    public static Map<String,String> getKeyForBean(Map<String, Object> map){
        Map<String,String> map1 = new HashMap<>();
        for (String str: map.keySet()) {
            String keys = "";
            if( str.indexOf("_") != -1 ){
                String[] s = str.split("_");
                for (int i = 0; i <s.length  ; i++) {
                    if(i==0)
                        keys += s[i];
                    else{
                        keys += toUpperFristChar(s[i]);
                    }
                }
            }else {
                keys = str;
            }

            map1.put(keys,str);
        }
        return map1;
    }

    /**
     * 获取所有Map中的可能与bean对应的key
     */
    public static Map<String,String> getKeyForBeanDelEnd(Map<String, Object> map){
        Map<String,String> map1 = new HashMap<>();
        for (String str: map.keySet()) {
            String keys = "";
            if( str.indexOf("_") != -1 ){
                String[] s = str.split("_");
                for (int i = 0; i <s.length  ; i++) {
                    if(i!=s.length-1){
                        keys += s[i];
                    }
                }
            }else {
                keys = str;
            }

            map1.put(keys,str);
        }
        return map1;
    }





}
