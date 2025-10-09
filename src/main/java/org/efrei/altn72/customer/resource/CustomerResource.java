package org.efrei.altn72.customer.resource;


import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.efrei.altn72.customer.data.Customer;


@Path("/customer")
public class CustomerResource {

    public static final Logger logger =
            Logger.getLogger(CustomerResource.class.getCanonicalName());

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Customer> getAllCustomers() {
        List<Customer> customers = null;
        try {
            customers = this.findAllCustomers();
            if (customers == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error calling findAllCustomers()", ex);
        }
        return customers;
    }


    private List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = (List<Customer>) em.createNamedQuery("findAllCustomers").getResultList();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error when finding all customers");
        }
        return customers;
    }
}