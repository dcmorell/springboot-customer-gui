package dcmorell.service;

import dcmorell.model.Customer;
import dcmorell.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public void initCustomers(){
    	Customer c1 = new Customer();
    	c1.setName("Matt Murdock");
    	c1.setAddress("Hellskitchen");
    	c1.setPhone("555-43210");
    	Customer c2 = new Customer();
    	c2.setName("Daniel Rand");
    	c2.setAddress("Manhattan");
    	c2.setPhone("555-01234");
    	Customer c3 = new Customer();
    	c3.setName("Luke Cage");
    	c3.setAddress("Harlem");
    	c3.setPhone("555-98765");
    	Customer c4 = new Customer();
    	c4.setName("Clint Barton");
    	c4.setAddress("Manhattan");
    	c4.setPhone("555-56789");
    	
    	customerRepository.deleteAll();
    	
    	customerRepository.save(c1);
    	customerRepository.save(c2);
    	customerRepository.save(c3);
    	customerRepository.save(c4);
    }
    
    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Iterable<Customer> listAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.delete(id);
    }

}
