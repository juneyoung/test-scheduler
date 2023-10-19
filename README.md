# 00. 개요
스케쥴링 작업을 지원할 수 있는 단독 모듈이다<br>
Spring 등 최신 라이브러리가 지원되지 않는 상황에 대처하기 위해 만들어진 클래스

### 특징
- 외부 라이브러리 의존이 없디
  - 스케쥴링 내부의 동작은 caller 가 판단한다
- 스케쥴링 유형
  - cron expression
  - interval
# 01. 지원 기능
- 초 단위 수행할 잡을 등록하고 주기적으로 실행할 수 있음

# 02. 사용법
### 주의 사항
- Caller 에서 반드시 `stop` 을 호출하여 하위 쓰레드에 interrupt 를 호출 하여야 한다
```java
public class ExampleCaller {
  private final ScheduleManager manager;

  public ExampleCaller(ExecutorService executorService, Runner runner) {
      // ScheduleManager 는 전달된 ExecutorService 에 Runner 조건에 맞게 하위 작업을 호출함
      this.manager = new BasicSchedulerManager(executorService);
      this.manager.registerSchedule("scheduler", runner);
      this.manager.start(); // 기동
  }
  
  public void execute() {
      // do whatever 
  }
  
  public void onDestroy() {
      this.manager.stop(); // interrupt 하위 쓰레드
  }
}
```

# 99. 사양
- JDK1.6 이상
