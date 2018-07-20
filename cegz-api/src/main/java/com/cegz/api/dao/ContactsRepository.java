package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cegz.api.model.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long>{

}
