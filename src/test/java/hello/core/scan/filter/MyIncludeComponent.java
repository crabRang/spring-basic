package hello.core.scan.filter;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
    //MyIncludeComponent가 붙은건 컴포넌트 스캔대상
}
