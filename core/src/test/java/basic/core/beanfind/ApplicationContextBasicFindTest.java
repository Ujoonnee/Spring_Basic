package basic.core.beanfind;

import basic.core.AppConfig;
import basic.core.member.MemberService;
import basic.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);

//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService = " + memberService.getClass());

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByType2() {
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

        // 구현체로 조회할 수도 있지만, 역할과 구현을 분리하고 역할에 의존해야 하므로 지양
    }

    @Test
    @DisplayName("빈 이름으로 조회 실패")
    void findByBeanNameFail() {
//        MemberService memberService = ac.getBean("없는 빈 이름", MemberService.class);

        // 람다식을 실행했을 때, 명시한 예외가 발생하면 성공
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("없는 빈 이름", MemberService.class));
    }

}
