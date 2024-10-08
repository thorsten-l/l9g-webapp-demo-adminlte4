/*
 * Copyright 2024 Thorsten Ludewig <t.ludewig@gmail.com>.
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
package l9g.webapp.demo.adminlte4.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class ClientSecurityConfig
{
  private final CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
  {
    log.debug("filterChain");

    http.authorizeHttpRequests(
      authorize -> authorize
        .requestMatchers("/api/v1/fail/403").denyAll()
        .requestMatchers(
          "/error/**", "/css/**", "/js/**", "/assets/*",
          "/webjars/**", "/login", "/login/**", "/actuator", "/actuator/**"
        ).permitAll()
        .anyRequest().authenticated())
      .formLogin(
        form -> form
          .loginPage("/login")
          .permitAll()
      )
      .logout(
        logout -> logout
          .logoutUrl("/logout")
          .logoutSuccessUrl("/login?logout")
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID")
          .permitAll()
      )
      .userDetailsService(userDetailsService);

    return http.build();
  }
}
