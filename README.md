# 📚 도서 검색 서비스 (Book Search Service)

도서 검색 서비스는 사용자가 도서를 검색하고 상세 정보를 조회할 수 있는 RESTful API를 제공합니다.   
단순 키워드 검색부터 복합 논리 검색까지 지원하며, 인기 검색어 조회 기능을 제공합니다.

## 📝 프로젝트 개요

* **기간**: 2025.08.11 ~ 2025.08.18
* **인원**: 1인
* **주요 목표**  
  **✔️ 확장 가능한 아키텍처 설계:** 도메인과 비즈니스 로직을 아키텍처의 중심에 두고 각 계층이 역할에 따라 분리되어 상호 독립적으로 동작할 수 있도록 설계  
  **✔️ 복합 조건 검색 제공:** OR, NOT 등 다양한 검색 연산자를 활용해 사용자에게 다양한 검색 경험 제공   
  **✔️ 빠른 도서 검색 제공:** 빠른 검색 기능 구현을 통해 사용자 중심의 실질적 가치를 창출

## 🚀 서비스 주요 기능 및 처리 흐름

### 주요 기능

* **도서 상세 조회**: 식별자를 통해 도서의 상세 정보를 조회합니다.
* **단순 검색**: 사용자가 입력한 단일 키워드를 기반으로 도서를 검색합니다.
* **복합 검색**: 두 개의 키워드와 논리 연산자(OR, NOT)를 조합해 도서를 검색합니다.
* **인기 검색어 조회**: 가장 많이 검색된 키워드 상위 10개를 조회할 수 있습니다.

