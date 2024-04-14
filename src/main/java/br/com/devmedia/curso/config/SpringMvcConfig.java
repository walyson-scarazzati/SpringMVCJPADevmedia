package br.com.devmedia.curso.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import br.com.devmedia.curso.web.conversor.TipoSexoConverter;

//para dizer que essa classe é de configuração
@Configuration

public class SpringMvcConfig extends WebMvcConfigurerAdapter{
	
	// fala para o Spring que quer trabalhar com arquivos estaticos
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/resources/bootstrap/");
    }
	
	// todas as classes que você cria utilizando as anotações do Spring no topo se
	// tornam um bean e passar a ser gerenciadas
	// pelo sistema de gestão de dependências do Spring
	// quando cria um método e a precisa que esse método seja gerenciado pelo Spring
	// a gente adiciona a ele uma notação
	// que é a @Bean assim o Spring passa controlar esse método
	@Bean
	// essa classe InternalResourceViewResolver é como Spring vai saber como
	// resolver as páginas
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		// prefixo onde o Spring vai procurar as páginas
		resolver.setPrefix("/WEB-INF/views/");

		// tipo de página, ou tipo de arquivo que nós vamos utilizar como página
		resolver.setSuffix(".jsp");

		// informa qual o tipo de recurso nas páginas .jsp serão utilizados, neste caso
		// o jstlView
		// está avisando ao MVC que lá nas páginas vai trabalhar com JSTL
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new TipoSexoConverter());
	}

	// para carregar as mensagens de um arquivo de propriedades
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		return source;
	}

}
