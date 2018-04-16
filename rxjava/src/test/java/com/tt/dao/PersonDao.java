package com.tt.dao;

import com.tt.util.Sleeper;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class PersonDao {

  private static final Logger log = LoggerFactory.getLogger(PersonDao.class);

  public Person findById(int id) {
    //SQL, SQL, SQL
    log.info("Loading {}", id);
    Sleeper.sleep(Duration.ofMillis(1000));
    return new Person();
  }

  public Observable<Person> rxFindById(int id) {
    return Observable.fromCallable(() -> findById(id));
  }

}