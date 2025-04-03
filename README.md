# muordi 설계명세서

## 서비스 개요
본 서비스(이하 무디)는 고객이 8개의 카테고리에서 상품을 하나씩 구매하여 자신의 코디를 완성해나가는 기능을 제공합니다.

---

## 구현 범위
본 어플리케이션은 다음의 과업을 포함합니다.
- 상품, 브랜드 관리에 대한 구현 (API 4).
- 전시 관리에 대한 구현 (API 1 ~ 4).
- 검색 & 통계에 대한 구현 (API ~ 3).
- 구현부에 대한 유닛, 통합 테스트.
- 주석, 문서화.

---

## 개발 환경
| 항목    | 사용 요소                          |
|-------|--------------------------------|
| 언어    | JAVA 21 ( openjdk temurin-21 ) |
| 빌드    | graddle                        |
| 프레임워크 | 스프링부트                          |
| DB추상화 | JPA                            |
| DB    | H2                             |
| 단위테스트 | junit                          |
| 통합테스트 | IntellJ http-client            |
| 문서화   | swagger                        |

---

## 실행 방법
```shell
./gradlew build
java -Dspring.profiles.active=init -jar build/libs/muordi-0.0.1-SNAPSHOT.jar
```
스프링 프로파일 설명
- init : 매 실행마다 기존 로컬 DB를 drop 하고 최초 데이터를 저장합니다. 최초 실행 또는 테스트 용으로 사용합니다.
- local : 로컬 DB를 사용합니다. entity 변경이 있는경우 DB 수정만 수행합니다.
- (empty) : 실서비스 용입니다. 스텍트레이스를 남기지 않습니다.

---

## 리소스
- swagger 문서 : http://localhost:8080/swagger
- 단위테스트 수행 : ./gradlew test
- 통합테스트 수행 : IntelliJ 에서 muordi-*.http 파일 읽어온 다음 실행하고 응답 확인.

---

## 과제 분석
### 제약 조건
과제 수행에 있어 다음의 제약 조건을 가정합니다. 
- 판매 상품의 판매가격 정보를 제외한 나머지 요소 ( 재고 확보, 배송료, 광고비 등 ) 에 대해서는 고려하지 않습니다.
- 무디의 전시카테고리 8개는 변경이 없습니다.

### 전제 조건
과제 사항에 명시하고 있지 않지만, 임의로 다음의 전제하에 기능을 구현합니다.
- 각 도메인 별 실무 부서와 개발 부서가 분리되어있습니다.
- 상품 수급, 전시 판매는 서비스별로, 기능별로 분리되어있습니다.
- 모든 상품은 하나의 브랜딩만 가능합니다. 브랜드가 다른 경우 별도의 상품코드를 발급해야합니다.
- 무디 서비스 내 전시 상품은 하나의 전시 카테고리로만 전시가 가능합니다.
- 판매담당자의 실수로 (과제의 전제조건과 다르게) 하나의 브랜드가 모든 카테고리에 상품 전시를 하지 않을 가능성이 있습니다.
- 하나의 브랜드가 하나의 카테고리에 두개 이상의 상품을 전시할 수도 있습니다.
- 서비스를 확대 개편하거나 다른 서브 서비스를 론칭할 수 있다는 가정하에 시스템을 구성합니다.

### 도메인 분석
문제의 요구사항을 각각의 도메인으로 분리하면 다음과 같습니다.

| 대분류      | 중분류     | 기능                  |
|----------|---------|---------------------|
| platform | admin   | 상품 관리 브랜드 관리        |
| contents | display | 전시 카테고리 관리 상품 전시 관리 |
| contents | explore | 검색 및 탐색             |


#### admin
- 상품과 브랜드를 관리합니다. 새로운 상품과 브랜드를 발굴하고 등록, 수정 그리고 삭제합니다.
- 무디 외에 다른 전시 서비스에서도 사용하는 기준정보를 제공합니다. 
- API 4 중 상품과 브랜드에 관한 기능을 제공합니다. 
 
#### display
- 전시 서비스를 관리합니다. 상품을 전시하거나, 전시 정보를 수정하거나 해제합니다.
- 무디의 전시 카테고리 8개를 관리합니다. 단 최초 설정 후 변경은 없다고 가정합니다.
- API 4 중 카테고리와 상품 전시에 관한 기능을 제공합니다.

#### explore
- 검색 및 통계 서비스를 제공합니다.
- 과제의 API 1, 2, 3 을 제공합니다.

---

