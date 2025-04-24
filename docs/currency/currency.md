# 동시성 이슈 보고서: 포인트 차감, 선착순 쿠폰 발급, 상품 재고 감소

## 1. 문제 식별


1. **포인트 차감**  
   여러 주문 요청이 동시에 들어올 경우, 사용자 포인트가 중복 차감될 수 있습니다.

2. **선착순 쿠폰 발급**  
   재고가 1개인 쿠폰을 다수 사용자가 동시에 발급받으려 할 경우, 중복 발급이 발생할 수 있습니다.

3. **상품 재고 감소**  
   동일한 상품을 여러 사용자가 동시에 주문하면 재고가 음수로 떨어질 수 있습니다.

---

## 2. 분석

### 2.1 포인트 차감
- `userPointRepository.findByUserId(...)`로 단순 조회
- 트랜잭션이 없거나 락이 없다면 중복 차감 가능

### 2.2 쿠폰 발급
- `couponRepository.findById(...)`를 통해 쿠폰 조회
- `couponStock` 감소 전에 동시 접근 시 여러 사용자에게 발급 가능

### 2.3 상품 재고
- `productRepository.findByProductId(...)`로 재고 조회
- 동시 접근 시 재고 차감 로직 중첩으로 음수 재고 발생 가능

---

## 3. 해결 방안

### 3.1 포인트 차감

```java
@Transactional
public boolean usePoint(...) {
    UserPoint point = pointRepository.findByUserIdForUpdate(...);
    point.use(...); // 포인트 차감
    pointRepository.save(point);
}
```
### 3.2 쿠폰 발급
```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT c FROM Coupon c WHERE c.couponId = :couponId")
Optional<Coupon> findByIdForUpdate(@Param("couponId") Integer couponId);
```
```java
@Transactional
public Boolean createCoupon(CommandCoupon command) {
    Coupon coupon = couponRepository.findByIdForUpdate(command.getCouponId())
        .orElseThrow(...);
    coupon.stockCheck(); // 내부에서 재고 감소
    ...
}
```

### 3.3 상품 재고
```java
@Transactional
public void orderProduct(...) {
    Product product = productRepository.findByIdForUpdate(...);
    product.decreaseStock(quantity); // 재고 차감
    productRepository.save(product);
}
```

### 4. 결론
포인트, 쿠폰, 재고는 모두 @Transactional + 비관적 락을 통해 동시성 문제를 방지해야 합니다.

핵심은 findByIdForUpdate() 패턴을 사용하고 DB 트랜잭션 안에서 처리하는 것입니다.

테스트는 ExecutorService + CountDownLatch를 이용한 멀티스레드 테스트로 검증 가능