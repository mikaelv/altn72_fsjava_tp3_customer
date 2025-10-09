package org.efrei.altn72.customer.data;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class DatabaseInitializer {
    
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());
    
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    public void initializeDatabase() {
        logger.log(Level.INFO, "Initializing database with sample customers");
        
        try {
            long customerCount = (Long) em.createQuery("SELECT COUNT(c) FROM Customer c").getSingleResult();
            
            if (customerCount == 0) {
                createSampleCustomers();
                logger.log(Level.INFO, "Sample customers created successfully");
            } else {
                logger.log(Level.INFO, "Database already contains customers, skipping initialization");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error initializing database: " + ex.getMessage(), ex);
        }
    }
    
    private void createSampleCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstname("Pierre");
        customer1.setLastname("Dupont");
        customer1.setEmail("pierre.dupont@example.fr");
        customer1.setPhone("+33-1-42-34-56-78");
        Address address1 = new Address();
        address1.setNumber(25);
        address1.setStreet("Rue de Rivoli");
        address1.setCity("Paris");
        address1.setProvince("Île-de-France");
        address1.setZip("75001");
        address1.setCountry("France");
        customer1.setAddress(address1);
        em.persist(address1);
        em.persist(customer1);
        
        Customer customer2 = new Customer();
        customer2.setFirstname("Marie");
        customer2.setLastname("Martin");
        customer2.setEmail("marie.martin@example.fr");
        customer2.setPhone("+33-4-91-23-45-67");
        Address address2 = new Address();
        address2.setNumber(142);
        address2.setStreet("Avenue de la République");
        address2.setCity("Marseille");
        address2.setProvince("Provence-Alpes-Côte d'Azur");
        address2.setZip("13001");
        address2.setCountry("France");
        customer2.setAddress(address2);
        em.persist(address2);
        em.persist(customer2);
        
        Customer customer3 = new Customer();
        customer3.setFirstname("Jean");
        customer3.setLastname("Bernard");
        customer3.setEmail("jean.bernard@example.fr");
        customer3.setPhone("+33-4-72-98-76-54");
        Address address3 = new Address();
        address3.setNumber(38);
        address3.setStreet("Cours Lafayette");
        address3.setCity("Lyon");
        address3.setProvince("Auvergne-Rhône-Alpes");
        address3.setZip("69003");
        address3.setCountry("France");
        customer3.setAddress(address3);
        em.persist(address3);
        em.persist(customer3);
        
        Customer customer4 = new Customer();
        customer4.setFirstname("Sophie");
        customer4.setLastname("Leroy");
        customer4.setEmail("sophie.leroy@example.fr");
        customer4.setPhone("+33-5-56-78-90-12");
        Address address4 = new Address();
        address4.setNumber(67);
        address4.setStreet("Rue Sainte-Catherine");
        address4.setCity("Bordeaux");
        address4.setProvince("Nouvelle-Aquitaine");
        address4.setZip("33000");
        address4.setCountry("France");
        customer4.setAddress(address4);
        em.persist(address4);
        em.persist(customer4);
        
        Customer customer5 = new Customer();
        customer5.setFirstname("Antoine");
        customer5.setLastname("Dubois");
        customer5.setEmail("antoine.dubois@example.fr");
        customer5.setPhone("+33-3-20-12-34-56");
        Address address5 = new Address();
        address5.setNumber(156);
        address5.setStreet("Rue Nationale");
        address5.setCity("Lille");
        address5.setProvince("Hauts-de-France");
        address5.setZip("59000");
        address5.setCountry("France");
        customer5.setAddress(address5);
        em.persist(address5);
        em.persist(customer5);
        
        em.flush();
    }
}