## 과제 구현

### 입력
각 기능에 대한 입력은 REST API로 구현하였습니다. 자세한 내용과 명세는 http://localhost:8080/swagger 문서를 참고해주십시오.

### 응답
내부에서 발생하는 오류는 GlobalExceptionController에서 처리하여 외부로 반송합니다. 이때 message 외 세부 사유를 포함하는 경우 detail에
필드에 JSON 형태로 기록이 됩니다.
```
404
---
{
  "timestamp": "2025-04-03T17:04:12.096+0900",
  "status": 500,
  "message": "BRAND 의 1 을(를) 다른 곳에서 참조중입니다",
  "detail": {
    "target": "BRAND",
    "key": 1
  },
  "trace": "...",
  "path": "/api/admin/v1/brand/-1"
}
```
| 내부 오류                        | 사유                                       | HTTP 응답 |
|------------------------------|------------------------------------------|---------|
| BaseException                | 무디 내부 오류의 원형, 알 수 없는 오류                  | 500     |
| AlreadyDoneException         | 해당 작업을 이미 수행하여 중복 수행할 수 없음               | 400     |
| NotFoundException            | 조회 하고자 하는 자원이 없음                         | 404     |
| RepositoryException          | Repository 접근 중 오류 발생                    | 500     |
| RepositoryIntegrityException | 데이터 무결성 위배가 발생하여 작업을 중단                  | 500     |
| RepositoryNotExistException  | 필요한 데이터가 존재하지 않음                         | 500     |
| UnavailableException         | 요청한 자원이 일시적으로 제공 불가 함                    | 500     |
| ShowcaseEmptyException       | 전시중이거나, 전시 필수조건에 부합하는 상품이 없음, 있어서는 안되는 일 | 500     |

Repository 접근에 대한 무결성 검증은 1차적으로 서비스 레이어에서 책임을 져야하며, 이 경우 400 또는 404 로 응답한다.

### 데이터 설계
각각의 도메인을 구성하는 entity 와 연관 관계는 다음과 같습니다.

#### BRAND ( 브랜드 )
| 컬럼   | PK | FK | UK | 타입      | 역할     |
|------|----|----|----|---------|--------|
| ID   | O  |    |    | INT(증분) | 브랜드 ID |
| NAME |    |    |    | TEXT    | 브랜드 이름 |

INDEX 
- NAME

#### PRODUCT ( 상품 )
| 컬럼       | PK | FK | UK | 타입       | 역할     |
|----------|----|----|----|-----------|--------|
| ID       | O  |    |    | LONG(증분)  | 상품 ID  |
| BRAND_ID |    | O  |    | BRAND.ID  | 브랜드 ID |
| PRICE    |    |    |    | INT       | 상품 판매가 |

INDEX
- BRAND_ID

#### CATEGORY ( 전시 카테고리 )
| 컬럼               | PK | FK | UK | 타입      | 역할      |
|------------------|----|----|----|---------|---------|
| ID               | O  |    |    | INT(증분) | 카테고리 ID |
| NAME             |    |    |    | TEXT    | 카테고리 이름 |
| DISPLAY_SEQUENCE |    |    | O  | INT     | 전시 순서   |

INDEX
- NAME
카테고리는 추가, 삭제, 변경 않으며, 전시 순서값은 고정입니다.


#### SHOWCASE ( 전시 )
| 컬럼          | PK | FK | UK | 타입          | 역할           |
|-------------|----|----|----|-------------|--------------|
| ID          | O  |    |    | INT(증분)     | 더미 키         |
| PRODUCT_ID  |    | O  | O  | PRODUCT.ID  | 상품에 대한 외래키   |
| CATEGORY_ID |    | O  |    | CATEGORY.ID | 카테고리에 대한 외래키 |

INDEX
- PRODUCT_ID
- BRAND_ID
상품 ID는 유일키 입니다. 이로써 하나의 상품은 하나의 카테고리에만 입점하는것을 보장합니다.

### 어플리케이션 구조
```root
├── common
│   ├── exception  공통 예외처리
│   ├── jpa        원자적 업데이트 기능 구현 라이브러리
│   └── util       기타 공통기능
├── config         설정
├── contents  
│   ├── display    전시 도메인
│   └── explore    통계 & 검색 도메인
└── platform
    └── admin      관리자 도메인
```
resources와 단위 테스트 파일은 스프링 표준 구조에 따라 배치합니다.

### 문제 해결

_ES를 이용한 문제 해결은 후술한다._

