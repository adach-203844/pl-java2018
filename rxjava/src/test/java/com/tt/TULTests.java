package com.tt;

import com.tt.cache.CacheServer;
import com.tt.dao.Person;
import com.tt.dao.PersonDao;
import com.tt.weather.Weather;
import com.tt.weather.WeatherClient;
import rx.observers.TestSubscriber;
import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

/**
 * Created by dubelm on 2017-05-15.
 */
public class TULTests {

  private static final Logger log = LoggerFactory.getLogger(TULTests.class);
  public static final BigDecimal FALLBACK = BigDecimal.ONE.negate();

  void print(Object obj) {
    log.info("We've got: {},", obj);
  }

  @Test
  public void shouldTest001() throws ExecutionException, InterruptedException {
    CompletableFuture<String> stringCompletableFuture = CompletableFuture.completedFuture("123");

    CompletableFuture<Integer> intFuture = stringCompletableFuture
        .thenApply((String item) -> item.length() + 1);

    CompletableFuture<Integer> integerCompletableFuture = intFuture
        .thenApply((Integer intVal) -> intVal * 10);

    print(integerCompletableFuture.get());
  }

  @Test
  public void shouldTest002() {
    Observable<String> just = Observable.just("123", "asda", "1231344");
    just.subscribe(this::print);
  }

  WeatherClient client = new WeatherClient();

  @Test
  public void shouldTest003() {
//        print(client.fetch("Warsaw"));
    Observable<Weather> lodz = client.rxFetch("Lodz");
    Observable<Weather> timeout = lodz.timeout(100, TimeUnit.MILLISECONDS);
    timeout.subscribe(this::print);
  }

  @Test
  public void shouldTest004() {
    Observable<Weather> warsaw = client.rxFetch("Warsaw");
    Observable<Weather> radom = client.rxFetch("Radom");

    Observable<Weather> weatherObservable = warsaw.mergeWith(radom);
    weatherObservable.subscribe(this::print);
  }

  PersonDao dao = new PersonDao();

  @Test
  public void shouldTest005() throws InterruptedException {
    Observable<Weather> warsaw = client
        .rxFetch("Warsaw")
        .subscribeOn(Schedulers.io());
    Observable<Person> personObservable = dao
        .rxFindById(55)
        .subscribeOn(Schedulers.io());

    Observable<String> zipped = warsaw
        .zipWith(personObservable, (Weather w, Person p) -> w + " : " + p);

    zipped.subscribe(this::print);

    TimeUnit.SECONDS.sleep(3);
//    Schedulers.computation();
//    Schedulers.test();
  }

  @Test
  public void shouldTest006() {
    Observable<String> strings = Observable
        .just("A", "B", "C")
        .repeat();

    Observable<Integer> numbers = Observable
        .range(1, 10)
        .map(x -> x * 100);

    Observable<String> zip = Observable
        .zip(strings, numbers, (s, n) -> s + n);

    zip.subscribe(this::print);
  }

  @Test
  public void shouldTest007() {
    CacheServer c1 = new CacheServer();
    CacheServer c2 = new CacheServer();

    Observable<String> s1 = c1.rxFindBy(123);
    Observable<String> s2 = c2.rxFindBy(123);

    Observable<String> allResults = s1.mergeWith(s2).first();

    allResults.toBlocking().subscribe(this::print);

  }

  @Test
  public void shouldTest008() {
    File dir = new File(".\\test\\test\\");

    Observable
        .interval(1, TimeUnit.SECONDS)
        .concatMapIterable(x -> childOfDir(dir))
        .distinct()
        .toBlocking()
        .subscribe(this::print);
  }

  List<String> childOfDir(File file) {
    return Arrays
        .asList(file.listFiles())
        .stream()
        .map(File::getName)
        .collect(Collectors.toList());
  }

  Observable<Long> verySlowService() {
    return Observable.timer(10, TimeUnit.MINUTES);
  }

  @Test
  public void shouldTest009() {
    Observable<Long> slow = verySlowService();

    TestScheduler testScheduler = Schedulers.test();
    TestSubscriber<Object> testSub = new TestSubscriber<>();

    slow
        .timeout(2, TimeUnit.SECONDS, testScheduler)
        .doOnError(ex -> log.warn("Something went wrong" + ex))
        .retry(4)
        .onErrorReturn(ex -> -1L)
        .subscribe(testSub);

    testSub.assertNoValues();
    testSub.assertNoErrors();

    testScheduler.advanceTimeBy(9999, TimeUnit.MILLISECONDS);

    testSub.assertNoValues();
    testSub.assertNoErrors();

    testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS);

    testSub.assertNoErrors();
    testSub.assertValue(-1L);
  }
}
