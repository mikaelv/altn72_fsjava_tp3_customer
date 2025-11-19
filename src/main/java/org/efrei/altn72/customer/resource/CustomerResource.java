package org.efrei.altn72.customer.resource;


import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.efrei.altn72.customer.data.Address;
import org.efrei.altn72.customer.data.Customer;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;


@Path("/customers")
public class CustomerResource {

    public static final Logger logger =
            Logger.getLogger(CustomerResource.class.getCanonicalName());


    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Customer> getAllCustomers() {
        return (List<Customer>) em.createNamedQuery("findAllCustomers").getResultList();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("id") int customerId) {
        var customer = findById(customerId);
        if (customer == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        else
            return customer;
    }

    @Path("{id}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createCustomer(Customer customer) {
        if (customer.getId() != 0 || customer.getAddress().getId() != null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Unexpected \"id\" attribute").build();
        else {
            em.persist(customer);
            // Il faut aussi persister l'adresse associée
            em.persist(customer.getAddress());
            em.flush();
            return Response.created(URI.create("/" + customer.getId())).build();
        }
    }


    @Path("{id}")
    @DELETE
    @Transactional
    public Response deleteCustomer(@PathParam("id") int customerId) {
        var customer = findById(customerId);
        if (customer != null) {
            em.remove(customer.getAddress());
            em.remove(customer);
            return Response.ok().build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        var oldCust = findById(id);
        if (oldCust != null) {
            em.merge(customer.getAddress());
            em.merge(customer);
            // force l'update pour lancer l'éventuelle ConstraintViolationException maintenant
            em.flush();
            return Response.ok().build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }


    @Path("{id}")
    @PATCH
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Response patchCustomer(@PathParam("id") int id, Customer partial) {
        var existing = findById(id);
        if (existing != null) {
            copyCustomerAttributes(existing, partial);
            copyAddressAttributes(existing.getAddress(), partial.getAddress());
            em.merge(existing.getAddress());
            em.merge(existing);
            em.flush();

            return Response.ok().build();
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    private static void copyCustomerAttributes(Customer existing, Customer partial) {
        if (existing == null || partial == null) return;

        if (partial.getFirstname() != null)
            existing.setFirstname(partial.getFirstname());
        if (partial.getLastname() != null)
            existing.setLastname(partial.getLastname());
        if (partial.getEmail() != null)
            existing.setEmail(partial.getEmail());
        if (partial.getPhone() != null)
            existing.setPhone(partial.getPhone());
    }

    private static void copyAddressAttributes(Address existing, Address partial) {
        if (existing == null || partial == null) return;

        if (partial.getNumber() != 0)
            existing.setNumber(partial.getNumber());
        if (partial.getStreet() != null)
            existing.setStreet(partial.getStreet());
        if (partial.getCity() != null)
            existing.setCity(partial.getCity());
        if (partial.getProvince() != null)
            existing.setProvince(partial.getProvince());
        if (partial.getZip() != null)
            existing.setZip(partial.getZip());
        if (partial.getCountry() != null)
            existing.setCountry(partial.getCountry());
    }


    /**
     * Simple query method to find Customer by ID.
     */
    @Nullable
    private Customer findById(int customerId) {
        return em.find(Customer.class, customerId);
    }
}