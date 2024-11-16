/*
 * Copyright (c) 2022-2024 KCloud-Platform-IoT Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author laokou
 */
public class Test {

	public static void main(String[] args) {
		JdkProxy jdkProxy = new JdkProxy(new RealHelloService());
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
		HelloService h = (HelloService)Proxy.newProxyInstance(classLoader, new Class[]{HelloService.class}, jdkProxy);
		System.out.println(h.say());
	}

	interface HelloService {
		String say();
	}

	public static class RealHelloService {

		public String invoke() {
			return "i am a java boy";
		}

	}

	static class JdkProxy implements InvocationHandler {

		private final RealHelloService realHelloService;

		JdkProxy(RealHelloService realHelloService) {
			this.realHelloService = realHelloService;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return realHelloService.invoke();
		}

	}

}
