package com.hibernate.audittest;

import com.hibernate.audittest.model.EnglishLevel;
import com.hibernate.audittest.repo.EnglishLevelRepository;
import com.hibernate.audittest.service.TestService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaContext;

@SpringBootApplication
public class AuditTestApplication implements CommandLineRunner {

  @Autowired
  EntityManager entityManager;
  @Autowired
  EnglishLevelRepository repository;
  @Autowired
  TestService testService;

  public static void main(String[] args) {
    SpringApplication.run(AuditTestApplication.class, args);
  }

  @Override
  public void run(String... strings) throws Exception {
    getTableList().forEach(System.out::println);
    changeRecord(2,"1");
    changeRecord(2,"2");
    printAllRecord();
    getAuditTableRecords();
    testService.auditQueryByRevision(1);
    testService.auditQueryByEntityClass();
  }

  public void printAllRecord(){
    repository.findAll().forEach(System.out::println);
  }

  public void changeRecord(int number, String change){
    EnglishLevel level=repository.findOne(number);
    level.setName(level.getName()+"="+change);
    repository.save(level);
  }

  public List<String> getAuditTableRecords(){
    Query q = entityManager.createNativeQuery("select * from ENGLISH_LEVEL_AUD");
    List<Object[]> list = q.getResultList();
    List<String> records=new ArrayList<>();
    for (Object[] arr : list) {
      for (Object o : arr) {
        records.add(o.toString());
      }
    }
    return records;
  }

  public List<String> getTableList(){
    Query q = entityManager.createNativeQuery("SHOW TABLES");
    List<Object[]> list = q.getResultList();
    List<String> tables=new ArrayList<>();
    for (Object[] arr : list) {
      for (Object o : arr) {
        tables.add(o.toString());
      }
    }
    return tables;
  }
}
