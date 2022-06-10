package io.hexaforce.demo.resource;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.hexaforce.demo.domain.PhoneCallHistory;

@RepositoryRestResource
//public interface PhoneCallHistoryRepository extends PagingAndSortingRepository<PhoneCallHistory, Integer> {
public interface PhoneCallHistoryRepository extends CrudRepository<PhoneCallHistory, Integer> {
	List<PhoneCallHistory> findBySystemTel(String systemTel);
	List<PhoneCallHistory> findBySystemTelOrderByCreatedDateDesc(String systemTel);
	List<PhoneCallHistory> findByCustomerTel(String customerTel);
	PhoneCallHistory findByContactId(String contactId);
}
