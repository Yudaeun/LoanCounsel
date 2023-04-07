# 대출 상담 API 구현(2023.01.24~2023.02.09)

---

> 금융에 대해 관심과 흥미를 가지게 된 이후, BackEnd 개발자로서 금융 도메인을 조금이라도 잘 이해하기 위한 개발 경험을 쌓고 싶어 진행했습니다. 최근 가장 핫한 이슈 중 하나인 ‘금융 상담’, 그 중에서도 대출 상담과 관련된 API를 만들어 보고자 했습니다. 
금융 도메인에 대해 잘 이해하기 위한 목적과 인터넷을 서핑하고 공부하며 주워 들었던 ‘빌더 패턴’과 ‘ModelMapper’, ssafy에서 배운 추상 클래스를 적용해보기 위해 시작했습니다. 짧은 기간이었지만, 빌더 패턴이 수시로 데이터가 변하는 상황에선 오히려 더 복잡하고, 빌더 패턴의 장점인 ‘객체의 일관성’의 장점을 이용할 수 없다는 것을 느낄 수 있었습니다.

최근 모든 업종에 DX가 이루어지고 있는데 대출 상담 또한 예외가 아닙니다.
비대면으로 상담을 받기 위해선 고객이 상담을 신청하는 과정을 거쳐야 합니다.
그러면 서버에 들어온 상담 요청 데이터를 통해 상담원이 고객과 화상 연결을 하는 과정을 거친다거나, 혹은 상담 요청 데이터만으로 대출 심사를 진행할 수 있을 것입니다.
그래서 대출 과정의 가장 첫 번째가 되는 대출 상담을 신청할 수 있는 서비스 로직을 만들어보고자 본 프로젝트를 했습니다.
> 

## 🐻 대출 상담 REST API

---

대출 상담 신청(생성), 상담 데이터 삭제, 상담 데이터 수정, 상담 데이터 조회(CRUD) BackEnd 로직 구현

## 개발환경

---

- Intellj
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- ModelMapper

## 개발 언어

---

- Java 8

## 프로젝트 구조

---

- Domain: 애플리케이션에서 사용되는 데이터 구조(엔티티)를 나타내며, 데이터베이스 테이블과 매핑된다.
- Controller: 클라이언트에서 들어온 요청을 받아 서비스 레이어로 보내 비즈니스 로직을 처리한 후, 응답 데이터를 클라이언트로 보내준다.
- DTO: 클라이언트로부터 전달받은 데이터를 저장하고 전달한다.
- Repository(DAO): 엔티티에서 생성된 DB에 접근한다.
- Service: 비즈니스 로직을 수행한다.
- Exception: 예외 처리를 수행한다.
- Config: ModelMapper 설정 레이어

