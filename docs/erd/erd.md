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
    INT op_quantity "주문 개수"
    DECIMAL product_price "상품 금액"
  }

  COUPON {
    INT coupon_id PK "쿠폰 ID"
    VARCHAR coupon_name "쿠폰 이름"
    INT coupon_stock "쿠폰 재고"
    INT discount "할인율"
    TIMESTAMP expired_date "만료일"
    INT state "쿠폰 상태"
  }

  USER_COUPON {
    VARCHAR user_id FK "회원 ID"
    INT coupon_id FK "쿠폰 ID"
    TIMESTAMP cdate "등록일"
    INT used "사용 상태"
  }

  ORDER {
    INT order_id PK "주문 ID"
    VARCHAR user_id FK "회원 ID"
    INT product_id "상품 ID"
    INT order_price "금액"
    INT quantity "수량"
    VARCHAR address "회원 주소"
    TIMESTAMP cdate "주문 날짜"
  }