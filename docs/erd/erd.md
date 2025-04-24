## ERD

```mermaid

erDiagram

  USER ||--o{ USER_POINT : has
  USER ||--o{ USER_COUPON : owns
  USER ||--o{ ORDER : places
  PRODUCT ||--o{ ORDER_PRODUCT : ordered
  COUPON ||--o{ USER_COUPON : issued
  ORDER ||--o{ ORDER_PRODUCT : includes
  USER_POINT ||--o{ POINT_HISTORY : records
  COUPON_CATEGORY ||--o{ COUPON : has
  COUPON ||--|| COUPON_STOCK : has
  ORDER ||--|| PAYMENT : has_one

  USER {
    VARCHAR user_id PK "회원 ID"
    VARCHAR address "주소"
    TIMESTAMP cdate "생성일"
  }

  USER_POINT {
    VARCHAR user_id FK "회원 ID"
    DECIMAL user_point "잔액"
  }

  POINT_HISTORY {
    INT history_id PK "내역 ID"
    VARCHAR user_id FK "회원 ID"
    DECIMAL amount "금액"
    INT point_type "사용 종류"
    TIMESTAMP cdate "생성일"
  }

  COUPON {
    INT coupon_id PK "쿠폰 ID"
    INT coupon_category FK "쿠폰 카테고리 ID"
    VARCHAR coupon_name "쿠폰 이름"
    INT discount "할인율"
    TIMESTAMP expired_date "만료일"
    INT coupon_state "쿠폰 상태 (정상, 만료, 삭제)"
  }

  COUPON_CATEGORY {
    INT category_id PK "쿠폰 카테고리 ID"
    VARCHAR category_name "카테고리 이름"
    VARCHAR category_desc "카테고리 설명"
  }

  COUPON_STOCK {
    INT coupon_id PK, FK "쿠폰 ID"
    INT coupon_stock "쿠폰 재고"
  }

  USER_COUPON {
    VARCHAR user_id FK "회원 ID"
    INT coupon_id FK "쿠폰 ID"
    TIMESTAMP cdate "등록일"
    INT used_state "사용 상태"
  }

  ORDER {
    INT order_id PK "주문 ID"
    VARCHAR user_id FK "회원 ID"
    INT coupon_id FK "쿠폰 ID"
    INT product_id "상품 ID"
    INT order_price "금액"
    INT quantity "수량"
    VARCHAR address "회원 주소"
    TIMESTAMP cdate "주문 날짜"
  }

  PRODUCT {
    INT product_id PK "상품 ID"
    VARCHAR product_name "상품명"
    DECIMAL product_price "상품 가격"
    INT stock "재고"
    INT product_state "상품 상태"
    TIMESTAMP cdate "상품 등록일"
    TIMESTAMP udate "상품 수정일"
  }

  ORDER_PRODUCT {
    INT op_id PK "상품 주문 ID"
    INT order_id FK "주문 ID"
    INT product_id FK "상품 ID"
    INT coupon_id FK "쿠폰 ID"
    INT op_quantity "주문 개수"
    DECIMAL product_price "상품 금액"
  }

  PAYMENT {
    INT payment_id PK "결제 ID"
    INT order_id FK "주문 ID"
    VARCHAR payment_method "결제 수단 (삼성페이, 카카오)"
    INT payment_amount "결제 금액"
    VARCHAR payment_status "상태 (요청, 성공, 실패, 취소)"
    TIMESTAMP payment_date "결제 완료 시각"
    TIMESTAMP cdate "생성일"
    TIMESTAMP udate "수정일"
  }