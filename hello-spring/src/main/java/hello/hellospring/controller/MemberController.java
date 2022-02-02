package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired  // spring이 spring container의 memberService를 자동으로 연결시켜줌. Dependency Injection.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