[API 명세서](https://documenter.getpostman.com/view/30686816/2sB3BGJVqC)

### 복합 검색 요청 흐름도

![image](https://github.com/user-attachments/assets/43f61ae3-16f0-46b7-81e8-c29963b7d6d7)

## 🏛 도메인 모델 상세

### 도서(Book) 도메인

#### 속성 (Attributes)

* `isbn` : `String` — 도서의 고유 식별자(국제 표준 도서 번호)
* `basicInfo` : `BasicInfo` — 기본 정보
* `publicationInfo` : `PublicationInfo` — 출판 정보

#### 기본 정보 (BasicInfo)

* `title` : `String` — 제목
* `subtitle` : `String` — 부제목
* `author` : `String` — 저자

#### 출판 정보 (PublicationInfo)

* `publisher` : `String` — 출판사
* `publishedDate` : `LocalDate` — 출판일

### 검색 쿼리(SearchQuery) 도메인

#### 속성 (Attributes)

* `firstKeyword` : `String` — 첫 번째 검색 키워드
* `secondKeyword` : `String` — 두 번째 검색 키워드
* `operator` : `SearchOperator` — 키워드 간 논리 연산자 (예: NONE, OR, NOT, AND)

#### 검색 연산자 (SearchOperator)

* `NONE` : 검색 연산자 없음
* `OR` : 두 키워드 중 하나 포함
* `NOT` : 첫 번째 키워드 포함, 두 번째 키워드 제외

#### 행위 (Behavior)

* **검색 쿼리 생성** : 키워드와 연산자를 받아 불변 객체로 생성
    * 키워드는 반드시 두 개여야 함
    * 연산자는 반드시 지정되어야 함
* **규칙 검증** : 생성 시 도메인 규칙(validate) 수행

## 🐳 실행 방법

#### 1. 레포지토리 클론

```bash
git clone https://github.com/yurim0628/book-search-service.git
cd book-search-service
```

#### 2. Docker Compose로 컨테이너 빌드 및 실행

```bash
docker compose up -d --build
```

#### 3. 애플리케이션 접속

* 웹 브라우저에서 아래 주소로 접속

```
http://localhost:8080
```

#### 4. 컨테이너 중지

```bash
docker compose down
```

## 🛠 기술 스택

* **Java 21:** 최신 언어 기능 및 성능 개선, 장기 지원\(LTS\)
* **Spring Boot 3.5.4:** 빠른 개발, 의존성 관리, REST API 구축에 최적화
* **MySQL 8.2:** 안정적이고 확장성 있는 관계형 데이터베이스
* **JUnit 5.10.0:** 단위 테스트 및 자동화 테스트 지원
* **Docker 27.4.0:** 환경 일관성, 배포 자동화, 컨테이너 기반 개발/운영

## 🏗️ 아키텍처 설계 및 패키지 구조

### 아키텍처 설계

본 프로젝트는 **유지보수성 (Maintainability)**, **확장성 (Extensibility)**, **재사용성 (Reusability)**, **안정성 (Robustness)** 확보를 위해 다음과 같은
주요 원칙과 패턴을 적용했습니다.

* **전략 패턴 (Strategy Pattern)으로 개방-폐쇄 원칙 (Open-Closed Principle, OCP) 준수**  
  복합 검색 기능을 전략 패턴 기반으로 설계해, 새로운 검색 전략을 추가할 때 기존 코드를 수정하지 않고 구현체만 추가하도록 구성했습니다. 이를 통해 다양한 검색 요구사항에도 유연하게 대응할 수 있습니다.


* **포트-어댑터 패턴 (Port-Adapter Pattern)으로 의존성 역전 (Dependency Inversion Principle, DIP) 준수**  
  도메인과 애플리케이션 계층이 외부 시스템에 의존하지 않도록 포트 인터페이스를 정의하고, 인프라스트럭처 계층에서 이를 구현했습니다. 이를 통해 도메인 로직이 기술적 세부사항에 영향을 받지 않도록 했습니다.


* **유스케이스 중심 분리로 단일 책임 원칙 (Single Responsibility Principle, SRP) 준수**  
  각 유스케이스를 별도의 클래스로 분리해, 각 클래스가 하나의 책임만 가지도록 했습니다. 이를 통해 수정 범위를 최소화하고 테스트 용이성을 높였습니다.


* **역할별 인터페이스 분리로 인터페이스 분리 원칙 (Interface Segregation Principle, ISP) 준수**  
  데이터 접근 포트를 세부 인터페이스로 분리해, 서비스가 필요한 메서드만 의존하도록 설계했습니다. 이를 통해 불필요한 의존성을 줄이고 테스트 용이성을 높였습니다.

자세한 내용과 적용 사례는 **[SOLID 원칙 기반 도서 검색 서비스 설계 및 구현](https://github.com/yurim0628/book-search-service/wiki/SOLID-%EC%9B%90%EC%B9%99-%EA%B8%B0%EB%B0%98-%EA%B2%80%EC%83%89-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%84%A4%EA%B3%84-%EB%B0%8F-%EA%B5%AC%ED%98%84)** 문서에서 확인할 수 있습니다.

### 클래스 다이어그램

![image](https://github.com/user-attachments/assets/62c5057f-71c3-4271-9469-1a4d0799b335)

### 도서(Book) 패키지 구조

```
book
├── application
│   ├── port
│   │   └── LoadBookPort.java
│   ├── service
│   │   └── BookQueryService.java
│   └── usecase
│       ├── LoadBookDetailUseCase.java
│       └── LoadBooksByKeywordUseCase.java
├── domain
│   ├── BasicInfo.java
│   ├── Book.java
│   └── PublicationInfo.java
├── infrastructure
│   └── persistence
│       ├── BookEntity.java
│       ├── BookJpaRepository.java
│       └── LoadBookAdapter.java
└── presentation
    └── web
        └── BookController.java
```

### 검색(Search) 패키지 구조

```
search
├── application
│   ├── port
│   │   └── BookInternalPort.java
│   ├── service
│   │   └── SearchService.java
│   └── usecase
│       └── KeywordSearchUseCase.java
├── domain
│   ├── SearchOperator.java
│   └── SearchQuery.java
├── infrastructure
│   └── internal
│       └── BookInternalAdapter.java
└── presentation
    └── web
        └── PopularKeywordController.java
```

### 계층별 역할 및 책임

| 계층 (Layer)                        | 역할 및 책임                                                                     |
|-----------------------------------|-----------------------------------------------------------------------------|
| **Domain**                        | - 핵심 비즈니스 모델과 규칙 포함<br>- 도메인 엔티티, 값 객체, 도메인 예외 구현<br>- 외부 의존성 없이 비즈니스 로직 전담 |
| **Application - UseCase**         | - 단일 비즈니스 기능 구현 (예: 도서 조회, 검색)<br>- 도메인 호출 및 업무 처리 집중                       |
| **Application - Service**         | - 여러 UseCase 조합 및 비즈니스 흐름 관리<br> - Presentation과 UseCase 중재자 역할 수행          |
| **Application - Port (Outbound)** | - 외부 시스템과의 의존성 추상화 <br>- 도메인 및 애플리케이션이 구체 구현에 의존하지 않도록 분리                   |
| **Infrastructure**                | - 외부 시스템 구체 구현체 포함 <br>- Port 인터페이스 구현으로 기술 상세 분리                           |
| **Presentation**                  | - 사용자 요청 처리 및 응답 반환<br>- 웹 컨트롤러, API 엔드포인트 포함<br>- 입력 검증 및 예외 처리 담당         |

## 🔑 주요 해결 과제 및 고려 사항

### 검색 요청 수행 시간 측정을 위한 AOP 적용

* 검색 요청 수행 시간 측정을 서비스 로직에 직접 구현하면 핵심 기능과 관심사가 뒤섞이는 문제가 발생합니다.
* AOP를 적용하여 메서드 실행 시간을 기록하고, 그 결과를 메타데이터로 포함하도록 함으로써 관심사를 분리했습니다.
* 자세한
  내용은 [검색 서비스 수행 시간 측정과 핵심 로직 분리를 위한 AOP 적용 사례](https://github.com/yurim0628/book-search-service/wiki/%EA%B2%80%EC%83%89-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%88%98%ED%96%89-%EC%8B%9C%EA%B0%84-%EC%B8%A1%EC%A0%95%EA%B3%BC-%ED%95%B5%EC%8B%AC-%EB%A1%9C%EC%A7%81-%EB%B6%84%EB%A6%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-AOP-%EC%A0%81%EC%9A%A9-%EC%82%AC%EB%A1%80)
  문서에서 확인할 수 있습니다.

### 검색 성능 향상을 위한 비동기 처리

* 인기 검색어 집계는 검색 요청과 별개로 처리되어야 하므로, 비동기 방식으로 구현했습니다.
* 이를 통해 검색 요청의 응답 속도를 개선하고, 집계 작업이 검색 성능에 영향을 주지 않도록 했습니다.
* 자세한
  내용은 [검색 성능 저하 방지를 위한 비동기 인기 검색어 집계 처리](https://github.com/yurim0628/book-search-service/wiki/%EA%B2%80%EC%83%89-%EB%B6%80%ED%95%98-%EC%B5%9C%EC%86%8C%ED%99%94%EB%A5%BC-%EC%9C%84%ED%95%9C-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%9D%B8%EA%B8%B0-%EA%B2%80%EC%83%89%EC%96%B4-%EC%A7%91%EA%B3%84-%EC%B2%98%EB%A6%AC)
  문서에서 확인할 수 있습니다.

### 인기 검색어 조회 성능 향상을 위한 인메모리 캐시 도입

* 인기 검색어는 메인 페이지에 자주 노출되어 DB 반복 조회 시 부하가 발생할 수 있습니다.
* 인메모리 캐시를 사용하면 빠른 조회가 가능하고, 변경이 적어 캐시 효율이 높습니다.
* 과제 환경에서는 로컬 캐시를 활용해 검색어 집계와 조회를 처리했으며, 추후 외부 캐시로 쉽게 교체할 수 있도록 확장성을 확보했습니다.
