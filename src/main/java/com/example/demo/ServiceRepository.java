package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
	
	@Query(value = "SELECT * from Service service WHERE service.name LIKE %:keyword% ", nativeQuery = true)
	List<Service> findByKeyword(@Param("keyword")String keyword);
	
	@Query("Select u from Service u Where u.name =?1")
	Service findByName(String servicename);
	
//	@Query(value = "SELECT service_provider.provider_name as serviceproviderid, category.name as categoryid, service.name, service.price FROM service_provider, category, service where service_provider.id = service.serviceproviderid and category.id = service.categoryid", nativeQuery = true)
//	List<Service> findtheallservices();
}