#### API 1
<b>카테고리 별 최저가격 브랜드의 이름과 상품가격, 그리고 가격 총액 조회</b>
```h2
select distinct on (CATEGORY_ID) CATEGORY_ID, BRAND_ID, PRICE from (
    select category_id as CATEGORY_ID, PRODUCT.brand_id as BRAND_ID, PRODUCT.price as PRICE
    from SHOWCASE
    inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
    inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
    order by CATEGORY.display_sequence asc, price asc, brand_id desc
);
```
SHOWCASE-PRODUCT 테이블을 조인하여 카테고리, 가격 오름차순 & 브랜드 내림차순으로 정렬한 다음 카테고리에 대한 DISTINCT ON 레코드를 구하면 됩니다. 그러나
DISTINCT ON 의 구현이 DB 마다 달라 일관적인 동작을 기대할 수 없어 Jpa 는 H2 의 DISTINCT ON 을 제공하지 않습니다(라고 합니다). 또한 H2 DB가 GROUP BY 
컬럼 외 다른 TEXT 컬럼의 SELECT 을 허용하지 않아 SQL 로 직접 처리하는 대신 전체 상품을 정렬한 다음 로직으로 DISTINC ON 을 구현하였습니다.

만약 전체 카테고리에 제품을 모두 전시하지 않는 브랜드가 있더라도 API 1의 조회 대상에 포함됩니다.

### API 2
<b>단일 브랜드로 모든 카테고리 상품 구매 시 최저 가격 브랜드와 카테고리 별 상품의 판매가, 총액 조회</b>
```h2
select BRAND_ID, sum(PRICE), count(*) from (
    select DISTINCT ON(CATEGORY_ID, PRODUCT_ID) category_id as CATEGORY_ID, PRODUCT.brand_id as BRAND_ID, PRODUCT.price as PRICE
    from SHOWCASE
    inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
    inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
    order by CATEGORY.display_sequence asc, price asc, brand_id desc
)
group by BRAND_ID
having count(*)=8
order by sum(PRICE)
limit 1;
```
우선 SHOWCASE-PRODUCT 테이블을 조인하여 카테고리, 가격 오름차순 & 브랜드 내림차순으로 정렬 한 다음 카테고리, 브랜드에 대한 DISTINCT ON을 구하는 
서브쿼리를 실행하여 카테고리-브랜드 별 최저가 상품의 가격을 조회합니다. 이걸 다시 브랜드로 GROUP BY 한 다음 가격 합을 구한 뒤 가격 합의 오름 차순으로
정렬하면 최저가 브랜드를 구할 수 있습니다. 만약 사용자가 실수로 하나의 브랜드를 모든 카테고리에 전시하지 않는 경우 금액 합이 과소 계상 될 수 있습니다. 
따라서 <b>브랜드 별 레코드 개수가 카테고리 개수와 일치</b>하는 경우에만 조회 대상이 되도록 조건을 겁니다.

단, 이렇게 한다음 다시 DB에서 상품 조회하는 경우 DB에 부하가 가해질 수 있고, 또 Jpa 나 H2 DB의 제약으로 해당 쿼리를 직접 실행하는것이 어려워
서브 쿼리 수행 후 금액 집계는 로직으로 구현하였습니다.

### API 3
<b>카테고리 이름으로 최저, 최고 가격 브랜드와 상품가격 조회</b>
```h2
select CATEGORY.NAME, BRAND.name as BRAND_NAME, product.price as PRICE
from SHOWCASE
inner join PRODUCT on SHOWCASE.product_id = PRODUCT.id
inner join CATEGORY on SHOWCASE.category_id = CATEGORY.id
inner join BRAND on PRODUCT.brand_id = BRAND.id
where CATEGORY.name='상의'
order by PRICE desc
limit 1;
```
SHOWCASE-CATEGORY 테이블을 조인하여 카테고리 이름 조건으로 최대 가격과 최소 가격을 구하는 두개의 쿼리를 병렬로 수행합니다.

### API 4
각 도메인 별로 CRUD 를 구현합니다. 이때 다양한 조건을 제공할 수 있도록 필요한 경우에 한해 INDEX 를 생성합니다.

---

## 더 생각해야 할 부분

