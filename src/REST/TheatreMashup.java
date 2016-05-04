package REST;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "theatremashup")
@XmlType(propOrder ={"review_id", "review_source","play_name", "title", "review_date", "genre", "critique"})
public class TheatreMashup {
	
	private int review_id;
	private String review_source;
	private String play_name;
	private String title;
	private String review_date;
	private String genre;
	private String critique;

	
	public TheatreMashup(){}	
	
	public int getId() {
		return review_id;
	}
	public void setId(int review_id) {
		this.review_id = review_id;
	}
	public String getReviewSource() {
		return review_source;
	}
	public void setReviewSource(String review_source) {
		this.review_source = review_source;
	}
	public String getPlayName() {
		return play_name;
	}
	public void setPlayName(String play_name) {
		this.play_name = play_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReviewDate() {
		return review_date;
	}
	public void setReviewDate(String review_date) {
		this.review_date = review_date;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getCritique() {
		return critique;
	}
	public void setCritique(String critique) {
		this.critique = critique;
	}


public String toString(){
	return "Review #" + this.review_id + ": " + "\n" + 
						this.review_source + "\n" +  
						this.play_name + "\n" + 
						this.title + "\n" + 
						this.review_date + "\n" + 
						this.genre + "\n" + 
						this.critique + "\n";
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

