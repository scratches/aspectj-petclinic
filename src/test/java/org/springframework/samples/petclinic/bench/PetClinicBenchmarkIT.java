/*
LazyInitBeanFactoryPostProcessor.java * Copyright 2016-2017 the original author or authors.
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
package org.springframework.samples.petclinic.bench;

import org.junit.jupiter.api.Disabled;
import org.openjdk.jmh.annotations.AuxCounters;
import org.openjdk.jmh.annotations.AuxCounters.Type;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import jmh.mbr.junit5.Microbenchmark;

@Measurement(iterations = 5, time = 1)
@Warmup(iterations = 1, time = 1)
@Fork(value = 2, warmups = 0)
@BenchmarkMode(Mode.AverageTime)
@Microbenchmark
public class PetClinicBenchmarkIT {

	@Disabled
	@Benchmark
	public void nocache(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=tx");
		state.run();
	}

	@Disabled
	@Benchmark
	public void noaspects(MainState state) throws Exception {
		state.run();
	}

	@Disabled
	@Benchmark
	public void manual(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=manual,tx,proxy");
		state.run();
	}

	@Benchmark
	public void pseudo(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=pseudo,tx,proxy",
				"-Dspring.cache.type=simple");
		state.run();
	}

	@Disabled
	@Benchmark
	public void simple(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=cache,tx,proxy",
				"-Dspring.cache.type=simple");
		state.run();
	}

	@Disabled
	@Benchmark
	public void jcache(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=cache,tx,proxy,jcache");
		state.run();
	}

	@Disabled
	@Benchmark
	public void aspectj(MainState state) throws Exception {
		state.addArgs("-Dspring.profiles.active=cache,tx,aspectj,jcache");
		state.addArgs("-javaagent:" + System.getProperty("user.home")
				+ "/.m2/repository/org/aspectj/aspectjweaver/1.9.2/aspectjweaver-1.9.2.jar");
		state.run();
	}

	@State(Scope.Thread)
	@AuxCounters(Type.EVENTS)
	public static class MainState extends ProcessLauncherState {

		public MainState() {
			super("target", "--server.port=0");
		}

		@Override
		public int getClasses() {
			return super.getClasses();
		}

		@Override
		public int getBeans() {
			return super.getBeans();
		}

		@TearDown(Level.Invocation)
		public void stop() throws Exception {
			super.after();
		}

		@Setup(Level.Trial)
		public void start() throws Exception {
			super.before();
		}
	}

}
