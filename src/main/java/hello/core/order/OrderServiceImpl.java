package hello.core.order;

import hello.core.member.Member;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
    private final MemberRepository memberRepository;
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 할인정책 정액 -> 정률 변경
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 추상클래스인(iscountPolicy) 뿐만이 아닌 구현클래스(FixDiscountPol
    // icy / RateDiscountPolicy)또 의존하고 있음 - DIP 위반
    // 기능 확장을 하면 클라이언트코드(OrderServiceImpl)도 변경을 해야함 - OCP위반
    private final DiscountPolicy discountPolicy;
    // 인터페이스에만 의존하도록 변경 -> 구현체가 없기 때문에 NullPointerException 발생
    // 문제해결을 위해 누군가가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현객체를 대신 생성하고 주입해주어야 함
    // 구현 객체를 생성하고 연결하는 책임을 가지는 클래스 생성 -> AppConfig.class

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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

    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}