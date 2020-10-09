package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
	@Autowired
	private BillRepository billRepository;
	
	public void save(Bill bill) {
		this.billRepository.save(bill);
	}
	
	public Bill findById(Integer id) {
		return this.billRepository.findOne(id);
	}
	
	public List<Bill> findAll(){
		return this.findAll();
	}
	public void delete(Bill bill) {
		this.billRepository.delete(bill);
	}
	public Collection<Bill> getPayedBills(){
		return this.billRepository.getPayedBills();
	}
	public Collection<Bill> getNotPayedBills(){
		return this.billRepository.getNotPayedBills();
	}
}
