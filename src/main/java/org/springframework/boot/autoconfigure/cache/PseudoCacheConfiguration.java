/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.boot.autoconfigure.cache;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @author Dave Syer
 *
 */
@Configuration
@Profile("pseudo")
@Import({ GenericCacheConfiguration.class, JCacheCacheConfiguration.class,
		EhCacheCacheConfiguration.class, HazelcastCacheConfiguration.class,
		InfinispanCacheConfiguration.class, CouchbaseCacheConfiguration.class,
		RedisCacheConfiguration.class, CaffeineCacheConfiguration.class,
		SimpleCacheConfiguration.class, NoOpCacheConfiguration.class })
@EnableConfigurationProperties(CacheProperties.class)
public class PseudoCacheConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public CacheManagerCustomizers cacheManagerCustomizers(
			ObjectProvider<CacheManagerCustomizer<?>> customizers) {
		return new CacheManagerCustomizers(
				customizers.orderedStream().collect(Collectors.toList()));
	}

}
