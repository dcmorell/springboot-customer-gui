package dcmorell.controller;

import dcmorell.model.Customer;
import dcmorell.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRestController {

	private final Logger log = LoggerFactory.getLogger(CustomerRestController.class);
	
    private CustomerService customerService;
    private MediaType jsonMediaType = MediaType.APPLICATION_JSON;
    
    @Autowired
    public void getCustomerService(CustomerService customerService) {
        this.customerService = customerService;
        this.customerService.initCustomers();
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Customer> getCustomers() {
        log.info("/customers");
    	Iterable<Customer> customers = customerService.listAll();
        return customers;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Customer addCustomer(@RequestParam("name") String name,
                                @RequestParam(name = "address", defaultValue = "") String address,
                                @RequestParam(name = "phone", defaultValue = "") String phone) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        log.info("Customer to Save: " + customer);
        try {
            customerService.saveCustomer(customer);
        } catch(Exception e) {
            log.error("Error when saving the Custormer");
            return null;
        }
        return customer;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") Integer id) {
        log.info("Customer to Id to Delete: " + id);
        try {
            customerService.deleteCustomer(id);
        } catch(EmptyResultDataAccessException e) {
            log.error("Custormer not found");
        }
    }


}
