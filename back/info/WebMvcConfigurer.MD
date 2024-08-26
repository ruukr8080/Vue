# WebMvcConfigurer interface
스프링이 제공하는 interface다.

## WebMvcConfigurer 을 쓰는 이유.

- (보일러플레이트)[최소한의 변경으로 여러곳에서 재사용되며, 반복적으로 비슷한 형태를 띄는 코드] 코드 없이 요구사항에 맞게 프레임워크를 조정할 수 있게 해줌.
- 특정한 스프링 클래스를 구현하거나 상속할 필요 없이 MVC 구성정보를 제어할 수 있게 해줌.
- @EnableWebMvc를 통해 활성화된 WebMVC App의 구성정보를 커스터마이징 하는 것을 돕기도 함.
- 스프링부트에 있는 기본설정이 맘에 안들거나 스프링에 추가적인 설정을 해줘야할 떄 쓴다.

이 전엔 @EnableWebMvc만을 이용해서 viewResolver에 자동 등록되는거에 만족했는데,
여기서 추가적인 세팅을 하려면 customViewResolver()와 같은 Bean을 직접 정의해야 한다.
근데 WebMvcConfigurer 를 사용하면 @EnableWebMvc가 자동으로 세팅해주는 설정에 더해서 개발자가 원하는 설정을
손쉽게 추가할 수 있다. 
즉,  Override가 가능하다!

---

## 일반적인 사용 용도.

### view resolver
- 컨트롤러에서 반환되는 뷰 이름이 실제 뷰 오브젝트로 변환되는 과정을 정의할 수 있다.
- 뷰가 리졸브드 되는 방식을 커스텀한 로직으로 변경 가능.
- ViewController만 일괄로 추가할 때
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/join").setViewName("join");
		registry.addViewController("/login").setViewName("login");
	}
}
```

### 인터셉터 등록
- request를 핸들링 하기 전 후로 처리할 작업이 있을때 이를 위한 커스텀 인터셉터를 구성하는 용도로 쓸 수 있다.
- ex) 글로벌 인터셉터를 등록하여 매번 로깅이나 보안검사를 할 수 있다. (시큐리티로 대체가능)


### resource handling
- html,js,image 와 같은 정적 리소스들을 어떻게 제어할지 구성할 수 있다.
- resource 경로를 설정해준다던가.
```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/");
}
```
### message 반환
- HTTP 메세지 컨버터를 추가하여 JSON,XML 과 같은 형식의 데이터를 읽고 쓸 수 있는 메세지 변환기를 등록할 수 있다.
```java
@Override
public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(new MyCustomMessageConverter());
}
```
### Path Matching과 Content Negotiation
- Path Matching rules 와 Content Negotiation options 를 구성할 수 있다.
```java
@Override
public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseTrailingSlashMatch(true);
}

@Override
public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false)
              .favorParameter(true)
              .parameterName("mediaType")
              .ignoreAcceptHeader(true)
              .useRegisteredExtensionsOnly(false)
              .defaultContentType(MediaType.APPLICATION_JSON)
              .mediaType("xml", MediaType.APPLICATION_XML);
}
```
### CORS Configuration
- 다른 도메인에 의해 접근되어야 할 때 필요한 CORS 규칙을 정의할 수 있다.
```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://allowed-origin.com")
            .allowedMethods("GET", "POST")
            .allowCredentials(true);
}
```







