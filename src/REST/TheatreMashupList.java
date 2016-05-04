package REST;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "todoList")
public class TheatreMashupList {

	@XmlElementWrapper(name="todo")
	private static List<TheatreMashup> messages = new ArrayList<TheatreMashup>();
	
	public static List<TheatreMashup> getMessages() {
		return messages;
	}
	
	public static void setMessages(List<TheatreMashup> p_messages) {
		messages = p_messages;
	}
	
    public List<TheatreMashup> findAll() {
        List<TheatreMashup> list = new ArrayList<TheatreMashup>();
        return list;
    }
    
    public TheatreMashup findById(int id) {
    	TheatreMashup Todo = null;
        return Todo;
    }   
    
    public TheatreMashup create(TheatreMashup Todo) {
        Todo.getId();
        Todo.getMessage();
        Todo.getTimestamp();
        //Todo.setId(id);
        return Todo;
    }

    public boolean delete(int id) {
    	return false;
    }

	public void update(TheatreMashup todo) {
		// TODO Auto-generated method stub
		
	}
	
}
