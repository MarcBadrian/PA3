package REST;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "todo")
@XmlType(propOrder ={"id", "todo_message","timestamp"})
public class TheatreMashup {
	
	private int id;
	private String todo_message;
	private Timestamp timestamp;
	
	public TheatreMashup(){}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return todo_message;
	}
	public void setToDoMessage(String todo_message) {
		this.todo_message = todo_message;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp() {
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();
		 
		// 2) get a java.util.Date from the calendar instance.
		//    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();
		 
		// 3) a java current time (now) instance
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		this.timestamp = currentTimestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}	

public String toString(){
	return "Message #" + this.id + ": " + this.todo_message;
}

//HELPER METHOD TO USE INDEX_OF FOR LIST
 @Override
 public boolean equals(Object o) {
     if(o == null) {
    	 return false;
     }else if( o instanceof TheatreMashup){
        int a_id = (Integer) ((TheatreMashup) o).getId();
        if(this.getId() == a_id){
        	return true;
        }
        else {
        return false;
        }       
    }
     return false;
    }
}

