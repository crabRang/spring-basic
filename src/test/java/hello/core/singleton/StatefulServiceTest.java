package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServieSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(testConfig.class);

        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA : A사용자가 10000원 주문
        statefulService1.order("userA", 10000);
        //ThreadB : B사용자가 20000원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자A 주문 금액 조회
        int price = statefulService1.getPrice();
        //A가 주문하고 조회하는 사이에 B가 주문을 했기 때문에 20000원이 조회된다.
        //statefulService1과 statefulService2가 같은 객체이기 때문에 공유되는 필드인 price가 10000 -> 20000으로 변경되는 것
        System.out.println("price = "+price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class testConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}