package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;


//    constructor injection, 조립 시점에 생성되고 외부와 격리되어 있을 수 있다는 장점이 있음
    @Autowired  // spring이 spring container의 memberService를 자동으로 연결시켜줌. Dependency Injection.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    필드에 @Autowired 어노테이션으로 필드 주입 방식의 DI 를 할 수 있으나 지양
//    @Autowired private final MemberService memberService;


//    setter injection (필드가 final 이 아니어야 함)
//    단점 : memberService 가 한 번 설정되면 이후에 바뀔 일이 없는데, setter 는 public 으로 계속 노출되어 있음

//    @Autowired
//    public MemberService setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("memvbers/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
