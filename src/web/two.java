package web;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class two {
	
	final public int MAX1 = 10;
	public final int MAX2 = 10;
	// touch .gitignore
	// Jerry 2015-08-30 13:29PM if one .class file is already added to repository via commit,
	// you have to use git rm to remove it first, in order to avoid it displayed in 
	// git status again and again
	public static void getCurrentName() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println(methodName);
		
	}
	
	// 2015-08-30 13:38PM external JSON library test
	public static void jsonTest() {
		JSONObject json = new JSONObject();  
		json.put("city", "Mumbai");  
		json.put("country", "India");  
		String output = json.toString();  
		System.out.println(output);
		
	}
	
	public static void TwoPlusTwo() throws Exception {
		@SuppressWarnings("rawtypes")
		Class cache = Integer.class.getDeclaredClasses()[0];
		Field c = cache.getDeclaredField("cache");
		c.setAccessible(true);
		Integer[] array = (Integer[]) c.get(cache);
		array[132] = array[133];
		System.out.printf("%d",2 + 2);
	}
	
	public static void main(String[] args) throws Exception {
		jsonTest();
		final Pattern SPACE = Pattern.compile(" ");
		Iterable<String> result = Arrays.asList(SPACE.split("Hello world Jerry"));
		Iterator<String> iterator = result.iterator();
		while(iterator.hasNext()){
			  String str = (String) iterator.next();
			  System.out.println(str);
		}
	}
}