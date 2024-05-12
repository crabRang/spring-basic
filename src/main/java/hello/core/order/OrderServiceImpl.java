package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    //주문생성요청
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        // 회원조회
        Member member = memberRepository.findById(memberId);
        // 할인 적용
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문 결과 반환
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}