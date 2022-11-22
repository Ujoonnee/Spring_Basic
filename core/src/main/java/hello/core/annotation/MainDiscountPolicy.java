package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    /**ㄹㄹ
     * @Qualifier 사용 시 내용을 문자열로 입력하므로 오입력해도 컴파일 단계에서 잡아내기 힘들다.
     * 따라서 새로운 애노테이션을 정의 후 정의한 애노테이션에 Qualifier를 지정한 뒤,
     * 프로그램에서 새 애노테이션을 사용함으로써 컴파일 단계에서 오류를 방지한다.
     */
}
