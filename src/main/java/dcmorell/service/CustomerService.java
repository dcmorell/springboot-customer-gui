package dcmorell.service;

import dcmorell.model.Customer;

public interface CustomerService {

	void initCustomers();
	
    Iterable<Customer> listAll();

    Customer getCustomerById(Integer id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Integer id);

}
