package org.efrei.altn72.customer.resource;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
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
        return (List<Customer>) em.createNamedQuery("findAllCustomers").getResultList();
    }


    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("id") int customerId) {
        throw new UnsupportedOperationException("TODO - implement");
   }

    /**
     * Simple query method to find Customer by ID.
     */
    @Nullable
    private Customer findById(int customerId) {
        return em.find(Customer.class, customerId);
    }

}