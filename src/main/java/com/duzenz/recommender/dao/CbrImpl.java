package com.duzenz.recommender.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duzenz.recommender.entities.Cbr;

@Repository
@Transactional
public class CbrImpl implements CbrDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public Cbr insertCbr(Cbr cbr) {
		em.persist(cbr);
		return cbr;
	}

}
