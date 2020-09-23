package expass4.main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

import com.google.gson.Gson;

import expass4.model.Todo;

public class App {
	
	static Todo todos = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		todos = new Todo();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
        get("/todos", (req, res) -> todos.toJson());
               
        put("/todos", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	todos = gson.fromJson(req.body(), Todo.class);
        
            return todos.toJson();
        	
        });
    }
    
}
