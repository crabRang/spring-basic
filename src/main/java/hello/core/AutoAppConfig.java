package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @ComponentScan : @Component가 붙은 클래스를 모두 스캔해서 스프링빈으로 등록해준다
@ComponentScan(
        //basePackages = "hello.core.member", //해당패키지(member)만 ComponentScan의 대상이 됨
        //basePackages를 지정하지 않으면, @@ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //기존의 예제를 유지하기위해 @Configuration 애노테이션이 붙은 설정정보를 제외(보통 제외하진않는다)
)
public class AutoAppConfig {

    @Bean(name="memoryMemberRepository")
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
