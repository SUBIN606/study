> `ResponseEntity`를 사용할지, 그냥 반환 값 POJO를 반환할지에 대한 이야기가 나왔다. 개인적으로 `ResponseEntity`를 사용하면 가독성이 떨어진다고 생각하기 때문에 POJO를 반환하는 게 좋겠다고 생각했지만 `ResponseEntity`는 왜, 언제 사용하는지에 대해서 모르고 있다는 걸 알게됐다.

# ResponseEntity
`ResponseEntity`는 `@ResponseBody`같은 것이지만 더 유연하게 상태와 헤더를 변경할 수 있다.
> `@ResponseBody`애노테이션은 `HttpMessageConveter`를 통해 응답 본문의 값을 직렬화 할 수 있게 한다. 해당 애노테이션은 메서드 혹은 클래스 수준에 사용할 수 있다.

즉, 상태를 조건에 따라 변경해야 한다거나 헤더에 값을 추가해 반환하고 싶은 경우는 `ResponseEntity`를 사용할 수 있다.

만약 반환 값이 없는 `void` 메서드를 만들면서 특정 상태 코드를 설정하고 싶다면 `@ResponseStatus` 애노테이션으로 상태 코드를 지정할 수 있다.

## ResponseEntity 기본 사용법
``` java
// 200 OK with empty body
ResponseEntity.ok()

// 200 OK with body
ResponseEntity.ok(body);
// or
ResponseEntity.of(body);  // 만약 Optional.Empty()를 반환하면 404가 되므로 주의

// status code with body
ResponseEntity.status(HttpStatus.OK).body(body);  // 원하는 상태 코드 설정 가능

// 생성자로 응답값, 헤더, 상태코드 초기화
new ResponseEntity(body, headers, status);
```

## 결론
개인적인 생각으로는 여전히 `@ResponseStatus`가 더 편리한 것 같다. 

메서드 선언부만 봐도 이 컨트롤러메서드가 무슨 일을 하는지 명확히 알 수 있기 때문이다. 나는 보통 `ResponseEntity`를 사용하기 보다는 아래와 같이 상태 코드를 애노테이션으로 명시한다. 반환부를 보지 않아도 다 파악할 수 있다.
``` java
@ResponseStatus(HttpStatus.CREATED)
@PostMapping("/students")
public Long createStudent(@ResponseBody Student student) {
  return studentCreator.create(student).getId();
}
```
그리고 상태 코드를 명시하지 않으면 기본적으로 200 OK 코드가 응답되기 때문에 일반 컨트롤러에서는 특정 메서드에서만 애노테이션을 사용하면 된다.

하지만 헤더에 여러 값을 세팅해야 한다거나 동적으로 상태 코드를 변경해야 하는 상황이 있다면 `ResponseEntity`를 사용하는게 맞다. `ResponseEntity<?>`만 허용하지 않으면 된다고 생각한다.

---

See also
- [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- [Spring Docs](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-return-types)
- [MappingJackson2HttpMessageConverter](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/json/MappingJackson2HttpMessageConverter.html)
