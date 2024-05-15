package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 애플리케이션의 실제동작에 필요한 구현객체를 생성
@Configuration
public class AppConfig {

    //@Bean(name="") 으로 이름을 지정해 줄 수도있다(관례상 안하는 것)
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
        // 구현체를 메모리 -> DB로 변경할 땐 AppConfig에서만 수정해주면 됨
        // 객체를 생성자를 통해서 주입 -> 생성자 주입
    }

    @Bean
    public static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        //할인정책을 변경할 때 이부분만 변경해주면된다.
        return new RateDiscountPolicy();
    }
}
