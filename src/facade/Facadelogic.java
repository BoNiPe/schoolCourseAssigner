package facade;

import com.google.gson.Gson;
import entity.Person;
import entity.RoleSchool;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Facadelogic implements FacadeInterface {

    //Interface used to interact with the entity manager factory for the persistence unit.
    //Persistence knows where to put the objects and afterwards the Entity Manager
    //takes the whole information, from which it "createsEntity"
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("BetaProjectbPU");
    EntityManager em = emf.createEntityManager();
    //So that we can have a single transaction after the initialization
    EntityTransaction tr;
    //Vital for the parsing to Gson
    private Gson gson = new Gson();
    //*************************************************
    Map<Integer, Person> people = new HashMap();

    @Override
    public String getPersonsAsJSON() {
        return ((people.isEmpty()) ? null : people.values().toString());
    }

    @Override
    public String getPersonAsJSON(Integer id) {
        Person a = em.find(Person.class, id);
        return ((a == null) ? null : gson.toJson(a));
    }

    @Override
    public Person addPersonFromJSON(String json) {
        initializeTransactions();
        tr.begin();
        Person p = gson.fromJson(json, Person.class);
        em.persist(p);
        tr.commit();
        System.out.println("id:" + p.getId() + ", object:  " + p.toString());
        return p;
    }

    @Override
    public RoleSchool addRoleFromJSON(String json, Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person deletePersonFromJSON(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initializeTransactions() {
        tr = em.getTransaction();
    }

    private void testingCode() {
        String addingperson = "{ \"firstName\":\"John\", \"lastName\":\"McLaren\", \"mail\":\"j@m.uk\", \"phone\":\"3456\" }";
        String addingperson2 = "{ \"firstName\":\"aaaaaa\", \"lastName\":\"aaaaa\", \"mail\":\"aaaaaa@m.uk\", \"phone\":\"33242346\" }";
        String addingperson3 = "{ \"firstName\":\"bbbbb\", \"lastName\":\"bbbb\", \"mail\":\"bbbbbb@m.uk\", \"phone\":\"3234324236\" }";
        Person p = addPersonFromJSON(addingperson);
        Person p2 = addPersonFromJSON(addingperson2);
        Person p3 = addPersonFromJSON(addingperson3);
        System.out.println("Object: " + p.toString());
        System.out.println("Object: " + p2.toString());
        System.out.println("Object: " + p3.toString());
        System.out.println("GetParticularPerson :" + getPersonAsJSON(100));
    }

    public static void main(String[] args) {

        new Facadelogic().testingCode();
    }

}