### RDB 처리 시 부하 분산
대용량 데이터 환경에서는 DB 서버와 APP 서버의 스펙을 고려하여 API 1, 2, 3 을 구현해야합니다. 일반적인 환경의 경우 상대적으로 WAS 의 부하가 적어 
합계 연산은 WAS에서 수행하거나, 이름 테이블은 JOIN 하지 않는 대신 별도의 쿼리를 날려 가져오는 방향으로 구현할 수 있지만, 상대적으로 DB 서버의 부하 한게가
큰 엔터프라이즈 환경에서는 모든 JOIN 과 SUM, AVG 연산을 쿼리로 수행하는것도 검토할 수 수 있습니다.

### ES 로의 전환
실서비스 환경에서는 트랜잭션이 활발히 이루어지므로, 사실상 FULLSCAN 이 이루어지는 DB에서 통계 정보를 수집하는것은 효율적인 선택은 아닙니다. 전시 상품의
정보변경이 고객 활동 트랜잭션보다는 훨씬 적으므로, 동적 색인 지연 시간동안의 일시적인 최신 정보의 불일치는 고객의 경험에 큰 악영향을 주지는 않을것이란 판단을 
하였습니다. 이에 ES를 이용해 API 1 ~ API 3 의 통계 정보를 작성하는 방법을 검토해보았습니다.

#### API 1
```
{
    "aggs": {
        "category": {
            "terms": {
                "field": "categoryId"
            }, 
            "aggs": {
                "price": {
                    "top_hits": {
                        "sort": [
                            {"price": "asc", "brandId": "desc"}
                        ],
                        "_source": {
                            "includes": ["categoryId", "categoryName", "brandId", "brandName", "price"]
                        },
                        "size":1
                    }
                }
            }
        }
    }
}
```
RDB SQL 의 SELECT DISTINCT ON 과 동일한 동작을 수행합니다.

#### API 2
```
{
    "aggs": {
        "brand": {
            "terms": {
                "field": "brandId"
            }, 
            "aggs": {
                "categories": {
                    "cardinality": {
                        "field": "categoryId"
                    }
                },
                "categories_filter": {
                    "bucket_selector": {
                        "buckets_path": {
                            "categories": "categories"
                        },
                        "script": "params.categories == 8"
                    }
                },
                "category": {
                    "terms": {
                        "field": "categoryId"
                    }, 
                    "aggs": {
                        "priceMin": {
                            "min": {
                                "field": "price"
                            }
                        }
                    }
                },
                "totalAmount": {
                    "sum_bucket": {
                        "buckets_path": "category>priceMin"
                    }
                }
            }
        },
        "brand_filter": {
            "min_bucket": {
                "buckets_path": "brand>totalAmount"
            }
        }
    }
}
```
기존의 전제 조건인 

1. 하나의 브랜드가 하나의 카테고리에 두개 이상의 상품을 전시할 수 있다 
2. 담당자의 실수로 일부 카테고리에 브랜드의 전시를 누락할 수도 있다.

의 경우에 대응하기 위해 min aggregation 과 bucket_selector를 사용하였습니다. 로직은 다음과 같습니다.
1. 브랜드 기준으로 aggregation을 수행합니다.
2. 버킷에 담긴 브랜드의 카테고리 가짓수(cardinality)를 구하여 카테고리 개수(=8)를 만족하는 버킷만 필터합니다.
3. 카테고리별로 상품의 최저가를 구합니다. 이 시점에 브랜드+카테고리 별 최저 상품 가격이 정리됩니다.
4. 버킷의 모든 상품의 가격의 합(totalAmount)을 구합니다.
5. totalAmount 기준 최저가의 브랜드 정보를 구합니다.

#### API 3
```
{
    "query": {
        "bool": {
            "must": [
                {"match" : {"categoryName" : ":카테고리 이름"}}
            ]
        }
    },
    "sort" : [
        {"price": {"order":"desc"}}
    ],
    "size": 1
}
```
카테고리 이름 기준으로 전시 상품을 조회하고 최고가, 최저가를 구하는 SQL 과 동일합니다.

#### 구현
Springboot에서 제공하는 `Elasticsearch Repositories` 로 인덱스 생성과 색인을 구현하고, Elasticsearch에서 제공하는
`Elasticsearch Java API Client` 를 이용해 native query를 실행하는 방법으로 구현합니다.

---

## 검증 방법
1. 각각의 기능 단위로 유닛 테스트를 구현하였습니다. 유닛 테스트는 Positive / Negative 케이스를 모두 고려합니다.
2. 통합테스트는 서비스 레벨의 유닛테스트와, intelliJ 의 http 에 정의한 시나리오로 수행합니다.
3. swagger를 이용하여 end-to-end 테스트를 수행합니다.










  
