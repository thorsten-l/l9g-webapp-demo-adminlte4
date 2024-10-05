/*
 * Copyright 2024 Thorsten Ludewig <t.ludewig@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thorsten Ludewig <t.ludewig@gmail.com>
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService
{

  public CustomUserDetailsService()
  {
    CustomUserDetails user = new CustomUserDetails(
      "user",
      "{noop}user",
      "User",
      "Userman",
      "user@the.net",
      Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
    );

    CustomUserDetails admin = new CustomUserDetails(
      "admin",
      "{noop}admin",
      "Admin",
      "Administrator",
      "admin@the.net",
      Arrays.asList(
        new SimpleGrantedAuthority("ROLE_ADMIN"),
        new SimpleGrantedAuthority("ROLE_USER")
      ));
    
    users.put(user.getUsername(), user);
    users.put(admin.getUsername(), admin);
  }

  @Override
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException
  {
    CustomUserDetails user = users.get(username);
    if (user == null)
    {
      throw new UsernameNotFoundException("User not found: " + username);
    }
    return user;
  }

  private final Map<String, CustomUserDetails> users = new HashMap<>();
}
