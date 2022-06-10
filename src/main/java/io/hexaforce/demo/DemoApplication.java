package io.hexaforce.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import io.hexaforce.demo.domain.PhoneCallHistory;
import io.hexaforce.demo.websocket.WebSocketHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import io.swagger.v3.oas.annotations.servers.Server;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Info;

//@OpenAPIDefinition(servers = { @Server(url = "https://mimamori-batch.ai-telephone.me.uk"), @Server(url = "http://localhost:8080") }, //
//info = @Info(title = "架電バッチ処理API", version = "1.0", description = "APIによる架電バッチ起動、WebSocketによる状況のリアルタイム取得"))


@EnableAsync
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {
		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(webSocket(), "/ws/{userName}").setAllowedOrigins("*");
		}
	}

	@Bean
	public WebSocketHandler webSocket() {
		return new WebSocketHandler();
	}

	@Configuration
	public class SpringRestConfiguration implements RepositoryRestConfigurer {
		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
			config.setDefaultMediaType(MediaType.APPLICATION_JSON);
			config.setEnableEnumTranslation(true);
//			config.getExposureConfiguration().disablePutForCreation();
			config.useHalAsDefaultJsonMediaType(false);
//			config.exposeRepositoryMethodsByDefault()
			config.exposeIdsFor(PhoneCallHistory.class);
		}

//		@Override
//		public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
//			objectMapper.registerModule(new SimpleModule("MyCustomModule") {
//				private static final long serialVersionUID = 1L;
//				@Override
//				public void setupModule(SetupContext context) {
//					AbstractTypeResolver resolver = new SimpleAbstractTypeResolver()
//							.addMapping(MyInterface.class, MyInterfaceImpl.class));
//					context.addAbstractTypeResolver(resolver);
//				}
//			});
//		}

	}

	@Configuration
	public class CorsFilter extends OncePerRequestFilter {

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
			response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addIntHeader("Access-Control-Max-Age", 10);
			filterChain.doFilter(request, response);
		}

	}

}
