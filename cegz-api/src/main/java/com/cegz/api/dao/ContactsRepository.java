package com.cegz.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cegz.api.model.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

	/**
	 * 查询车主审核列表
	 * 
	 * @return
	 */
	@Query(value = "select c.id, c.name, c.phone, c.status from Contacts c where 1=1 and c.isDeleted = 0")
	public List<Contacts> getContactsExamineList();

}
