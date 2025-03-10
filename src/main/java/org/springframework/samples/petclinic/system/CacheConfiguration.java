package org.springframework.samples.petclinic.system;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.PseudoCacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Cache configuration intended for caches providing the JCache API. This configuration
 * creates the used cache for the application and enables statistics that become
 * accessible via JMX.
 */
@Configuration
class CacheConfiguration {

	@Configuration
	@Profile("aspectj")
	@EnableCaching(mode = AdviceMode.ASPECTJ)
	protected static class AspectCacheConfiguration {
	}

	@Configuration
	@Profile("proxy")
	@EnableCaching(mode = AdviceMode.PROXY)
	protected static class SpringCacheConfiguration {
	}

	@Configuration
	@Profile("pseudo")
	@Import(PseudoCacheConfiguration.class)
	protected static class OtherCacheConfiguration {
	}

	@Configuration
	@Profile("manual")
	protected static class ManualCacheConfiguration {

		@Bean
		public CacheManager cacheManager() {
			return new ConcurrentMapCacheManager("vets");
		}
	}

	@Configuration
	@Profile("jcache")
	protected static class JCacheConfiguration {

		@Bean
		public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
			return cm -> {
				cm.createCache("vets", cacheConfiguration());
			};
		}

		/**
		 * Create a simple configuration that enable statistics via the JCache
		 * programmatic configuration API.
		 * <p>
		 * Within the configuration object that is provided by the JCache API standard,
		 * there is only a very limited set of configuration options. The really relevant
		 * configuration options (like the size limit) must be set via a configuration
		 * mechanism that is provided by the selected JCache implementation.
		 */
		private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
			return new MutableConfiguration<>().setStatisticsEnabled(true);
		}

	}

}
