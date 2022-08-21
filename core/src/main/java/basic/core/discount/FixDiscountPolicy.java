package basic.core.discount;

import basic.core.member.Grade;
import basic.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountfixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountfixAmount;
        } else {
            return 0;
        }
    }
}
