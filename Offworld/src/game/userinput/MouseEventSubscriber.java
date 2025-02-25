package game.userinput;

import java.lang.reflect.Method;
import java.util.ArrayList;
import main.*;
import processing.event.MouseEvent;

public class MouseEventSubscriber {
	public String name;
	public ArrayList<Method> methods = new ArrayList<Method>();
	private ArrayList<Object> objects = new ArrayList<Object>();
	public MouseEventSubscriber(String methodName, Method method, Object parent) {
		this.name = methodName;
		this.methods.add(method);
		this.objects.add(parent);
	}
	
	public MouseEventSubscriber(String methodName) {
		this.name = methodName;
	}
	
	public void add(Method method, Object object) {
		if(this.objects.contains(object)) {
			Main.gameApplet.die("Object/Method already being stored. "+this.name+"() "+object+", "+method);
			return;
		}
		
		this.methods.add(method);
		this.objects.add(object);
	}
	
	public void remove(Object o) {
		int index = findIndex(o);
		if(index == -1) {
			Main.gameApplet.die("Object not found.");
			return;
		}
		
		this.methods.remove(index);
		this.objects.remove(index);
	}
	
	public int findIndex(Object o) {
		for(int i = 0; i < objects.size(); i++) {
			
			//In case object.equals() is overrided we must use == as a safety measure.
			if(objects.get(i) == o) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void handle(MouseEvent arg) {
		Object[] args = new Object[1];
		args[0] = arg;
		for(int i = 0; i < objects.size(); i++) {
			try {
				
				methods.get(i).invoke(objects.get(i), args);
			} catch(Exception e) {
				e.printStackTrace();
				Main.gameApplet.die("[MOUSE] Error in MouseEventSubscriber: "+e);
				return;
			}
		}
	}
	
}
