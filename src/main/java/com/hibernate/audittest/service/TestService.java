package com.hibernate.audittest.service;

import com.hibernate.audittest.model.EnglishLevel;
import com.hibernate.audittest.repo.EnglishLevelRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  @Autowired
  EntityManager entityManager;
  @Autowired
  EnglishLevelRepository repository;

  @Transactional
  public void auditQueryByRevision(int revision){
    AuditReader reader = AuditReaderFactory.get(entityManager);
    AuditQuery query = reader.createQuery()
        .forEntitiesAtRevision(EnglishLevel.class, revision);
    List result=query.getResultList();
    result.forEach(System.out::println);
  }

  @Transactional
  public void auditQueryByEntityClass(){
    AuditReader reader = AuditReaderFactory.get(entityManager);
    AuditQuery query = reader.createQuery()
        .forRevisionsOfEntity(EnglishLevel.class, false, true);
    List result=query.getResultList();
    result.forEach(System.out::println);
  }

}
