package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI컨테이너")
    void pureContatiner(){
        AppConfig appConfig = new AppConfig();
        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른 것을 확인
        System.out.println("memberService1 = "+memberService1); //hello.core.member.MemberServiceImpl@105fece7
        System.out.println("memberService2 = "+memberService2); //hello.core.member.MemberServiceImpl@3ec300f1

        //memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);

        //memberService를 호출할 때마다 다른 객체가 생성되는 것
        //웹 어플리케이션의 특징 : 고객의 요청이 굉장히 많다.
        //요청을 할 수록 객체가 생성되면 효율적이지 않음 -> JVM에 여러개의 인스턴스가 생성 -> 메모리낭비가 굉장히 심하다
        //해결방안 : 해당 객체가 1개만 생성되고, 공유하도록 설계하면 된다 -> 싱글톤패턴
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest(){
        //private로 생성자를 막아두어 컴파일 오류발생
        //new SingletonService();

        //1. 조회 : 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();
        //2. 조회 : 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance();

        //참조값이 같은 것을 확인
        System.out.println("singletonService1 = "+singletonService1); //hello.core.singleton.SingletonService@2145433b
        System.out.println("singletonService2 = "+singletonService2); //hello.core.singleton.SingletonService@2145433b

        assertThat(singletonService1).isSameAs(singletonService2);
        //same : ==
        //equal : java의 equals
        singletonService1.logic();

        //싱글톤 패턴을 사용하면 기존객체를 재활용하기 때문에 메모리 낭비가 없다
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //1. 조회 : 호출할 때마다 같은 객체를 반환
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        //1. 조회 : 호출할 때마다 같은 객체를 반환
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = "+memberService1);
        System.out.println("memberService2 = "+memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }
}