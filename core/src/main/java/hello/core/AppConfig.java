package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /**
     * @Configuration에 의해 AppConfig가 CGLIB에 의해 처리 되어 memberRepository의 싱글톤이 보장됨
     * @Configuration 대신 @Bean을 사용한다면 순수 자바코드가 동작하고, new MemoryMemberRepository()가 세번 호출됨
     *
     * 문제 1. 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않음
     *          - 각각의 MemoryMemberRepository의 instance는 동일하지 않음(싱글톤 깨짐)
     * 문제 2. 문제 1의 호출된 메서드에 의해 생성된 instance는 spring bean이 아님
     */

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixedDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
