package org.springframework.samples.petclinic.owner;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillControler {
	private static final String VIEWS_BILL_CREATE_OR_UPDATE_FORM = "bills/createOrUpdateBillForm";
	
	@Autowired
	private BillService billService;
	
	@RequestMapping(value="/Bill", method=RequestMethod.GET)
	public List<Bill> findAll() {
		return billService.findAll();
	}
	
	/*
	@RequestMapping(value="/bills/{idBill}", method=RequestMethod.GET)
	public Bill findById(@PathVariable("idBill")Integer id) {
		Bill b = billService.findById(id);
		return b;
	}
	*/
	@RequestMapping(value="/bills/{idBill}", method=RequestMethod.GET)
	public ResponseEntity<Bill> findById(@PathVariable("idBill")Integer id) {
		Bill b = billService.findById(id);
		if(b!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(b);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@RequestMapping(value = "/bills/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Bill bill = new Bill();
        model.put("bills", bill);
        return VIEWS_BILL_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/bills/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid @RequestBody Bill bill, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_BILL_CREATE_OR_UPDATE_FORM;
        } else {
            this.billService.save(bill);
            return "redirect:/bills/" + bill.getId();
        }
    }
	
	@RequestMapping(value="/bills/delete/{idBill}", method=RequestMethod.DELETE)
    public ResponseEntity<Bill> processDeletion (@PathVariable("idBill")Integer id){
    	Bill b = billService.findById(id);
    	if(b!=null) {
    		billService.delete(b);
    		return ResponseEntity.status(HttpStatus.OK).body(b);
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }
	@RequestMapping(value="/bills/update/{idBill}", method=RequestMethod.PATCH)
	public String processUpdation (@PathVariable("idBill")Integer id, @Valid @RequestBody Bill bill, BindingResult result) {
		Bill currentBill = billService.findById(id);
		if(currentBill!=null) {
    		if(result.hasErrors()) {
    			return "Bad update";
    		}
    		else {
    			currentBill.setMoney(bill.getMoney());
    			currentBill.setPaymentDate(bill.getPaymentDate());
    			currentBill.setVisit(bill.getVisit());
    			billService.save(currentBill);
    			return "redirect:/bills/" + id;
    		}
    		
    	}
    	else {
    		return "Id not found";
    	}
	}
	
	@RequestMapping(value="/bills", method=RequestMethod.GET)
	public ResponseEntity<Collection<Bill>> getPaidBills(@RequestParam(name="filter")String filter){
		if(filter.compareTo("pagadas")==0) {
			return ResponseEntity.status(HttpStatus.OK).body(this.billService.getPayedBills());
		}
		else if(filter.compareTo("no_pagadas")==0) {
			return ResponseEntity.status(HttpStatus.OK).body(this.billService.getNotPayedBills());
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	/*
	@RequestMapping(value="/bills/{idBill}/visits/{idVisit}", method=RequestMethod.GET)
	public Visit getVisit(@PathVariable("idBill")Integer idBill, @PathVariable("idVisit")Integer idVisit) {
		Bill b = this.billService.findById(idBill);
		
	}*/
}
