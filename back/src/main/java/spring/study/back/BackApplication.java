package spring.study.back;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Board API", version = "2.0"))
//http://localhost:8000/swagger-ui/index.html
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	@Bean
//	@ApiOperation(value = "BOARD의 CRUD를 위한 설정", notes = "Mapping 메서드들 중에 PutMapping,PatchMapping 등의 신기술 매핑을 이용하기 위해선 이 코드를 빈에 넣어줘야한다...고 하는데 아닐거 같음")
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
		return new HiddenHttpMethodFilter();
	}
}
