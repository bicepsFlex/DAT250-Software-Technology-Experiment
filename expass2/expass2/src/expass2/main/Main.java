package expass2.main;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import expass2.model.Address;
import expass2.model.Bank;
import expass2.model.CreditCard;
import expass2.model.Person;
import expass2.model.Pincode;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "expass2";
    private static EntityManagerFactory factory;
    
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        List<CreditCard> ccs = new ArrayList<CreditCard>();
        List<Address> as = new ArrayList<Address>();
        
        em.getTransaction().begin();
        Person p = new Person();
        p.setName("Jarek");
        em.persist(p);

        Address a = new Address();
        a.setStreet("Inndalsveien");
        a.setNumber(28);
        as.add(a);
        p.setAddresses(as);
        em.persist(p);
        em.persist(a);
        
        CreditCard cc = new CreditCard();
        cc.setNumber(1234567890);
        cc.setLimit(1000000);
        cc.setBalance(50);
        ccs.add(cc);
        p.setCreditCards(ccs);
        em.persist(p);
        em.persist(cc);
        
        Pincode pc = new Pincode();
        pc.setPincode("1234");
        pc.setCount(3);
        cc.setPincode(pc);
        em.persist(cc);
        em.persist(pc);
        
        Bank b = new Bank();
        b.setName("JPBank");
        b.setCreditCards(ccs);
        em.persist(cc);
        em.persist(b);
        
        em.getTransaction().commit();
        

        Query qp = em.createQuery("SELECT p FROM Person p");
        List<Person> pList = qp.getResultList();
        for (Person pL : pList) {
        	System.out.println(pL);
        }
        Query qa = em.createQuery("SELECT a FROM Address a");
        List<Address> aList = qa.getResultList();
        for (Address aL : aList) {
        	System.out.println(aL);
        }
        Query qb = em.createQuery("SELECT b FROM Bank b");
        List<Bank> bList = qb.getResultList();
        for (Bank bL : bList) {
        	System.out.println(bL);
        }
        Query qpc = em.createQuery("SELECT pc FROM Pincode pc");
        List<Pincode> pcList = qpc.getResultList();
        for (Pincode pcL : pcList) {
        	System.out.println(pcL);
        }
        Query qcc = em.createQuery("SELECT cc FROM CreditCard cc");
        List<CreditCard> ccList = qcc.getResultList();
        for (CreditCard ccL : ccList) {
        	System.out.println(ccL);
        }
	}
}
