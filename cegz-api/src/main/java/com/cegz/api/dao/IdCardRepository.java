package com.cegz.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cegz.api.model.IdCard;
/**
 * 身份证持久化接口
 * @author lijiaxin
 * @date 2018年7月24日
 */
public interface IdCardRepository extends JpaRepository<IdCard, Long>{

}
