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

import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Thorsten Ludewig <t.ludewig@gmail.com>
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
  private static final long serialVersionUID = 1980587084511633413L;

  public boolean hasRole(String role)
  {
    if (role == null || role.isEmpty())
    {
      return false;
    }

    String roleWithPrefix
      = role.startsWith("ROLE_") ? role : "ROLE_" + role;

    for (GrantedAuthority authority : authorities)
    {
      if (authority.getAuthority().equals(roleWithPrefix))
      {
        return true;
      }
    }

    return false;
  }

  public boolean isAdmin()
  {
    return hasRole("ROLE_ADMIN");
  }

  private final String username;

  private final String password;

  private final String firstname;

  private final String lastname;

  private final String email;

  private final Collection<? extends GrantedAuthority> authorities;

  private final boolean accountNonExpired = true;

  private final boolean accountNonLocked = true;

  private final boolean credentialsNonExpired = true;

  private final boolean enabled = true;
}
