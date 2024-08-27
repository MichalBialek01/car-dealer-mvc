package pl.bialek.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.bialek.ComponentScanMarker;

@Configuration
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
@Import(JPAConfiguration.class )
public class AppConfiguration {
}
