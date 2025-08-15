# 📚 도서 검색 시스템
도서 검색 시스템을 구축하기 위한 것으로, 사용자가 다양한 키워드를 통해 도서를 검색할 수 있는 기능을 제공합니다.

##  기능 개요 및 핵심 프로세스

### 서비스 기능
* **도서 상세 조회**: 식별자를 통해 도서의 상세 정보를 조회합니다.
* **단순 검색**: 사용자가 입력한 단일 키워드를 기반으로 도서를 검색합니다.
* **복합 검색**: 사용자가 입력한 두 개의 키워드를 기반으로 도서를 검색합니다. 키워드 간의 논리 연산자(AND, OR)를 선택할 수 있습니다.

API 명세서는 [여기](https://documenter.getpostman.com/view/30686816/2sB3BGJVqC#2086d0bc-eddb-420a-9e81-d77e143ddde4)에서 확인할 수 있습니다.

### 복합 검색 요청 흐름도
![](https://github.com/user-attachments/assets/23d1fff0-0458-471f-a8c7-e4027ed53ddf)

## 도메인 모델 

### 📖 도서(Book) 도메인

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

### 🔍 검색 쿼리(SearchQuery) 도메인

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


## 아키텍처 결정 사항

### 설계 원칙 및 아키텍처 개요

본 프로젝트는 유지보수성과 확장성을 극대화하기 위해 다음과 같은 주요 원칙과 패턴을 적용했습니다.  

- 전략 패턴 (Strategy Pattern)으로 개방-폐쇄 원칙 (Open-Closed Principle, OCP) 준수
- 단일 책임 원칙 (Single Responsibility Principle, SRP) 기반 유스케이스 분리
- 포트-어댑터 패턴 (Port-Adapter Pattern)으로 의존성 역전 (Dependency Inversion Principle, DIP) 준수
- 인터페이스 분리 원칙 (Interface Segregation Principle, ISP) 기반 역할별 인터페이스 분리

자세한 내용과 적용 사례는 [설계 원칙 문서](docs/design-principles.md)에서 확인할 수 있습니다.

### 클래스 다이어그램 
![](https://github.com/user-attachments/assets/250243a5-c055-453a-ac34-a67ef0197014)

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
│       ├── LoadBooksByAnyKeywordUseCase.java
│       ├── LoadBooksByKeywordExcludingUseCase.java
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

### 계층별 역할 및 책임

| 계층 (Layer)                | 역할 및 책임                                                                     |
|---------------------------|-----------------------------------------------------------------------------|
| **Domain (도메인)**          | - 핵심 비즈니스 모델과 규칙 포함<br>- 도메인 엔티티, 값 객체, 도메인 예외 구현<br>- 외부 의존성 없이 비즈니스 로직 전담 |
| **Application - UseCase** | - 단일 비즈니스 기능 구현 (예: 도서 조회, 검색)<br>- 도메인 호출 및 업무 처리 집중                       |
| **Application - Service** | - 여러 UseCase 조합 및 비즈니스 흐름 관리<br> - Presentation과 UseCase 중재자 역할 수행          |
| **Port (Outbound)**       | - 외부 시스템과의 의존성 추상화 <br>- 도메인 및 애플리케이션이 구체 구현에 의존하지 않도록 분리                   |
| **Infrastructure**        | - 외부 시스템 구체 구현체 포함 <br>- Port 인터페이스 구현으로 기술 상세 분리                           |
| **Presentation**          | - 사용자 요청 처리 및 응답 반환<br>- 웹 컨트롤러, API 엔드포인트 포함<br>- 입력 검증 및 예외 처리 담당         |


## 기술 스택 및 사용 이유
| 기술 스택       | 버전     | 사용 이유 |
|----------------|---------|------------------------------------------------------|
| Java           | 21      | 최신 언어 기능 및 성능 개선, 장기 지원(LTS)           |
| Spring Boot    | 3.5.4   | 빠른 개발, 의존성 관리, REST API 구축에 최적화        |
| MySQL          | 8.2     | 안정적이고 확장성 있는 관계형 데이터베이스            |
| JUnit          | 5.10.0  | 단위 테스트 및 자동화 테스트 지원                    |
| Docker         | 27.4.0  | 환경 일관성, 배포 자동화, 컨테이너 기반 개발/운영      |



## 실행 방법

#### 1. Docker 이미지 빌드

```bash
docker build -t book-image -f ./Dockerfile .
```

#### 2. Docker Compose로 컨테이너 실행

```bash
docker compose up
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

## 문제 해결 과정에서의 주요 고려사항
### 검색 요청 수행 시간 측정을 위한 AOP 적용으로 관심사 분리
* 검색 요청 수행 시간 측정을 서비스 로직에 직접 구현하면 핵심 기능과 관심사가 뒤섞이는 문제가 발생합니다.
* AOP를 적용하여 메서드 실행 시간을 기록하고, 그 결과를 메타데이터로 포함하도록 함으로써 관심사를 분리했습니다.

### 인기 검색어 집계를 비동기로 처리해 응답 속도 향상
* 인기 검색어 집계는 검색 요청과 별개로 처리되어야 하므로, 비동기 방식으로 구현했습니다.
* 이를 통해 검색 요청의 응답 속도를 개선하고, 집계 작업이 검색 성능에 영향을 주지 않도록 했습니다.

### 캐시로 인기 검색어를 관리해 조회 속도 향상
* 인기 검색어는 메인 페이지에 자주 노출되어 DB 반복 조회 시 부하가 발생할 수 있습니다.
* 인메모리 캐시를 사용하면 빠른 조회가 가능하고, 변경이 적어 캐시 효율이 높습니다.
* 과제 환경에서는 로컬 캐시(Map + PriorityQueue)를 활용해 검색어 집계와 조회를 효율적으로 처리했습니다.
  * 인터페이스로 분리해, 추후 Redis 등 외부 캐시로 쉽게 교체할 수 있도록 확장성을 확보했습니다.
  * 시간 조건(최근 N일 기준 등)은 생략했지만, 구조상 향후 기능 확장이 가능하도록 설계했습니다.