![image](https://user-images.githubusercontent.com/54846663/230574542-6abb0d42-60e2-4689-aea4-cf595cf31536.png)
---

## 테이블 정의

| counsel_id | Long(BIGINT) | Soft Delete | PK |
| --- | --- | --- | --- |
| created_date | datetime | not null | 생성일자 |
| is_deleted | bit | not null | 이용가능여부 |
| updated_date | datetime |  | 수정일자 |
| address | varchar(50) |  | 주소 |
| address_detail | varchar(50) |  | 주소 상세 |
| applied_date | datetime |  | 신청일자 |
| phone_num | varchar(13) |  | 전화번호 |
| email | carchar(50) |  | 책임자 이메일 |
| memo | text |  | 상담 메모 |
| name | varchar(12) |  | 상담자 이름 |
| zip_code | varchar(5) |  | 우편번호 |
| is_work | bit |  | 직장여부 |
| salary | int |  | 연봉 |
| age | int |  | 나이 |

**SOFT Delete 방식 사용:** 삭제를 하더라도 실제 DB에 물리적으로 삭제를 처리하지 않도록 한다. 지금까지 hard delete를 이용해서 삭제를 하면 실제로 데이터가 바로 삭제되기 때문에 flag를 통해 삭제 처리를 했는지 안했는지를 둬서 데이터를 다시 살려야 하는 경우(고객이 마음을 바꾼경우) 등에서 데이터가 아예 사라지면 곤란하기 때문에 soft delete를 사용

<aside>
🐻 createdDate, updateDate, isDeleted는 서비스가 있다면, 모든 엔티티 클래스에서 공통적으로 소유할 수 있을 것이다. 그래서 따로 분리하여 `BaseEntity` 클래스에 만들었다.

</aside>

```jsx
@Where(clause="is_deleted=false") //soft delete를 사용해서 삭제 여부를 제어
public class Application extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;

    @Column(columnDefinition = "varchar(12) DEFAULT NULL COMMENT '신청자'")
    private String name;
...생략
```

## 대출 상담 등록, 조회, 수정, 삭제 구현(CRUD)

1. 상담 받길 원하는 사람이 상담 내용 데이터를 작성한다.
2. 클라이언트로부터 데이터를 받아와서 그 값 그대로 반환한다.
3. 서비스 패키지에 구현한다.
4. 요청된 데이터를 받고 응답을 보낼 DTO를 만든다.
5. 엔티티를 DB에 저장할 수 있는 repository를 만든다.
6. 응답을 받아서 데이터를 내보내는 controller를 만든다.

```jsx
@Override
    public Response create(Request request){
        Counsel counsel=modelMapper.map(request,Counsel.class);//요청값이 Counsel 엔티티로 매핑
        counsel.setAppliedDate(LocalDateTime.now());
        Counsel created=counselRepository.save(counsel);
        System.out.println(created.getAddress()+created.getEmail());
        return modelMapper.map(created,Response.class);
    }
```

- `create()`: 대출 상담 요청 데이터가 들어오면 객체를 생성하고 엔티티를 매핑한다.
- CounselService 인터페이스 생성 후, 이를 CounselServiceImpl 클래스가 상속 받아 서비스 로직 구현

### 엔티티에 Builder 패턴 적용

```jsx
				Counsel counsel=modelMapper.map(request, Counsel.class); //요청값이 Counsel 엔티티로 매핑
        counsel.builder().
                appliedDate(LocalDateTime.now()).
                build();
```

금융 데이터는 무결성, 일관성이 중요할 것이라 생각해 Builder 패턴을 적용해서 객체의 일관성을 보장하기로 했다. 그래서 `create()`를 구현할 때 초기에는 builder 패턴을 이용해 객체를 생성했다.
하지만 update 기능을 구현할 때 Builder 패턴으로 객체를 수정하면 Builder 패턴의 사용 의미를 잃는다는 것을 깨달았고, 오히려 코드 짜기 복잡해진다는 것을 느낄 수 있었다. 그래서 다시 getter, setter를 이용한 생성방식으로 변경했다.

Builder 패턴의 장점을 살리기 위해선 새로운 객체를 생성해서 기존 객체가 변하지 않도록 구현해야 할 것 같다는 고민을 하게 되었다.

![image](https://user-images.githubusercontent.com/54846663/230574492-6158e456-95ce-4be0-bb61-4cff8628efe8.png)
그와 별개로, Builder 패턴을 적용할 때 오류가 발생해서 꽤나 애를 먹었었다. 알고보니, 상속 관계가 있는 Entity에 Builder를 적용해서 발생한 문제였다. 부모인 BaseEntity와 자식인 Counsel 모두 @Builder를 사용해서 오류가 발생했다. 자식을 통해 부모 멤버 변수를 초기화하지 못하는 문제- 즉, 객체지향 원칙 중 리스코프치환원칙에 위배되어 발생한 문제이기 때문에 `@SuperBuilder` 을 통해 부모 객체의 필드값도 지정하도록 만들어야 한다.

### 대출 상담 데이터 생성 POST Method

```jsx
		@Description("대출 상담 정보 입력")
    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request){
        return ok(counselService.create(request));
    }
```

클라이언트에서 요청이 들어오면 Service 레이어의 `get()`을 호출해 서비스 로직을 모두 수행하며 상담 정보를 저장한다. 성공적으로 객체를 생성하면 `ResponseDTO`의 `ok()`를 통해 성공 상태 코드와 함께 반환한다. 실패하면 사용자 예외처리에 의해 실패 코드를 반환한다.

```jsx
			
			public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data);
    } //ResponseDTO 클래스

		public static ResultObject getSuccess(){
        return new ResultObject(ResultType.SUCCESS);
    } //ResultObject 클래스
		
		public enum ResultType {
    SUCCESS("0000","success"),
        SYSTEM_ERROR("9000","system error");
    private final String code;
    private final String desc;
		} //예외처리 ResultType 클래스
```

ssafy 교육을 받으며 JAVA와 OOP에 대해 중점적으로 교육을 받으며 예외처리의 중요성을 느낄 수 있었다. 또한, *‘객체지향의 사실과 오해’* 라는 책을 읽으면서 행위에 따른 클래스 분리의 중요성도 느꼈다. 그래서 이러한 부분에 집중해서 작성하기 위해 노력했다.

### 대출상담 데이터 조회 구현 후 테스트 코드 작성

```jsx
		@Test
    void 상담엔티티가_존재할때_상담아이디가있으면_true(){
        Long findId=1L;

        Counsel entity=Counsel.builder()
                .counselId(1L)
                .build();
        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual=counselService.get(findId);
        assertThat(actual.getCounselId()).isSameAs(findId);
    }

		@Test
    void get_없는_엔티티를_요청했을때_예외반환(){
        Long findId=2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));
        Assertions.assertThrows(BaseException.class,()->counselService.get(findId));
    }
```

![image](https://user-images.githubusercontent.com/54846663/230574432-55eb28e6-35a7-43ed-bea6-4c4769b5cc25.png)
CREATE 기능 구현 이후, READ, UPDATE, DELETE 기능도 비슷한 방식으로 구현했다.

다만, 데이터를 삭제할 때 SOFT Delete 방식을 사용하기로 했기 때문에 Delete 기능을 구현할 때는 삭제 요청이 들어오면 isDeleted를 true로 변경하여 삭제처리가 됐다는 의미로 변경해준다. 그러면 View에서는 isDeleted가 false인 데이터만 조회되도록 한다.

```jsx
@Override
    public void delete(Long counselId) {
        Counsel counsel=counselRepository.findById(counselId).orElseThrow(()->{
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        counsel.setIsDeleted(true);
        counselRepository.save(counsel);
    }
```

### 추상 클래스 Controller-AbstController 구현

요청에 대한 응답값을 모두 통일하기 위해 추상 클래스 컨트롤러를 만들었다. 이를 상속받는 CounselController는 요청에 대한 응답값을 통일하고 코드 중복을 줄일 수 있게 된다. 그리고 AbstController에서 정의한 형태로 응답값을 반환해서 CounselController는 비즈니스 로직에만 집중할 수 있다.

```jsx
public abstract class AbstController { //요청에 대한 응답값을 통일하기 위함
    protected <T> ResponseDTO<T> ok() {
        return ok(null, ResultObject.getSuccess());
    }

    protected <T> ResponseDTO<T> ok(T data) {
        return ok(data, ResultObject.getSuccess());
    }

    protected <T> ResponseDTO<T> ok(T data, ResultObject result) {
        ResponseDTO<T> obj = new ResponseDTO<>();
        obj.setResult(result);
        obj.setData(data);

        return obj;
    }

}
```

---
