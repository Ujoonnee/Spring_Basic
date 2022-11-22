package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    /**
     * Provider 또는 proxy를 사용해 실제 요청 시점에 빈을 주입(또는 생성)받을 수 있다.<br>
     * 핵심은 진짜 객체의 조회를 필요한 시점까지 지연시켜 처리할 수 있다는 것
     */
    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
//        MyLogger myLogger = myLoggerProvider.getObject();
        String requestUrl = request.getRequestURL().toString();
        myLogger.setRequestURL(requestUrl);

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.log("controller test");
        Thread.sleep(2000L);
        logDemoService.logic("testId");

        return "OK";
    }
}
