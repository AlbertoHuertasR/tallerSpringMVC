package org.springframework.samples.petclinic.owner;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>{

	@Query("select b from Bill b where b.visit <> null")
	Collection<Bill> getPayedBills();
	
	@Query("select b from Bill b where b.visit = null")
	Collection<Bill> getNotPayedBills();
	
	
}