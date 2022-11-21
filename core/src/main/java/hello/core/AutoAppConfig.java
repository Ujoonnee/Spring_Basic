package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    /**
     * basePackage : 해당 패키지부터 하위 패키지의 @Component를 스캔, 중복 설정 가능
     * basePackageClasses : 해당 클래스의 패키지부터 하위 패키지의 @Component를 스캔, 중복 설정 가능
     * 위 두 설정값이 없으면(default), @ComponentScan이 붙은 클래스의 패키지를 basePackage로 간주하고 스캔
     *
     * excludeFilters = 해당 조건의 class는 bean으로 등록하지 않음
     */

}
