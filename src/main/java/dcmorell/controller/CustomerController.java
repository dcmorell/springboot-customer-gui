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

@Controller
public class CustomerController {

	private final Logger log = LoggerFactory.getLogger(CustomerController.class);
	
	private CustomerService customerService;
	
	@Autowired
    public void getCustomerService(CustomerService customerService) {
        this.customerService = customerService;
        this.customerService.initCustomers();
    }
	
    @RequestMapping(value = "/gui/customers", method = RequestMethod.GET)
    public String getCustomersModel(Model model) {
    	log.info("/gui/customers");
    	
    	model.addAttribute("customers", customerService.listAll());
        return "customers2";
    }
    
    /*@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }*/

    @RequestMapping(value = "/gui/customers/add", method = RequestMethod.GET)
	public String addCustomer(Model model){
    	log.info("/gui/customers/add");
    	
		model.addAttribute("customer", new Customer());
		return "customerform";
	}
    
    @RequestMapping(value = "/gui/customers/add", method = RequestMethod.POST)
    public String addCustomer(@RequestParam("name") String name,
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
        return "customerok";
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
