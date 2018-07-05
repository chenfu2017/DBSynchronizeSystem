package com.chenfu.utils;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jUtil {
	
	public static <T>  T getBean(String beanName){	
		SAXReader reader = new SAXReader();
	    Document document = null;
	    T t = null; 
		try {
			document = reader.read(new File("src/properties.xml"));
		    Element beans = document.getRootElement();
            Method setmeth;   
		    List<Element> list = beans.elements();
			    for (Element element : list) {
			    	 String no = element.attributeValue("id");
			    	 if(no!=null && no.equals(beanName)){
			    		 String classpath = element.attributeValue("class");
			    		 t = (T) Class.forName(classpath).newInstance();
			    		return t;
			    	 }
				}
			}
		     catch (Exception e) {
		    	 e.printStackTrace();
		}
		return t;
	}

	public static <T>  T getBean(String beanName, T t){	
		SAXReader reader = new SAXReader();
	    Document document = null;
		try {
			document = reader.read(new File("src/properties.xml"));
		    Element beans = document.getRootElement();
		    Field[] properties = t.getClass().getDeclaredFields();
            Method setmeth;   
		    List<Element> list = beans.elements();
			    for (Element element : list) {
			    	 String no = element.attributeValue("id");
			    	 if(no!=null && no.equals(beanName)){
			    			 t=(T)t.getClass().newInstance();
			    			 for (int j = 0; j < properties.length; j++) {
		                     setmeth = t.getClass().getMethod(   
		                             "set"  + properties[j].getName().substring(0, 1)   .toUpperCase()   
		                              + properties[j].getName().substring(1),properties[j].getType());   
		                     setmeth.invoke(t, element.elementText(properties[j].getName()));		                               
			    			 }  
			    		return t;
			    	 }
				}
			}
		     catch (Exception e) {
		    	 e.printStackTrace();
		}
		return t;
	}
	
	public static <T>  T getBean(String beanName, Class c){	
		SAXReader reader = new SAXReader();
	    Document document = null;
	    T t = null;
		try {
			document = reader.read(new File("src/properties.xml"));
		    Element beans = document.getRootElement();
		    Field[] properties = c.getDeclaredFields(); 
            Method setmeth;   
		    List<Element> list = beans.elements();	    
			    for (Element element : list) {
			    	 String no = element.attributeValue("id");
			    	 if(no!=null && no.equals(beanName)){
			    			  t = (T) c.newInstance();
			    			 for (int j = 0; j < properties.length; j++) {
		                     setmeth = c.getMethod("set"+properties[j].getName().substring(0, 1).toUpperCase()+ properties[j].getName().substring(1),properties[j].getType());   
		                     setmeth.invoke(t, element.elementText(properties[j].getName()));		                               
			    			 }  
			    		return t;
			    	 }
				}
			}
		     catch (Exception e) {
		    	 e.printStackTrace();
		}
		return t;
	}
}
