package expass4.main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import expass4.model.Todo;

public class App {
	
	static Todo todos = null;
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory;
	
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
		
        get("/todos", (req, res) ->{
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
            Query q = em.createQuery("select t from Todo t");
            List<Todo> todos = q.getResultList();
        	Gson json = new GsonBuilder().setPrettyPrinting().create();
        	String todosString = json.toJson(todos);
            return todosString;
        });
               
        put("/todos/:id", (req,res) -> {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
        	try {
				Gson gson = new Gson();
				todos = gson.fromJson(req.body(), Todo.class);
				em.getTransaction().begin();
				Query q = em.createQuery("select t from Todo t WHERE t.id = :id");
				q.setParameter("id", Long.parseLong(req.params(":id")));
				Todo todo = (Todo) q.getSingleResult();
				if(todos.getSummary() == null && todos.getDescription() == null) {
					res.status(202);
					return "202 No changes specified, are you sure?";
				} else {
					if(todos.getSummary() != null) {
						todo.setSummary(todos.getSummary());
					}
					if(todos.getDescription() != null) {
						todo.setDescription(todos.getDescription());
					}
				}
				em.persist(todo);
				em.getTransaction().commit();
				em.close();
				return todo.toJson();
			} catch (NoResultException e) {
				res.status(404);
				return "404 Try Again Later :)";
			}
        	});
        
        post("/todos", (req,res) -> {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
			Gson gson = new Gson();
			todos = gson.fromJson(req.body(), Todo.class);
			em.getTransaction().begin();
			Todo todo = new Todo();
			if(todos.getSummary() == null && todos.getDescription() == null) {
				res.status(202);
				return "202 no changes specified\nAre you doing it right?";
			} else { 
				if(todos.getSummary() != null) {
					todo.setSummary(todos.getSummary());
				}
				if(todos.getDescription() != null) {
					todo.setDescription(todos.getDescription());
				}
				em.persist(todo);
				em.getTransaction().commit();
				em.close();
			}
			return todo.toJson();
			});
        
        delete("/todos/:id", (req,res) -> {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
			try {
				String id = req.params(":id");
				em.getTransaction().begin();
				Query q = em.createQuery("select t from Todo t WHERE t.id = :id");
				q.setParameter("id", Long.parseLong(id));
				Todo todo = (Todo) q.getSingleResult();
				Query q1 = em.createQuery("DELETE FROM Todo t WHERE t.id = :id");
				q1.setParameter("id", Long.parseLong(id)).executeUpdate();
				em.getTransaction().commit();
				em.close();
				return "Successfully Deleted:\n"+ todo.toJson() +"\nHave a Nice day";
			} catch (NoResultException e) {
				res.status(404);
				return "404 Wait What :O";
			}
        });
    }
    
}
